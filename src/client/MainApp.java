package client;

import client.view.FXMLConstant;
import client.view.StageController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 客户端main
 * 
 * @author <a href="https://github.com/MysticalDream" target=
 *         "_blank">MysticalDream</a>
 * @version 1.0 <br>
 *          <b>PackageName:</b> client <br>
 *          <b>ClassName:</b> MainApp <br>
 *          <b>Date:</b> 2021年6月16日 下午2:09:11
 */
public class MainApp extends Application {

	private StageController stageController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageController = new StageController();
		stageController.setPrimaryStage("primaryStage", primaryStage);
		stageController.loadStage(FXMLConstant.LOGINID, FXMLConstant.LOGINLOCA);
		stageController.loadStage(FXMLConstant.RECONNECTID, FXMLConstant.RECONNECTLOCA, StageStyle.TRANSPARENT);
		Stage stage = stageController.getStage(FXMLConstant.LOGINID);
		stage.setResizable(false);
		stage.setOnCloseRequest((event) -> {
			System.exit(0);
		});
		stageController.showStage(FXMLConstant.LOGINID);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
