package com.fuda.dc.lhtz.client.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbClient {
	private static final Logger LOG = LoggerFactory.getLogger(DbClient.class);
	
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/stock?useUnicode=true&&characterEncoding=utf-8&autoReconnect = true";
	private static final String USER = "work";
	private static final String PASSWD = "passw0rd";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	private Connection conn = null;
	/**
	 * 私有构造函数
	 */
	private DbClient() {
		init();
	}
	
	private void init() {
		try {
			Class.forName(DRIVER); //加载mysq驱动
		} catch (ClassNotFoundException e) {
			LOG.error("驱动加载错误");
		}
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWD);
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error("数据库链接错误, 请检查url、用户、密码等！");
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				close();
			}
		}));
	}
	
	/**
	 * 获取LinkFileClient实例，单例模式
	 * 
	 * @return
	 */
	public static DbClient getInstance() {
		return DbClientInstance.INSTANCE;
	}
	
	/**
	 * 私有内部静态类，单例延迟加载
	 */
	private static class DbClientInstance {
		public static final DbClient INSTANCE = new DbClient();
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
	}
}
 