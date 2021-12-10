package lapr2.agpsdapplication.controller.humanresourcesofficer;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.candidature.Candidature;
import lapr2.framework.company.candidature.CandidatureManager;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.category.CategoryManager;
import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.location.postcode.PostCodeManager;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.company.user.user.User;
import lapr2.framework.company.user.user.UserManager;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.utils.WordUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterServiceProviderController implements Initializable, ChildrenController {

	@FXML
	public TextField txtTaxIdentificationNumber;

	@FXML
	public TextField txtMechanographicalNumber;

	@FXML
	public TextField txtFullName;

	@FXML
	public TextField txtAbbreviatedName;

	@FXML
	public TextField txtEmail;

	@FXML
	public TextField txtPostCodeStr;

	@FXML
	public ComboBox<Category> categoryComboBox;

	@FXML
	public ComboBox<GeographicalArea> geographicalAreaComboBox;

	@FXML
	public Label warningsLabel;

	@FXML
	private TableView<Category> categoryTableView;

	@FXML
	private TableView<GeographicalArea> geographicalAreaTableView;

	@FXML
	private TableColumn<Category, String> idColumn;

	@FXML
	private TableColumn<Category, String> descriptionColumn;

	@FXML
	private TableColumn<GeographicalArea, String> designationColumn;

	@FXML
	private TableColumn<GeographicalArea, String> postcodeColumn;

	@FXML
	private TableColumn<GeographicalArea, Double> radiusColumn;

	private CandidatureManager candidatureManager;

	private ServiceProviderManager serviceProviderManager;

	private UserManager userManager;

	private PostCodeManager postCodeManager;

	private List<Category> categoryList;

	private List<GeographicalArea> geographicalAreaList;

	private HumanResourcesOfficerPageController humanResourcesOfficerPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CategoryManager categoryManager = HomeServices.getInstance().getCompany().getDataManager().get(CategoryManager.class);
		GeographicalAreaManager geographicalAreaManager = HomeServices.getInstance().getCompany().getDataManager().get(GeographicalAreaManager.class);
		userManager = HomeServices.getInstance().getCompany().getDataManager().get(UserManager.class);
		candidatureManager = HomeServices.getInstance().getCompany().getDataManager().get(CandidatureManager.class);
		serviceProviderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class);
		postCodeManager = HomeServices.getInstance().getCompany().getDataManager().get(PostCodeManager.class);
		categoryComboBox.setItems(FXCollections.observableArrayList(categoryManager.getElements()));
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
		postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("centeredPostCode"));
		radiusColumn.setCellValueFactory(new PropertyValueFactory<>("radius"));
		geographicalAreaComboBox.setItems(FXCollections.observableArrayList(geographicalAreaManager.getElements()));
		categoryList = new ArrayList<>();
		geographicalAreaList = new ArrayList<>();
		this.txtTaxIdentificationNumber.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> onGetCandidature(newValue));
	}

	private void onGetCandidature(Boolean value) {
		if (!value) {
			String taxIdentificationNumber = txtTaxIdentificationNumber.getText().trim();
			if (WordUtils.isTaxIdentificationNumberValid(taxIdentificationNumber)) {
				Candidature candidature = candidatureManager.getCandidature(taxIdentificationNumber);
				if (candidature != null) {
					txtFullName.setText(candidature.getName());
					txtEmail.setText(candidature.getEmail());
					txtPostCodeStr.setText(candidature.getPostalAddress().getPostCode().toString());
					for (Category category : candidature.getCategories()) {
						categoryList.add(category);
						categoryTableView.getItems().add(category);
					}
				} else {
					WarningUtilities.showLabelWarning(warningsLabel, "There is no candidature with that tax identification number", 2, 0.1d);
				}
			} else if (taxIdentificationNumber.isEmpty()) {
				WarningUtilities.showLabelWarning(warningsLabel, "The tax identification number cannot be empty", 2, 0.1d);
				txtTaxIdentificationNumber.requestFocus();
			} else {
				WarningUtilities.showLabelWarning(warningsLabel, "The tax identification number is not valid", 2, 0.1d);
				txtTaxIdentificationNumber.requestFocus();
			}
		}
	}

	public void onRegisterServiceProvider(ActionEvent event) {
		String mechanographicalNumber = txtMechanographicalNumber.getText().trim();
		String fullName = txtFullName.getText().trim();
		String abbreviatedName = txtAbbreviatedName.getText().trim();
		String email = txtEmail.getText().trim();
		String postcodeStr = txtPostCodeStr.getText().trim();

		if (!isServiceProviderValid(mechanographicalNumber, fullName, abbreviatedName, email, postcodeStr)) return;


		PostCode postCode = postCodeManager.getPostCode(postcodeStr);
		ServiceProvider serviceProvider = serviceProviderManager.createServiceProvider(mechanographicalNumber, fullName, abbreviatedName, email, postCode);
		if (serviceProviderManager.isSecure(serviceProvider)) {
			serviceProviderManager.add(serviceProvider);

			String email1 = serviceProvider.getEmail();
			String password = WordUtils.generatePassword(6);

			User user = userManager.createUser(email1, password);
			userManager.add(user);
			user.addRole(User.Role.SERVICE_PROVIDER);
			for (Category category : categoryList) {
				serviceProvider.addCategory(category);
			}
			for (GeographicalArea geographicalArea : geographicalAreaList) {
				serviceProvider.addGeographicalArea(geographicalArea);
			}

			Application.getSceneManager().loadWarningPopUp("Success",
					String.format("The service provider was successfully registered.%nEmail: %s%nPassword: %s", email, password)
					, "/images/warnings/success.png");

			humanResourcesOfficerPageController.onHome(null);
		} else {
			WarningUtilities.showLabelWarning(warningsLabel, "An unexpected error occurred", 2, 0.1d);
		}

	}

	public void onAddCategory() {
		Category category = categoryComboBox.getValue();
		if (category != null) {
			if (!categoryList.contains(category)) {
				categoryList.add(category);
				categoryTableView.getItems().add(category);
			} else {
				WarningUtilities.showLabelWarning(warningsLabel, "That category is already in registered", 2, 0.1d);
			}
		} else {
			WarningUtilities.showLabelWarning(warningsLabel, "Choose a category", 2, 0.1d);
			categoryComboBox.requestFocus();
		}
	}

	public void onAddGeographicalArea() {
		GeographicalArea geographicalArea = geographicalAreaComboBox.getValue();
		if (geographicalArea != null) {
			if (!geographicalAreaList.contains(geographicalArea)) {
				geographicalAreaList.add(geographicalArea);
				geographicalAreaTableView.getItems().add(geographicalArea);
			} else {
				WarningUtilities.showLabelWarning(warningsLabel, "That geographical area is already in registered", 2, 0.1d);
			}
		} else {
			WarningUtilities.showLabelWarning(warningsLabel, "Choose a geographical area", 2, 0.1d);
			geographicalAreaComboBox.requestFocus();
		}
	}

	public void onRemoveCategory() {
		Category category = categoryTableView.getSelectionModel().getSelectedItem();
		if (category != null) {
			categoryList.remove(category);
			categoryTableView.getItems().remove(category);
		} else {
			WarningUtilities.showLabelWarning(warningsLabel, "Choose a category to remove", 2, 0.1d);
			categoryTableView.requestFocus();
		}
	}

	public void onRemoveGeographicalArea() {
		GeographicalArea geographicalArea = geographicalAreaTableView.getSelectionModel().getSelectedItem();
		if (geographicalArea != null) {
			geographicalAreaList.remove(geographicalArea);
			geographicalAreaTableView.getItems().remove(geographicalArea);
		} else {
			WarningUtilities.showLabelWarning(warningsLabel, "Choose a geographical area to remove", 2, 0.1d);
			geographicalAreaTableView.requestFocus();
		}
	}

	@Override
	public void setParentController(Initializable initializable) {
		humanResourcesOfficerPageController = (HumanResourcesOfficerPageController) initializable;
	}

	private boolean isServiceProviderValid(String mechanographicalNumber, String fullName, String abbreviatedName, String email, String postcodeStr) {
		boolean isValid = true;

		if (mechanographicalNumber.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The mechanographical number cannot be empty", 2, 0.1d);
			txtMechanographicalNumber.requestFocus();
			isValid = false;
		} else if (serviceProviderManager.getServiceProviderMN(mechanographicalNumber) != null) {
			WarningUtilities.showLabelWarning(warningsLabel, "The mechanographical number is already in use", 2, 0.1d);
			txtMechanographicalNumber.requestFocus();
			isValid = false;
		} else if (fullName.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The full name cannot be empty", 2, 0.1d);
			txtFullName.requestFocus();
			isValid = false;
		} else if (abbreviatedName.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The abbreviated name cannot be empty", 2, 0.1d);
			txtAbbreviatedName.requestFocus();
			isValid = false;
		} else if (email.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The e-mail cannot be empty", 2, 0.1d);
			txtEmail.requestFocus();
			isValid = false;
		} else if (!WordUtils.isEmailValid(email)) {
			WarningUtilities.showLabelWarning(warningsLabel, "The e-mail is not valid", 2, 0.1d);
			txtEmail.requestFocus();
			isValid = false;
		} else if (serviceProviderManager.getServiceProvider(email) != null) {
			WarningUtilities.showLabelWarning(warningsLabel, "The e-mail is already in use", 2, 0.1d);
			txtEmail.requestFocus();
			isValid = false;
		} else if (postcodeStr.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The postcode cannot be empty", 2, 0.1d);
			txtPostCodeStr.requestFocus();
			isValid = false;
		} else if (!WordUtils.isPostCodeValid(postcodeStr)) {
			WarningUtilities.showLabelWarning(warningsLabel, "The post code is not valid", 2, 0.1d);
			txtPostCodeStr.requestFocus();
			isValid = false;
		} else if (postCodeManager.getPostCode(postcodeStr) == null) {
			WarningUtilities.showLabelWarning(warningsLabel, "The postcode is not valid", 2, 0.1d);
			txtPostCodeStr.requestFocus();
			isValid = false;
		} else if (categoryList.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The service provider needs categories", 2, 0.1d);
			categoryComboBox.requestFocus();
			isValid = false;
		} else if (geographicalAreaList.isEmpty()) {
			WarningUtilities.showLabelWarning(warningsLabel, "The service provider needs geographical areas", 2, 0.1d);
			geographicalAreaComboBox.requestFocus();
			isValid = false;
		}

		return isValid;
	}
}

