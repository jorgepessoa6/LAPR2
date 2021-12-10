package lapr2.agpsdapplication.controller.administrator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.scene.SceneManager;
import lapr2.agpsdapplication.utils.NameUtils;
import lapr2.framework.company.user.administrator.Administrator;
import lapr2.framework.company.user.administrator.AdministratorManager;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AdministratorPageController implements Initializable {

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
	private FlowPane paneSpecifyCategory;

	@FXML
	private FlowPane paneViewCategories;

	@FXML
	private FlowPane paneSpecifyService;

	@FXML
	private FlowPane paneViewServices;

	@FXML
	private FlowPane paneSpecifyGeographicalArea;

	@FXML
	private FlowPane paneViewGeographicalAreas;

	private final Set<Pane> optionPanes = new HashSet<>();

	private SceneManager sceneManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Administrator administrator = HomeServices.getInstance().getCompany().getDataManager().get(AdministratorManager.class).getAdministrator(HomeServices.getInstance().getSession().getUser().getEmail());
		lblFirstName.setText(NameUtils.getFirstName(administrator.getName()));
		lblLastName.setText(NameUtils.getLastName(administrator.getName()));

		optionPanes.addAll(Arrays.asList(paneHome, paneLogout, paneSpecifyCategory, paneViewCategories, paneSpecifyService, paneViewServices, paneSpecifyGeographicalArea, paneViewGeographicalAreas));

		sceneManager = Application.getSceneManager();

		sceneManager.selectPane(optionPanes, paneContent, paneHome, "/fxml/administrator/homepage.fxml", this);
	}

	public void onHome(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneHome, "/fxml/administrator/homepage.fxml", this);
	}

	public void onLogout(MouseEvent mouseEvent) {
		Application.getSceneManager().loadLoginPage();

		HomeServices.getInstance().getSession().logout();
	}

	public void onSpecifyCategory(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneSpecifyCategory, "/fxml/administrator/specifycategorypage.fxml", this);
	}

	public void onViewCategories(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewCategories, "/fxml/administrator/viewcategoriespage.fxml");
	}

	public void onSpecifyService(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneSpecifyService, "/fxml/administrator/specifyservicepage.fxml", this);
	}

	public void onViewServices(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewServices, "/fxml/administrator/viewservicespage.fxml");
	}

	public void onSpecifyGeographicalArea(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneSpecifyGeographicalArea, "/fxml/administrator/specifygeographicalareapage.fxml", this);
	}

	public void onViewGeographicalAreas(MouseEvent mouseEvent) {
		sceneManager.selectPane(optionPanes, paneContent, paneViewGeographicalAreas, "/fxml/administrator/viewgeographicalareaspage.fxml");
	}
}
