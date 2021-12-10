package lapr2.agpsdapplication.controller.serviceprovider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.company.user.serviceprovider.dailyavailability.DailyAvailability;
import lapr2.framework.company.user.serviceprovider.dailyavailability.DailyAvailabilityList;
import lapr2.framework.company.user.serviceprovider.dailyavailability.TimePattern;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddDailyAvailabilityController implements Initializable, ChildrenController {

	@FXML
	private DatePicker datePicker;

	@FXML
	private Slider sliderStartTime;

	@FXML
	private Slider sliderEndTime;

	@FXML
	private ComboBox<TimePattern> cmbPattern;

	@FXML
	private ComboBox<Integer> cmbRepetitions;

	@FXML
	private Label lblWarnings;

	private ServiceProviderPageController serviceProviderPageController;

	private ServiceProvider serviceProvider;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		serviceProvider = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class).getServiceProvider(HomeServices.getInstance().getSession().getUser().getEmail());

		sliderStartTime.valueProperty().addListener((observable, oldValue, newValue) -> adjustEndTimeSlider());
		sliderEndTime.valueProperty().addListener((observable, oldValue, newValue) -> adjustStartTimeSlider());

		datePicker.setConverter(new StringConverter<LocalDate>() {

			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate object) {
				if (object == null)
					return "";
				return dateTimeFormatter.format(object);
			}

			@Override
			public LocalDate fromString(String string) {
				if (string == null || string.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(string, dateTimeFormatter);
			}
		});

		cmbPattern.getItems().addAll(TimePattern.values());

		for (int i = 0, maxRepetitions = 20; i < maxRepetitions; i++) {
			cmbRepetitions.getItems().add(i + 1);
		}
	}

	@Override
	public void setParentController(Initializable initializable) {
		this.serviceProviderPageController = (ServiceProviderPageController) initializable;
	}

	public void onSelectDate(ActionEvent actionEvent) {
		LocalDate now = LocalDate.now();

		if (now.isAfter(datePicker.getValue())) {
			WarningUtilities.showLabelWarning(lblWarnings, "The day is invalid", 2, 0.1);
		}
	}

	public void onAddDailyAvailability(ActionEvent actionEvent) {
		LocalDate now = LocalDate.now();
		double startTime = sliderStartTime.getValue();
		double endTime = sliderEndTime.getValue();

		if (!hasValidValueData(now, startTime, endTime)) return;

		DailyAvailabilityList dailyAvailabilityList = serviceProvider.getDailyAvailabilityList();

		DailyAvailability dailyAvailability = createDailyAvailability(startTime, endTime, dailyAvailabilityList);

		if (dailyAvailability == null) return;

		if (!dailyAvailability.isValid()) {
			WarningUtilities.showLabelWarning(lblWarnings, "The daily availability is not valid", 2, 0.1);
		} else if (dailyAvailabilityList.isOverlapping(dailyAvailability)) {
			WarningUtilities.showLabelWarning(lblWarnings, "The daily availability is overlapping another one", 2, 0.1);
		} else if (!dailyAvailabilityList.isSecure(dailyAvailability)) {
			WarningUtilities.showLabelWarning(lblWarnings, "The daily availability is not valid or duplicated", 2, 0.1);
		} else {
			boolean success = dailyAvailabilityList.add(dailyAvailability, cmbPattern.getValue(), cmbRepetitions.getValue() == null ? 0 : cmbRepetitions.getValue());

			if (success) {
				Application.getSceneManager().loadWarningPopUp("Success", "The operation was concluded with success", "/images/warnings/success.png");
			} else {
				Application.getSceneManager().loadWarningPopUp("Warning", "Not all daily availabilities were added due to overlapping", "/images/warnings/warning.png");
			}
			serviceProviderPageController.onHome(null);
		}
	}

	private boolean hasValidValueData(LocalDate now, double startTime, double endTime) {
		if (datePicker.getValue() == null) {
			WarningUtilities.showLabelWarning(lblWarnings, "The day is not selected", 2, 0.1);
			datePicker.requestFocus();
			return false;
		} else if (now.isAfter(datePicker.getValue())) {
			WarningUtilities.showLabelWarning(lblWarnings, "The day is not valid", 2, 0.1);
			return false;
		} else if (startTime == sliderStartTime.getMax()) {
			WarningUtilities.showLabelWarning(lblWarnings, "The start time is not valid", 2, 0.1);
			sliderStartTime.requestFocus();
			return false;
		} else if (startTime >= endTime) {
			WarningUtilities.showLabelWarning(lblWarnings, "The end time is not valid", 2, 0.1);
			sliderEndTime.requestFocus();
			return false;
		} else if (cmbPattern.getValue() != null && cmbRepetitions.getValue() == null) {
			WarningUtilities.showLabelWarning(lblWarnings, "The repetitions number is not valid", 2, 0.1);
			cmbRepetitions.requestFocus();
			return false;
		}

		return true;
	}

	private void adjustEndTimeSlider() {
		if (sliderStartTime.getValue() > sliderEndTime.getValue()) {
			sliderEndTime.setValue(getClosestSliderValue(sliderStartTime.getValue()));
		}
	}

	private void adjustStartTimeSlider() {
		if (sliderStartTime.getValue() > sliderEndTime.getValue()) {
			sliderStartTime.setValue(getClosestSliderValue(sliderEndTime.getValue()));
		}
	}

	private double getClosestSliderValue(double value) {
		int decimal = (int) (value % 1 / 0.25);

		double increment;

		if (decimal < 1) {
			increment = 0d;
		} else if (decimal < 3) {
			increment = 0.5d;
		} else {
			increment = 1.0d;
		}

		return (int) value + increment;
	}

	private DailyAvailability createDailyAvailability(double startTime, double endTime, DailyAvailabilityList dailyAvailabilityList) {
		int startHour = (int) startTime;
		int endHour = (int) endTime;

		int startMinute = (int) ((startTime % 1) * 60);
		int endMinute = (int) ((endTime % 1) * 60);

		LocalDateTime start = datePicker.getValue().atTime(startHour, startMinute);

		if (LocalDateTime.now().isAfter(start)) {
			WarningUtilities.showLabelWarning(lblWarnings, "The start time is not valid", 2, 0.1);
			sliderStartTime.requestFocus();
			return null;
		}

		LocalDateTime end;

		if (endHour == 24) {
			end = datePicker.getValue().plusDays(1).atTime(0, endMinute);
		} else {
			end = datePicker.getValue().atTime(endHour, endMinute);
		}

		return dailyAvailabilityList.createDailyAvailability(start, end);
	}
}
