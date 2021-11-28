package client.view.warning;

import client.service.Service;
import client.view.ControlledStage;
import client.view.StageController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReconnectViewController implements ControlledStage {

	@SuppressWarnings("unused")
	private StageController stageController;
	@FXML
	private Label tip;

	@FXML
	private void clickReconnect() {
		Service.reconnect();
	}

	@FXML
	private void clickExit() {
		System.exit(0);
	}

	@Override
	public void setStageController(StageController stageController) {
		this.stageController = stageController;
	}

}
