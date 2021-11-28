package client.view.messageview.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import client.view.messageview.MessageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.User;

public class FriendListView implements MessageView<HBox> {
	HBox hBox;
	User user;

	public FriendListView(User user) {
		this.user = user;
	}

	private void layOut() {
		hBox = new HBox();
		try {
			ImageView img;
			img = new ImageView(new Image(new FileInputStream(new File(user.getHeadImagePath()))));
			img.setFitHeight(60);
			img.setFitWidth(50);
			img.setPreserveRatio(false);
			VBox vBox = new VBox(10);
			VBox.setMargin(vBox, new Insets(25, 0, 10, 50));
			Label name = new Label("用户名:" + user.getUserName());
			Label account = new Label("账号:" + user.getAccount());
			vBox.setAlignment(Pos.CENTER);
			vBox.getChildren().addAll(name, account);
			hBox.getChildren().addAll(img, vBox);
			hBox.setAlignment(Pos.CENTER_LEFT);
		} catch (FileNotFoundException e) {
			System.out.println("好友头像遗失");
		}
	}

	@Override
	public HBox produce() {
		this.layOut();
		return this.hBox;
	}

}
