package cn.tedu.service;

import java.util.List;
import java.util.UUID;
import cn.tedu.mapper.ProductMapper;
import cn.tedu.pojo.Product;
import cn.tedu.utils.MapperUtil;
import cn.tedu.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

@Service
public class ProductService {
	/*
	 * 根据控制层参数,到持久层获取分页查询的数据
	 */
	@Autowired
	private ProductMapper productMapper;

	public EasyUIResult pageQuery(Integer page, Integer rows) {
		//sql语句:select * from table limit #{start},#{rows}
		int start=(page-1)*rows;
		//封装EasyUIResult total select count(*)
		//List<Product>
		EasyUIResult result=new EasyUIResult();
		List<Product> pList=productMapper.pageQuery(start,rows);
		Integer total=productMapper.queryCount();
		//封装数据
		result.setRows(pList);
		result.setTotal(total);
		return result;
	}

	@Autowired
	private JedisCluster jedis;

	public Product queryById(String productId) {
		//select * from t_product where product_id=#{}
		//根据id生成一个key
		String key="product_query_id_"+productId;
		//实现缓存逻辑
		//5分钟之内发送最多3条验证码--思考题
		String lock="update_"+productId+".lock";
		if(jedis.exists(lock)){//直接获取数据库数据返回
			return productMapper.queryById(productId);
		}
		try{
			if(jedis.exists(key)){//redis存在数据
				//将缓存数据获取
				String data=jedis.get(key);
				//TODO 解析成product对象返回 data是json
				Product product= MapperUtil.MP.readValue(data, Product.class);
				return product;
			}else{//缓存没有数据,从数据库获取
				Product product=productMapper.queryById(productId);
				//将数据存储在缓存一份,供后续使用
				//转化成json字符串
				String jsonData=MapperUtil.MP.writeValueAsString(product);
				jedis.setex(key, 60*60*24*2,jsonData);
				return product;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public void saveProduct(Product product) {
		//TODO redis缓存存储数据
		//TODO 补充完成product的数据
		String productId=UUID.randomUUID().toString();
		product.setProductId(productId);
		productMapper.saveProduct(product);
	}

	//更新时对象加锁： 避免返回
	public void updateProduct(Product product) {
		//生成锁的key
		String lock="udpate_"+product.getProductId()+".lock";
		jedis.setex(lock,60*60*24*2,"");
		//删除缓存
		String key="product_query_id_"+product.getProductId();
		jedis.del(key);
		productMapper.updateProduct(product);
		jedis.del(lock);
	}

	//整合Elasticsearch后启用
	public List<Product> search(String indexName, String value) {

		return null;
	}

	public void createIndex(String indexName) {

	}
}
