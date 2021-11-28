package server.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import server.service.ServerOut;
import server.service.handler.Handler;
import server.util.DBUtil;

public class DeleteFriendHandler implements Handler {

	@Override
	public void handle(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		byte[] myAccount = new byte[is.read()];// 先读取长度
		is.read(myAccount);
		byte[] friendAccount = new byte[is.read()];// 先读取长度
		is.read(friendAccount);
		String account1Str = new String(myAccount, "utf-8");
		String account2Str = new String(friendAccount, "utf-8");
		DBUtil.deleteFriend(account1Str, account2Str);
		ServerOut.RespondDeleteFriend(account1Str, account2Str);
	}

}
