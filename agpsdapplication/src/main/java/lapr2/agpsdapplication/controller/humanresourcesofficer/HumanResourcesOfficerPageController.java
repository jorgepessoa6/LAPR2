package lapr2.agpsdapplication.controller.humanresourcesofficer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.scene.SceneManager;
import lapr2.agpsdapplication.utils.NameUtils;
import lapr2.framework.company.user.humanresourcesofficer.HumanResourcesOfficer;
import lapr2.framework.company.user.humanresourcesofficer.HumanResourcesOfficerManager;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class HumanResourcesOfficerPageController implements Initializable {

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
	private FlowPane paneRegisterServiceProvider;

	@FXML
	private FlowPane paneViewApplications;

	@FXML
	private FlowPane paneViewServiceProviders;

	@FXML
	private FlowPane paneEvaluateServiceProvider;

	@FXML
	private FlowPane paneViewEvaluations;

	private final Set<Pane> optionPanes = new HashSet<>();

	private SceneManager sceneManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HumanResourcesOfficer humanResourcesOfficer = HomeServices.getInstance().getCompany().getDataManager().get(HumanResourcesOfficerManager.class).getHumanResourcesOfficer(HomeServices.getInstance().getSession().getUser().getEmail());
		lblFirstName.setText(NameUtils.getFirstName(humanResourcesOfficer.getName()));
		lblLastName.setText(NameUtils.getLastName(humanResourcesOfficer.getName()));

		optionPanes.addAll(Arrays.asList(paneHome, paneLogout, paneRegisterServiceProvider, paneViewApplications, paneViewServiceProviders, paneEvaluateServiceProvider, paneViewEvaluations));

		sceneManager = Application.getSceneManager();

		sceneManager.selectPane(optionPanes, paneContent, paneHome, "/fxml/humanresourcesofficer/homepage.fxml", this);
	}

	public void onHome(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneHome, "/fxml/humanresourcesofficer/homepage.fxml", this);
	}

	public void onLogout(MouseEvent mouseEvent) {
		Application.getSceneManager().loadLoginPage();

		HomeServices.getInstance().getSession().logout();
	}

	public void onRegisterServiceProvider(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneRegisterServiceProvider, "/fxml/humanresourcesofficer/registerserviceproviderpage.fxml", this);
	}

	public void onViewApplications(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewApplications, "/fxml/humanresourcesofficer/viewapplicationspage.fxml");
	}

	public void onEvaluateServiceProvider(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneEvaluateServiceProvider, "/fxml/humanresourcesofficer/evaluateserviceproviderpage.fxml", this);
	}

	public void onViewEvaluations(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewEvaluations, "/fxml/humanresourcesofficer/viewevaluationspage.fxml");
	}

	public void onViewServiceProviders(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewServiceProviders, "/fxml/humanresourcesofficer/viewserviceproviderspage.fxml");
	}
}
