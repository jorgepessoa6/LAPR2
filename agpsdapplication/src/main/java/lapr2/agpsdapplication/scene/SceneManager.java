package lapr2.agpsdapplication.scene;

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
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.controller.warnings.WarningController;
import lapr2.framework.company.service.ServiceManager;
import lapr2.framework.company.user.user.User;
import lapr2.framework.homeservices.HomeServices;
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
		this.stage.setTitle("Home Services Management by flow");
		this.stage.getIcons().add(new Image(ServiceManager.class.getResourceAsStream("/images/users/request.png")));

		stage.setOnCloseRequest(event -> {
			stage.close();
			HomeServices.getInstance().getCompany().getDataManager().saveAll();
			System.exit(0);
		});
	}

	public void loadLoginPage() {
		loadFXML("/fxml/loginpage.fxml");
	}

	public void loadRolePage() {
		Stage roleStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/rolepage.fxml"));

		Parent root = loadParent(loader);

		Scene scene = new Scene(root);

		roleStage.setResizable(false);
		roleStage.setScene(scene);
		roleStage.sizeToScene();
		roleStage.initModality(Modality.APPLICATION_MODAL);
		roleStage.showAndWait();
		roleStage.requestFocus();
	}

	public void loadUserPage(User.Role role) {
		switch (role) {
			case ADMINISTRATOR:
				loadFXML("/fxml/administrator/administratorpage.fxml");
				break;
			case CLIENT:
				loadFXML("/fxml/client/clientpage.fxml");
				break;
			case HUMAN_RESOURCES_OFFICER:
				loadFXML("/fxml/humanresourcesofficer/humanresourcesofficerpage.fxml");
				break;
			case SERVICE_PROVIDER:
				loadFXML("/fxml/serviceprovider/serviceproviderpage.fxml");
				break;
			default:
				System.exit(0);
				break;
		}
	}

	public void loadSubmitApplicationPage() {
		loadFXML("/fxml/unregistereduser/submitapplicationpage.fxml");
	}

	private Initializable loadFXML(String filePath) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));

		Parent root = loadParent(loader);

		assert root != null;

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

	public Node loadNode(String filePath) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));

		return loadParent(loader);
	}

	public Node loadNode(String filePath, Initializable initializable) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(filePath));

		Parent root = loadParent(loader);

		ChildrenController childrenController = loader.getController();
		childrenController.setParentController(initializable);

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

	public void selectPane(Set<Pane> optionPanes, Pane paneContent, Pane pane, String filepath) {
		paneContent.getChildren().clear();

		for (Pane optionPane : optionPanes) {
			optionPane.getStyleClass().remove(SELECT_STYLE_CLASS);
		}

		pane.getStyleClass().add(SELECT_STYLE_CLASS);

		paneContent.getChildren().clear();

		Node node = Application.getSceneManager().loadNode(filepath);

		paneContent.getChildren().add(node);
	}

	public void selectPane(Set<Pane> optionPanes, Pane paneContent, Pane pane, String filepath, Initializable initializable) {
		paneContent.getChildren().clear();

		for (Pane optionPane : optionPanes) {
			optionPane.getStyleClass().remove(SELECT_STYLE_CLASS);
		}

		pane.getStyleClass().add(SELECT_STYLE_CLASS);

		paneContent.getChildren().clear();

		Node node = Application.getSceneManager().loadNode(filepath, initializable);

		paneContent.getChildren().add(node);
	}

	public void loadRegisterClient() {
		loadFXML("/fxml/unregistereduser/registerclientpage.fxml");
	}
}
