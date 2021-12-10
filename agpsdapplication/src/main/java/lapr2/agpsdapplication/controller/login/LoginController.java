package lapr2.agpsdapplication.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.user.user.User;
import lapr2.framework.company.user.user.UserManager;
import lapr2.framework.exceptions.InvalidLoginException;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.session.CurrentSession;
import lapr2.framework.utils.WordUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

	@FXML
	private Label warningsLabel;

	@FXML
	private TextField emailField;

	@FXML
	private PasswordField passwordField;

	private UserManager userManager;

	private CurrentSession currentSession;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userManager = HomeServices.getInstance().getCompany().getDataManager().get(UserManager.class);
		currentSession = HomeServices.getInstance().getSession();
	}

	public void login(ActionEvent actionEvent) {
		if (emailField.getText().isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The e-mail cannot be empty", 2, 0.1d);
		} else if (!WordUtils.isEmailValid(emailField.getText())) {
			WarningUtilities.showLabelWarning(warningsLabel, "The e-mail is not valid", 2, 0.1d);
		} else if (userManager.getUser(emailField.getText()) == null) {
			WarningUtilities.showLabelWarning(warningsLabel, "There is no user with that email", 2, 0.1d);
		} else if (passwordField.getText().isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The password cannot be empty", 2, 0.1d);
		} else if (!userManager.getUser(emailField.getText()).getPassword().equals(passwordField.getText())) {
			WarningUtilities.showLabelWarning(warningsLabel, "The password is incorrect", 2, 0.1d);
		} else {
			try {
				currentSession.login(emailField.getText(), passwordField.getText());

				User user = currentSession.getUser();

				if (user.getRoles().size() == 1) {
					Application.getSceneManager().loadUserPage(user.getRoles().iterator().next());
				} else {
					Application.getSceneManager().loadRolePage();
				}
			} catch (InvalidLoginException e) {
				WarningUtilities.showLabelWarning(warningsLabel, e.getMessage(), 2, 0.1d);
			}
		}
	}

	public void onSubmitApplication(MouseEvent mouseEvent) {
		Application.getSceneManager().loadSubmitApplicationPage();
	}

	public void onRegisterClient(MouseEvent mouseEvent) {
		Application.getSceneManager().loadRegisterClient();
	}
}
