package lapr2.agpsdapplication.controller.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.Company;
import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.location.postcode.PostCodeManager;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.utils.WordUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SpecifyGeographicalAreaController implements Initializable, ChildrenController {

	@FXML
	private TextField txtTravelCost;

	@FXML
	private TextField txtRadius;

	@FXML
	private TextField txtDesignation;

	@FXML
	private TextField txtPostCode;

	@FXML
	private Label lblWarning;

	private Company company;

	private PostCodeManager postCodeManager;

	private GeographicalAreaManager geographicalAreaManager;

	private AdministratorPageController administratorPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		company = HomeServices.getInstance().getCompany();

		postCodeManager = company.getDataManager().get(PostCodeManager.class);
		geographicalAreaManager = company.getDataManager().get(GeographicalAreaManager.class);

		this.txtPostCode.setPromptText("Postcode (e.g. " + postCodeManager.getRandomPostCodeDesignation() + ")");
	}

	public void onSpecify(ActionEvent actionEvent) {
		String designation = txtDesignation.getText().trim();
		String postCode = txtPostCode.getText().trim();
		String[] postCodeFields = postCode.split("-");
		String radius = txtRadius.getText().trim();
		String travelCost = txtTravelCost.getText().trim().replace(",", ".");

		if (designation.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The designation is not valid", 2, 0.1d);
			txtDesignation.requestFocus();
		} else if (postCode.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The postcode is not valid", 2, 0.1d);
			txtPostCode.requestFocus();
		} else if (postCodeFields.length != 2) {
			WarningUtilities.showLabelWarning(lblWarning, "The postcode must have 2 fields separated by '-'", 2, 0.1d);
			txtPostCode.requestFocus();
		} else if (!WordUtils.isInteger(postCodeFields[0]) || !WordUtils.isInteger(postCodeFields[1])) {
			WarningUtilities.showLabelWarning(lblWarning, "The postcode is only constituted by numbers", 2, 0.1d);
			txtPostCode.requestFocus();
		} else if (postCodeManager.getPostCode(postCode) == null) {
			WarningUtilities.showLabelWarning(lblWarning, "The postcode does not exists in the database", 2, 0.1d);
			txtDesignation.requestFocus();
		} else if (radius.isEmpty() || !WordUtils.isDouble(radius)) {
			WarningUtilities.showLabelWarning(lblWarning, "The radius is not valid", 2, 0.1d);
			txtRadius.requestFocus();
		} else if (travelCost.isEmpty() || !WordUtils.isDouble(travelCost)) {
			WarningUtilities.showLabelWarning(lblWarning, "The travel cost is invalid", 2, 0.1d);
			txtTravelCost.requestFocus();
		} else {
			PostCode centeredPostcode = postCodeManager.getPostCode(postCode);
			HashMap<PostCode, Double> acting = company.getExternalGeographicalService().getActing(centeredPostcode, Double.parseDouble(radius));
			GeographicalArea geographicalArea = geographicalAreaManager.createGeographicalArea(designation, centeredPostcode, Double.parseDouble(radius), Double.parseDouble(travelCost), acting);

			if (geographicalAreaManager.isSecure(geographicalArea) && geographicalArea.isValid()) {
				geographicalAreaManager.add(geographicalArea);

				Application.getSceneManager().loadWarningPopUp("Success", "The geographical area was successfully added", "/images/warnings/success.png");

				administratorPageController.onHome(null);
			} else {
				WarningUtilities.showLabelWarning(lblWarning, "The geographical area is not valid or duplicated", 2, 0.1d);
			}
		}
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.administratorPageController = (AdministratorPageController) initializable;
	}
}
