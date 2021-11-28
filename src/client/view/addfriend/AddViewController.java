package client.view.addfriend;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import client.service.Request;
import client.service.messagehelper.MessageListButler;
import client.view.ControlledStage;
import client.view.StageController;
import client.view.chat.ChatInterfaceController;
import client.view.chat.FriendCellFactory;
import client.view.chat.manager.FriendListManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import models.User;
/**
 * 添加好友视图
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> client.view.addfriend
 * <br><b>ClassName:</b> AddViewController
 * <br><b>Date:</b> 2021年6月16日 下午2:34:35
 */
public class AddViewController implements ControlledStage {

	@SuppressWarnings("unused")
	private StageController stageController;

	@FXML
	private ListView<User> listView;
	@FXML
	private Label tip;

	@FXML
	private void initialize() {
		new Thread(() -> {
			Request.getUserList();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (UserList.getList() == null) {
			}
			;
			Platform.runLater(() -> {
				listView.setItems(UserList.getList());
			});
		}).start();
		listView.setCellFactory((param) -> new FriendCellFactory());
	}

	@FXML
	private void add() {
		int index = listView.getSelectionModel().getSelectedIndex();
		User user = listView.getSelectionModel().getSelectedItem();
		if (index == -1) {
			showTip(tip, 1000L, "请选择一个好友");
		} else {
			listView.getItems().remove(index);
			Request.addFriend(ChatInterfaceController.user, user);
			FriendListManager.addFriend(user);
			MessageListButler.storeMessageList(user.getAccount(), FXCollections.observableArrayList());
		}
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

	@Override
	public void setStageController(StageController stageController) {
		this.stageController = stageController;

	}

}
