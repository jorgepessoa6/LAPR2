package lapr2.agpsdapplication.controller.administrator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewGeographicalAreasController implements Initializable {

	@FXML
	private TableColumn tableColumnPostCode;

	@FXML
	private TableColumn tableColumnRadius;

	@FXML
	private TableColumn tableColumnTravelCost;

	@FXML
	private TableView tableViewGeographicalAreas;

	@FXML
	private TableColumn tableColumnDesignation;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GeographicalAreaManager geographicalAreaManager = HomeServices.getInstance().getCompany().getDataManager().get(GeographicalAreaManager.class);

		tableColumnDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
		tableColumnPostCode.setCellValueFactory(new PropertyValueFactory<>("postcode"));
		tableColumnRadius.setCellValueFactory(new PropertyValueFactory<>("radius"));
		tableColumnTravelCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

		List<TableGeographicalArea> tableGeographicalAreas = new ArrayList<>();
		geographicalAreaManager.getElements().forEach(geographicalArea -> tableGeographicalAreas.add(new TableGeographicalArea(geographicalArea)));
		tableViewGeographicalAreas.setItems(FXCollections.observableArrayList(tableGeographicalAreas));
	}

	public class TableGeographicalArea {

		@Getter
		private final String designation;

		@Getter
		private final String postcode;

		@Getter
		private final String radius;

		@Getter
		private final String cost;

		public TableGeographicalArea(GeographicalArea geographicalArea) {
			this.designation = geographicalArea.getDesignation();
			this.postcode = geographicalArea.getCenteredPostCode().getPostCodeDesignation();
			this.radius = String.format(Locale.ENGLISH, "%.2f km", geographicalArea.getRadius() / 1000);
			this.cost = String.format(Locale.ENGLISH, "%.2f%s", geographicalArea.getTravelCost(), HomeServices.getInstance().getFileConfiguration().getCurrency().getSymbol());
		}
	}

}
