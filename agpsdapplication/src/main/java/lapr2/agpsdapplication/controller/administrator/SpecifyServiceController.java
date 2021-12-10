package lapr2.agpsdapplication.controller.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.category.CategoryManager;
import lapr2.framework.company.service.FixedService;
import lapr2.framework.company.service.Service;
import lapr2.framework.company.service.ServiceManager;
import lapr2.framework.company.servicetype.ServiceType;
import lapr2.framework.company.servicetype.ServiceTypeManager;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.utils.WordUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class SpecifyServiceController implements Initializable, ChildrenController {

	@FXML
	private ComboBox<ServiceType> serviceTypesComboBox;

	@FXML
	private ComboBox<Category> categoriesComboBox;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtCost;

	@FXML
	private TextField txtBriefDescription;

	@FXML
	private TextField txtCompleteDescription;

	@FXML
	private VBox boxDuration;

	@FXML
	private Slider sliderDuration;

	@FXML
	private Label lblWarnings;

	private AdministratorPageController administratorPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ServiceTypeManager serviceTypeManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceTypeManager.class);
		CategoryManager categoryManager = HomeServices.getInstance().getCompany().getDataManager().get(CategoryManager.class);
		serviceTypesComboBox.getItems().addAll(serviceTypeManager.getElements());
		categoriesComboBox.getItems().addAll(categoryManager.getElements());
		boxDuration.setVisible(false);
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.administratorPageController = (AdministratorPageController) initializable;
	}

	public void onSelectServiceType(ActionEvent actionEvent) {
		if (serviceTypesComboBox.getValue().hasAdditionalData()) {
			sliderDuration.setValue(0);
			boxDuration.setVisible(true);
		} else {
			boxDuration.setVisible(false);
			sliderDuration.setValue(0);
		}
	}

	public void onAddService(ActionEvent actionEvent) {
		String id = txtId.getText().trim();
		String cost = txtCost.getText().trim();
		String briefDescription = txtBriefDescription.getText().trim();
		String completeDescription = txtCompleteDescription.getText().trim();

		double duration = sliderDuration.getValue();

		ServiceType serviceType = serviceTypesComboBox.getValue();
		Category category = categoriesComboBox.getValue();

		if (hasValidData(id, cost, briefDescription, completeDescription, duration, serviceType, category)) {
			Service service = serviceType.createService(id, briefDescription,
					completeDescription, Double.parseDouble(cost.replace(",", ".")), category);

			if (service.hasAdditionalAttributes()) {
				FixedService fixedService = (FixedService) service;
				fixedService.setPredefinedDuration(duration);
			}

			ServiceManager serviceManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceManager.class);

			if (!service.isValid() || !serviceManager.isSecure(service)) {
				WarningUtilities.showLabelWarning(lblWarnings, "The service is not valid or duplicated", 2, 0.1);
			} else {
				serviceManager.add(service);

				Application.getSceneManager().loadWarningPopUp("Success", "The service was successfully added", "/images/warnings/success.png");

				administratorPageController.onHome(null);
			}
		}
	}

	private boolean hasValidData(String id, String cost, String briefDescription, String completeDescription, double duration, ServiceType serviceType, Category category) {
		if (serviceType == null) {
			WarningUtilities.showLabelWarning(lblWarnings, "The service type is not valid", 2, 0.1);
			serviceTypesComboBox.requestFocus();
			return false;
		} else if (category == null) {
			WarningUtilities.showLabelWarning(lblWarnings, "The category is not valid", 2, 0.1);
			categoriesComboBox.requestFocus();
			return false;
		} else if (id.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarnings, "The unique identifier is not valid", 2, 0.1);
			txtId.requestFocus();
			return false;
		} else if (cost.isEmpty() || !WordUtils.isDouble(cost.replace(",", "."))) {
			WarningUtilities.showLabelWarning(lblWarnings, "The cost is not valid", 2, 0.1);
			txtCost.requestFocus();
			return false;
		} else if (briefDescription.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarnings, "The brief description is not valid", 2, 0.1);
			txtBriefDescription.requestFocus();
			return false;
		} else if (completeDescription.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarnings, "The complete description is not valid", 2, 0.1);
			txtCompleteDescription.requestFocus();
			return false;
		} else if (serviceType.hasAdditionalData() && (duration == 0 || duration % 0.5 != 0)) {
			WarningUtilities.showLabelWarning(lblWarnings, "The duration is not valid", 2, 0.1);
			sliderDuration.requestFocus();
			return false;
		}

		return true;
	}

}
