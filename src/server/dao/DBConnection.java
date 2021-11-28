package server.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	/**
	 * ThreadLocal保存Connection对象
	 */
	private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

	/**
	 * 连接Connection
	 * 
	 * @return 返回Connection对象 <b>Author:</b>
	 *         <a href="https://github.com/MysticalDream" target=
	 *         "_blank">MysticalDream</a> <b>Date:</b> 2021年5月28日 下午9:38:43
	 */
	public static Connection getDBConnection() {
		Connection connection = connectionHolder.get();
		if (connection == null) {
			final InputStream proInS = DBConnection.class.getClassLoader()
					.getResourceAsStream("server\\dao\\db.properties");
			final Properties properties = new Properties();
			try {
				properties.load(proInS);
				Class.forName(properties.getProperty("driverClass"));
				connection = DriverManager.getConnection(properties.getProperty("url"),
						properties.getProperty("username"), properties.getProperty("password"));
				TableTo.initialize(connection);
				return connection;
			} catch (IOException e) {
				System.out.println("加载配置文件异常");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("驱动加载失败");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("数据库连接异常");
				e.printStackTrace();
			}
		}
		return connection;
	}

	/**
	 * 
	 * 关闭Connection连接
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月28日 下午9:38:17
	 */
	public static void closeConnection() {
		Connection connection = connectionHolder.get();
		if (connection != null) {
			try {
				connection.close();
				connectionHolder.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
