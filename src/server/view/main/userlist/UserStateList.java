package server.view.main.userlist;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import models.User;

public class UserStateList {
	private static Map<String, BooleanProperty> userMap = new ConcurrentHashMap<>();

	public static void changeStatus(String account, Boolean state) {
		userMap.get(account).set(state);
	}

	public static void fillMap(ArrayList<User> list) {
		for (User user : list) {
			userMap.putIfAbsent(user.getAccount(), new SimpleBooleanProperty(false));
		}
	}

	public static void addUser(String account) {
		userMap.putIfAbsent(account, new SimpleBooleanProperty(false));
	}

	public static Boolean getState(String account) {
		return userMap.get(account).getValue();
	}

	public static SimpleBooleanProperty getProperty(String account) {
		return (SimpleBooleanProperty) userMap.get(account);
	}
}
