package client.view.chat;

import client.view.messageview.MessageView;
import client.view.messageview.impl.FriendListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import models.User;

public class FriendCellFactory extends ListCell<User> implements Callback<ListView<User>, ListCell<User>> {

	@Override
	protected void updateItem(User item, boolean empty) {
		super.updateItem(item, empty);
		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(null);
			MessageView<HBox> view = new FriendListView(item);
			setGraphic(view.produce());
		}
	}

	@Override
	public ListCell<User> call(ListView<User> param) {
		return this;
	}

}
