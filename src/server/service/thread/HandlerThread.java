package server.service.thread;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import server.service.flag.MessageFlag;
import server.service.handler.Handler;
import server.service.handler.HandlerFactory;

public class HandlerThread implements Runnable {
	private Socket socket;

	public HandlerThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			while (true) {
				Integer flag = is.read();
				if (flag != MessageFlag.HEARTBEAT) {
					Handler handler = HandlerFactory.getHandler(flag);
					handler.handle(socket);
				}
			}
		} catch (IOException e) {
			return;
		}

	}

}
