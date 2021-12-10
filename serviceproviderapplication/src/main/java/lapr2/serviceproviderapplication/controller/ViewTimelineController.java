package lapr2.serviceproviderapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder.SimpleServiceExecutionOrder;
import lapr2.serviceproviderapplication.Application;
import lapr2.serviceproviderapplication.ServiceProviderApplication;
import lombok.Getter;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewTimelineController implements Initializable {

	private static final String ALL_CLIENTS = "All";

	@FXML
	private ComboBox cmbClients;

	@FXML
	private ToggleGroup toggleGroup;

	@FXML
	private RadioButton all;

	@FXML
	private RadioButton past;

	@FXML
	private RadioButton future;

	@FXML
	private Accordion accordionOrders;

	private Toggle previousButton = all;

	private List<OrderPane> allOrders = new ArrayList<>();

	private List<OrderPane> pastOrders = new ArrayList<>();

	private List<OrderPane> futureOrders = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LocalDateTime now = LocalDateTime.now();

		List<SimpleServiceExecutionOrder> oreder = ServiceProviderApplication.getInstance().getSimpleServiceExecutionOrderManager().getOrdersTimeline();

		cmbClients.getItems().add(ALL_CLIENTS);

		for (SimpleServiceExecutionOrder order : oreder) {
			TitledPane titledPane = ((SingleTitledPaneController) Application.getSceneManager().loadTitledPane("/fxml/singletitledpane.fxml")).getTitlePane(order);
			cmbClients.getItems().add(order.getClient());

			if (order.getSchedule().isAfter(now)) {
				futureOrders.add(new OrderPane(order, titledPane));
			} else {
				pastOrders.add(new OrderPane(order, titledPane));
			}
			allOrders.add(new OrderPane(order, titledPane));
		}

		cmbClients.getSelectionModel().selectFirst();
		onButtonClicked(new ActionEvent());
	}

	public void onButtonClicked(ActionEvent actionEvent) {
		RadioButton buttonSelected = (RadioButton) toggleGroup.getSelectedToggle();

		accordionOrders.getPanes().clear();

		String selectedClient = (String) cmbClients.getSelectionModel().getSelectedItem();

		if (past.equals(buttonSelected)) {
			for (OrderPane order : pastOrders)
				if (selectedClient.equals(ALL_CLIENTS) || selectedClient.equals(order.getOrder().getClient()))
					accordionOrders.getPanes().add(order.getPane());
		} else if (future.equals(buttonSelected)) {
			for (OrderPane order : futureOrders)
				if (selectedClient.equals(ALL_CLIENTS) || selectedClient.equals(order.getOrder().getClient()))
					accordionOrders.getPanes().add(order.getPane());
		} else {
			for (OrderPane order : allOrders)
				if (selectedClient.equals(ALL_CLIENTS) || selectedClient.equals(order.getOrder().getClient()))
					accordionOrders.getPanes().add(order.getPane());
		}

		previousButton = buttonSelected;
	}

	private class OrderPane {

		@Getter
		private final SimpleServiceExecutionOrder order;

		@Getter
		private final TitledPane pane;

		public OrderPane(SimpleServiceExecutionOrder order, TitledPane pane) {
			this.order = order;
			this.pane = pane;
		}
	}
}
