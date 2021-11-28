package client.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import client.service.handler.Handler;
import client.service.messagehelper.MessageListButler;
import client.view.chat.manager.FriendListManager;
import javafx.collections.FXCollections;
import models.User;
/**
 * 添加好友策略
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.handler.impl
 * <br><b>ClassName:</b> AddFriendHandler
 * <br><b>Date:</b> 2021年6月16日 下午2:22:15
 */
public class AddFriendHandler implements Handler {

	@Override
	public void handle(Socket socket) throws IOException {
		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			User user = (User) ois.readObject();
			FriendListManager.addFriend(user);
			MessageListButler.storeMessageList(user.getAccount(), FXCollections.observableArrayList());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
