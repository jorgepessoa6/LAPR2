package lapr2.agpsdapplication.controller.administrator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.category.CategoryManager;
import lapr2.framework.company.service.ServiceManager;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCategoriesController implements Initializable {

	@FXML
	private TableView<TableCategory> tableViewCategories;

	@FXML
	private TableColumn<String, String> tableColumnId;

	@FXML
	private TableColumn<String, String> tableColumnDescription;

	@FXML
	private TableColumn<Integer, String> tableColumnNumberServices;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CategoryManager categoryManager = HomeServices.getInstance().getCompany().getDataManager().get(CategoryManager.class);
		List<Category> categories = categoryManager.getElements();

		categories.forEach(category -> tableViewCategories.getItems().add(new TableCategory(category)));

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		tableColumnNumberServices.setCellValueFactory(new PropertyValueFactory<>("numberOfServices"));
	}

	public class TableCategory {

		@Getter
		private String id;

		@Getter
		private String description;

		@Getter
		private String numberOfServices;

		public TableCategory(Category category) {
			this.id = category.getId();
			this.description = category.getDescription();
			this.numberOfServices = String.valueOf(HomeServices.getInstance().getCompany().getDataManager().get(ServiceManager.class).getServicesOf(category).size());
		}
	}
}
