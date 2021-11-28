package client.service.messagehelper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.User;
import models.message.entity.TextMessage;
/**
 * 管理聊天记录
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.service.messagehelper
 * <br><b>ClassName:</b> MessageListButler
 * <br><b>Date:</b> 2021年6月16日 下午2:14:04
 */
public class MessageListButler {

	public static Map<String, ObservableList<TextMessage>> messageMap = new ConcurrentHashMap<>();

	public static ObservableList<TextMessage> getMessageList(String account) {
		return messageMap.get(account);
	}

	public static boolean storeMessageList(String account, ObservableList<TextMessage> list) {
		messageMap.putIfAbsent(account, list);
		return true;
	}

	public static void fillMessageList(List<User> list) {
		for (User user : list) {
			storeMessageList(user.getAccount(), FXCollections.observableArrayList());
		}
	}

	public static boolean removeMessageList(String account) {
		if (messageMap.remove(account) == null)
			return false;
		else {
			return true;
		}
	}
}
