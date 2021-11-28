package server.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;
import models.User;
import models.message.entity.TextMessage;
import server.service.flag.MessageFlag;
import server.service.flag.ResponseFlag;
import server.service.thread.ServerListener;
import server.util.DBUtil;
import server.view.main.ManagementController;
import server.view.main.userlist.Manager;
import server.view.main.userlist.UserStateList;

public class ServerOut {
	public static ManagementController manager;

	/**
	 * 回应登录
	 * 
	 * @param socket
	 * @param account
	 * @param passWord
	 * @throws IOException <br/>
	 *                     <b>Date:</b> 2021年6月10日 上午9:58:37
	 */
	public static void RespondLogin(Socket socket, String account, String passWord) throws IOException {
		OutputStream os = socket.getOutputStream();
		os.write(MessageFlag.LOGIN);// 对应登录
		if (DBUtil.userExist(account)) {
			if (DBUtil.passWordVerify(account, passWord)) {
				os.write(ResponseFlag.SUCCESS);
				DBUtil.updateState(account, true);// 更新状态
				ServerListener.put(account, socket);// 注入map
				UserStateList.changeStatus(account, true);// 更新状态
				Platform.runLater(() -> {
					manager.showSelected();
				});
				User user = DBUtil.getUserSingle(account);// 获取该用户
				user.setFriendList(DBUtil.getFriendList(account));
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(user);
			} else {
				os.write(ResponseFlag.WRONGPASSWORD);
			}
		} else {
			os.write(ResponseFlag.INEXISTENCE);
		}
	}

	/**
	 * 回应注册
	 * 
	 * @param socket
	 * @param user
	 * @throws IOException <br/>
	 *                     <b>Date:</b> 2021年6月10日 上午9:58:47
	 */
	public static void RespondRegister(Socket socket, User user) throws IOException {
		OutputStream os = socket.getOutputStream();
		os.write(MessageFlag.REGISTER);
		if (DBUtil.userExist(user.getAccount())) {
			os.write(ResponseFlag.DUPLICATE);// 账号已有
		} else {
			DBUtil.insertUserInfo(user);
			os.write(ResponseFlag.SUCCESS);// 插入成功
			Manager.addUser(user);
		}
	}

	/**
	 * 私信
	 * 
	 * @param textMessage
	 * @throws IOException <br/>
	 *                     <b>Date:</b> 2021年6月12日 下午10:36:55
	 */
	public static void RespondSendPrivateMessage(TextMessage textMessage) throws IOException {
		Socket socket = ServerListener.getSocket(textMessage.getReceiver().getAccount());
		if (socket != null) {
			OutputStream os = socket.getOutputStream();
			os.write(MessageFlag.PRIVATEMESSAGE);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(textMessage);
		}
	}

	/**
	 * 群聊
	 * 
	 * @param textMessage
	 * @throws IOException <br/>
	 *                     <b>Date:</b> 2021年6月12日 下午10:37:09
	 */
	public static void RespondSendGroupMessage(TextMessage textMessage) throws IOException {
		String account = textMessage.getSender().getAccount();
		ArrayList<Socket> list = ServerListener.getSocketWithout(account);
		for (Socket socket : list) {
			OutputStream os = socket.getOutputStream();
			os.write(MessageFlag.GROUPMESSAGE);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(textMessage);
		}
	}

	/**
	 * 删除好友
	 * 
	 * @param account1
	 * @param account2
	 * @throws IOException <br/>
	 *                     <b>Date:</b> 2021年6月12日 下午10:37:19
	 */
	public static void RespondDeleteFriend(String account1, String account2) throws IOException {
		Socket socket = ServerListener.getSocket(account2);
		if (socket != null) {
			OutputStream os = socket.getOutputStream();
			os.write(MessageFlag.DELETEFRIEND);
			byte[] account = account1.getBytes("utf-8");
			os.write(account.length);
			os.write(account);
		}
	}

	/**
	 * 获取用户列表
	 * 
	 * @param account <br/>
	 *                <b>Date:</b> 2021年6月12日 下午10:38:21
	 * @throws IOException
	 */
	public static void RespondGetUserList(Socket socket, String account) throws IOException {
		ArrayList<User> allUser = (ArrayList<User>) DBUtil.getALLUserInfo();
		ArrayList<User> friendList = DBUtil.getFriendList(account);
		allUser.removeAll(friendList);
		User user = DBUtil.getUserSingle(account);
		allUser.remove(user);
		OutputStream os = socket.getOutputStream();
		os.write(MessageFlag.GETUSERLIST);
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(allUser);
	}

	public static void RespondAddFriend(String account1, String account2) throws IOException {
		Socket socket = ServerListener.getSocket(account2);
		if (socket != null) {
			OutputStream os = socket.getOutputStream();
			User user = DBUtil.getUserSingle(account1);
			os.write(MessageFlag.ADDFRIEND);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(user);

		}
	}

	/**
	 * 强制下线
	 * 
	 * @param socket <br/>
	 *               <b>Date:</b> 2021年6月10日 上午10:24:23
	 */
	public static void RespondKickOff(Socket socket) {
		OutputStream os;
		try {
			os = socket.getOutputStream();
			os.write(MessageFlag.OFFLINE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
