package lapr2.agpsdapplication.controller.serviceprovider;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.company.user.serviceprovider.dailyavailability.DailyAvailability;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ViewDailyAvailabilitiesPage implements Initializable {

	@FXML
	private TableView tableView;

	@FXML
	private TableColumn tableColumnDate;

	@FXML
	private TableColumn tableColumnStartTime;

	@FXML
	private TableColumn tableColumnEndTime;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ServiceProvider serviceProvider = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class).getServiceProvider(HomeServices.getInstance().getSession().getUser().getEmail());

		tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		tableColumnStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		tableColumnEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

		ArrayList<DailyAvailability> dailyAvailabilities = new ArrayList<>(serviceProvider.getDailyAvailabilityList().getElements());
		dailyAvailabilities.sort(Comparator.comparing(DailyAvailability::getStartDate));

		dailyAvailabilities.forEach(dailyAvailability -> {
			if (dailyAvailability.getStartDate().compareTo(LocalDateTime.now()) >= 0) {
				tableView.getItems().add(new TableDailyAvailability(dailyAvailability));
			}
		});
	}

	public class TableDailyAvailability {

		@Getter
		private String date;

		@Getter
		private String startTime;

		@Getter
		private String endTime;

		public TableDailyAvailability(DailyAvailability dailyAvailability) {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

			date = dateTimeFormatter.format(dailyAvailability.getStartDate());
			startTime = timeFormatter.format(dailyAvailability.getStartDate());
			endTime = timeFormatter.format(dailyAvailability.getEndDate());
		}
	}
}
