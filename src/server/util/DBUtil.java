package server.util;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.User;
import server.dao.DBConnection;

public final class DBUtil {
	/**
	 * 得到用户表所有用户
	 * 
	 * @return <b>Author:</b> <a href="https://github.com/MysticalDream" target=
	 *         "_blank">MysticalDream</a> <b>Date:</b> 2021年5月29日 上午9:41:26
	 */
	public static List<User> getALLUserInfo() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> list = new ArrayList<User>();
		String sql = "SELECT * FROM service.user";
		try {
			ps = DBConnection.getDBConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String userName = rs.getString("username");
				String passWord = rs.getString("password");
				String account = rs.getString("account");
				Boolean sex = rs.getBoolean("sex");
				Date birthday = rs.getDate("birthday");
				String imgPath = rs.getString("imgpath");
				Date applyDate = rs.getDate("applydate");
				Boolean status = rs.getBoolean("state");
				User user = new User(userName, account, null, sex, birthday.toLocalDate(), imgPath,
						applyDate.toLocalDate(), passWord, status);
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// DBUtil.closeCnnection();
		}
		return list;
	}

	public static User getUserSingle(String accountL) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		String sql = "SELECT * FROM service.user WHERE account=?";
		try {
			ps = DBConnection.getDBConnection().prepareStatement(sql);
			ps.setString(1, accountL);
			rs = ps.executeQuery();
			while (rs.next()) {
				String userName = rs.getString("username");
				String passWord = rs.getString("password");
				String account = rs.getString("account");
				Boolean sex = rs.getBoolean("sex");
				Date birthday = rs.getDate("birthday");
				String imgPath = rs.getString("imgpath");
				Date applyDate = rs.getDate("applydate");
				Boolean status = rs.getBoolean("state");
				user = new User(userName, account, null, sex, birthday.toLocalDate(), imgPath, applyDate.toLocalDate(),
						passWord, status);
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 获取好友列表
	 * 
	 * @param account
	 * @return <b>Author:</b> <a href="https://github.com/MysticalDream" target=
	 *         "_blank">MysticalDream</a> <b>Date:</b> 2021年5月31日 下午4:03:09
	 */
	public static ArrayList<User> getFriendList(String account) {
		PreparedStatement ps = null;
		String sql = "SELECT * FROM service.friend where user1=? OR user2=?";
		ResultSet rs = null;
		ArrayList<User> userList = new ArrayList<User>();
		try {
			ps = DBConnection.getDBConnection().prepareStatement(sql);
			ps.setString(1, account);
			ps.setString(2, account);
			rs = ps.executeQuery();
			String account1;
			String account2;
			while (rs.next()) {
				account1 = rs.getString("user1");
				account2 = rs.getString("user2");
				User user = DBUtil.getUserSingle(account.equals(account1) ? account2 : account1);
				user.setPassWord(null);
				userList.add(user);
			}
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 判断用户是否存在(根据账号)
	 * 
	 * @param account
	 * @return 用户存在返回true,否则返回false; <b>Author:</b>
	 *         <a href="https://github.com/MysticalDream" target=
	 *         "_blank">MysticalDream</a> <b>Date:</b> 2021年5月29日 上午10:05:24
	 */
	public static boolean userExist(String account) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT account,COUNT(*) FROM user WHERE account=?";
		try {
			ps = DBConnection.getDBConnection().prepareStatement(sql);
			ps.setString(1, account);
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt("COUNT(*)") > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// DBUtil.closeCnnection();
		}

	}

	/**
	 * 验证密码
	 * 
	 * @param account
	 * @param passWord
	 * @return <b>Author:</b> <a href="https://github.com/MysticalDream" target=
	 *         "_blank">MysticalDream</a> <b>Date:</b> 2021年5月30日 下午12:20:29
	 */
	public static boolean passWordVerify(String account, String passWord) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT password FROM user WHERE account=?";
		try {
			ps = DBConnection.getDBConnection().prepareStatement(sql);
			ps.setNString(1, account);
			rs = ps.executeQuery();
			rs.next();
			return rs.getString("password").equals(passWord);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 更新用户状态
	 * 
	 * @param account
	 * @param state   <b>Author:</b>
	 *                <a href="https://github.com/MysticalDream" target=
	 *                "_blank">MysticalDream</a> <b>Date:</b> 2021年5月30日 下午5:46:31
	 */
	public static void updateState(String account, Boolean state) {
		PreparedStatement ps = null;
		String sql = "UPDATE `service`.`user` SET `state` = ? WHERE `account` = ?";
		if (userExist(account)) {
			try {
				ps = DBConnection.getDBConnection().prepareStatement(sql);
				ps.setBoolean(1, state);
				ps.setString(2, account);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 插入新的用户
	 * 
	 * @param user
	 * @param passWord <b>Author:</b>
	 *                 <a href="https://github.com/MysticalDream" target=
	 *                 "_blank">MysticalDream</a> <b>Date:</b> 2021年5月29日 上午10:56:06
	 */
	public static void insertUserInfo(User user) {
		PreparedStatement ps = null;
		String sql = "INSERT INTO `service`.`user`(`username`, `password`, `account`, `sex`, `birthday`, `imgpath`, `applydate`) VALUES (?, ?, ?, ?, ?, ?, ?)";
		if (!userExist(user.getAccount())) {
			try {
				ps = DBConnection.getDBConnection().prepareStatement(sql);
				ps.setString(1, user.getUserName());
				ps.setString(2, user.getPassWord());
				ps.setString(3, user.getAccount());
				ps.setBoolean(4, user.getSex());
				ps.setDate(5, java.sql.Date.valueOf(user.getBirthday()));
				ps.setString(6, user.getHeadImagePath());
				ps.setDate(7, java.sql.Date.valueOf(user.getApplyDate()));
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				// DBUtil.closeCnnection();
			}
		}
	}

	/**
	 * 根据账号删除
	 * 
	 * @param account <b>Author:</b>
	 *                <a href="https://github.com/MysticalDream" target=
	 *                "_blank">MysticalDream</a> <b>Date:</b> 2021年5月29日 上午10:56:55
	 */

	public static void deleteUserInfo(String account) {
		PreparedStatement ps = null;
		String sql = "DELETE FROM `service`.`user` WHERE account = ?";
		if (DBUtil.userExist(account)) {
			try {
				ps = DBConnection.getDBConnection().prepareStatement(sql);
				ps.setString(1, account);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				// DBUtil.closeCnnection();
			}
		}
	}

	/**
	 * 插入好友
	 * 
	 * @param account1
	 * @param account2 <b>Author:</b>
	 *                 <a href="https://github.com/MysticalDream" target=
	 *                 "_blank">MysticalDream</a> <b>Date:</b> 2021年5月31日 下午3:33:59
	 */
	public static void insertFriend(String account1, String account2) {
		if (!isFriend(account1, account2)) {
			PreparedStatement ps = null;
			String sql = "INSERT INTO `service`.`friend`(`user1`, `user2`) VALUES (? , ?)";
			try {
				ps = DBConnection.getDBConnection().prepareStatement(sql);
				ps.setString(1, account1);
				ps.setString(2, account2);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

	/**
	 * 根据账号删除好友
	 * 
	 * @param account1
	 * @param account2 <b>Author:</b>
	 *                 <a href="https://github.com/MysticalDream" target=
	 *                 "_blank">MysticalDream</a> <b>Date:</b> 2021年5月31日 下午3:50:38
	 */
	public static void deleteFriend(String account1, String account2) {

		if (isFriend(account1, account2)) {
			PreparedStatement ps = null;
			String sql = "DELETE FROM service.friend WHERE (user1=? and user2=?) OR (user1=? and user2=?)";
			try {
				ps = DBConnection.getDBConnection().prepareStatement(sql);
				ps.setString(1, account1);
				ps.setString(2, account2);
				ps.setString(3, account2);
				ps.setString(4, account1);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 清除给定账号所有用户
	 * 
	 * @param account <br/>
	 *                <b>Date:</b> 2021年6月13日 下午4:57:22
	 */
	public static void clearFriend(String account) {
		PreparedStatement ps = null;
		String sql = "DELETE FROM `service`.`friend` WHERE user1=? OR user2=?";
		try {

			ps = DBConnection.getDBConnection().prepareStatement(sql);
			ps.setString(1, account);
			ps.setString(2, account);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 根据账号验证两个用户是否是好友关系
	 * 
	 * @param account1
	 * @param account2
	 * @return <b>Author:</b> <a href="https://github.com/MysticalDream" target=
	 *         "_blank">MysticalDream</a> <b>Date:</b> 2021年5月31日 下午3:49:04
	 */
	private static boolean isFriend(String account1, String account2) {
		PreparedStatement ps = null;
		String sql = "SELECT * FROM service.friend WHERE (user1=? and user2=?) OR (user1=? and user2=?)";
		ResultSet rs = null;
		try {
			ps = DBConnection.getDBConnection().prepareStatement(sql);
			ps.setString(1, account1);
			ps.setString(2, account2);
			ps.setString(3, account2);
			ps.setString(4, account1);
			rs = ps.executeQuery();
			return rs.next() ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 关闭Connection <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月28日 下午10:05:12
	 */
	public static void closeCnnection() {
		DBConnection.closeConnection();
	}

	/**
	 * 禁止实例化
	 */
	private DBUtil() {

	}
}