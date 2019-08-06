package com.tedu.service;

import java.util.List;
import java.util.UUID;

import com.tedu.pojo.Product;
import com.tedu.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.tedu.mapper.ProductMapper;
import org.springframework.stereotype.Service;

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

	public Product queryById(String productId) {
		//select * from t_product where product_id=#{}
		return productMapper.queryById(productId);
		//TODO 引入缓存逻辑 redis
	}
	public void saveProduct(Product product) {
		//TODO redis缓存存储数据
		//TODO 补充完成product的数据
		String productId=UUID.randomUUID().toString();
		product.setProductId(productId);
		productMapper.saveProduct(product);
	}
	public void updateProduct(Product product) {
		//TODO 缓存逻辑的数据一致性,缓存和数据库
		//TODO 考虑高并发的数据修改
		productMapper.updateProduct(product);
	}
}
