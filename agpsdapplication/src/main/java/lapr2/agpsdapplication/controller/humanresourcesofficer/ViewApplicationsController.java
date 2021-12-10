package lapr2.agpsdapplication.controller.humanresourcesofficer;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.framework.company.candidature.Candidature;
import lapr2.framework.company.candidature.CandidatureManager;
import lapr2.framework.company.candidature.qualification.AcademicQualification;
import lapr2.framework.company.candidature.qualification.ProfessionalQualification;
import lapr2.framework.company.category.Category;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;


public class ViewApplicationsController implements Initializable {

    @FXML
    public TextField txtTaxIdentificationNumber;

    @FXML
    public TextField txtFullName;

    @FXML
    public TextField txtPhoneNumber;

    @FXML
    public TextField txtEmail;

    @FXML
    public TextField txtAddress;

    @FXML
    public TextField txtPostcode;

    @FXML
    public ComboBox<Candidature> candidatureComboBox;

    @FXML
    private TableView<Category> categoryTableView;
    @FXML
    private TableView<ProfessionalQualification> professionalQualificationTableView;
    @FXML
    private TableView<AcademicQualification> academicQualificationTableView;

    @FXML
    private TableColumn<AcademicQualification, String> degreeColumn;
    @FXML
    private TableColumn<AcademicQualification, String> designationColumn;
    @FXML
    private TableColumn<AcademicQualification, String> classificationColumn;
    @FXML
    private TableColumn<ProfessionalQualification, String> descriptionColumn;
    @FXML
    private TableColumn<Category, String> uniqueIdentifierCategoryColumn;
    @FXML
    private TableColumn<Category, String> descriptionCategoryColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
		CandidatureManager candidatureManager = HomeServices.getInstance().getCompany().getDataManager().get(CandidatureManager.class);
        degreeColumn.setCellValueFactory(new PropertyValueFactory<>("degree"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        uniqueIdentifierCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        candidatureComboBox.setItems(FXCollections.observableArrayList(candidatureManager.getElements()));
    }

    public void onViewCandidature(ActionEvent event) {
        categoryTableView.getItems().clear();
        academicQualificationTableView.getItems().clear();
        professionalQualificationTableView.getItems().clear();
        Candidature candidature = candidatureComboBox.getValue();
        txtFullName.setText(candidature.getName());
        txtEmail.setText(candidature.getEmail());
        txtPhoneNumber.setText(candidature.getPhoneNumber());
        txtTaxIdentificationNumber.setText(candidature.getTaxIdentificationNumber());
        txtAddress.setText(candidature.getPostalAddress().getAddress());
        txtPostcode.setText(candidature.getPostalAddress().getPostCode().toString());
        Set<Category> categoryList = candidature.getCategories();
        Set<AcademicQualification> academicQualificationList = candidature.getAcademicQualifications();
        Set<ProfessionalQualification> professionalQualificationsList = candidature.getProfessionalQualifications();
        categoryTableView.getItems().addAll(categoryList);
        academicQualificationTableView.getItems().addAll(academicQualificationList);
        professionalQualificationTableView.getItems().addAll(professionalQualificationsList);
    }
}
