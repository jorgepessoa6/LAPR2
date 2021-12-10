package lapr2.agpsdapplication.controller.unregistereduser;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.candidature.Candidature;
import lapr2.framework.company.candidature.CandidatureManager;
import lapr2.framework.company.candidature.qualification.AcademicQualification;
import lapr2.framework.company.candidature.qualification.ProfessionalQualification;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.category.CategoryManager;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCodeManager;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.utils.WordUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubmitApplicationController implements Initializable {

	@FXML
	public Label lblWarning;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtTIN;

	@FXML
	private TextField txtPhone;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtPostalAddress;

	@FXML
	private TextField txtPostCode;

	@FXML
	private TextField txtAcademicDegree;

	@FXML
	private TextField txtAcademicDesignation;

	@FXML
	private TextField txtAcademicClassification;

	@FXML
	private TextField txtProfessionalDescription;

	@FXML
	private ComboBox<Category> cmbCategory;

	@FXML
	private TableView<Category> categoryTableView;

	@FXML
	private TableView<ProfessionalQualification> professionalQualificationTableView;

	@FXML
	private TableView<AcademicQualification> academicQualificationTableView;

	@FXML
	private TableColumn degreeColumn;

	@FXML
	private TableColumn designationColumn;

	@FXML
	private TableColumn classificationColumn;

	@FXML
	private TableColumn descriptionColumn;

	@FXML
	private TableColumn categoryColumn;

	private List<Category> categoryList;

	private List<AcademicQualification> academicQualificationList;

	private List<ProfessionalQualification> professionalQualificationList;

	private CandidatureManager candidatureManager;

	private PostCodeManager postCodeManager;

	private ServiceProviderManager serviceProviderManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CategoryManager categoryManager = HomeServices.getInstance().getCompany().getDataManager().get(CategoryManager.class);
		candidatureManager = HomeServices.getInstance().getCompany().getDataManager().get(CandidatureManager.class);
		postCodeManager = HomeServices.getInstance().getCompany().getDataManager().get(PostCodeManager.class);
		serviceProviderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class);

		cmbCategory.setItems(FXCollections.observableArrayList(categoryManager.getElements()));

		degreeColumn.setCellValueFactory(new PropertyValueFactory<>("degree"));
		designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
		classificationColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));

		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		this.categoryList = new ArrayList<>();
		this.academicQualificationList = new ArrayList<>();
		this.professionalQualificationList = new ArrayList<>();

		txtPostCode.setPromptText("e.g. " + postCodeManager.getRandomPostCodeDesignation());
	}

	public void onAddCandidature(ActionEvent actionEvent) {
		String taxIdentificationNumber = txtTIN.getText().trim();
		String name = txtName.getText().trim();
		String phone = txtPhone.getText().trim();
		String email = txtEmail.getText().trim();
		String postalAddressStr = txtPostalAddress.getText().trim();
		String postCodeStr = txtPostCode.getText().trim();

		if (!isCandidatureValid(taxIdentificationNumber, name, phone, email, postalAddressStr, postCodeStr)) return;

		PostalAddress pa = new PostalAddress(postalAddressStr, postCodeManager.getPostCode(postCodeStr));
		Candidature candidature = candidatureManager.createCandidature(name, taxIdentificationNumber, phone, email, pa);

		if (candidatureManager.isSecure(candidature)) {
			candidatureManager.add(candidature);
			for (Category category : categoryList) {
				candidature.addCategory(category);
			}
			for (AcademicQualification academicQualification : academicQualificationList) {
				candidature.addAcademicQualification(academicQualification);
			}
			for (ProfessionalQualification professionalQualification : professionalQualificationList) {
				candidature.addProfessionalQualification(professionalQualification);
			}
			Application.getSceneManager().loadWarningPopUp("Success", "The candidature was successfully submitted", "/images/warnings/success.png");
			Application.getSceneManager().loadLoginPage();
		} else {
			WarningUtilities.showLabelWarning(lblWarning, "The candidature is not valid or duplicated", 2, 0.1d);
		}

	}

	public void onCancel(ActionEvent actionEvent) {
		Application.getSceneManager().loadLoginPage();
	}

	public void onAddAcademicQualification(ActionEvent actionEvent) {
		String degree = txtAcademicDegree.getText().trim();
		String designation = txtAcademicDesignation.getText().trim();
		String classification = txtAcademicClassification.getText().trim();
		if (degree.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The academic degree can't be empty.", 2, 0.1d);
			txtAcademicDegree.requestFocus();
		} else if (designation.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The academic designation can't be empty.", 2, 0.1d);
			txtAcademicDesignation.requestFocus();
		} else if (classification.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The academic classification can't be empty.", 2, 0.1d);
			txtAcademicClassification.requestFocus();
		} else {
			AcademicQualification academicQualification = new AcademicQualification(degree, designation, classification);
			if (!academicQualificationList.contains(academicQualification)) {
				academicQualificationList.add(academicQualification);
				academicQualificationTableView.getItems().add(academicQualification);
			} else {
				WarningUtilities.showLabelWarning(lblWarning, "This academic qualification is already added.", 2, 0.1d);
			}
		}
	}

	public void onAddProfessionalQualification(ActionEvent actionEvent) {
		String description = txtProfessionalDescription.getText().trim();
		if (!description.isEmpty()) {
			ProfessionalQualification professionalQualification = new ProfessionalQualification(description);
			if (!professionalQualificationList.contains(professionalQualification)) {
				professionalQualificationList.add(professionalQualification);
				professionalQualificationTableView.getItems().add(professionalQualification);
			} else {
				WarningUtilities.showLabelWarning(lblWarning, "This professional qualification  is already added.", 2, 0.1d);
			}
		} else {
			WarningUtilities.showLabelWarning(lblWarning, "The professional qualification can't be empty.", 2, 0.1d);
			txtProfessionalDescription.requestFocus();
		}
	}

	public void onAddCategory(ActionEvent actionEvent) {
		Category category = cmbCategory.getValue();
		if (category != null) {
			if (!categoryList.contains(category)) {
				categoryList.add(category);
				categoryTableView.getItems().add(category);
			} else {
				WarningUtilities.showLabelWarning(lblWarning, "The category is already added.", 2, 0.1d);
			}
		} else {
			WarningUtilities.showLabelWarning(lblWarning, "Choose a category.", 2, 0.1d);
			cmbCategory.requestFocus();
		}
	}

	private boolean isCandidatureValid(String taxId, String name, String phone, String email, String postalAddress, String postCode) {
		boolean isValid = true;
		if (taxId.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The tax identification number can't be empty", 2, 0.1d);
			txtTIN.requestFocus();
			isValid = false;
		} else if (!WordUtils.isTaxIdentificationNumberValid(taxId)) {
			WarningUtilities.showLabelWarning(lblWarning, "The tax identification number is not valid", 2, 0.1d);
			txtTIN.requestFocus();
			isValid = false;
		} else if (name.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The name can't be empty", 2, 0.1d);
			txtName.requestFocus();
			isValid = false;
		} else if (!WordUtils.isPhoneNumberValid(phone)) {
			WarningUtilities.showLabelWarning(lblWarning, "The phone number is not valid", 2, 0.1d);
			txtPhone.requestFocus();
			isValid = false;
		} else if (email.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The email can't be empty", 2, 0.1d);
			txtEmail.requestFocus();
			isValid = false;
		} else if (!isEmailValid(email)) {
			txtEmail.requestFocus();
			isValid = false;
		} else if (postalAddress.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The postal address can't be empty", 2, 0.1d);
			txtPostalAddress.requestFocus();
			isValid = false;
		} else if (postCode.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The postcode can't be empty", 2, 0.1d);
			txtPostCode.requestFocus();
			isValid = false;
		} else if (!WordUtils.isPostCodeValid(postCode)) {
			WarningUtilities.showLabelWarning(lblWarning, "The postcode is not valid", 2, 0.1d);
			txtPostCode.requestFocus();
			isValid = false;
		} else if (postCodeManager.getPostCode(postCode) == null) {
			WarningUtilities.showLabelWarning(lblWarning, "The postcode does not exists in the database", 2, 0.1d);
			txtPostCode.requestFocus();
			isValid = false;
		} else if (categoryList.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "At least a category is necessary", 2, 0.1d);
			cmbCategory.requestFocus();
			isValid = false;
		} else if (!areQualificationsValid()) {
			isValid = false;
		}

		return isValid;
	}

	private boolean isEmailValid(String email) {
		boolean isValid = true;

		if (!WordUtils.isEmailValid(email)) {
			WarningUtilities.showLabelWarning(lblWarning, "The e-mail formatting is not valid", 2, 0.1d);
			txtEmail.requestFocus();
			isValid = false;
		} else if (serviceProviderManager.getServiceProvider(email) != null) {
			WarningUtilities.showLabelWarning(lblWarning, "The e-mail is already in use", 2, 0.1d);
			txtEmail.requestFocus();
			isValid = false;
		}

		return isValid;
	}

	private boolean areQualificationsValid() {
		boolean isValid = true;

		if (academicQualificationList.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "At least an academic qualification is necessary", 2, 0.1d);
			txtAcademicDegree.requestFocus();
			isValid = false;
		} else if (professionalQualificationList.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "At least a professional qualification is necessary", 2, 0.1d);
			txtProfessionalDescription.requestFocus();
			isValid = false;
		}

		return isValid;
	}

}
