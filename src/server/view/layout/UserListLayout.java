package server.view.layout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.User;
import server.view.main.userlist.UserStateList;

public class UserListLayout {

	private User user;

	private HBox hBox;

	public UserListLayout(User user) {
		this.user = user;
	}

	{
	}

	private void layOut() {
		try {
			hBox = new HBox();
			Label account = new Label();
			ImageView img = new ImageView();
			Label state = new Label();
			state.textProperty().bind(UserStateList.getProperty(user.getAccount()).asString());
			Image image = new Image(new FileInputStream(new File(user.getHeadImagePath())));
			img.setImage(image);
			account.setText("账号:" + user.getAccount());
			hBox.setAlignment(Pos.CENTER_LEFT);
			hBox.setSpacing(20);
			hBox.getChildren().addAll(img, account);
			img.setFitHeight(60);
			img.setFitWidth(50);
			img.setPreserveRatio(false);
		} catch (FileNotFoundException e) {
			System.out.println("头像遗失");
		}
	}

	public HBox produce() {
		layOut();
		return hBox;
	}

}
