package lapr2.serviceproviderapplication.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import lapr2.serviceproviderapplication.Application;
import lapr2.serviceproviderapplication.scene.SceneManager;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

	@FXML
	private Pane paneContent;

	@FXML
	private FlowPane paneHome;

	@FXML
	private FlowPane paneImportServiceExecutionOrders;

	@FXML
	private FlowPane paneViewServiceExecutionOrders;

	@FXML
	private FlowPane paneViewHistoricalTimeline;

	private HashSet<Pane> optionPanes = new HashSet<>();

	private SceneManager sceneManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sceneManager = Application.getSceneManager();

		optionPanes.addAll(Arrays.asList(paneHome, paneImportServiceExecutionOrders, paneViewServiceExecutionOrders, paneViewHistoricalTimeline));
	}

	public void onHome(MouseEvent mouseEvent) {
		final String styleClass = "selected";

		paneContent.getChildren().clear();

		for (Pane optionPane : optionPanes)
			optionPane.getStyleClass().remove(styleClass);
		paneHome.getStyleClass().add(styleClass);
	}

	public void onImportServiceExecutionOrders(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneImportServiceExecutionOrders, "/fxml/importserviceexecutionorderspage.fxml");
	}

	public void onViewServiceExecutionOrders(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewServiceExecutionOrders, "/fxml/viewserviceexecutionorderspage.fxml");
	}

	public void onViewHistoricalTimeline(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewHistoricalTimeline, "/fxml/viewtimelinepage.fxml");
	}
}
