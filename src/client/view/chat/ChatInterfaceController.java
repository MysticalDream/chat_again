package client.view.chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import client.service.Request;
import client.service.flag.MessageFlag;
import client.service.handler.Handler;
import client.service.handler.HandlerFactory;
import client.service.handler.impl.DeleteFriendHandler;
import client.service.handler.impl.GroupMessageHandler;
import client.service.handler.impl.OfflineHandler;
import client.service.handler.impl.PrivateMessageHandler;
import client.service.messagehelper.MessageListButler;
import client.service.messagehelper.MessageManager;
import client.util.DateUtil;
import client.view.ControlledStage;
import client.view.FXMLConstant;
import client.view.StageController;
import client.view.chat.manager.FriendListManager;
import client.view.groupchat.GroupChatViewController;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import models.User;
import models.message.entity.TextMessage;

/**
 * 聊天界面控制器
 * 
 * @author <a href="https://github.com/MysticalDream" target=
 *         "_blank">MysticalDream</a>
 * @version 1.0 <br>
 *          <b>PackageName:</b> client.controller <br>
 *          <b>ClassName:</b> ChatInterfaceController <br>
 *          <b>Date:</b> 2021年5月26日 上午11:56:14
 */
public class ChatInterfaceController implements ControlledStage {

	private StageController stageController;
	@FXML
	private ImageView headImage;
	@FXML
	private TextField searchText;
	@FXML
	private Label nameTitle;
	@FXML
	private MenuButton topButton;
	@FXML
	private ListView<User> listLeft;
	@FXML
	private ListView<TextMessage> listRight;
	@FXML
	private TextArea messageArea;
	@FXML
	private VBox tipBox;
	@FXML
	private Label tipDetail;
	@FXML
	private SplitPane splitPane;

	private static final int LIMIT = 2048;

	public static User user;

	public ChatInterfaceController() {
		MessageManager.chatController = this;// 粗糙解决
		GroupChatViewController.user = user;
		Handler handler = new PrivateMessageHandler();
		Handler handler2 = new DeleteFriendHandler();
		Handler handler3 = new GroupMessageHandler();
		OfflineHandler handler4 = new OfflineHandler();
		handler4.setCall(() -> {
			Platform.runLater(() -> {
				stageController.getStage(FXMLConstant.CHATID).close();
				Request.notifyOffline(user.getAccount());
				stageController.showStage(FXMLConstant.LOGINID);
			});
		});
		HandlerFactory.register(MessageFlag.PRIVATEMESSAGE, handler);
		HandlerFactory.register(MessageFlag.DELETEFRIEND, handler2);
		HandlerFactory.register(MessageFlag.GROUPMESSAGE, handler3);
		HandlerFactory.register(MessageFlag.OFFLINE, handler4);
		MessageListButler.fillMessageList(user.getFriendList());
		FriendListManager.fillFriend(user.getFriendList());

	}

	/**
	 * 初始化控制器类,加载fxml文件后,此方法会自动调用。
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 上午11:55:44
	 * 
	 * @throws FileNotFoundException
	 */

