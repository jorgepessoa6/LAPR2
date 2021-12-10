package lapr2.agpsdapplication.controller.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postaladdress.PostalAddressList;
import lapr2.framework.company.manager.DataManager;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.servicerequest.ServiceRequestManager;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.company.user.user.User;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable, ChildrenController {

	@FXML
	private Label serviceRequestsCountLabel;

	@FXML
	private Label firstServiceRequestLabel;

	@FXML
	private Label secondServiceRequestLabel;

	@FXML
	private Label thirdServiceRequestLabel;

	@FXML
	private Label postalAddressCountLabel;

	@FXML
	private Label firstPostalAddressLabel;

	@FXML
	private Label secondPostalAddressLabel;

	@FXML
	private Label thirdPostalAddressLabel;

	private ClientPageController clientPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DataManager dataManager = HomeServices.getInstance().getCompany().getDataManager();
		User user = HomeServices.getInstance().getSession().getUser();

		setupPostalAddressesPane(dataManager, user);
		setupServiceRequestsPane(dataManager, user);
	}

	private void setupServiceRequestsPane(DataManager dataManager, User user) {
		ClientManager clientManager = dataManager.get(ClientManager.class);
		ServiceRequestManager serviceRequestManager = dataManager.get(ServiceRequestManager.class);

		Client client = clientManager.getClient(user.getEmail());

		List<ServiceRequest> serviceRequestList = serviceRequestManager.getServicesRequests(client);

		serviceRequestsCountLabel.setText(String.format("%d service requests", serviceRequestList.size()));

		setupServiceRequestDescription(serviceRequestList, 1, firstServiceRequestLabel);
		setupServiceRequestDescription(serviceRequestList, 2, secondServiceRequestLabel);
		setupServiceRequestDescription(serviceRequestList, 3, thirdServiceRequestLabel);
	}

	private void setupServiceRequestDescription(List<ServiceRequest> serviceRequestList, int count, Label label) {
		if (serviceRequestList.size() >= count) {
			ServiceRequest serviceRequest = serviceRequestList.get(count - 1);

			label.setText(String.format("%.2f%s at %s", serviceRequest.getTotalCost(), HomeServices.getInstance().getFileConfiguration().getCurrency().getSymbol(), serviceRequest.getPostalAddress().getAddress()));
		} else {
			label.setText("No service providers defined yet");
		}
	}

	private void setupPostalAddressesPane(DataManager dataManager, User user) {
		ClientManager clientManager = dataManager.get(ClientManager.class);

		Client client = clientManager.getClient(user.getEmail());

		PostalAddressList postalAddressList = client.getPostalAddressList();

		postalAddressCountLabel.setText(String.format("%d postal addresses", postalAddressList.getElements().size()));

		setApplicationDescription(postalAddressList, 1, firstPostalAddressLabel);
		setApplicationDescription(postalAddressList, 2, secondPostalAddressLabel);
		setApplicationDescription(postalAddressList, 3, thirdPostalAddressLabel);
	}

	private void setApplicationDescription(PostalAddressList postalAddressList, int count, Label label) {
		if (postalAddressList.getElements().size() >= count) {
			PostalAddress postalAddress = postalAddressList.getElements().get(count - 1);

			label.setText(String.format("%s - %s", postalAddress.getAddress(), postalAddress.getPostCode().getPostCodeDesignation()));
		} else {
			label.setText("No postal addresses defined yet");
		}
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.clientPageController = (ClientPageController) initializable;
	}

	public void onViewPostalAddresses(MouseEvent mouseEvent) {
		clientPageController.onViewPostalAddress(null);
	}

	public void onViewServiceRequests(MouseEvent mouseEvent) {
		clientPageController.onViewServiceRequests(null);
	}
}
