package client.view.chat.manager;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.User;

public class FriendListManager {

	public static ObservableList<User> friendList = null;

	public static void fillFriend(ArrayList<User> friendList) {
		FriendListManager.friendList = FXCollections.observableArrayList(friendList);
	}

	public static void addFriend(User user) {
		Platform.runLater(() -> {
			friendList.add(user);
		});
	}

	public static boolean deleteFriend(User user) {
		return friendList.remove(user);
	}

	public static void deleteFriend(String account) {
		Platform.runLater(() -> {
			User user = null;
			for (User u : friendList) {
				if (u.getAccount().equals(account)) {
					user = u;
				}
			}
			friendList.remove(user);
		});
	}
}
