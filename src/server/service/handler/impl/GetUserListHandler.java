package server.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import server.service.ServerOut;
import server.service.handler.Handler;

public class GetUserListHandler implements Handler {

	@Override
	public void handle(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		byte[] buf = new byte[is.read()];
		is.read(buf);
		String account = new String(buf, "utf-8");
		ServerOut.RespondGetUserList(socket, account);
	}

}
