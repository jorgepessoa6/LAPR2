package lapr2.agpsdapplication.controller.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.affectation.Affectation;
import lapr2.framework.company.affectation.AffectationManager;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.company.servicerequest.ServiceRequestManager;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DecideProposedTimePageController implements Initializable, ChildrenController {

	@FXML
	private TableView<TableService> tableServices;

	@FXML
	private TableColumn columnServiceNumber;

	@FXML
	private TableColumn columnService;

	@FXML
	private TableColumn columnServiceDescription;

	@FXML
	private TableColumn columnServiceDuration;

	@FXML
	private TableColumn columnServiceSchedule;

	@FXML
	private TableColumn columnServiceProvider;

	@FXML
	private Label lblWarnings;

	private ServiceRequestManager serviceRequestManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Client client = HomeServices.getInstance().getCompany().getDataManager().get(ClientManager.class).getClient(HomeServices.getInstance().getSession().getUser().getEmail());

		this.serviceRequestManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceRequestManager.class);

		columnServiceNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		columnService.setCellValueFactory(new PropertyValueFactory<>("service"));
		columnServiceDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		columnServiceDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
		columnServiceSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
		columnServiceProvider.setCellValueFactory(new PropertyValueFactory<>("serviceProvider"));

		ArrayList<Affectation> affectations = HomeServices.getInstance().getCompany().getDataManager().get(AffectationManager.class).getElements();

		LocalDateTime now = LocalDateTime.now();

		//affectations.removeIf(affectation -> !affectation.getState().equals(Affectation.State.SUBMITTED) && affectation.getSchedule().getStartTime().isAfter(now) && affectation.getServiceRequest().getClient().equals(client));
		affectations.removeIf(affectation -> !affectation.getState().equals(Affectation.State.SUBMITTED));
		affectations.removeIf(affectation -> affectation.getSchedule().getStartTime().isBefore(now));
		affectations.removeIf(affectation -> !affectation.getServiceRequest().getClient().equals(client));

		affectations.forEach(affectation -> tableServices.getItems().add(new TableService(affectation)));
	}

	@Override
	public void setParentController(Initializable initializable) {
	}

	public void onAcceptSchedule(ActionEvent actionEvent) {
		if (tableServices.getSelectionModel().getSelectedItem() == null) {
			WarningUtilities.showLabelWarning(lblWarnings, "There is no service selected", 2, 0.1);
		} else {
			TableService tableService = tableServices.getSelectionModel().getSelectedItem();
			Affectation affectation = tableService.getAffectation();

			boolean affect = affectation.affect();

			tableServices.getItems().remove(tableService);

			if (affect) {
				Application.getSceneManager().loadWarningPopUp("Success", "The service execution order for that schedule was successfully created", "/images/warnings/success.png");
			} else {
				Application.getSceneManager().loadWarningPopUp("Error", "An unexpected error occurred while creating the service execution order", "/images/warnings/error.png");
			}
		}
	}

	public void onDeclineSchedule(ActionEvent actionEvent) {
		if (tableServices.getSelectionModel().getSelectedItem() == null) {
			WarningUtilities.showLabelWarning(lblWarnings, "There is no service selected", 2, 0.1);
		} else {
			TableService tableService = tableServices.getSelectionModel().getSelectedItem();
			Affectation affectation = tableService.getAffectation();

			affectation.setState(Affectation.State.REJECTED);

			tableServices.getItems().remove(tableService);

			Application.getSceneManager().loadWarningPopUp("Success", "Soon, there will be a rescheduling for that service", "/images/warnings/success.png");
		}
	}

	public class TableService {

		@Getter
		private int number;

		@Getter
		private String service;

		@Getter
		private String description;

		@Getter
		private String duration;

		@Getter
		private String schedule;

		@Getter
		private String serviceProvider;

		@Getter
		private Affectation affectation;

		public TableService(Affectation affectation) {
			this.affectation = affectation;
			this.number = serviceRequestManager.getElements().indexOf(affectation.getServiceRequest()) + 1;
			this.service = affectation.getServiceDescription().getService().getBriefDescription();
			this.description = affectation.getServiceDescription().getDescription();
			this.duration = durationToString(affectation.getServiceDescription().getDuration());
			this.schedule = scheduleToString(affectation.getSchedule());
			this.serviceProvider = affectation.getServiceProvider().getAbbreviatedName();
		}

		private String durationToString(float duration) {
			float minutes = duration % 1;

			return String.format("%dh%dm", (int) duration, (int) (minutes * 60));
		}

		private String scheduleToString(Schedule schedule) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

			return formatter.format(schedule.getStartTime());
		}
	}
}
