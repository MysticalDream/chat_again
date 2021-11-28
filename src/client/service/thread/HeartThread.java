package client.service.thread;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import client.service.Service;
import client.service.flag.MessageFlag;
/**
 * 心跳线程30s发送一次
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.thread
 * <br><b>ClassName:</b> HeartThread
 * <br><b>Date:</b> 2021年6月13日 下午7:51:35
 */
public class HeartThread implements Runnable {

	private Socket socket;

	public HeartThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			OutputStream os = socket.getOutputStream();
			while (true) {
				Thread.sleep(30000);//30s
				os.write(MessageFlag.HEARTBEAT);
			}
		} catch (IOException | InterruptedException e) {
			try {
				if (!socket.isClosed()) {
					socket.close();
					Service.isConnected = false;
					Service.reconnect();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

}
