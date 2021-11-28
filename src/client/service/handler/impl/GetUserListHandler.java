package client.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import client.service.handler.Handler;
import client.view.addfriend.UserList;
import models.User;
/**
 * 获取用户列表
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.handler.impl
 * <br><b>ClassName:</b> GetUserListHandler
 * <br><b>Date:</b> 2021年6月16日 下午2:21:29
 */
public class GetUserListHandler implements Handler {

	@Override
	public void handle(Socket socket) throws IOException {

		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			@SuppressWarnings("unchecked")
			ArrayList<User> userList = (ArrayList<User>) ois.readObject();
			UserList.setList(userList);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
