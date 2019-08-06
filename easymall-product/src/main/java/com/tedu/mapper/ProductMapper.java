package com.tedu.mapper;

import java.util.List;
import com.tedu.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProductMapper {

	@Select("select * from t_product limit #{start},#{rows}")
	List<Product> pageQuery
            (@Param("start") int start, @Param("rows") Integer rows);

	@Select("select count(product_id) from t_product")
	Integer queryCount();


	Product queryById(String productId);

	void saveProduct(Product product);
	void updateProduct(Product product);

}
