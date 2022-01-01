package com.upc.gzq.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.upc.gzq.converter.Page;
import com.upc.gzq.dao.ProductMapper;
import com.upc.gzq.entity.Product;
import com.upc.gzq.entity.ProductType;
import com.upc.gzq.service.ProductService;
import com.upc.gzq.utils.KeyUtil;

public class ProductMapperTest {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void getSqlSession() {
		InputStream resourceAsStream=null;
		try {
			resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetProductList() {
		SqlSession session = sqlSessionFactory.openSession();
		ProductMapper productMapper = session.getMapper(ProductMapper.class);
		List<Product> products = productMapper.getProducts(0, 2);
		System.out.println(products);
		session.close();
	}
//	@Test
//	public void testGetProductPage() {
//		SqlSession session = sqlSessionFactory.openSession();
//		ProductMapper productMapper = session.getMapper(ProductMapper.class);
//		ProductService service = new ProductService();
//		service.setProductMapper(productMapper);
//		Page<Product> page = service.getProductPage("1", "4");
//		System.out.println(page.getDataList());
//		System.out.println(page.getTotalPages());
//		System.out.println(page.getTotalItems());
//		session.close();
//	}
	@Test
	public void testGetProductTypeList() {
		SqlSession session = sqlSessionFactory.openSession();
		ProductMapper productMapper = session.getMapper(ProductMapper.class);
		List<ProductType> productTypes = productMapper.selectProductTypeList(1,4);
		System.out.println(productTypes);
		session.close();
	}
	@Test
	public void testInsertNewProductType() {
		SqlSession session = sqlSessionFactory.openSession(true);
		ProductMapper productMapper = session.getMapper(ProductMapper.class);
		ProductType productType = new ProductType();
		productType.setTid(KeyUtil.getUniqueKey());
		productType.setTname("运动户外");
		productType.setTcode(2);
		productType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		int res = productMapper.insertNewProductType(productType);
		System.out.println(res);
	}

}











