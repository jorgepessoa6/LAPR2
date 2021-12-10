package lapr2.serviceproviderapplication.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder.SimpleServiceExecutionOrder;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SingleTitledPaneController implements Initializable {

	@FXML
	private TitledPane titledPane;

	@FXML
	private Label lblCategory;

	@FXML
	private Label lblPostalAddress;

	@FXML
	private Label lblSchedule;

	@FXML
	private Label lblClient;

	@FXML
	private Label lblServiceType;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public TitledPane getTitlePane(SimpleServiceExecutionOrder order) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		this.titledPane.setText(String.format("%sh - %s", order.getSchedule().format(formatter), order.getCategory()));

		this.lblCategory.setText(order.getCategory());
		this.lblPostalAddress.setText(order.getPostalAddress());
		this.lblSchedule.setText(order.getSchedule().format(formatter));
		this.lblClient.setText(order.getClient());
		this.lblServiceType.setText(order.getServiceType());

		return this.titledPane;
	}
}
