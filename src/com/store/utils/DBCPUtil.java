package com.store.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

/**
 * 数据库操作的工具类
 * 
 * @author zhujunwei 2019年1月24日 下午3:36:21
 */
public class DBCPUtil {

	private static ThreadLocal<Connection> tl=new ThreadLocal<>();
	static DataSource dataSource = null;
	static {
		try {
			dataSource = BasicDataSourceFactory.createDataSource(getProperties());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到DataSource连接资源
	 * 
	 * @return dataSource
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 得到Connection连接
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		return dataSource.getConnection();

	}

	/**
	 * 得到配置文件
	 * 
	 * @return properties
	 */
	private static Properties getProperties() {
		Properties properties = null;
		try {
			properties = new Properties();
//			InputStream is = new FileInputStream("src//dbcp.properties");
			// 使用类加载器，去读取src底下的资源文件。 后面在servlet
			InputStream is = DBCPUtil.class.getClassLoader().getResourceAsStream("dbcp.properties");
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 释放资源
	 * 
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void release(Connection conn, Statement st, ResultSet rs) {
		closeRs(rs);
		closeSt(st);
		closeConn(conn);
	}

	public static void release(Connection conn, Statement st) {
		closeSt(st);
		closeConn(conn);
	}

	private static void closeRs(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs = null;
		}
	}

	private static void closeSt(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st = null;
		}
	}

	private static void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}

	// 开启事务
	public static void startTransaction() throws SQLException {
		getConnection().setAutoCommit(false);
	}

	/**
	 * 暂时不要用：20190310
	 * 事务提交且释放连接
	 */
	public static void commitAndClose() {
		Connection conn = null;
		try {
			conn = getConnection();
//			conn.setAutoCommit(true);
			// 事务提交
			conn.commit();
			// 关闭资源
			conn.close();
			// 解除版定
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 暂时不要用：20190310
	 * 事务回滚且释放资源
	 */
	public static void rollbackAndClose() {
		Connection conn = null;
		try {
			conn = getConnection();
			// 事务回滚
			conn.rollback();
			// 关闭资源
			conn.close();
			// 解除版定
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
