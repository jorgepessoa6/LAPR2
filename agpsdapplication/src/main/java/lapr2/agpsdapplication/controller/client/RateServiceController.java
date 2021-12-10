package lapr2.agpsdapplication.controller.client;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.Company;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrderManager;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.servicerequest.ServiceRequestManager;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.company.user.user.User;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class RateServiceController implements Initializable, ChildrenController {

	@FXML
	private TableView<TableServiceExecutionOrder> serviceExecutionOrderTableView;

	@FXML
	private TableColumn serviceColumn;

	@FXML
	private TableColumn serviceDescriptionColumn;

	@FXML
	private TableColumn addressColumn;

	@FXML
	private TableColumn postCodeColumn;

	@FXML
	private TableColumn scheduleColumn;

	@FXML
	private TableColumn serviceProviderColumn;

	@FXML
	private Button btnSeeInvoice;

	@FXML
	private Slider sliderRate;

	@FXML
	private Button btnRate;

	@FXML
	private Label lblServiceRate;

	@FXML
	private ImageView imageServiceRate;

	@FXML
	private Label lblWarning;

	@FXML
	public ComboBox<ComboBoxServiceRequest> cmbServiceRequest;

	private ClientPageController clientPageController;

	private ServiceExecutionOrderManager serviceExecutionOrderManager;

	private List<ServiceExecutionOrder> listServiceExecutionOrder;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HomeServices homeServices = HomeServices.getInstance();
		Company company = homeServices.getCompany();
		User user = homeServices.getSession().getUser();
		Client client = company.getDataManager().get(ClientManager.class).getClient(user.getEmail());

		lblServiceRate.setVisible(false);
		sliderRate.setVisible(false);
		btnRate.setVisible(false);
		imageServiceRate.setVisible(false);
		btnSeeInvoice.setDisable(true);

		ServiceRequestManager serviceRequestManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceRequestManager.class);
		this.serviceExecutionOrderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceExecutionOrderManager.class);

		List<ComboBoxServiceRequest> comboBoxServiceRequests = new ArrayList<>();

		serviceRequestManager.getServicesRequests(client).forEach(serviceRequest -> comboBoxServiceRequests.add(new ComboBoxServiceRequest(serviceRequest)));

		cmbServiceRequest.setItems(FXCollections.observableArrayList(comboBoxServiceRequests));

		serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
		serviceDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		postCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));
		scheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));
		serviceProviderColumn.setCellValueFactory(new PropertyValueFactory<>("serviceProvider"));

		this.listServiceExecutionOrder = new ArrayList<>();
	}

	public void onSelectServiceRequest(ActionEvent actionEvent) {
		ServiceRequest selectedServiceRequest = cmbServiceRequest.getValue().getServiceRequest();

		if (selectedServiceRequest == null) {
			WarningUtilities.showLabelWarning(lblWarning, "That request is not valid", 2, 0.1d);
			return;
		}

		listServiceExecutionOrder.clear();

		listServiceExecutionOrder = serviceExecutionOrderManager.getCompletedUnratedServiceExecutionOrdersOfServiceRequest(selectedServiceRequest);

		for (ServiceExecutionOrder serviceExecutionOrder : listServiceExecutionOrder) {
			TableServiceExecutionOrder tableServiceExecutionOrder = new TableServiceExecutionOrder(serviceExecutionOrder);
			serviceExecutionOrderTableView.getItems().add(tableServiceExecutionOrder);
		}

		lblServiceRate.setVisible(true);
		sliderRate.setVisible(true);
		btnRate.setVisible(true);
		imageServiceRate.setVisible(true);
		btnSeeInvoice.setDisable(false);
	}

	public void onSelectSeeInvoice(ActionEvent actionEvent) {
		ServiceRequest serviceRequest = cmbServiceRequest.getValue().getServiceRequest();

		if (serviceRequest != null) {
			Application.getSceneManager().loadWarningPopUp("Invoice",
					String.format("Cost: %.2f%s%nClient: %s%nAddress: %s",
							serviceRequest.getTotalCost(),
							HomeServices.getInstance().getFileConfiguration().getCurrency().getSymbol(),
							serviceRequest.getClient().getName(),
							serviceRequest.getPostalAddress().getAddress()), "/images/warnings/success.png");
		} else {
			WarningUtilities.showLabelWarning(lblWarning, "You must select a service request.", 2, 0.1d);
		}
	}

	public void onRateService(ActionEvent actionEvent) {
		TableServiceExecutionOrder tableServiceExecutionOrder = serviceExecutionOrderTableView.getSelectionModel().getSelectedItem();

		if (tableServiceExecutionOrder != null) {
			int rating = (int) sliderRate.getValue();
			tableServiceExecutionOrder.getServiceExecutionOrder().rateServiceExecutionOrder(rating);
			Application.getSceneManager().loadWarningPopUp("Success", "The service was successfully rated", "/images/warnings/success.png");
			clientPageController.onHome(null);
		} else {
			WarningUtilities.showLabelWarning(lblWarning, "You must select a service from the table to rate", 2, 0.1d);
		}
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.clientPageController = (ClientPageController) initializable;
	}

	public class TableServiceExecutionOrder {

		@Getter
		private final String service;

		@Getter
		private final String serviceDescription;

		@Getter
		private final String address;

		@Getter
		private final String postCode;

		@Getter
		private final String schedule;

		@Getter
		private final String serviceProvider;

		@Getter
		private final ServiceExecutionOrder serviceExecutionOrder;

		private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		public TableServiceExecutionOrder(ServiceExecutionOrder serviceExecutionOrder) {
			this.service = serviceExecutionOrder.getAffectation().getServiceDescription().getService().getBriefDescription();
			this.serviceDescription = serviceExecutionOrder.getAffectation().getServiceDescription().getDescription();
			this.address = serviceExecutionOrder.getAffectation().getServiceRequest().getPostalAddress().getAddress();
			this.postCode = serviceExecutionOrder.getAffectation().getServiceRequest().getPostalAddress().getPostCode().getPostCodeDesignation();
			this.schedule = dateTimeFormatter.format(serviceExecutionOrder.getAffectation().getSchedule().getStartTime());
			this.serviceProvider = serviceExecutionOrder.getAffectation().getServiceProvider().getCompleteName();
			this.serviceExecutionOrder = serviceExecutionOrder;
		}
	}

	public class ComboBoxServiceRequest {

		private String name;

		@Getter
		private ServiceRequest serviceRequest;

		private String currencySymbol = HomeServices.getInstance().getFileConfiguration().getCurrency().getSymbol();

		public ComboBoxServiceRequest(ServiceRequest serviceRequest) {
			this.name = String.format(Locale.ENGLISH, "%.2f%s at %s", serviceRequest.getTotalCost(), currencySymbol,
					serviceRequest.getPostalAddress().getAddress());
			this.serviceRequest = serviceRequest;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}
}
