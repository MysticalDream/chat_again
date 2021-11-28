package client.view.addfriend;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.User;
/**
 * 添加好友用户列表(还不是好友)
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.view.addfriend
 * <br><b>ClassName:</b> UserList
 * <br><b>Date:</b> 2021年6月16日 下午2:34:58
 */
public class UserList {

	private static ObservableList<User> list;

	public static ObservableList<User> getList() {
		return list;
	}

	public static void setList(ObservableList<User> list) {
		UserList.list = list;
	}

	public static void setList(ArrayList<User> list) {
		UserList.list = FXCollections.observableArrayList(list);
	}
}
