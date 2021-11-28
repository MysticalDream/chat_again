package client.view.login;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;

import client.service.Request;
import client.service.Service;
import client.service.flag.MessageFlag;
import client.service.handler.HandlerFactory;
import client.service.handler.impl.LoginHandler;
import client.service.handler.impl.LoginHandler.Callback;
import client.util.pattern.MatchUtil;
import client.util.pattern.Regex;
import client.util.tip.ShowTipUtil;
import client.util.tip.TipUtil;
import client.view.ControlledStage;
import client.view.FXMLConstant;
import client.view.StageController;
import client.view.chat.ChatInterfaceController;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import models.Tip;
import models.User;

public class LoginViewController implements ControlledStage {

	private StageController stageController;
	@FXML
	private TextField account;
	@FXML
	private Label tip1;
	@FXML
	private PasswordField passWord;
	@FXML
	private Label tip2;
	@FXML
	private CheckBox remember;
	private final static int LIMIT = 11;
	private final static int PASSLIMIT = 20;

	public LoginViewController() {
		Service.setCall(() -> {
			Platform.runLater(() -> {
				stageController.getStage(FXMLConstant.LOGINID).close();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("❌");
				alert.setHeaderText("失败");
				alert.setContentText("与服务器连接失败");
				alert.show();
			});
		});
		Service.connect();
		LoginHandler handler = new LoginHandler();
		handler.setCall(new Callback() {
			@Override
			public void inexistentAccount() {
				Platform.runLater(() -> {
					ShowTipUtil.showTip(tip1, 2000L, "该账号不存在，请先注册");
				});
			}

			@Override
			public void wrongPassword() {
				Platform.runLater(() -> {
					ShowTipUtil.showTip(tip2, 2000L, "密码错误");
				});
			}

			@Override
			public void success(User user) {
				ChatInterfaceController.user = user;

				Platform.runLater(() -> {
					stageController.loadStage(FXMLConstant.CHATID, FXMLConstant.CHATLOCA);
					Stage stage = stageController.getStage(FXMLConstant.CHATID);
					stage.setMinHeight(500);
					stage.setMinWidth(705);
					stageController.showStage(FXMLConstant.CHATID, FXMLConstant.LOGINID);
				});
			}

		});
		HandlerFactory.register(MessageFlag.LOGIN, handler);
	}

	UnaryOperator<TextFormatter.Change> formatter1 = (t) -> {
		if (t.isContentChange()) {
			if (t.getControlNewText().length() == 0) {
				return t;
			}
			try {
				Long.parseLong(t.getControlNewText());
			} catch (NumberFormatException e) {
				ShowTipUtil.showTip(tip1, 1000L, "请输入纯数字");
				return null;
			}
		}
		return t;
	};

	UnaryOperator<TextFormatter.Change> formatter2 = (t) -> {
		if (t.isContentChange()) {
			if (t.getControlNewText().length() == 0) {
				return t;
			}
			Matcher m = MatchUtil.getMatcher(Regex.CHINESE, t.getControlNewText());
			if (t.getControlNewText().indexOf(" ") != -1 || m.find()) {
				ShowTipUtil.showTip(tip2, 1000L, "非法输入");
				return null;
			}
		}
		return t;
	};

	@FXML
	private void initialize() {
		// 限制账号长度
		account.lengthProperty().addListener((ObservableValue<? extends Number> arg0, Number arg1, Number arg2) -> {
			Tip tip = new Tip(tip1, "请输入不大于" + LIMIT + "位的账号", 1000L);
			String text = TipUtil.limitLength(account.getText(), LIMIT, tip);
			if (text != null) {
				account.setText(text);
			}
		});
		// 账号格式限制
		account.setTextFormatter(new TextFormatter<>(formatter1));
		// 密码长度限制
		passWord.lengthProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					Tip tip = new Tip(tip2, "请输入不大于" + PASSLIMIT + "位的密码", 1000L);
					String text = TipUtil.limitLength(passWord.getText(), PASSLIMIT, tip);
					if (text != null) {
						passWord.setText(text);
					}
				});
		// 密码格式限制
		passWord.setTextFormatter(new TextFormatter<>(formatter2));

	}

	@FXML
	private void handleClickLogin() {
		verify();
	}

	/**
	 * 验证密码
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021年6月1日 下午2:56:01
	 */
	private void verify() {
		String ac;
		String pw;
		if ((ac = account.getText()).isBlank()) {
			Platform.runLater(() -> {
				ShowTipUtil.showTip(tip1, 1000L, "账号不能为空");
			});
			return;
		}
		if ((pw = passWord.getText()).isBlank()) {
			Platform.runLater(() -> {
				ShowTipUtil.showTip(tip2, 1000L, "密码不能为空");
			});
			return;
		}
		Request.verifyLogin(ac, pw);
	}

	@FXML
	private void handleClickFindPassWord() {

	}

	@FXML
	private void handleClickRegister() {
		stageController.loadStage(FXMLConstant.REGISTERID, FXMLConstant.REGISTERLOCA);
		stageController.getStage(FXMLConstant.REGISTERID).setResizable(false);
		stageController.show(FXMLConstant.REGISTERID, FXMLConstant.LOGINID);
	}

	@Override
	public void setStageController(StageController stageController) {
		this.stageController = stageController;
	}
}
