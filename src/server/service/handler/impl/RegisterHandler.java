package server.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import models.User;
import server.service.ServerOut;
import server.service.handler.Handler;

public class RegisterHandler implements Handler {

	@Override
	public void handle(Socket socket) throws IOException {
		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			User user = (User) ois.readObject();
			ServerOut.RespondRegister(socket, user);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
