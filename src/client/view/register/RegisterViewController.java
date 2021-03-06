package client.view.register;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;

import client.service.Request;
import client.service.flag.MessageFlag;
import client.service.handler.HandlerFactory;
import client.service.handler.impl.RegisterHandler;
import client.service.handler.impl.RegisterHandler.Callback;
import client.util.DateUtil;
import client.util.pattern.MatchUtil;
import client.util.pattern.Regex;
import client.util.tip.ShowTipUtil;
import client.util.tip.TipUtil;
import client.view.ControlledStage;
import client.view.FXMLConstant;
import client.view.StageController;
import file.FileManager;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.Tip;
import models.User;

public class RegisterViewController implements ControlledStage {

	private StageController stageController;
	@FXML
	private ImageView headImage;
	@FXML
	private Label topTip;
	@FXML
	private Label tip1;
	@FXML
	private Label tip2;
	@FXML
	private Label tip3;
	@FXML
	private Label tip4;
	@FXML
	private Label tip5;
	@FXML
	private RadioButton man;
	@FXML
	private RadioButton girl;
	@FXML
	private DatePicker birthday;
	@FXML
	private TextField userName;
	@FXML
	private TextField account;
	@FXML
	private PasswordField passWord1;
	@FXML
	private PasswordField passWord2;

	private String imgPath = null;

	private final static int LIMIT = 11;
	private final static int NAMELIMIT = 20;
	private final static int PASSLIMIT = 20;

	public RegisterViewController() {
		RegisterHandler handler = new RegisterHandler();
		handler.setCall(new Callback() {

			@Override
			public void success() {
				Platform.runLater(() -> {
					stageController.getStage(FXMLConstant.REGISTERID).close();

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("??????");
					alert.setHeaderText("??????");
					alert.setContentText("????????????");
					alert.show();
					stageController.unloadStage(FXMLConstant.REGISTERID);
				});
			}

			@Override
			public void duplicate() {
				Platform.runLater(() -> {
					ShowTipUtil.showTip(tip2, 2000L, "?????????????????????????????????????????????");
				});
			}
		});
		HandlerFactory.register(MessageFlag.REGISTER, handler);
	}

	UnaryOperator<TextFormatter.Change> formatter1 = (t) -> {
		if (t.isContentChange()) {
			if (t.getControlNewText().length() == 0) {
				return t;
			}
			if (t.getControlNewText().indexOf(" ") != -1) {
				ShowTipUtil.showTip(tip1, 1000L, "????????????????????????");
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
			try {
				Long.parseLong(t.getControlNewText());
			} catch (NumberFormatException e) {
				ShowTipUtil.showTip(tip2, 1000L, "??????????????????");
				return null;
			}
		}
		return t;
	};
	UnaryOperator<TextFormatter.Change> formatter3 = (t) -> {
		if (t.isContentChange()) {
			if (t.getControlNewText().length() == 0) {
				return t;
			}
			Matcher m = MatchUtil.getMatcher(Regex.CHINESE, t.getControlNewText());
			if (t.getControlNewText().indexOf(" ") != -1 || m.find()) {
				ShowTipUtil.showTip(tip3, 1000L, "????????????");
				return null;
			}
		}
		return t;
	};
	UnaryOperator<TextFormatter.Change> formatter4 = (t) -> {
		if (t.isContentChange()) {
			if (t.getControlNewText().length() == 0) {
				return t;
			}
			Matcher m = MatchUtil.getMatcher(Regex.CHINESE, t.getControlNewText());
			if (t.getControlNewText().indexOf(" ") != -1 || m.find()) {
				ShowTipUtil.showTip(tip4, 1000L, "????????????");
				return null;
			}
		}
		return t;
	};

	/**
	 * ??????FXML?????????
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021???5???29??? ??????3:35:44
	 */
	@FXML
	private void initialize() {
		// ???????????????????????????
		birthday.setDayCellFactory((DatePicker param) -> {
			return new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					if (item.isAfter(LocalDate.now())) {
						setDisable(true);
					}
				}
			};
		});

		// ?????????????????????
		userName.lengthProperty().addListener((ObservableValue<? extends Number> arg0, Number arg1, Number arg2) -> {
			Tip tip = new Tip(tip1, "??????????????????" + NAMELIMIT + "?????????????????????", 1000L);
			String text = TipUtil.limitLength(userName.getText(), NAMELIMIT, tip);
			if (text != null) {
				userName.setText(text);
			}
		});

