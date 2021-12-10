package lapr2.agpsdapplication.controller.unregistereduser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.location.postcode.PostCodeManager;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.company.user.user.User;
import lapr2.framework.company.user.user.UserManager;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.utils.WordUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterClientController implements Initializable {

	@FXML
	public TextField txtPostCodeStr;

	@FXML
	public TextField txtAddress;

	@FXML
	public TextField txtFullName;

	@FXML
	public TextField txtTaxIdentificationNumber;

	@FXML
	public TextField txtEmail;

	@FXML
	public PasswordField txtConfirmPassword;

	@FXML
	public TextField txtPhoneNumber;

	@FXML
	public PasswordField txtPassword;

	@FXML
	public Label warningsLabel;

	@FXML
	public Button btnRegisterClient;

	private UserManager userManager;

	private ClientManager clientManager;

	private PostCodeManager postCodeManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		postCodeManager = HomeServices.getInstance().getCompany().getDataManager().get(PostCodeManager.class);
		clientManager = HomeServices.getInstance().getCompany().getDataManager().get(ClientManager.class);
		userManager = HomeServices.getInstance().getCompany().getDataManager().get(UserManager.class);
	}

	public void onRegisterClient(ActionEvent actionEvent) {
		String name = txtFullName.getText().trim();
		String email = txtEmail.getText().trim();
		String password = txtPassword.getText().trim();
		String confirmPassword = txtConfirmPassword.getText().trim();
		String taxIdentificationNumber = txtTaxIdentificationNumber.getText().trim();
		String phoneNumber = txtPhoneNumber.getText().trim();
		String postCodeStr = txtPostCodeStr.getText().trim();
		String address = txtAddress.getText().trim();

		if (!isClientValid(name, email, password, confirmPassword, taxIdentificationNumber, phoneNumber, postCodeStr, address))
			return;

		PostCode postCode = postCodeManager.getPostCode(postCodeStr);
		PostalAddress postalAddress = new PostalAddress(address, postCode);
		Client client = clientManager.createClient(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
		if (clientManager.isSecure(client)) {
			clientManager.add(client);
			User user = userManager.createUser(email, password);
			userManager.add(user);
			user.addRole(User.Role.CLIENT);
			Application.getSceneManager().loadWarningPopUp("Success", "The client was added with success", "/images/warnings/success.png");
			Application.getSceneManager().loadLoginPage();
		} else {
			WarningUtilities.showLabelWarning(warningsLabel, "An unexpected error occurred", 2, 0.1d);
		}
	}

	public void onCancel(ActionEvent actionEvent) {
		Application.getSceneManager().loadLoginPage();
	}

	private boolean isClientValid(String name, String email, String password, String confirmPassword, String taxIdentificationNumber, String phoneNumber, String postCodeStr, String address) {
		boolean isValid = true;

		if (name.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The full name cannot be empty", 2, 0.1d);
			txtFullName.requestFocus();
			isValid = false;
		} else if (!isEmailValid(email)) {
			txtEmail.requestFocus();
			isValid = false;
		} else if (password.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The password cannot be empty", 2, 0.1d);
			txtPassword.requestFocus();
			isValid = false;
		} else if (confirmPassword.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The password cannot be empty", 2, 0.1d);
			txtConfirmPassword.requestFocus();
			isValid = false;
		} else if (!confirmPassword.equals(password)) {
			WarningUtilities.showLabelWarning(warningsLabel, "The passwords are different", 2, 0.1d);
			txtConfirmPassword.requestFocus();
			isValid = false;
		} else if (taxIdentificationNumber.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The tax identification number cannot be empty", 2, 0.1d);
			txtTaxIdentificationNumber.requestFocus();
			isValid = false;
		} else if (!isTaxIdentificationNumberValid(taxIdentificationNumber)) {
			txtTaxIdentificationNumber.requestFocus();
			isValid = false;
		} else if (phoneNumber.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The phone number cannot be empty", 2, 0.1d);
			txtPhoneNumber.requestFocus();
			isValid = false;
		} else if (!WordUtils.isPhoneNumberValid(phoneNumber)) {
			WarningUtilities.showLabelWarning(warningsLabel, "The phone number is not valid", 2, 0.1d);
			txtPhoneNumber.requestFocus();
			isValid = false;
        } else if (postCodeStr.isEmpty()) {
            WarningUtilities.showLabelWarning(warningsLabel, "The postcode cannot be empty", 2, 0.1d);
            txtPostCodeStr.requestFocus();
            isValid = false;
        } else if (!WordUtils.isPostCodeValid(postCodeStr)) {
            WarningUtilities.showLabelWarning(warningsLabel, "The postcode is not valid", 2, 0.1d);
            txtPostCodeStr.requestFocus();
            isValid = false;
        } else if (postCodeManager.getPostCode(postCodeStr) == null) {
            WarningUtilities.showLabelWarning(warningsLabel, "The postcode is not valid", 2, 0.1d);
            isValid = false;
        } else if (address.isEmpty()) {
            WarningUtilities.showLabelWarning(warningsLabel, "The address cannot be empty", 2, 0.1d);
            txtAddress.requestFocus();
            isValid = false;
        }

		return isValid;
	}

	private boolean isEmailValid(String email) {
		boolean isValid = true;

		if (email.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The e-mail cannot be empty", 2, 0.1d);
			isValid = false;
		} else if (!WordUtils.isEmailValid(email)) {
			WarningUtilities.showLabelWarning(warningsLabel, "The e-mail is not valid", 2, 0.1d);
			isValid = false;
		} else if (clientManager.getClient(email) != null) {
			WarningUtilities.showLabelWarning(warningsLabel, "The e-mail is already in use", 2, 0.1d);
			isValid = false;
		}

		return isValid;
	}

	private boolean isTaxIdentificationNumberValid(String taxIdentificationNumber) {
		boolean isValid = true;

		if (!WordUtils.isTaxIdentificationNumberValid(taxIdentificationNumber)) {
			WarningUtilities.showLabelWarning(warningsLabel, "The tax identification number is not valid", 2, 0.1d);
			isValid = false;
		} else if (clientManager.getClientTIN(taxIdentificationNumber) != null) {
			WarningUtilities.showLabelWarning(warningsLabel, "The tax identification number is already in use", 2, 0.1d);
			isValid = false;
		}

		return isValid;
	}
}