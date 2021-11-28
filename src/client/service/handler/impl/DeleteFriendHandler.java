package client.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import client.service.handler.Handler;
import client.view.chat.manager.FriendListManager;
/**
 * 删除好友策略
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.handler.impl
 * <br><b>ClassName:</b> DeleteFriendHandler
 * <br><b>Date:</b> 2021年6月16日 下午2:21:50
 */
public class DeleteFriendHandler implements Handler {

	@Override
	public void handle(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		byte[] account = new byte[is.read()];
		is.read(account);
		String accountStr = new String(account, "utf-8");
		FriendListManager.deleteFriend(accountStr);
	}

}
