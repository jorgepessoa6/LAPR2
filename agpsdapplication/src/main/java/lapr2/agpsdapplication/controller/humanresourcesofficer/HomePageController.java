package lapr2.agpsdapplication.controller.humanresourcesofficer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.framework.company.candidature.Candidature;
import lapr2.framework.company.candidature.CandidatureManager;
import lapr2.framework.company.manager.DataManager;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable, ChildrenController {

	@FXML
	private Label serviceProviderCountLabel;

	@FXML
	private Label firstServiceProviderLabel;

	@FXML
	private Label secondServiceProviderLabel;

	@FXML
	private Label thirdServiceProviderLabel;

	@FXML
	private Label applicationCountLabel;

	@FXML
	private Label firstApplicationLabel;

	@FXML
	private Label secondApplicationLabel;

	@FXML
	private Label thirdApplicationLabel;

	private HumanResourcesOfficerPageController humanResourcesOfficerPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DataManager dataManager = HomeServices.getInstance().getCompany().getDataManager();

		setupApplicationsPane(dataManager);
		setupServiceProviderPane(dataManager);
	}

	private void setupServiceProviderPane(DataManager dataManager) {
		ServiceProviderManager serviceProviderManager = dataManager.get(ServiceProviderManager.class);

		serviceProviderCountLabel.setText(String.format("%d service providers", serviceProviderManager.getElements().size()));

		setupServiceProviderDescription(serviceProviderManager, 1, firstServiceProviderLabel);
		setupServiceProviderDescription(serviceProviderManager, 2, secondServiceProviderLabel);
		setupServiceProviderDescription(serviceProviderManager, 3, thirdServiceProviderLabel);
	}

	private void setupServiceProviderDescription(ServiceProviderManager serviceProviderManager, int count, Label label) {
		if (serviceProviderManager.getElements().size() >= count) {
			ServiceProvider serviceProvider = serviceProviderManager.getElements().get(count - 1);

			label.setText(String.format("%s - %s", serviceProvider.getMechanographicalNumber(), serviceProvider.getEmail()));
		} else {
			label.setText("No service providers defined yet");
		}
	}

	private void setupApplicationsPane(DataManager dataManager) {
		CandidatureManager candidatureManager = dataManager.get(CandidatureManager.class);

		applicationCountLabel.setText(String.format("%d applications", candidatureManager.getElements().size()));

		setApplicationDescription(candidatureManager, 1, firstApplicationLabel);
		setApplicationDescription(candidatureManager, 2, secondApplicationLabel);
		setApplicationDescription(candidatureManager, 3, thirdApplicationLabel);
	}

	private void setApplicationDescription(CandidatureManager candidatureManager, int count, Label label) {
		if (candidatureManager.getElements().size() >= count) {
			Candidature candidature = candidatureManager.getElements().get(count - 1);

			label.setText(String.format("%s - %s", candidature.getName(), candidature.getTaxIdentificationNumber()));
		} else {
			label.setText("No applications defined yet");
		}
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.humanResourcesOfficerPageController = (HumanResourcesOfficerPageController) initializable;
	}

	public void onViewApplications(MouseEvent mouseEvent) {
		humanResourcesOfficerPageController.onViewApplications(null);
	}

	public void onViewServiceProviders(MouseEvent mouseEvent) {
		humanResourcesOfficerPageController.onViewServiceProviders(null);
	}
}
