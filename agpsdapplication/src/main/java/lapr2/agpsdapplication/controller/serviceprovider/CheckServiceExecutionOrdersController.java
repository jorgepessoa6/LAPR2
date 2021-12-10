package lapr2.agpsdapplication.controller.serviceprovider;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrderManager;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.io.serviceexecutionorder.CSVOrders;
import lapr2.framework.io.serviceexecutionorder.ServiceExecutionOrderIO;
import lapr2.framework.io.serviceexecutionorder.XLSOrdersAdapter;
import lapr2.framework.io.serviceexecutionorder.XMLOrdersAdapter;
import lombok.Getter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CheckServiceExecutionOrdersController implements Initializable, ChildrenController {

	@FXML
	private Label lblWarning;

	@FXML
	private ComboBox<Format> cmbFormat;

	@FXML
	private DatePicker datePickerEndDate;

	@FXML
	private DatePicker datePickerStartDate;

	@FXML
	private TableView<TableServiceExecutionOrder> tableServiceExecutionOrders;

	@FXML
	private TableColumn columnService;

	@FXML
	private TableColumn columnDescription;

	@FXML
	private TableColumn columnAddress;

	@FXML
	private TableColumn columnPostcode;

	@FXML
	private TableColumn columnSchedule;

	@FXML
	private TableColumn columnClient;

	private ServiceProviderPageController serviceProviderPageController;

	private ServiceProvider serviceProvider;

	private ServiceExecutionOrderManager serviceExecutionOrderManager;

	private List<ServiceExecutionOrder> serviceExecutionOrdersOfPeriod = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbFormat.setItems(FXCollections.observableArrayList(Format.values()));
		this.serviceExecutionOrderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceExecutionOrderManager.class);
		serviceProvider = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class).getServiceProvider(HomeServices.getInstance().getSession().getUser().getEmail());

		columnService.setCellValueFactory(new PropertyValueFactory<>("service"));
		columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		columnPostcode.setCellValueFactory(new PropertyValueFactory<>("postCode"));
		columnSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
		columnClient.setCellValueFactory(new PropertyValueFactory<>("client"));
	}

	public void onConfirmPeriod(ActionEvent actionEvent) {
		LocalDate startDate = datePickerStartDate.getValue();
		LocalDate endDate = datePickerEndDate.getValue();

		if (startDate == null || endDate == null) {
			WarningUtilities.showLabelWarning(lblWarning, "You must select the dates first", 2, 0.1d);
			return;
		} else if (endDate.isBefore(startDate)) {
			WarningUtilities.showLabelWarning(lblWarning, "The time period is not valid", 2, 0.1d);
			return;
		} else {
			LocalDateTime startTime = startDate.atTime(6, 0);
			LocalDateTime endTime = endDate.atTime(0, 0).plusDays(1);

			serviceExecutionOrdersOfPeriod.clear();

			List<ServiceExecutionOrder> serviceProviderOrders = serviceExecutionOrderManager.getServiceExecutionOrdersOfServiceProvider(serviceProvider);

			for (ServiceExecutionOrder serviceExecutionOrder : serviceProviderOrders) {
				if (serviceExecutionOrder.getAffectation().getSchedule().getStartTime().compareTo(startTime) >= 0
						&& serviceExecutionOrder.getAffectation().getSchedule().getStartTime().compareTo(endTime) <= 0) {
					serviceExecutionOrdersOfPeriod.add(serviceExecutionOrder);
				}
			}

			for (ServiceExecutionOrder serviceExecutionOrder : serviceExecutionOrdersOfPeriod) {
				tableServiceExecutionOrders.getItems().add(new TableServiceExecutionOrder(serviceExecutionOrder));
			}
		}
	}

	public void onConfirmFormat(ActionEvent actionEvent) {
		Format format = cmbFormat.getValue();

		if (format != null) {
			switch (format) {
				case CSV:
					ServiceExecutionOrderIO csvIO = new CSVOrders();
					if (csvIO.exportServiceExecutionOrders(serviceExecutionOrdersOfPeriod)) {
						Application.getSceneManager().loadWarningPopUp("Success", "The orders were successfully exported to " + HomeServices.FOLDER_PATH + "orders", "/images/warnings/success.png");
					}
					break;
				case XLS:
					ServiceExecutionOrderIO xlsIO = new XLSOrdersAdapter();
					if (xlsIO.exportServiceExecutionOrders(serviceExecutionOrdersOfPeriod)) {
						Application.getSceneManager().loadWarningPopUp("Success", "The orders were successfully exported to " + HomeServices.FOLDER_PATH + "orders", "/images/warnings/success.png");
					}
					break;
				case XML:
					ServiceExecutionOrderIO xmlIO = new XMLOrdersAdapter();
					if (xmlIO.exportServiceExecutionOrders(serviceExecutionOrdersOfPeriod)) {
						Application.getSceneManager().loadWarningPopUp("Success", "The orders were successfully exported to " + HomeServices.FOLDER_PATH + "orders", "/images/warnings/success.png");
					}
					break;
			}

			serviceProviderPageController.onHome(null);
		} else {
			WarningUtilities.showLabelWarning(lblWarning, "You must select a format first", 2, 0.1d);
		}
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.serviceProviderPageController = (ServiceProviderPageController) initializable;
	}

	public enum Format {
		CSV(".csv"),
		XLS(".xls"),
		XML(".xml");

		@Getter
		private String name;

		Format(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}
	}

	public class TableServiceExecutionOrder {

		@Getter
		private final String service;

		@Getter
		private final String description;

		@Getter
		private final String address;

		@Getter
		private final String postCode;

		@Getter
		private final String schedule;

		@Getter
		private final String client;

		@Getter
		private final ServiceExecutionOrder serviceExecutionOrder;

		private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		public TableServiceExecutionOrder(ServiceExecutionOrder serviceExecutionOrder) {
			this.service = serviceExecutionOrder.getAffectation().getServiceDescription().getService().getBriefDescription();
			this.description = serviceExecutionOrder.getAffectation().getServiceDescription().getDescription();
			this.address = serviceExecutionOrder.getAffectation().getServiceRequest().getPostalAddress().getAddress();
			this.postCode = serviceExecutionOrder.getAffectation().getServiceRequest().getPostalAddress().getPostCode().getPostCodeDesignation();
			this.schedule = dateTimeFormatter.format(serviceExecutionOrder.getAffectation().getSchedule().getStartTime());
			this.client = serviceExecutionOrder.getAffectation().getServiceRequest().getClient().getName();
			this.serviceExecutionOrder = serviceExecutionOrder;
		}
	}
}
