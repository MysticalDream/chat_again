package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableTo {
	public static void initialize(Connection connection) {
		try {
			PreparedStatement ps = null;
			String sql = "CREATE DATABASE IF NOT EXISTS service";
			ps = connection.prepareStatement(sql);
			ps.executeUpdate();

			String sql1 = "CREATE TABLE IF NOT EXISTS `service`.`user`  (" + "  `id` int(10) NOT NULL AUTO_INCREMENT,"
					+ "  `username` varchar(30) NULL," + "  `password` varchar(100) NOT NULL,"
					+ "  `account` varchar(30) NOT NULL," + "  `sex` tinyint(1) NULL," + "  `birthday` date NULL,"
					+ "  `imgpath` varchar(255) NULL," + "  `applydate` date NULL," + "`state` tinyint(1) DEFAULT '0',"
					+ "  PRIMARY KEY (`id`, `account`)," + "   UNIQUE KEY (`account`)" + ")";
			ps = connection.prepareStatement(sql1);
			ps.executeUpdate();
			String sql2 = "CREATE TABLE IF NOT EXISTS `service`.`friend` (" + "  `id` int NOT NULL AUTO_INCREMENT,"
					+ "  `user1` varchar(30)  NOT NULL," + "  `user2` varchar(30)  NOT NULL," + "  PRIMARY KEY (`id`)"
					+ ")";
			ps = connection.prepareStatement(sql2);
			ps.executeUpdate();
			
			String sql3 = "use service";
			ps=connection.prepareStatement(sql3);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
