package server.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import server.service.ServerOut;
import server.service.handler.Handler;
import server.util.DBUtil;

public class AddFriendHandler implements Handler {

	@Override
	public void handle(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		byte[] user1 = new byte[is.read()];
		is.read(user1);
		byte[] user2 = new byte[is.read()];
		is.read(user2);
		String str1 = new String(user1, "utf-8");
		String str2 = new String(user2, "utf-8");
		DBUtil.insertFriend(str1, str2);
		ServerOut.RespondAddFriend(str1, str2);
	}

}
