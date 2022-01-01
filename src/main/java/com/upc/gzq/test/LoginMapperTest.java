package com.upc.gzq.test;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.upc.gzq.LoggerConfig;
import com.upc.gzq.dao.LoginMapper;
import com.upc.gzq.entity.Admin;
import com.upc.gzq.utils.KeyUtil;

public class LoginMapperTest {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void getSqlSession() {
		LoggerConfig.initLog();
		InputStream resourceAsStream=null;
		try {
			resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testInsertAdmin() {
		SqlSession session = sqlSessionFactory.openSession();
		LoginMapper loginMapper = session.getMapper(LoginMapper.class);
		Admin admin = new Admin(KeyUtil.getUniqueKey(), "gzqiang", "upc", new Timestamp(System.currentTimeMillis()));
		int res= loginMapper.insertAdminByAid(admin);
		System.out.println(res);
		session.close();
	}

	@Test
	public void testGetAdminByAid() {
		SqlSession openSession = sqlSessionFactory.openSession();
		LoginMapper loginMapper = openSession.getMapper(LoginMapper.class);
		Admin admin = loginMapper.getAdminByAid("162133370817089414");
		System.out.println(admin);
		openSession.close();
	}
}
