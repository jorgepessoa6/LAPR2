package lapr2.agpsdapplication.controller.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.Company;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postaladdress.PostalAddressList;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.location.postcode.PostCodeManager;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.company.user.user.User;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.utils.WordUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPostalAddressController implements Initializable, ChildrenController {

	@FXML
	private TextField txtAddress;

	@FXML
	private TextField txtPostCode;

	@FXML
	private Label lblWarnings;

	private PostCodeManager postCodeManager;

	private PostalAddressList postalAddressList;

	private ClientPageController clientPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HomeServices homeServices = HomeServices.getInstance();
		Company company = homeServices.getCompany();
		User user = homeServices.getSession().getUser();
		Client client = company.getDataManager().get(ClientManager.class).getClient(user.getEmail());

		this.postCodeManager = company.getDataManager().get(PostCodeManager.class);
		this.postalAddressList = client.getPostalAddressList();
		this.txtPostCode.setPromptText("e.g., " + postCodeManager.getRandomPostCodeDesignation());
	}

	public void onConfirm(ActionEvent actionEvent) {
		if (!areFieldsValid()) return;

		String address = txtAddress.getText().trim();
		String postCodeDesignation = txtPostCode.getText().trim();

		PostCode postCode = postCodeManager.getPostCode(postCodeDesignation);
		PostalAddress postalAddress = postalAddressList.createPostalAddress(address, postCode);

		if (postalAddressList.isSecure(postalAddress)) {
			postalAddressList.add(postalAddress);

			clientPageController.onHome(null);

			Application.getSceneManager().loadWarningPopUp("Success", "The postal address was successfully added", "/images/warnings/success.png");
		}
		else WarningUtilities.showLabelWarning(lblWarnings, "Please try another fields", 2, 0.1f);
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.clientPageController = (ClientPageController) initializable;
	}

	private boolean areFieldsValid() {
		String address = txtAddress.getText().trim();
		String postCodeDesignation = txtPostCode.getText().trim();

		boolean isValid = true;

		if (address.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarnings, "Address cannot be empty", 2, 0.1f);
			txtAddress.requestFocus();
			isValid = false;
		} else if (postCodeDesignation.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarnings, "Post code cannot be empty", 2, 0.1f);
			txtPostCode.requestFocus();
			isValid = false;
		} else if (!WordUtils.isPostCodeValid(postCodeDesignation)) {
			WarningUtilities.showLabelWarning(lblWarnings, "Post code is invalid", 2, 0.1f);
			txtPostCode.requestFocus();
			isValid = false;
		} else if (postCodeManager.getPostCode(postCodeDesignation) == null) {
			WarningUtilities.showLabelWarning(lblWarnings, "Post code does not exist", 2, 0.1f);
			txtPostCode.requestFocus();
			isValid = false;
		} else {
			try {
				PostCode postCode = postCodeManager.getPostCode(postCodeDesignation);
				PostalAddress postalAddress = new PostalAddress(address, postCode);
				if (!postalAddressList.isValid(postalAddress)) {
					WarningUtilities.showLabelWarning(lblWarnings, "Something went wrong\nTry another fields", 2, 0.1f);
					isValid = false;
				}
			} catch (Exception e) {
				WarningUtilities.showLabelWarning(lblWarnings, "Something went wrong\nTry another fields", 2, 0.1f);
				isValid = false;
			}
		}

		return isValid;
	}
}
