package com.upc.gzq.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;

@Deprecated
public class DBUtils {
	private static DruidDataSource ds = new DruidDataSource();
	private static Properties properties = new Properties();

	public static Connection getConnection() {

		ClassLoader classLoader = DBUtils.class.getClassLoader();
		Connection connection = null;
		try {
			InputStream inputStream = classLoader.getResourceAsStream("jdbc.properties");
			properties.load(inputStream);
			ds.setUrl(properties.getProperty("url"));
			ds.setPassword(properties.getProperty("password"));
			ds.setUsername(properties.getProperty("user"));
			ds.setDriverClassName(properties.getProperty("Driver"));
			connection = ds.getConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void releaseConnection(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
