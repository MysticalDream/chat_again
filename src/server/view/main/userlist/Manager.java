package server.view.main.userlist;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.User;
import server.util.DBUtil;

public class Manager {

	public static ObservableList<User> list;

	public static void initMap() {
		ArrayList<User> userList = (ArrayList<User>) DBUtil.getALLUserInfo();
		UserStateList.fillMap(userList);
		list = FXCollections.observableArrayList(userList);
	}

	public static void addUser(User user) {
		Platform.runLater(() -> {
			list.add(user);// 新注册的
		});
		UserStateList.addUser(user.getAccount());
	}

	public static void deleteUser(User user) {
		Platform.runLater(() -> {
			list.remove(user);
		});
	}
}
