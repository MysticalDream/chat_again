package client.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import client.service.flag.ResponseFlag;
import client.service.handler.Handler;
/**
 * 注册策略
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.handler.impl
 * <br><b>ClassName:</b> RegisterHandler
 * <br><b>Date:</b> 2021年6月16日 下午2:20:22
 */
public class RegisterHandler implements Handler {
	public interface Callback {

		void duplicate();

		void success();
	}

	private Callback call;

	@Override
	public void handle(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		int respond = is.read();
		if (respond == ResponseFlag.DUPLICATE) {
			call.duplicate();
		} else {
			call.success();
		}
	}

	public void setCall(Callback call) {
		this.call = call;
	}
}
