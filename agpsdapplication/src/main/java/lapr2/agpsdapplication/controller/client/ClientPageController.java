package lapr2.agpsdapplication.controller.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.scene.SceneManager;
import lapr2.agpsdapplication.utils.NameUtils;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class ClientPageController implements Initializable {

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
	private FlowPane paneRequestService;

	@FXML
	private FlowPane paneDecideProposedTime;

	@FXML
	private FlowPane paneRateServices;

	@FXML
	private FlowPane paneViewServiceRequests;

	@FXML
	private FlowPane paneAddPostalAddress;

	@FXML
	private FlowPane paneViewPostalAddresses;

	private final Set<Pane> optionPanes = new HashSet<>();

	private SceneManager sceneManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Client client = HomeServices.getInstance().getCompany().getDataManager().get(ClientManager.class).getClient(HomeServices.getInstance().getSession().getUser().getEmail());
		lblFirstName.setText(NameUtils.getFirstName(client.getName()));
		lblLastName.setText(NameUtils.getLastName(client.getName()));

		optionPanes.addAll(Arrays.asList(paneHome, paneLogout, paneRequestService, paneDecideProposedTime, paneRateServices,
				paneViewServiceRequests, paneAddPostalAddress, paneViewPostalAddresses));

		sceneManager = Application.getSceneManager();

		sceneManager.selectPane(optionPanes, paneContent, paneHome, "/fxml/client/homepage.fxml", this);
	}

	public void onHome(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneHome, "/fxml/client/homepage.fxml", this);
	}

	public void onLogout(MouseEvent mouseEvent) {
		Application.getSceneManager().loadLoginPage();

		HomeServices.getInstance().getSession().logout();
	}

	public void onRequestService(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneRequestService, "/fxml/client/requestservicepage.fxml", this);
	}

	public void onDecideProposedTime(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneDecideProposedTime, "/fxml/client/decideproposedtimepage.fxml", this);
	}

	public void onRateServices(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneRateServices, "/fxml/client/rateservicepage.fxml", this);
	}

	public void onViewServiceRequests(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewServiceRequests, "/fxml/client/viewservicerequestspage.fxml");
	}

	public void onAddPostalAddress(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneAddPostalAddress, "/fxml/client/addpostaladdresspage.fxml", this);
	}

	public void onViewPostalAddress(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewPostalAddresses, "/fxml/client/viewpostaladdressespage.fxml");
	}

}
