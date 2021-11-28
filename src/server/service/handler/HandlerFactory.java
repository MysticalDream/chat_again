package server.service.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import server.service.flag.MessageFlag;
import server.service.handler.impl.AddFriendHandler;
import server.service.handler.impl.DeleteFriendHandler;
import server.service.handler.impl.GetUserListHandler;
import server.service.handler.impl.GroupMessageHandler;
import server.service.handler.impl.LoginHandler;
import server.service.handler.impl.PrivateMessageHandler;
import server.service.handler.impl.RegisterHandler;

public class HandlerFactory {
	private static Map<Integer, Handler> map = new ConcurrentHashMap<>();

	static {
		register(MessageFlag.LOGIN, new LoginHandler());
		register(MessageFlag.REGISTER, new RegisterHandler());
		register(MessageFlag.PRIVATEMESSAGE, new PrivateMessageHandler());
		register(MessageFlag.DELETEFRIEND, new DeleteFriendHandler());
		register(MessageFlag.GROUPMESSAGE, new GroupMessageHandler());
		register(MessageFlag.GETUSERLIST, new GetUserListHandler());
		register(MessageFlag.ADDFRIEND, new AddFriendHandler());
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
