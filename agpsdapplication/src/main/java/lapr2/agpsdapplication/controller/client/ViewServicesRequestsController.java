package lapr2.agpsdapplication.controller.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.company.servicerequest.ServiceDescription;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.servicerequest.ServiceRequestManager;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.company.user.user.User;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewServicesRequestsController implements Initializable {

	@FXML
	public ComboBox<ComboBoxServiceRequest> servicesRequestsComboBox;

	@FXML
	public TextField txtAddress;

	@FXML
	public TextField txtPostcode;

	@FXML
	public TextField txtTotalCost;

	@FXML
	public TableView<TableServiceDescription> serviceDescriptionTableView;

	@FXML
	public TableView scheduleTableView;

	@FXML
	public TableColumn descriptionColumn;

	@FXML
	public TableColumn categoryColumn;

	@FXML
	public TableColumn durationColumn;

	@FXML
	public TableColumn scheduleColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		User user = HomeServices.getInstance().getSession().getUser();
		Client client = HomeServices.getInstance().getCompany().getDataManager().get(ClientManager.class).getClient(user.getEmail());
		ServiceRequestManager serviceRequestManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceRequestManager.class);

		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryDescription"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		scheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));

		serviceRequestManager.getServicesRequests(client).forEach(serviceRequest -> servicesRequestsComboBox.getItems().add(new ComboBoxServiceRequest(serviceRequest)));
	}

	public void onViewServiceRequest(ActionEvent actionEvent) {
		serviceDescriptionTableView.getItems().clear();
		scheduleTableView.getItems().clear();
		ServiceRequest serviceRequest = servicesRequestsComboBox.getValue().getServiceRequest();
		txtAddress.setText(serviceRequest.getPostalAddress().getAddress());
		txtPostcode.setText(serviceRequest.getPostalAddress().getPostCode().getPostCodeDesignation());
		txtTotalCost.setText(String.format(Locale.ENGLISH, "%.2f%s", serviceRequest.getTotalCost(), HomeServices.getInstance().getFileConfiguration().getCurrency().getSymbol()));
		List<Schedule> schedulePreferencesList = serviceRequest.getScheduleList().getSchedulePreferencesList();
		List<ServiceDescription> serviceDescriptionList = serviceRequest.getServiceDescriptionList().getServiceDescriptions();

		schedulePreferencesList.forEach(schedule -> scheduleTableView.getItems().add(new TableSchedulePreference(schedule)));
		serviceDescriptionList.forEach(serviceDescription -> serviceDescriptionTableView.getItems().add(new TableServiceDescription(serviceDescription)));
	}

	public class TableServiceDescription {

		@Getter
		private String description;

		@Getter
		private String categoryDescription;

		@Getter
		private String duration;

		public TableServiceDescription(ServiceDescription serviceDescription) {
			this.description = serviceDescription.getDescription();
			this.categoryDescription = serviceDescription.getService().getCategory().getDescription();
			this.duration = String.format("%dh%dm", (int) serviceDescription.getDuration(), (int) (serviceDescription.getDuration() % 1 * 60));
		}
	}

	public class TableSchedulePreference {

		@Getter
		private String schedule;

		private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		public TableSchedulePreference(Schedule schedule) {
			this.schedule = dateTimeFormatter.format(schedule.getStartTime());
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
