package lapr2.agpsdapplication.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import lapr2.agpsdapplication.Application;
import lapr2.framework.company.user.user.User;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.session.CurrentSession;

import java.net.URL;
import java.util.ResourceBundle;

public class RolePageController implements Initializable {

	@FXML
	private ComboBox<User.Role> roleComboBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CurrentSession currentSession = HomeServices.getInstance().getSession();

		roleComboBox.getItems().addAll(currentSession.getUser().getRoles());
	}

	public void onSelect(ActionEvent actionEvent) {
		Application.getSceneManager().loadUserPage(roleComboBox.getValue());

		((Stage) roleComboBox.getScene().getWindow()).close();
	}
}
