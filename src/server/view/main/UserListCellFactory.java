package server.view.main;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.User;
import server.view.layout.UserListLayout;

public class UserListCellFactory extends ListCell<User> implements Callback<ListView<User>, ListCell<User>> {

	@Override
	protected void updateItem(User item, boolean empty) {
		super.updateItem(item, empty);
		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(null);
			UserListLayout view = new UserListLayout(item);
			setGraphic(view.produce());
		}
	}

	@Override
	public ListCell<User> call(ListView<User> param) {
		return this;
	}

}
