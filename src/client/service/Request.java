package client.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import client.service.flag.MessageFlag;
import client.view.chat.ChatInterfaceController;
import models.User;
import models.message.entity.TextMessage;
/**
 * 向服务器发送消息//TODO 粗糙解决
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service
 * <br><b>ClassName:</b> Request
 * <br><b>Date:</b> 2021年6月16日 下午2:32:05
 */
public class Request {

	private static Socket socket;

	public static void setSocket(Socket socket) {
		Request.socket = socket;
	}

	/**
	 * 登陆验证
	 * 
	 * @param account
	 * @param password <br/>
	 *                 <b>Date:</b> 2021年6月11日 下午11:30:55
	 */
	public static void verifyLogin(String account, String password) {
		try {
			OutputStream os = socket.getOutputStream();
			os.write(MessageFlag.LOGIN);
			byte[] buf1 = account.getBytes("utf-8");// 账号
			byte[] buf2 = password.getBytes("utf-8");// 密码
			os.write(buf1.length);// 写入长度
			os.write(buf1);
			os.write(buf2.length);// 写入长度
			os.write(buf2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册验证
	 * 
	 * @param user <br/>
	 *             <b>Date:</b> 2021年6月11日 下午11:31:09
	 */
	public static void verifyRegister(User user) {
		try {
			OutputStream os = socket.getOutputStream();
			os.write(MessageFlag.REGISTER);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(user);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 发送消息
	 * 
	 * @param textMessage <br/>
	 *                    <b>Date:</b> 2021年6月11日 下午11:31:24
	 */
	public static void sendMessage(TextMessage textMessage, int flag) {
		try {
			OutputStream os = socket.getOutputStream();
			os.write(flag);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(textMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除好友
	 * 
	 * @param myAccount
	 * @param friendAccount <br/>
	 *                      <b>Date:</b> 2021年6月12日 下午11:16:37
	 */
	public static void deleteFriend(String myAccount, String friendAccount) {
		try {
			OutputStream os = socket.getOutputStream();
			os.write(MessageFlag.DELETEFRIEND);
			byte[] buf1 = myAccount.getBytes("utf-8");
			byte[] buf2 = friendAccount.getBytes("utf-8");
			os.write(buf1.length);
			os.write(buf1);
			os.write(buf2.length);
			os.write(buf2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取好友列表
	 *
	 * <br/>
	 * <b>Date:</b> 2021年6月12日 下午11:16:46
	 */
	public static void getUserList() {
		try {
			OutputStream os = socket.getOutputStream();
			os.write(MessageFlag.GETUSERLIST);
			byte[] buf = ChatInterfaceController.user.getAccount().getBytes("utf-8");
			os.write(buf.length);
			os.write(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加好友
	 * 
	 * @param account1
	 * @param account2 <br/>
	 *                 <b>Date:</b> 2021年6月12日 下午11:16:59
	 */
	public static void addFriend(User me, User friend) {
		try {
			OutputStream os = socket.getOutputStream();
			os.write(MessageFlag.ADDFRIEND);
			byte[] user1 = me.getAccount().getBytes("utf-8");
			os.write(user1.length);
			os.write(user1);
			byte[] user2 = friend.getAccount().getBytes("utf-8");
			os.write(user2.length);
			os.write(user2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通知离线
	 * 
	 * @param account <br/>
	 *                <b>Date:</b> 2021年6月12日 下午11:17:13
	 */
	public static void notifyOffline(String account) {
		try {
			OutputStream os = socket.getOutputStream();
			os.write(MessageFlag.OFFLINE);
			byte[] buf = account.getBytes("utf-8");
			os.write(buf.length);
			os.write(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
