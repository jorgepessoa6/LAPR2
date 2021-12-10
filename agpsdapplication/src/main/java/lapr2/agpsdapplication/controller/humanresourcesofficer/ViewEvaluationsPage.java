package lapr2.agpsdapplication.controller.humanresourcesofficer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.company.user.serviceprovider.rating.Rating;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.util.*;

public class ViewEvaluationsPage implements Initializable {

	@FXML
	private TableView<TableEvaluation> tableViewEvaluations;

	@FXML
	private TableColumn tableColumnNumber;

	@FXML
	private TableColumn tableColumnName;

	@FXML
	private TableColumn tableColumnLabel;

	@FXML
	private TableColumn tableColumnRating;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ServiceProviderManager serviceProviderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class);

		tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
		tableColumnRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

		List<TableEvaluation> tableEvaluations = new ArrayList<>();

		serviceProviderManager.getElements().forEach(serviceProvider -> {
			if (serviceProvider.getRating().getLabel() != Rating.Label.NONE)
				tableEvaluations.add(new TableEvaluation(serviceProvider));
		});

		tableEvaluations.sort(Comparator.comparing(TableEvaluation::getLabel).thenComparing(TableEvaluation::getName));

		tableViewEvaluations.getItems().addAll(tableEvaluations);
	}

	public class TableEvaluation {

		@Getter
		private String number;

		@Getter
		private String name;

		@Getter
		private Rating.Label label;

		@Getter
		private String rating;

		public TableEvaluation(ServiceProvider serviceProvider) {
			this.number = serviceProvider.getMechanographicalNumber();
			this.name = serviceProvider.getCompleteName();
			this.label = serviceProvider.getRating().getLabel();
			this.rating = String.format(Locale.ENGLISH, "%.2f", serviceProvider.getRating().getMean());
		}

	}
}
