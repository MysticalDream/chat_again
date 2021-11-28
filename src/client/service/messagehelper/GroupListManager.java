package client.service.messagehelper;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.message.entity.TextMessage;

public class GroupListManager {
	private static ObservableList<TextMessage> list = FXCollections.observableArrayList();

	public static void addItem(TextMessage textMessage) {
		if (textMessage != null) {
			Platform.runLater(() -> {
				list.add(textMessage);
			});
		}
	}

	public static ObservableList<TextMessage> getList() {
		return list;
	}

	public static void setList(ObservableList<TextMessage> list) {
		GroupListManager.list = list;
	}

}
