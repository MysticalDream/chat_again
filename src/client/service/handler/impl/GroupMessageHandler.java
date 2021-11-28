package client.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import client.service.handler.Handler;
import client.service.messagehelper.GroupListManager;
import models.message.entity.TextMessage;
/**
 * 群聊策略
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.handler.impl
 * <br><b>ClassName:</b> GroupMessageHandler
 * <br><b>Date:</b> 2021年6月16日 下午2:21:11
 */
public class GroupMessageHandler implements Handler {

	@Override
	public void handle(Socket socket) throws IOException {

		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			TextMessage textMessage = (TextMessage) ois.readObject();
			GroupListManager.addItem(textMessage);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
