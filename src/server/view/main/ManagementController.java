package server.view.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.User;
import server.service.ServerOut;
import server.service.Service;
import server.service.flag.MessageFlag;
import server.service.handler.HandlerFactory;
import server.service.handler.impl.OfflineHandler;
import server.service.thread.ServerListener;
import server.util.DBUtil;
import server.view.ControlledStage;
import server.view.StageController;
import server.view.main.userlist.Manager;
import server.view.main.userlist.UserStateList;

public class ManagementController implements ControlledStage {
	StageController stageController;
	@FXML
	private TextField port;
	@FXML
	private Label topTip;
	@FXML
	private Label tipOn;
	@FXML
	private ListView<User> listLeft;
	@FXML
	private ImageView userImg;
	@FXML
	private Label nameLabel;
	@FXML
	private Label accountLabel;
	@FXML
	private Label passwordLabel;
	@FXML
	private Label birthdayLabel;
	@FXML
	private Label sexLabel;
	@FXML
	private Label statusLabel;

	public ManagementController() {
		ServerOut.manager = this;// 拙略的方法
		Service.bind(5258);
		Manager.initMap();
		OfflineHandler handler = new OfflineHandler();
		handler.setCall(() -> {
			Platform.runLater(() -> {
				showUserDetail(listLeft.getSelectionModel().getSelectedItem());
			});
		});
		HandlerFactory.register(MessageFlag.OFFLINE, handler);
	}

	/**
	 * 
	 * 初始化控制器类,加载fxml文件后,此方法会自动调用。 <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月28日 下午12:38:45
	 */
	@FXML
	private void initialize() {
		listLeft.setItems(Manager.list);
		listLeft.setCellFactory((param) -> new UserListCellFactory());
		listLeft.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			showUserDetail(newValue);
		});
	}

	/**
	 * 处理修改端口
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月28日 下午12:42:09
	 */
	@FXML
	private void handleModifyPort() {
		port.setEditable(true);
	}

	/**
	 * 点击提交修改的端口
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月28日 下午12:41:33
	 */
	@FXML
	private void handleSubmitPort() {
		port.setEditable(false);
	}

	@FXML
	private void handleOn() {
		tipOn.setText("开启");
	}

	@FXML
	private void handleClose() {
		tipOn.setText("关闭");
	}

	/**
	 * 点击强制下线
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月28日 下午12:41:07
	 */
	@FXML
	private void handleForcedOffline() {
		User user = listLeft.getSelectionModel().getSelectedItem();
		if (user != null) {
			if (UserStateList.getState(user.getAccount())) {
				ServerOut.RespondKickOff(ServerListener.getSocket(user.getAccount()));
			} else {
				showTip(topTip, 2000L, "该用户未上线");
			}
		} else {
			showTip(topTip, 2000L, "未选择用户");
		}
	}

	/**
	 * 点击删除用户
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月28日 下午12:40:48
	 */
	@FXML
	private void handleDeleteUser() {
		User user = listLeft.getSelectionModel().getSelectedItem();
		if (user != null) {
			ServerOut.RespondKickOff(ServerListener.getSocket(user.getAccount()));
			Manager.deleteUser(user);
			DBUtil.deleteUserInfo(user.getAccount());
			DBUtil.clearFriend(user.getAccount());

		} else {
			showTip(topTip, 2000L, "未选择用户");
		}
	}

	/**
	 * 点击禁言
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年5月28日 下午12:40:36
	 */
	@FXML
	private void handleBannedPost() {

	}

	public void showSelected() {
		showUserDetail(listLeft.getSelectionModel().getSelectedItem());
	}

	/**
	 * 显示用户信息
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年6月2日 上午12:12:31
	 */
	private void showUserDetail(User user) {
		if (user != null) {
			try {
				userImg.setImage(new Image(new FileInputStream(user.getHeadImagePath())));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			nameLabel.setText(user.getUserName());
			accountLabel.setText(user.getAccount());
			passwordLabel.setText(user.getPassWord());
			birthdayLabel.setText(user.getBirthday().toString());
			sexLabel.setText(user.getSex() ? "男" : "女");
			statusLabel.setText(UserStateList.getState(user.getAccount()) ? "在线" : "离线");
		} else {
			userImg.setImage(null);
			nameLabel.setText("");
			accountLabel.setText("");
			passwordLabel.setText("");
			birthdayLabel.setText("");
			sexLabel.setText("");
			statusLabel.setText("");
		}
	}

	@Override
	public void setStageController(StageController stageController) {
		this.stageController = stageController;
	}

	public static ExecutorService pool = new ThreadPoolExecutor(0, 1, 0L, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(1));// 创建线程池,单线池

	/**
	 * 提示方法
	 * 
	 * @param label
	 * @param millis
	 * @param tipContent <b>Author:</b>
	 *                   <a href="https://github.com/MysticalDream" target=
	 *                   "_blank">MysticalDream</a> <b>Date:</b> 2021年5月29日
	 *                   下午3:18:58
	 */
	private void showTip(Label label, long millis, String tipContent) {
		label.setText(tipContent);
		try {
			pool.execute(() -> {
				label.setVisible(true);
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					System.out.println("睡眠中断");
					e.printStackTrace();
				}
				label.setVisible(false);
			});
		} catch (RejectedExecutionException e) {
			return;
		}
	}
}
