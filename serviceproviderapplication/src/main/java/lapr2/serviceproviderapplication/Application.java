package lapr2.serviceproviderapplication;

import javafx.stage.Stage;
import lapr2.serviceproviderapplication.scene.SceneManager;
import lombok.Getter;

public class Application extends javafx.application.Application {

	@Getter
	private static SceneManager sceneManager;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Application.sceneManager = new SceneManager(primaryStage);

		Application.sceneManager.loadMainPage();
	}
}
