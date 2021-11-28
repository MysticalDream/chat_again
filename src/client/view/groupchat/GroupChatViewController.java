package client.view.groupchat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import client.service.Request;
import client.service.flag.MessageFlag;
import client.service.messagehelper.GroupListManager;
import client.util.DateUtil;
import client.view.ControlledStage;
import client.view.StageController;
import client.view.chat.TextMessageCellFactory;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.User;
import models.message.entity.TextMessage;

public class GroupChatViewController implements ControlledStage {
	StageController stageController;
	@FXML
	ListView<TextMessage> list;
	@FXML
	TextArea message;
	@FXML
	Label tip;
	public static User user;
	private static final int LIMIT = 2048;

	@FXML
	private void initialize() {
		tip.setVisible(false);
		list.setItems(GroupListManager.getList());
		list.setCellFactory((param) -> new TextMessageCellFactory(user));
		message.lengthProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					if (message.getText().length() > LIMIT) {
						message.setText(message.getText().substring(0, LIMIT));
					}
				});
	}

	@FXML
	private void send() {
		String content = message.getText();
		if (!content.isBlank())// 非空白
		{
			message.setText("");
			TextMessage textMessage = new TextMessage(content, DateUtil.getLocalDateTimeNow("http://www.baidu.com"),
					user, null);
			GroupListManager.addItem(textMessage);
			Request.sendMessage(textMessage, MessageFlag.GROUPMESSAGE);
		} else {
			message.setText("");// 删除回车
			showTip(tip, 1000L, "不能发送空白消息");// 显示1秒
		}
	}

	public static ExecutorService pool = new ThreadPoolExecutor(0, 1, 0L, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(1));// 创建线程池,单线池

	/**
	 * 提示方法
	 * 
	 * @param tip2
	 * @param millis
	 * @param tipContent <b>Author:</b>
	 *                   <a href="https://github.com/MysticalDream" target=
	 *                   "_blank">MysticalDream</a> <b>Date:</b> 2021年5月29日
	 *                   下午3:18:58
	 */
	private void showTip(Label tip2, long millis, String tipContent) {
		tip2.setText(tipContent);
		try {
			pool.execute(() -> {
				tip2.setVisible(true);
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					System.out.println("睡眠中断");
					e.printStackTrace();
				}
				tip2.setVisible(false);
			});
		} catch (RejectedExecutionException e) {
			return;
		}
	}

	/**
	 * 响应回车发送信息
	 * 
	 * @param event <b>Author:</b>
	 *              <a href="https://github.com/MysticalDream" target=
	 *              "_blank">MysticalDream</a> <b>Date:</b> 2021年5月27日 下午5:47:30
	 */
	@FXML
	private void handleEnter(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			send();
		}
	}

	@Override
	public void setStageController(StageController stageController) {
		this.stageController = stageController;
	}

}
