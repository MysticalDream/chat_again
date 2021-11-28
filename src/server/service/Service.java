package server.service;

import java.io.IOException;
import java.net.ServerSocket;

import server.service.thread.ServerListener;

public class Service {
	public static boolean status = false;

	public static void bind(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			status = true;
			Thread thread = new Thread(new ServerListener(ss));//监听
			thread.setName(thread.getName() + ":listen");
			thread.start();
		} catch (IOException e) {
			e.printStackTrace();
			status = false;
			bind(port);
		}
	}
}