	@FXML
	private void initialize() {
		new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Platform.runLater(() -> {
				stageController.getStage(FXMLConstant.CHATID).setOnCloseRequest((event) -> {
					Request.notifyOffline(user.getAccount());
					stageController.showStage(FXMLConstant.LOGINID, FXMLConstant.CHATID);
				});
			});
		}).start();

		/**
		 * 好友列表
		 */
		listLeft.setItems(FriendListManager.friendList);
		/*
		 * 限制发送信息长度 2048,无提示
		 */
		messageArea.lengthProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					if (messageArea.getText().length() > LIMIT) {
						messageArea.setText(messageArea.getText().substring(0, LIMIT));
					}
				});
		/**
		 * 加载头像
		 */
		try {
			headImage.setImage(new Image(new FileInputStream(new File(user.getHeadImagePath()))));
		} catch (FileNotFoundException e) {
			System.out.println("头像图片遗失");
		}
		/**
		 * 好友选择显示界面
		 */
		listLeft.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				splitPane.setVisible(true);
				nameTitle.setText(newValue.getUserName());
				listRight.setItems(MessageListButler.getMessageList(newValue.getAccount()));

			} else {
				splitPane.setVisible(false);
			}
		});
		/**
		 * 好友列表视图
		 */
		listLeft.setCellFactory((param) -> new FriendCellFactory());
		/**
		 * 聊天气泡界面
		 */
		listRight.setCellFactory((param) -> new TextMessageCellFactory(user));
	}

	public void scrollToBottom() {
		// listRight.scrollTo(listRight.getItems().size()-1);
		// 滚动卡死
	}

	/**
	 * 点击群聊
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 下午12:57:35
	 */
	@FXML
	private void handleClickGroupChat() {
		stageController.loadStage(FXMLConstant.GROUPCHATID, FXMLConstant.GROUPCHATLOCA);
		stageController.showStage(FXMLConstant.GROUPCHATID);
	}

	/**
	 * 点击添加好友
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 下午12:58:02
	 */
	@FXML
	private void handleClickAdd() {
		stageController.loadStage(FXMLConstant.ADDFRIENDID, FXMLConstant.ADDFRIENDLOCA);
		stageController.show(FXMLConstant.ADDFRIENDID, FXMLConstant.CHATID);
	}

	/**
	 * 点击搜索
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 下午12:58:22
	 */
	@FXML
	private void handleClickSearch() {

	}

	/**
	 * 点击下线
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 下午12:58:39
	 */
	@FXML
	public void handleClickOffline() {
		Request.notifyOffline(user.getAccount());
		stageController.showStage(FXMLConstant.LOGINID, FXMLConstant.CHATID);
	}

	/**
	 * 点击个人资料
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 下午12:58:56
	 */
	@FXML
	private void handleClickPersonalInformation() {

	}

	/**
	 * 点击好友资料
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 下午12:59:14
	 */
	@FXML
	private void handleClickFriendInformation() {

	}

	/**
	 * 点击删除好友
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 下午1:00:37
	 */
	@FXML
	private void handleClickDeleteFriend() {
		User user = listLeft.getSelectionModel().getSelectedItem();
		if (user != null) {
			Request.deleteFriend(ChatInterfaceController.user.getAccount(), user.getAccount());
			listLeft.getItems().remove(user);
		}
	}

	/**
	 * 点击添加群聊人员
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 下午1:01:02
	 */
	@FXML
	private void handleClickAddMember() {

	}

	/**
	 * 点击群聊成员列表
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 下午1:01:28
	 */
	@FXML
	private void handleClickMemberList() {

	}

	/**
	 * 点击发送
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月26日 下午1:02:00
	 * 
	 */

	@FXML
	private void handleClickSend() {
		String content = messageArea.getText();
		if (!content.isBlank()) {// 非空白
			messageArea.setText("");
			TextMessage textMessage = new TextMessage(content, DateUtil.getLocalDateTimeNow("http://www.baidu.com"),
					user, listLeft.getSelectionModel().getSelectedItem());
			MessageManager.addMessageToList(textMessage, true);
			Request.sendMessage(textMessage, MessageFlag.PRIVATEMESSAGE);
		} else {
			messageArea.setText("");// 删除回车
			showSendTip(1000L, "不能发送空白消息");// 显示1秒
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
			handleClickSend();
		}
	}

	@Override
	public void setStageController(StageController stageController) {
		this.stageController = stageController;
	}

	/**
	 * 
	 * 显示发送按钮处的提示标签 <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月27日 下午5:44:48
	 */
	public static ExecutorService pool = new ThreadPoolExecutor(0, 1, 0L, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(1));// 创建线程池,单线池

	private void showSendTip(long millis, String tipContent) {
		tipDetail.setText(tipContent);
		try {
			pool.execute(() -> {
				tipBox.setVisible(true);// 显示
				try {
					Thread.sleep(millis);// 睡眠毫秒
				} catch (InterruptedException e) {
					System.out.println("睡眠中断");
					return;
				}
				tipBox.setVisible(false);// 隐藏
			});
		} catch (RejectedExecutionException e) {// 线程还未完成时返回
			return;
		}
	}
}
