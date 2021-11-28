package server.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import models.message.entity.TextMessage;
import server.service.ServerOut;
import server.service.handler.Handler;

public class GroupMessageHandler implements Handler {

	@Override
	public void handle(Socket socket) throws IOException {
		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			TextMessage textMessage = (TextMessage) ois.readObject();
			ServerOut.RespondSendGroupMessage(textMessage);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
