package client.service.thread;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import client.service.handler.Handler;
import client.service.handler.HandlerFactory;

/**
 * 读取服务器信息线程
 * 
 * @author <a href="https://github.com/MysticalDream" target=
 *         "_blank">MysticalDream</a>
 * @version 1.0 <br>
 *          <b>PackageName:</b> client.service.thread <br>
 *          <b>ClassName:</b> ReadThread <br>
 *          <b>Date:</b> 2021年6月13日 下午7:52:03
 */
public class ReadThread implements Runnable {

	private Socket socket;

	public ReadThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			while (true) {
				Handler handler = HandlerFactory.getHandler(is.read());
				handler.handle(socket);
			}
		} catch (IOException e) {
			
			return;
		}
	}

}
