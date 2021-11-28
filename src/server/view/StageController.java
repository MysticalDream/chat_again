package server.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageController {

	/**
	 * 存储Stage的名称和对应对象
	 */
	private Map<String, Stage> stages = new ConcurrentHashMap<>();
	
	/**
	 * 将设置好的stage放入Map
	 * 
	 * @param name  stage名字
	 * @param stage Stage对象 <br/>
	 *              <b>Date:</b> 2021年6月7日 下午10:16:01
	 */
	public void addStage(String name, Stage stage) {
		this.stages.putIfAbsent(name, stage);
	}

	/**
	 * 依据Stage的名字name获取Stage对象
	 * 
	 * @param name
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月7日 下午10:18:08
	 */
	public Stage getStage(String name) {
		return this.stages.get(name);
	}

	/**
	 * 设置主舞台
	 * 
	 * @param name         主舞台名字
	 * @param primaryStage 主舞台对象 <br/>
	 *                     <b>Date:</b> 2021年6月7日 下午10:19:10
	 */
	public void setPrimaryStage(String name, Stage primaryStage) {
		this.addStage(name, primaryStage);
	}

	/**
	 * 加载FXML文件
	 * 
	 * @param name
	 * @param location
	 * @param styles
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月7日 下午10:38:31
	 */
	public boolean loadStage(String name, String location, StageStyle... styles) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(StageController.class.getResource(location));
			Pane pane = (Pane) loader.load();
			ControlledStage controlledStage = (ControlledStage) loader.getController();
			controlledStage.setStageController(this);
			Scene scene = new Scene(pane);
			Stage stage = new Stage();
			stage.setScene(scene);
			for (StageStyle style : styles) {
				stage.initStyle(style);
			}
			// 存储
			this.addStage(name, stage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 显示Stage
	 * 
	 * @param name
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月7日 下午10:46:10
	 */
	public boolean showStage(String name) {
		this.getStage(name).show();
		return true;
	}

	/**
	 * 显示Stage并关闭对应窗口
	 * 
	 * @param name
	 * @param closeName
	 * @return <br/>
	 *         <b>Date:</b> 2021年6月7日 下午10:46:59
	 */
	public boolean showStage(String showName, String closeName) {
		this.getStage(closeName).close();
		this.showStage(showName);
		return true;
	}

	public boolean unloadStage(String name) {
		if (stages.remove(name) == null) {
			System.out.println("移除窗口失败");
			return false;
		} else {
			System.out.println("移除成功");
			return true;
		}

	}
}