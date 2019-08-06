package com.tedu.controller;

import com.tedu.service.ProductService;
import com.tedu.pojo.Product;
import com.tedu.vo.EasyUIResult;
import com.tedu.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/product/manage")
public class ProductController {
	@Autowired
	private ProductService productService;
	//商品分页查询
	@RequestMapping("/pageManage")
	public EasyUIResult pageQuery(Integer page, Integer rows){
		//控制层不关心业务层封装数据逻辑
		EasyUIResult result=
				productService.pageQuery(page,rows);
		return result;
	}

	//单个商品查询
	@RequestMapping("/item/{productId}")
	public Product queryById(@PathVariable String
			productId){
		return productService.queryById(productId);
	}

	//新增商品数据
	//前端传递请求体参数
	//productName=wan&productPrice=5000&productCategory=shouji
	//springmvc处理参数时根据你自己接受格式转化
	@RequestMapping("/save")
	public SysResult saveProduct(Product product){
		//利用一个异常逻辑判断新增成功失败
		try{
			productService.saveProduct(product);
			return SysResult.ok();//{"status":200,"msg":"ok"}
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201,
					e.getMessage(), null);
		}
	}

	//商品的数据更新
	@RequestMapping("/update")
	public SysResult updateProdut(Product product){
		//业务层调用持久层修改数据
		try{
			productService.updateProduct(product);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);	
		}
	}
}



