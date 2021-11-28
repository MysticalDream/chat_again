package client.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import client.service.thread.HeartThread;
import client.service.thread.ReadThread;
/**
 * 连接服务
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service
 * <br><b>ClassName:</b> Service
 * <br><b>Date:</b> 2021年6月16日 下午2:32:10
 */
public class Service {

	private static Socket socket;

	public static boolean isConnected = false;

	public interface Callback {
		void showNoServer();
	}

	private static Callback call;

	public static void connect() {
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 5258), 10000);
			isConnected = true;
			Request.setSocket(socket);
			Thread thread1 = new Thread(new HeartThread(socket));
			thread1.setName(thread1.getName() + ":heartbeat");
			thread1.start();
			Thread thread2 = new Thread(new ReadThread(socket));
			thread2.setName(thread2.getName() + "readthread");
			thread2.start();
		} catch (IOException e) {
			isConnected = false;
			call.showNoServer();
		}
	}

	public static void setCall(Callback call) {
		Service.call = call;
	}

	public synchronized static void reconnect() {
		while (!isConnected) {
			System.out.println("连接中...");
			connect();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("错误内容：" + e.getMessage());
			}
		}
	}

}
