package client.service.handler.impl;

import java.io.IOException;
import java.net.Socket;

import client.service.handler.Handler;
/**
 * 离线策略
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.handler.impl
 * <br><b>ClassName:</b> OfflineHandler
 * <br><b>Date:</b> 2021年6月16日 下午2:20:46
 */
public class OfflineHandler implements Handler {
	public interface CallBack {
		void offline();
	}

	private CallBack call;

	@Override
	public void handle(Socket socket) throws IOException {
		call.offline();
	}

	public void setCall(CallBack call) {
		this.call = call;
	}
}
