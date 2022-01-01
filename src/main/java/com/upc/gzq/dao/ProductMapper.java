package com.upc.gzq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.upc.gzq.entity.Product;
import com.upc.gzq.entity.ProductType;

@Repository
public interface ProductMapper {
	public List<Product> getProducts(@Param("index")int index,@Param("size")int size);
	public int getTotalProductNo();
	public int updateProduct(Product product);
	public int insertNewProduct(Product product);
	public Product selectProductByPid(@Param("pid")String pid);
	public List<Product> selectProductByPtype(@Param("ptype")int ptype);
	public List<Product> getAllProducts();
	
	
	public int insertNewProductType(ProductType productType);
	public int deleteProductType(ProductType productType);
	public int updateProductType(ProductType productType);
	public ProductType selectProductTypeByTid(@Param("tid") String tid);
	public ProductType selectProductTypeByTcode(@Param("tcode") Integer tcode);
	public ProductType selectProductTypeByTname(@Param("tname") String tname);
	public int getTotalTypeNum();
	public List<ProductType> selectAllTypes();
	public List<ProductType> selectProductTypeList(@Param("index")int index,@Param("size")int size);
}
