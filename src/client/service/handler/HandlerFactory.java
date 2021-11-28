package client.service.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import client.service.flag.MessageFlag;
import client.service.handler.impl.AddFriendHandler;
import client.service.handler.impl.GetUserListHandler;
/**
 * 策略工厂
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.handler
 * <br><b>ClassName:</b> HandlerFactory
 * <br><b>Date:</b> 2021年6月16日 下午2:22:40
 */
public class HandlerFactory {
	private static Map<Integer, Handler> map = new ConcurrentHashMap<>();

	static {
		Handler handler = new GetUserListHandler();
		Handler handler1 = new AddFriendHandler();
		HandlerFactory.register(MessageFlag.GETUSERLIST, handler);
		HandlerFactory.register(MessageFlag.ADDFRIEND, handler1);
	}

	public static Handler getHandler(Integer flag) {
		return map.get(flag);
	}

	public static void register(Integer flag, Handler handler) {
		if (null == flag || null == handler) {
			return;
		}
		map.putIfAbsent(flag, handler);
	}
}
