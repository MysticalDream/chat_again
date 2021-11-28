package server.service.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import server.service.ServerOut;

public class ServerListener implements Runnable {
	private ServerSocket ss;

	public static Map<String, Socket> map = new ConcurrentHashMap<>();

	public ServerListener(ServerSocket ss) {
		this.ss = ss;
	}

	@Override
	public void run() {
		ExecutorService es = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2,
				Runtime.getRuntime().availableProcessors() * 2, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		while (true) {
			try {
				Socket socket = ss.accept();
				es.execute(new HandlerThread(socket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Socket getSocket(String account) {
		return map.get(account);
	}

	public static ArrayList<Socket> getSocketWithout(String account) {
		ArrayList<Socket> list = new ArrayList<>();
		for (Map.Entry<String, Socket> entry : map.entrySet()) {
			if (!(entry.getKey().equals(account))) {
				list.add(entry.getValue());
			}
		}
		return list;
	}

	/**
	 * 强制下线
	 * 
	 * @param account
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月10日 上午10:25:03
	 * @throws IOException
	 */
	public static boolean kickOff(String account) throws IOException {
		Socket socket = map.get(account);
		if (socket != null) {
			ServerOut.RespondKickOff(socket);
			map.remove(account, socket);
			return true;
		}
		return false;
	}

	/**
	 * 移除在线记录
	 * 
	 * @param account
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月13日 下午1:18:10
	 */
	public static boolean remove(String account) {
		return map.remove(account) != null;
	}

	/**
	 * 加入在线记录
	 * 
	 * @param account
	 * @param socket  <br/>
	 *                <b>Date:</b> 2021年6月13日 下午1:20:30
	 */
	public static void put(String account, Socket socket) {
		map.putIfAbsent(account, socket);
	}
}
