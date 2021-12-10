package lapr2.agpsdapplication.controller.client;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.Company;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.category.CategoryManager;
import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.service.FixedService;
import lapr2.framework.company.service.Service;
import lapr2.framework.company.service.ServiceManager;
import lapr2.framework.company.servicerequest.*;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RequestServiceController implements Initializable, ChildrenController {

	private final List<Schedule> scheduleList = new ArrayList<>();

	private final List<ServiceDescription> serviceDescriptionList = new ArrayList<>();

	private final List<Float> durations = new ArrayList<>();

	@FXML
	private VBox boxPostalAddress;

	@FXML
	private ComboBox<ComboBoxPostalAddress> cmbPostalAddresses;

	@FXML
	private VBox boxDescription;

	@FXML
	private ComboBox<Category> cmbCategory;

	@FXML
	private ComboBox<Service> cmbService;

	@FXML
	private VBox boxDuration;

	@FXML
	private TextField txtServiceDescription;

	@FXML
	private Slider sldDuration;

	@FXML
	private TextField txtTotalCost;

	@FXML
	private TableView<TableServiceDescription> tableServices;

	@FXML
	private TableColumn columnService;

	@FXML
	private TableColumn columnCategory;

	@FXML
	private TableColumn columnServiceDescription;

	@FXML
	private TableColumn columnDuration;

	@FXML
	private HBox boxSchedules;

	@FXML
	private DatePicker datePicker;

	@FXML
	private Slider sldHour;

	@FXML
	private TableView<TableSchedule> tableSchedules;

	@FXML
	private TableColumn columnDate;

	@FXML
	private TableColumn columnStartTime;

	@FXML
	private Label lblWarning;

	private Company company;

	private ServiceRequestManager serviceRequestManager;

	private ServiceRequest serviceRequest;

	private Client client;

	private ClientPageController clientPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.serviceRequestManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceRequestManager.class);
		this.company = HomeServices.getInstance().getCompany();
		boxSchedules.setDisable(true);
		boxDescription.setDisable(true);
		client = company.getDataManager().get(ClientManager.class).getClient(HomeServices.getInstance().getSession().getUser().getEmail());

		List<ComboBoxPostalAddress> postalAddressList = new ArrayList<>();
		client.getPostalAddressList().getElements().forEach(postalAddress -> postalAddressList.add(new ComboBoxPostalAddress(postalAddress)));
		cmbPostalAddresses.setItems(FXCollections.observableArrayList(postalAddressList));

		ArrayList<Category> categories = company.getDataManager().get(CategoryManager.class).getElements();
		cmbCategory.setItems(FXCollections.observableArrayList(categories));

		columnService.setCellValueFactory(new PropertyValueFactory<>("service"));
		columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
		columnServiceDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		columnDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

		columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		columnStartTime.setCellValueFactory(new PropertyValueFactory<>("time"));
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.clientPageController = (ClientPageController) initializable;
	}

	public void onSelectPostalAddress(ActionEvent actionEvent) {
		PostalAddress postalAddress = cmbPostalAddresses.getValue().getPostalAddress();
		if (postalAddress == null) return;

		GeographicalArea geographicalArea = company.getDataManager().get(GeographicalAreaManager.class).getClosestGeographicalArea(postalAddress.getPostCode());

		if (PostCode.getDistance(geographicalArea.getCenteredPostCode(), postalAddress.getPostCode()) > geographicalArea.getRadius()) {
			WarningUtilities.showLabelWarning(lblWarning, "There are no geographical areas for that address", 2, 0.1d);
			cmbPostalAddresses.requestFocus();
			return;
		}

		boxPostalAddress.setDisable(true);
		boxDescription.setDisable(false);
		serviceRequest = serviceRequestManager.createServiceRequest(client, postalAddress);
		txtTotalCost.setText(String.format(Locale.ENGLISH, "%.2f%s", serviceRequest.getTotalCost(), HomeServices.getInstance().getFileConfiguration().getCurrency().getSymbol()));
	}

	public void onSelectCategory(ActionEvent actionEvent) {
		if (cmbCategory.getValue() == null) return;

		cmbService.setItems(FXCollections.observableArrayList(company.getDataManager().get(ServiceManager.class).getServicesOf(cmbCategory.getValue())));
	}

	public void onSelectService(ActionEvent actionEvent) {
		Service service = cmbService.getValue();

		if (service == null) return;

		if (service.hasAdditionalAttributes()) {
			boxDuration.setVisible(false);
		} else {
			boxDuration.setVisible(true);
		}
	}

	public void onAddService(ActionEvent actionEvent) {
		Service service = cmbService.getValue();
		String description = txtServiceDescription.getText().trim();
		double sliderDuration = sldDuration.getValue();

		if (service == null) {
			WarningUtilities.showLabelWarning(lblWarning, "The service is not valid", 2, 0.1d);
			cmbService.requestFocus();
			return;
		} else if (description.isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "The service description is not valid", 2, 0.1d);
			txtServiceDescription.requestFocus();
			return;
		} else if (!service.hasAdditionalAttributes() && (sliderDuration == 0.0d || sliderDuration % 0.5d != 0d)) {
			WarningUtilities.showLabelWarning(lblWarning, "The service description is not valid", 2, 0.1d);
			txtServiceDescription.requestFocus();
			return;
		}

		ServiceDescriptionList serviceDescriptionList = serviceRequest.getServiceDescriptionList();
		ServiceDescription serviceDescription;
		if (service.hasAdditionalAttributes()) {
			FixedService fixedService = (FixedService) service;
			serviceDescription = serviceDescriptionList.createServiceDescription(description, service, (float) fixedService.getPredefinedDuration());
		} else {
			serviceDescription = serviceDescriptionList.createServiceDescription(description, service, (float) sliderDuration);
		}

		if (serviceDescriptionList.isSecure(serviceDescription)) {
			serviceDescriptionList.addServiceDescription(serviceDescription);

			updateServices();
		} else {
			WarningUtilities.showLabelWarning(lblWarning, "That service is it not valid or duplicated", 2, 0.1d);
			cmbService.requestFocus();
		}

		cmbCategory.setValue(null);
		cmbService.setValue(null);
		sldDuration.setValue(0);
		txtServiceDescription.setText("");
	}

	public void onConfirmServices(ActionEvent actionEvent) {
		if (serviceRequest.getServiceDescriptionList().getServiceDescriptions().isEmpty()) {
			WarningUtilities.showLabelWarning(lblWarning, "You need to add at least one service to continue", 2, 0.1d);
			cmbService.requestFocus();
			return;
		}

		boxDescription.setDisable(true);

		ServiceDescription serviceDescription = Collections.max(serviceRequest.getServiceDescriptionList().getServiceDescriptions(), Comparator.comparingDouble(ServiceDescription::getDuration));
		sldHour.setMax(24 - serviceDescription.getDuration());

		boxSchedules.setDisable(false);
	}

	public void onAddSchedule(ActionEvent actionEvent) {
		LocalDateTime now = LocalDateTime.now();

		LocalDate localDate = datePicker.getValue();

		if (localDate == null) {
			WarningUtilities.showLabelWarning(lblWarning, "The date is not valid", 2, 0.1d);
			datePicker.requestFocus();
			return;
		}

		LocalDateTime selected = localDate.atTime((int) sldHour.getValue(), (int) (sldHour.getValue() % 1d * 60d));

		if (selected.isBefore(now)) {
			WarningUtilities.showLabelWarning(lblWarning, "It is not possible to requests services at the past", 2, 0.1d);
			datePicker.requestFocus();
			return;
		}

		Schedule schedule = serviceRequest.getScheduleList().createSchedulePreference(selected.getYear(), selected.getMonthValue(), selected.getDayOfMonth(), selected.getHour(), selected.getMinute());

		if (serviceRequest.getScheduleList().isSecure(schedule)) {
			serviceRequest.getScheduleList().addSchedulePreference(schedule);
			updateSchedules();
		} else {
			WarningUtilities.showLabelWarning(lblWarning, "The schedule is not valid or duplicated", 2, 0.1d);
		}

		sldHour.setValue(0);
		datePicker.setValue(null);
	}

	public void onRequestService(ActionEvent actionEvent) {
		if (serviceRequest.isValid()) {
			if (serviceRequestManager.isSecure(serviceRequest)) {
				serviceRequestManager.add(serviceRequest);

				Application.getSceneManager().loadWarningPopUp("Success", "The service request as successfully created", "/images/warnings/success.png");

				clientPageController.onHome(null);
			} else {
				WarningUtilities.showLabelWarning(lblWarning, "The service request is not valid, please check all fields", 2, 0.1d);
			}
		} else {
			WarningUtilities.showLabelWarning(lblWarning, "The service request is not valid, please check all fields", 2, 0.1d);
		}
	}

	private void updateServices() {
		List<TableServiceDescription> tableServiceDescriptions = new ArrayList<>();

		serviceRequest.getServiceDescriptionList().getServiceDescriptions().forEach(serviceDescription -> tableServiceDescriptions.add(new TableServiceDescription(serviceDescription)));

		tableServices.setItems(FXCollections.observableArrayList(tableServiceDescriptions));

		txtTotalCost.setText(String.format(Locale.ENGLISH, "%.2f%s", serviceRequest.getTotalCost(), HomeServices.getInstance().getFileConfiguration().getCurrency().getSymbol()));
	}

	private void updateSchedules() {
		List<TableSchedule> schedules = new ArrayList<>();

		serviceRequest.getScheduleList().getSchedulePreferencesList().forEach(schedule -> schedules.add(new TableSchedule(schedule)));

		tableSchedules.setItems(FXCollections.observableArrayList(schedules));
	}


	public class TableServiceDescription {

		@Getter
		private final String service;

		@Getter
		private final String description;

		@Getter
		private final String category;

		@Getter
		private final String duration;

		public TableServiceDescription(ServiceDescription serviceDescription) {
			this.service = serviceDescription.getService().toString();
			this.description = serviceDescription.getDescription();
			this.category = serviceDescription.getService().getCategory().getDescription();
			this.duration = String.format("%dh%dm", (int) serviceDescription.getDuration(), (int) (serviceDescription.getDuration() % 1 * 60));
		}
	}

	public class TableSchedule {

		@Getter
		private final String date;

		@Getter
		private final String time;

		private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		public TableSchedule(Schedule schedule) {
			String[] fullDate = dateTimeFormatter.format(schedule.getStartTime()).split(" ");
			this.date = fullDate[0];
			this.time = fullDate[1];
		}
	}

	public class ComboBoxPostalAddress {

		@Getter
		private final PostalAddress postalAddress;

		public ComboBoxPostalAddress(PostalAddress postalAddress) {
			this.postalAddress = postalAddress;
		}

		@Override
		public String toString() {
			return String.format("%s, %s, %s (%s)", postalAddress.getAddress(),
					postalAddress.getPostCode().getLocality(),
					postalAddress.getPostCode().getDistrict(),
					postalAddress.getPostCode().getPostCodeDesignation());
		}
	}
}

