package lapr2.agpsdapplication.controller.serviceprovider;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.framework.company.manager.DataManager;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrderManager;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.company.user.serviceprovider.dailyavailability.DailyAvailability;
import lapr2.framework.company.user.user.User;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable, ChildrenController {

	@FXML
	private Label serviceExecutionOrdersCountLabel;

	@FXML
	private Label firstServiceExecutionOrderLabel;

	@FXML
	private Label secondServiceExecutionOrderLabel;

	@FXML
	private Label thirdServiceExecutionOrderLabel;

	@FXML
	private Label dailyAvailabilitiesCountLabel;

	@FXML
	private Label firstDailyAvailabilityLabel;

	@FXML
	private Label secondDailyAvailabilityLabel;

	@FXML
	private Label thirdDailyAvailabilityLabel;

	private ServiceProviderPageController serviceProviderPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DataManager dataManager = HomeServices.getInstance().getCompany().getDataManager();
		User user = HomeServices.getInstance().getSession().getUser();

		setupDailyAvailabilitiesPane(dataManager, user);
		setupServiceExecutionOrdersPane(dataManager, user);
	}

	private void setupServiceExecutionOrdersPane(DataManager dataManager, User user) {
		ServiceProviderManager serviceProviderManager = dataManager.get(ServiceProviderManager.class);
		ServiceExecutionOrderManager serviceExecutionOrderManager = dataManager.get(ServiceExecutionOrderManager.class);
		ServiceProvider serviceProvider = serviceProviderManager.getServiceProvider(user.getEmail());

		List<ServiceExecutionOrder> serviceExecutionOrderList = serviceExecutionOrderManager.getServiceExecutionOrdersOfServiceProvider(serviceProvider);

		LocalDateTime now = LocalDateTime.now();

		serviceExecutionOrderList.removeIf(serviceExecutionOrder -> serviceExecutionOrder.getAffectation().getSchedule().getStartTime().plusMinutes((int) (serviceExecutionOrder.getAffectation().getServiceDescription().getDuration() * 60)).isAfter(now));
		serviceExecutionOrdersCountLabel.setText(String.format("%d service execution orders", serviceExecutionOrderList.size()));

		setupServiceExecutionOrdersDescription(serviceExecutionOrderList, 1, firstServiceExecutionOrderLabel);
		setupServiceExecutionOrdersDescription(serviceExecutionOrderList, 2, secondServiceExecutionOrderLabel);
		setupServiceExecutionOrdersDescription(serviceExecutionOrderList, 3, thirdServiceExecutionOrderLabel);
	}

	private void setupServiceExecutionOrdersDescription(List<ServiceExecutionOrder> serviceExecutionOrderList, int count, Label label) {
		if (serviceExecutionOrderList.size() >= count) {
			ServiceExecutionOrder serviceExecutionOrder = serviceExecutionOrderList.get(count - 1);

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

			label.setText(String.format("%s at %s", serviceExecutionOrder.getAffectation().getServiceDescription().getDescription(),
					dateTimeFormatter.format(serviceExecutionOrder.getAffectation().getSchedule().getStartTime())));
		} else {
			label.setText("No service execution orders defined yet");
		}
	}

	private void setupDailyAvailabilitiesPane(DataManager dataManager, User user) {
		ServiceProviderManager serviceProviderManager = dataManager.get(ServiceProviderManager.class);
		ServiceProvider serviceProvider = serviceProviderManager.getServiceProvider(user.getEmail());

		List<DailyAvailability> dailyAvailabilityList = serviceProvider.getDailyAvailabilityList().getElements();

		LocalDateTime now = LocalDateTime.now();
		List<DailyAvailability> dailyAvailabilityList1 = new ArrayList<>();
		for (DailyAvailability dailyAvailability : dailyAvailabilityList) {
			if (dailyAvailability.getStartDate().compareTo(now) >= 0) {
				dailyAvailabilityList1.add(dailyAvailability);
			}
		}

		dailyAvailabilitiesCountLabel.setText(String.format("%d daily availabilities", dailyAvailabilityList1.size()));

		setDailyAvailabilitiesDescription(dailyAvailabilityList1, 1, firstDailyAvailabilityLabel);
		setDailyAvailabilitiesDescription(dailyAvailabilityList1, 2, secondDailyAvailabilityLabel);
		setDailyAvailabilitiesDescription(dailyAvailabilityList1, 3, thirdDailyAvailabilityLabel);
	}

	private void setDailyAvailabilitiesDescription(List<DailyAvailability> dailyAvailabilityList, int count, Label label) {
		if (dailyAvailabilityList.size() >= count) {
			DailyAvailability dailyAvailability = dailyAvailabilityList.get(count - 1);

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DateTimeFormatter hourTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

			label.setText(String.format("%s %s-%s",
					dateTimeFormatter.format(dailyAvailability.getStartDate()),
					hourTimeFormatter.format(dailyAvailability.getStartDate()),
					hourTimeFormatter.format(dailyAvailability.getEndDate())));
		} else {
			label.setText("No daily availabilities defined yet");
		}
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.serviceProviderPageController = (ServiceProviderPageController) initializable;
	}

	public void onViewServiceExecutionOrders(MouseEvent mouseEvent) {
		serviceProviderPageController.onViewServiceExecutionOrders(null);
	}

	public void onViewDailyAvailabilities(MouseEvent mouseEvent) {
		serviceProviderPageController.onViewDailyAvailabilities(null);
	}
}
