package server;

import javafx.application.Application;
import javafx.stage.Stage;
import server.view.FXMLConstant;
import server.view.StageController;
/**
 * 服务端main
 * @author <a href="https://github.com/MysticalDream" target="_blank">MysticalDream</a>
 * @version 1.0
 * <br><b>PackageName:</b> server
 * <br><b>ClassName:</b> MainApp
 * <br><b>Date:</b> 2021年6月16日 下午2:10:22
 */
public class MainApp extends Application {

	private StageController stageController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageController = new StageController();
		stageController.setPrimaryStage("primaryStage", primaryStage);
		stageController.loadStage(FXMLConstant.MAINID, FXMLConstant.MAINLOCA);
		stageController.getStage(FXMLConstant.MAINID).setOnCloseRequest((event) -> {
			System.exit(1);
		});
		stageController.showStage(FXMLConstant.MAINID);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
