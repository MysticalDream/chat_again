package server.service.handler.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import server.service.handler.Handler;
import server.service.thread.ServerListener;
import server.util.DBUtil;
import server.view.main.userlist.UserStateList;

public class OfflineHandler implements Handler {

	public interface CallBack{
		void show();
	}
	private CallBack call;
	@Override
	public void handle(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		byte[] buf = new byte[is.read()];
		is.read(buf);
		String account = new String(buf, "utf-8");
		DBUtil.updateState(account, false);
		ServerListener.remove(account);
		UserStateList.changeStatus(account, false);
		call.show();
	}
	public void setCall(CallBack call) {
		this.call = call;
	}

}
