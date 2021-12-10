package lapr2.agpsdapplication.controller.serviceprovider;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.scene.SceneManager;
import lapr2.agpsdapplication.utils.NameUtils;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ServiceProviderPageController implements Initializable {

	@FXML
	private Pane paneContent;

	@FXML
	private Label lblFirstName;

	@FXML
	private Label lblLastName;

	@FXML
	private FlowPane paneHome;

	@FXML
	private FlowPane paneLogout;

	@FXML
	private FlowPane paneAddDailyAvailability;

	@FXML
	private FlowPane paneViewDailyAvailabilities;

	@FXML
	private FlowPane paneEvaluateServiceExecutionOrder;

	@FXML
	private FlowPane paneViewServiceExecutionOrders;

	private final Set<Pane> optionPanes = new HashSet<>();
	
	private SceneManager sceneManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ServiceProvider serviceProvider = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class).getServiceProvider(HomeServices.getInstance().getSession().getUser().getEmail());
		lblFirstName.setText(NameUtils.getFirstName(serviceProvider.getCompleteName()));
		lblLastName.setText(NameUtils.getLastName(serviceProvider.getCompleteName()));

		optionPanes.addAll(Arrays.asList(paneHome, paneLogout, paneAddDailyAvailability, paneViewDailyAvailabilities, paneEvaluateServiceExecutionOrder, paneViewDailyAvailabilities, paneViewServiceExecutionOrders));

		sceneManager = Application.getSceneManager();

		sceneManager.selectPane(optionPanes, paneContent, paneHome, "/fxml/serviceprovider/homepage.fxml", this);
	}

	public void onHome(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneHome, "/fxml/serviceprovider/homepage.fxml", this);
	}

	public void onLogout(MouseEvent mouseEvent) {
		Application.getSceneManager().loadLoginPage();

		HomeServices.getInstance().getSession().logout();
	}

	public void onAddDailyAvailability(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneAddDailyAvailability, "/fxml/serviceprovider/adddailyavailabilitypage.fxml", this);
	}

	public void onViewDailyAvailabilities(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewDailyAvailabilities, "/fxml/serviceprovider/viewdailyavailabilitiespage.fxml");
	}

	public void onEvaluateServiceExecutionOrder(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneEvaluateServiceExecutionOrder, "/fxml/serviceprovider/evaluateserviceexecutionorderpage.fxml", this);
	}

	public void onViewServiceExecutionOrders(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewServiceExecutionOrders, "/fxml/serviceprovider/checkserviceexecutionorderspage.fxml", this);
	}
}
