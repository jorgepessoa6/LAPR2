package lapr2.serviceproviderapplication.scene;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lapr2.framework.company.service.ServiceManager;
import lapr2.framework.homeservices.HomeServices;
import lapr2.serviceproviderapplication.controller.warnings.WarningController;
import lombok.Getter;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

public class SceneManager {

	private static final String SELECT_STYLE_CLASS = "selected";

	@Getter
	private Stage stage;

	public SceneManager(Stage stage) {
		this.stage = stage;
		this.stage.setTitle("Service Provider Application by flow");
		this.stage.getIcons().add(new Image(ServiceManager.class.getResourceAsStream("/images/users/request.png")));

		stage.setOnCloseRequest(event -> {
			stage.close();
			System.exit(0);
		});
	}

	public void loadMainPage() {
		loadFXML("/fxml/mainpage.fxml");
	}

	public void selectPane(Set<Pane> optionPanes, Pane paneContent, Pane pane, String filepath) {
		paneContent.getChildren().clear();

		for (Pane optionPane : optionPanes)
			optionPane.getStyleClass().remove(SELECT_STYLE_CLASS);
		pane.getStyleClass().add(SELECT_STYLE_CLASS);

		Node node = loadNode(filepath);

		paneContent.getChildren().add(node);
	}

	public Initializable loadTitledPane(String fxmlPath) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/singletitledpane.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Could not load stage with fxml path " + loader.getLocation().toString(), e);
		}
		return loader.getController();
	}

	private Node loadNode(String filePath) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));

		return loadParent(loader);
	}

	private Initializable loadFXML(String filePath) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));

		Parent root = loadParent(loader);

		Scene scene = new Scene(root);

		stage.setResizable(false);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.centerOnScreen();
		stage.show();

		return loader.getController();
	}

	private Parent loadParent(FXMLLoader loader) {
		Parent root = null;

		try {
			root = loader.load();
		} catch (IOException e) {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Could not load stage with fxml path" + loader.getLocation().toString(), e);
		}

		return root;
	}

	public void loadWarningPopUp(String title, String content, String imagePath) {
		Stage warningStage = new Stage();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/warnings/warningpopup.fxml"));

		Parent root = loadParent(loader);

		WarningController warningController = loader.getController();

		Scene scene = new Scene(root);

		warningController.setContent(title, content, imagePath);

		warningStage.initStyle(StageStyle.UNDECORATED);
		warningStage.setResizable(false);
		warningStage.setScene(scene);
		warningStage.sizeToScene();
		warningStage.centerOnScreen();
		warningStage.initModality(Modality.APPLICATION_MODAL);
		warningStage.showAndWait();
		warningStage.requestFocus();
	}

}
