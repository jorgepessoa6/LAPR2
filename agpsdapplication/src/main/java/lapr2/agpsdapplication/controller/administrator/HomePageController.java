package lapr2.agpsdapplication.controller.administrator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.category.CategoryManager;
import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.company.manager.DataManager;
import lapr2.framework.company.service.Service;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrderManager;
import lapr2.framework.company.servicerequest.ServiceDescription;
import lapr2.framework.company.user.administrator.AdministratorManager;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.company.user.humanresourcesofficer.HumanResourcesOfficerManager;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.company.user.user.UserManager;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class HomePageController implements Initializable, ChildrenController {

	@FXML
	private Label userCountLabel;

	@FXML
	private Label serviceProviderCountLabel;

	@FXML
	private Label administratorCountLabel;

	@FXML
	private Label clientCountLabel;

	@FXML
	private Label humanResourcesOfficerCountLabel;

	@FXML
	private Label categoryCountLabel;

	@FXML
	private Label firstCategoryLabel;

	@FXML
	private Label secondCategoryLabel;

	@FXML
	private Label thirdCategoryLabel;

	@FXML
	private Label geographicalAreasCountLabel;

	@FXML
	private Label firstGeographicalAreaLabel;

	@FXML
	private Label secondGeographicalAreaLabel;

	@FXML
	private Label thirdGeographicalAreaLabel;

	@FXML
	private PieChart chart;

	private AdministratorPageController administratorPageController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DataManager dataManager = HomeServices.getInstance().getCompany().getDataManager();

		setupUsersPane(dataManager);
		setupCategoriesPane(dataManager);
		setupGeographicalAreasPane(dataManager);
		setupChart(dataManager);
	}


	public void onViewCategories(MouseEvent mouseEvent) {
		administratorPageController.onViewCategories(mouseEvent);
	}

	public void onViewGeographicalAreas(MouseEvent mouseEvent) {
		administratorPageController.onViewGeographicalAreas(mouseEvent);
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.administratorPageController = (AdministratorPageController) initializable;
	}

	private void setupUsersPane(DataManager dataManager) {
		userCountLabel.setText(String.format("%d users", dataManager.get(UserManager.class).getElements().size()));
		serviceProviderCountLabel.setText(String.format("%d service providers", dataManager.get(ServiceProviderManager.class).getElements().size()));
		administratorCountLabel.setText(String.format("%d administrators", dataManager.get(AdministratorManager.class).getElements().size()));
		clientCountLabel.setText(String.format("%d clients", dataManager.get(ClientManager.class).getElements().size()));
		humanResourcesOfficerCountLabel.setText(String.format("%d human resources officer", dataManager.get(HumanResourcesOfficerManager.class).getElements().size()));
	}

	private void setupCategoriesPane(DataManager dataManager) {
		CategoryManager categoryManager = dataManager.get(CategoryManager.class);

		categoryCountLabel.setText(String.format("%d categories", categoryManager.getElements().size()));

		setCategoryDescription(categoryManager, 1, firstCategoryLabel);
		setCategoryDescription(categoryManager, 2, secondCategoryLabel);
		setCategoryDescription(categoryManager, 3, thirdCategoryLabel);
	}

	private void setCategoryDescription(CategoryManager categoryManager, int count, Label label) {
		if (categoryManager.getElements().size() >= count) {
			Category category = categoryManager.getElements().get(count - 1);

			label.setText(String.format("%s - %s", category.getId(), category.getDescription()));
		} else {
			label.setText("No categories defined yet");
		}
	}

	private void setupGeographicalAreasPane(DataManager dataManager) {
		GeographicalAreaManager geographicalAreaManager = dataManager.get(GeographicalAreaManager.class);

		geographicalAreasCountLabel.setText(String.format("%d geographical areas", geographicalAreaManager.getElements().size()));

		String currency = HomeServices.getInstance().getFileConfiguration().getCurrency().getSymbol();

		setGeographicalAreaDescription(geographicalAreaManager, 1, firstGeographicalAreaLabel, currency);
		setGeographicalAreaDescription(geographicalAreaManager, 2, secondGeographicalAreaLabel, currency);
		setGeographicalAreaDescription(geographicalAreaManager, 3, thirdGeographicalAreaLabel, currency);
	}

	private void setGeographicalAreaDescription(GeographicalAreaManager geographicalAreaManager, int count, Label label, String currency) {
		if (geographicalAreaManager.getElements().size() >= count) {
			GeographicalArea geographicalArea = geographicalAreaManager.getElements().get(count - 1);

			String radius = String.format(Locale.ENGLISH, "%.2f", geographicalArea.getRadius() / 1000);
			String travelCost = String.format(Locale.ENGLISH, "%.2f", geographicalArea.getTravelCost());

			label.setText(String.format("%s (%s), %s km, %s %s", geographicalArea.getDesignation(), geographicalArea.getCenteredPostCode().getPostCodeDesignation(), radius,
					travelCost, currency));
		} else {
			label.setText("No geographical areas defined yet");
		}
	}

	private void setupChart(DataManager dataManager) {
		ServiceExecutionOrderManager serviceExecutionOrderManager = dataManager.get(ServiceExecutionOrderManager.class);

		ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

		HashMap<Category, Double> rawData = new HashMap<>();

		for (ServiceExecutionOrder serviceExecutionOrder : serviceExecutionOrderManager.getElements()) {
				ServiceDescription serviceDescription = serviceExecutionOrder.getAffectation().getServiceDescription();
				Service service = serviceDescription.getService();
				Category category = service.getCategory();

				double cost = service.getCost(serviceDescription.getDuration());
				if (rawData.containsKey(category)) {
                    rawData.put(category, rawData.get(category) + cost);
				} else {
					rawData.put(category, cost);
				}
		}

		String currency = HomeServices.getInstance().getFileConfiguration().getCurrency().getSymbol();

		for (Map.Entry<Category, Double> entry : rawData.entrySet()) {
			data.add(new PieChart.Data(String.format(Locale.ENGLISH, "%s - %.2f%s", entry.getKey().getDescription(),
					entry.getValue(), currency), entry.getValue()));
		}

		chart.setData(data);
	}
}