		// ?????????????????????
		userName.setTextFormatter(new TextFormatter<>(formatter1));

		// ??????????????????
		account.lengthProperty().addListener((ObservableValue<? extends Number> arg0, Number arg1, Number arg2) -> {
			Tip tip = new Tip(tip2, "??????????????????" + LIMIT + "????????????", 1000L);
			String text = TipUtil.limitLength(account.getText(), LIMIT, tip);
			if (text != null) {
				account.setText(text);
			}
		});

		// ??????????????????
		account.setTextFormatter(new TextFormatter<>(formatter2));
		// ??????????????????
		passWord1.lengthProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					Tip tip = new Tip(tip3, "??????????????????" + PASSLIMIT + "????????????", 1000L);
					String text = TipUtil.limitLength(passWord1.getText(), PASSLIMIT, tip);
					if (text != null) {
						passWord1.setText(text);
					}
				});
		// ??????????????????
		passWord2.lengthProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					Tip tip = new Tip(tip4, "??????????????????" + PASSLIMIT + "????????????", 1000L);
					String text = TipUtil.limitLength(passWord2.getText(), PASSLIMIT, tip);
					if (text != null) {
						passWord2.setText(text);
					}
				});
		// ??????????????????
		passWord1.setTextFormatter(new TextFormatter<>(formatter3));
		// ??????????????????
		passWord2.setTextFormatter(new TextFormatter<>(formatter4));
	}

	/**
	 * ????????????
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021???5???30??? ??????12:54:41
	 */
	@FXML
	private void handleSelectImg() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("????????????");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("GIF", "*.gif"), new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("BMP", "*.bmp"));
		File file = chooser.showOpenDialog(stageController.getStage(FXMLConstant.REGISTERID));// TODO ????????????????????????
		if (file == null) {
			return;
		}
		FileManager manager = new FileManager();
		manager.Changesave(file);
		try {
			InputStream is = new FileInputStream(manager.usersaveLocation);
			headImage.setImage(new Image(is));
			imgPath = manager.usersaveLocation;
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ????????????
	 *
	 * <b>Author:</b>
	 * <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
	 * <b>Date:</b> 2021???5???30??? ??????12:54:30
	 */
	@FXML
	private void handleClickRegister() {
		User user = null;
		if ((user = getInfo()) != null) {
			Request.verifyRegister(user);
		}
	}

	private User getInfo() {
		User user = new User();
		user.setSex(man.isSelected());
		user.setApplyDate(DateUtil.getLocalDateNow("http://www.baidu.com"));
		if (imgPath == null) {
			Platform.runLater(() -> {
				ShowTipUtil.showTip(topTip, 1000L, "???????????????");
			});
			return null;
		} else {
			user.setHeadImagePath(imgPath);
		}
		if (userName.getText().isBlank()) {
			Platform.runLater(() -> {
				ShowTipUtil.showTip(tip1, 1000L, "?????????????????????");
			});
			return null;
		} else {
			user.setUserName(userName.getText());
		}
		if (account.getText().isBlank()) {
			Platform.runLater(() -> {
				ShowTipUtil.showTip(tip2, 1000L, "??????????????????");
			});
			return null;
		} else {
			user.setAccount(account.getText());
		}
		if (passWord1.getText().isBlank()) {
			Platform.runLater(() -> {
				ShowTipUtil.showTip(tip3, 1000L, "??????????????????");
			});
			return null;
		}
		if (!passWord1.getText().equals(passWord2.getText())) {
			Platform.runLater(() -> {
				ShowTipUtil.showTip(tip3, 1000L, "???????????????????????????");
			});
			return null;
		}
		if (birthday.getValue() == null) {
			Platform.runLater(() -> {
				ShowTipUtil.showTip(tip5, 1000L, "???????????????");
			});
			return null;
		} else {
			user.setBirthday(birthday.getValue());
		}
		String passWord = passWord1.getText();
		user.setPassWord(passWord);
		return user;
	}

	@FXML
	private void handleClickCancel() {
		stageController.getStage(FXMLConstant.REGISTERID).close();
	}

	@Override
	public void setStageController(StageController stageController) {
		this.stageController = stageController;
	}
}
