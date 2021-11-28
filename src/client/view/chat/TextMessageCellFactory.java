package client.view.chat;

import client.view.messageview.MessageView;
import client.view.messageview.impl.TextMessageView;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.User;
import models.message.entity.TextMessage;

public class TextMessageCellFactory extends ListCell<TextMessage>
		implements Callback<ListView<TextMessage>, ListCell<TextMessage>> {

	User user;

	public TextMessageCellFactory(User user) {
		this.user = user;
	}

	@Override
	protected void updateItem(TextMessage item, boolean empty) {
		super.updateItem(item, empty);
		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(null);
			MessageView<VBox> view = new TextMessageView(item, user.getAccount().equals(item.getSender().getAccount()));
			setGraphic(view.produce());
		}
	}

	@Override
	public ListCell<TextMessage> call(ListView<TextMessage> param) {
		return this;
	}

}
