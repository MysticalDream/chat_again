package server.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import server.service.ServerOut;
import server.service.handler.Handler;

public class LoginHandler implements Handler {

	@Override
	public void handle(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		byte[] account = new byte[is.read()];// 先读取长度
		is.read(account);
		byte[] password = new byte[is.read()];// 先读取长度
		is.read(password);
		String accountStr = new String(account, "utf-8");
		String passwordStr = new String(password, "utf-8");
		ServerOut.RespondLogin(socket, accountStr, passwordStr);
	}

}
