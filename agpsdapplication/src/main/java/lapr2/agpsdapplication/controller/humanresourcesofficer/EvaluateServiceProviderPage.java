package lapr2.agpsdapplication.controller.humanresourcesofficer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.company.user.serviceprovider.rating.Rating;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class EvaluateServiceProviderPage implements Initializable, ChildrenController {

	@FXML
	private ComboBox<ComboBoxServiceProvider> cmbServiceProvider;

	@FXML
	private Label lblServiceProviderMean;

	@FXML
	private Label lblServiceProviderStd;

	@FXML
	private Label lblPopulationMean;

	@FXML
	private Label lblPopulationStd;

	@FXML
	private BarChart<String, Integer> barChart;

	@FXML
	private ComboBox<Rating.Label> cmbLabel;

	@FXML
	private Label lblWarnings;

	@FXML
	private Button btnChange;

	private final XYChart.Series<String, Integer> series = new XYChart.Series<>();

	private HumanResourcesOfficerPageController humanResourcesOfficerPageController;

	private ServiceProviderManager serviceProviderManager;

	private ServiceProvider selectedServiceProvider;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.serviceProviderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class);

		barChart.setBarGap(0d);
		barChart.setCategoryGap(0d);

		barChart.getData().add(series);

		for (int i = Rating.MIN_RATING; i <= Rating.MAX_RATING; i++) {
			series.getData().add(new XYChart.Data<>(String.valueOf(i), 0));
		}

		Node n = barChart.lookup(".data0.chart-bar");
		n.setStyle("-fx-bar-fill: #ff1616");
		n = barChart.lookup(".data1.chart-bar");
		n.setStyle("-fx-bar-fill: #ff5416");
		n = barChart.lookup(".data2.chart-bar");
		n.setStyle("-fx-bar-fill: #ff9616");
		n = barChart.lookup(".data3.chart-bar");
		n.setStyle("-fx-bar-fill: #ffe316");
		n = barChart.lookup(".data4.chart-bar");
		n.setStyle("-fx-bar-fill: #a9ff16");
		n = barChart.lookup(".data5.chart-bar");
		n.setStyle("-fx-bar-fill: #27ff0f");

		cmbLabel.getItems().addAll(Rating.Label.values());
		cmbLabel.setDisable(true);
		btnChange.setDisable(true);

		serviceProviderManager.getElements().forEach(serviceProvider -> cmbServiceProvider.getItems().add(new ComboBoxServiceProvider(serviceProvider)));
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.humanResourcesOfficerPageController = (HumanResourcesOfficerPageController) initializable;
	}

	public void onSelectServiceProvider(ActionEvent actionEvent) {
		ComboBoxServiceProvider comboBoxServiceProvider = cmbServiceProvider.getValue();

		if (comboBoxServiceProvider != null) {
			this.selectedServiceProvider = comboBoxServiceProvider.getServiceProvider();

			setupPane();
			setupBarChart();
		}
	}

	private void setupPane() {
		cmbLabel.setDisable(false);
		btnChange.setDisable(false);

		Rating serviceProviderRating = selectedServiceProvider.getRating();

		lblServiceProviderMean.setText(String.format(Locale.ENGLISH, "Mean: %.2f", serviceProviderRating.getMean()));
		lblServiceProviderStd.setText(String.format(Locale.ENGLISH, "Standard deviation: %.2f", serviceProviderRating.getStandardDeviation()));

		lblPopulationMean.setText(String.format(Locale.ENGLISH, "Mean: %.2f", serviceProviderManager.getPopulationMean()));
		lblPopulationStd.setText(String.format(Locale.ENGLISH, "Standard deviation: %.2f", serviceProviderManager.getPopulationStandardDeviation()));

		if (serviceProviderRating.getLabel() == Rating.Label.NONE) {
			cmbLabel.setPromptText(String.format("Suggested label: %s", serviceProviderRating.getSuggestedLabel()));
		} else {
			cmbLabel.setValue(serviceProviderRating.getLabel());
		}
	}

	private void setupBarChart() {
		Rating serviceProviderRating = selectedServiceProvider.getRating();

		for (XYChart.Data<String, Integer> data : series.getData()) {
			data.setYValue(serviceProviderRating.getReviewNumber(Integer.valueOf(data.getXValue())));
		}

		barChart.requestLayout();
	}

	public void onEvaluate(ActionEvent actionEvent) {
		Rating.Label ratingLabel = cmbLabel.getValue();

		if (ratingLabel == null) {
			WarningUtilities.showLabelWarning(lblWarnings, "The label is not valid", 2, 0.1);
		} else if (selectedServiceProvider == null) {
			WarningUtilities.showLabelWarning(lblWarnings, "Select a service provider first", 2, 0.1);
		} else if (ratingLabel == selectedServiceProvider.getRating().getLabel()) {
			WarningUtilities.showLabelWarning(lblWarnings, "The label is the same", 2, 0.1);
		} else {
			selectedServiceProvider.getRating().setLabel(ratingLabel);

			Application.getSceneManager().loadWarningPopUp("Success", "The rating label was set with success", "/images/warnings/success.png");

			humanResourcesOfficerPageController.onHome(null);
		}
	}

	public class ComboBoxServiceProvider {

		@Getter
		private ServiceProvider serviceProvider;

		public ComboBoxServiceProvider(ServiceProvider serviceProvider) {
			this.serviceProvider = serviceProvider;
		}

		@Override
		public String toString() {
			return String.format("%s - %s", serviceProvider.getCompleteName(), serviceProvider.getMechanographicalNumber());
		}
	}
}
