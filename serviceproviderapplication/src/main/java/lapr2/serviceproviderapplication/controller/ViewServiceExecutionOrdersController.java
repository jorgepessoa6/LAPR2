package lapr2.serviceproviderapplication.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder.SimpleServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder.SimpleServiceExecutionOrderManager;
import lapr2.framework.utils.WordUtils;
import lapr2.serviceproviderapplication.ServiceProviderApplication;
import lombok.Getter;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ViewServiceExecutionOrdersController implements Initializable {

	@FXML
	private ComboBox<SortType> cmbSort;

	@FXML
	private TableView<TableServiceExecutionOrder> tableOrders;

	@FXML
	private TableColumn columnClient;

	@FXML
	private TableColumn columnCategory;

	@FXML
	private TableColumn columnSchedule;

	@FXML
	private TableColumn columnServiceType;

	@FXML
	private TableColumn columnAddress;

	@FXML
	private TableColumn columnDistance;

	private SimpleServiceExecutionOrderManager simpleServiceExecutionOrderManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		simpleServiceExecutionOrderManager = ServiceProviderApplication.getInstance().getSimpleServiceExecutionOrderManager();
		List<SimpleServiceExecutionOrder> simpleServiceExecutionOrderList = simpleServiceExecutionOrderManager.getOrdersTimeline();

		columnClient.setCellValueFactory(new PropertyValueFactory<>("client"));
		columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
		columnSchedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
		columnServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
		columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		columnDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));

		cmbSort.setItems(FXCollections.observableArrayList(SortType.values()));

		simpleServiceExecutionOrderList.forEach(simpleServiceExecutionOrder -> tableOrders.getItems().add(new TableServiceExecutionOrder(simpleServiceExecutionOrder)));
	}

	public void onSort(ActionEvent actionEvent) {
		if (cmbSort.getValue() != null) {
			List<SimpleServiceExecutionOrder> simpleServiceExecutionOrderList = simpleServiceExecutionOrderManager.getOrdersTimeline();
			switch (cmbSort.getValue()) {
				case CLIENT:
					simpleServiceExecutionOrderList.sort(Comparator.comparing(SimpleServiceExecutionOrder::getClient));
					break;
				case CATEGORY:
					simpleServiceExecutionOrderList.sort(Comparator.comparing(SimpleServiceExecutionOrder::getCategory));
					break;
				case SCHEDULE:
					simpleServiceExecutionOrderList.sort(Comparator.comparing(SimpleServiceExecutionOrder::getSchedule));
					break;
				case SERVICE_TYPE:
					simpleServiceExecutionOrderList.sort(Comparator.comparing(SimpleServiceExecutionOrder::getServiceType));
					break;
				case ADDRESS:
					simpleServiceExecutionOrderList.sort(Comparator.comparing(SimpleServiceExecutionOrder::getPostalAddress));
					break;
				case DISTANCE:
					simpleServiceExecutionOrderList.sort(Comparator.comparing(SimpleServiceExecutionOrder::getDistance));
					break;
			}

			tableOrders.getItems().clear();
			simpleServiceExecutionOrderList.forEach(simpleServiceExecutionOrder -> tableOrders.getItems().add(new TableServiceExecutionOrder(simpleServiceExecutionOrder)));
		}
	}

	public enum SortType {
		CLIENT,
		CATEGORY,
		SCHEDULE,
		SERVICE_TYPE,
		ADDRESS,
		DISTANCE;

		@Override
		public String toString() {
			return WordUtils.capitalize(this.name());
		}
	}

	public class TableServiceExecutionOrder {

		@Getter
		private final String serviceType;

		@Getter
		private final String address;

		@Getter
		private final String category;

		@Getter
		private final String client;

		@Getter
		private final String schedule;

		@Getter
		private final String distance;

		@Getter
		private final SimpleServiceExecutionOrder simpleServiceExecutionOrder;

		private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		public TableServiceExecutionOrder(SimpleServiceExecutionOrder simpleServiceExecutionOrder) {
			this.category = simpleServiceExecutionOrder.getCategory();
			this.serviceType = simpleServiceExecutionOrder.getServiceType();
			this.client = simpleServiceExecutionOrder.getClient();
			this.address = simpleServiceExecutionOrder.getPostalAddress();
			this.schedule = dateTimeFormatter.format(simpleServiceExecutionOrder.getSchedule());
			this.distance = String.format("%.2f km", simpleServiceExecutionOrder.getDistance() / 1000);
			this.simpleServiceExecutionOrder = simpleServiceExecutionOrder;
		}
	}
}
