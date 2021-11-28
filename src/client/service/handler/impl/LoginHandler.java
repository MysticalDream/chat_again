package client.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import client.service.flag.ResponseFlag;
import client.service.handler.Handler;
import models.User;
/**
 * 登录策略
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.handler.impl
 * <br><b>ClassName:</b> LoginHandler
 * <br><b>Date:</b> 2021年6月16日 下午2:20:10
 */
public class LoginHandler implements Handler {
	public interface Callback {
		void inexistentAccount();

		void wrongPassword();

		void success(User user);
	}

	private Callback call;

	@Override
	public void handle(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		int respond = is.read();
		try {
			if (respond == ResponseFlag.SUCCESS) {
				// 验证成功
				ObjectInputStream ois = new ObjectInputStream(is);
				User user = (User) ois.readObject();
				call.success(user);

			} else if (respond == ResponseFlag.WRONGPASSWORD) {// 密码错误
				call.wrongPassword();
			} else {
				// 账号不存在
				call.inexistentAccount();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setCall(Callback call) {
		this.call = call;
	}

}
