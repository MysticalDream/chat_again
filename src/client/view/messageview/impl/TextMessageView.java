package client.view.messageview.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import client.util.DateUtil;
import client.view.messageview.MessageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import models.message.entity.TextMessage;

public class TextMessageView implements MessageView<VBox> {
	private VBox vBox = null;
	private TextMessage textMessage;
	private boolean isMy;

	public TextMessageView(TextMessage textMessage, boolean isMy) {
		this.textMessage = textMessage;
		this.isMy = isMy;
		layOut();
	}

	private void layOut() {
		String url = textMessage.getSender().getHeadImagePath();
		String content = textMessage.getContent();
		vBox = new VBox();// 垂直布局，最外层
		HBox hBox = new HBox();// 消息头像盒子
		HBox hBox2 = new HBox();// 时间盒子
		hBox2.setAlignment(Pos.CENTER);// 时间居中
		Label messageTime;
		Label news = new Label(content);
		double[] point;// Polygon三点位置
		Polygon triangle;
		try {
			/*
			 * 消息时间
			 */
			DateUtil.setDatePattern("yyyy年MM月dd日HH时mm分ss秒");
			messageTime = new Label(DateUtil.format(textMessage.getTime()));// 消息时间
			messageTime.setMaxHeight(20);// 时间所占高度
			hBox2.getChildren().add(messageTime);// 添加时间
			/*
			 * 头像
			 */
			Image img = new Image(new FileInputStream(new File(url)));// 头像
			ImageView imgBox = new ImageView(img);// 头像显示
			imgBox.setPreserveRatio(false);
			imgBox.setFitHeight(40);
			imgBox.setFitWidth(50);
			/*
			 * 消息内容样式
			 */
			news.setMaxWidth(245);
			news.setFont(Font.font(14));
			news.setWrapText(true);
			news.setStyle("-fx-background-color:rgb(255,62,150);-fx-background-radius:8px;");
			news.setPadding(new Insets(5));

			if (this.isMy) {
				hBox.setAlignment(Pos.TOP_RIGHT);
				point = new double[] { 0.0, 0.0, 0.0, 10.0, 10.0, 5.0 };
				triangle = new Polygon(point);
				hBox.getChildren().addAll(news, triangle, imgBox);
			} else {
				hBox.setAlignment(Pos.TOP_LEFT);
				point = new double[] { 0.0, 5.0, 10.0, 0.0, 10.0, 10.0 };
				triangle = new Polygon(point);
				hBox.getChildren().addAll(imgBox, triangle, news);
			}
			triangle.setFill(Color.rgb(255, 62, 150));
			HBox.setMargin(triangle, new Insets(20, 0, 0, 0));
			HBox.setMargin(imgBox, new Insets(10, 0, 0, 0));
			HBox.setMargin(news, new Insets(10, 0, 0, 0));
			vBox.getChildren().addAll(hBox2, hBox);
		} catch (FileNotFoundException e) {
			System.out.println("聊天头像遗失");
		}
	}

	@Override
	public VBox produce() {
		return this.vBox;
	}
}
