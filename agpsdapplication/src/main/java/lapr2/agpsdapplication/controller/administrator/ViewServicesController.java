package lapr2.agpsdapplication.controller.administrator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.framework.company.service.Service;
import lapr2.framework.company.service.ServiceManager;
import lapr2.framework.company.servicetype.ServiceTypeManager;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewServicesController implements Initializable {

	@FXML
	private TableView tableViewServiceTypes;

	@FXML
	private TableColumn tableColumnServiceTypes;

	@FXML
	private TableView tableViewServices;

	@FXML
	private TableColumn tableColumnServiceId;

	@FXML
	private TableColumn tableColumnServiceCategory;

	@FXML
	private TableColumn tableColumnServiceDescription;

	@FXML
	private TableColumn tableColumnServiceCost;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ServiceTypeManager serviceTypeManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceTypeManager.class);
		ServiceManager serviceManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceManager.class);

		tableColumnServiceTypes.setCellValueFactory(new PropertyValueFactory<>("type"));

		tableColumnServiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnServiceCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
		tableColumnServiceDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		tableColumnServiceCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

		tableViewServiceTypes.getItems().addAll(serviceTypeManager.getElements());

		serviceManager.getElements().forEach(service -> tableViewServices.getItems().add(new TableService(service)));
	}

	public class TableService {

		@Getter
		private String id;

		@Getter
		private String category;

		@Getter
		private String description;

		@Getter
		private String cost;

		public TableService(Service service) {
			this.id = service.getId();
			this.category = service.getCategory().getDescription();
			this.description = service.getBriefDescription();
			this.cost = String.format(Locale.ENGLISH, "%.2f%s", service.getCost(), Currency.getInstance(HomeServices.getInstance().getFileConfiguration().getProperty("currencyCode")).getSymbol());
		}
	}
}
