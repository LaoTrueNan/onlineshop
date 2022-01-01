package com.upc.gzq.test;


import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.upc.gzq.LoggerConfig;
import com.upc.gzq.dao.LoginMapper;
import com.upc.gzq.entity.Admin;

public class AdminMybatisTest {
	
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
	public void test() {
		LoggerConfig.initLog();
		Logger logger = Logger.getLogger(AdminMybatisTest.class);
		logger.info("starting test......");
		SqlSession session = sqlSessionFactory.openSession();
		LoginMapper loginMapper = session.getMapper(LoginMapper.class);
		Admin admin = loginMapper.getAdminByAid("123498573967");
		System.out.println(admin);
	}

}
