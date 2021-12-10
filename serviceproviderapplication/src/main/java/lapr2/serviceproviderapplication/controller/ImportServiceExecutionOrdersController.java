package lapr2.serviceproviderapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder.SimpleServiceExecutionOrder;
import lapr2.framework.io.serviceexecutionorder.CSVOrders;
import lapr2.framework.io.serviceexecutionorder.ServiceExecutionOrderIO;
import lapr2.framework.io.serviceexecutionorder.XLSOrdersAdapter;
import lapr2.framework.io.serviceexecutionorder.XMLOrdersAdapter;
import lapr2.serviceproviderapplication.Application;
import lapr2.serviceproviderapplication.ServiceProviderApplication;
import lapr2.serviceproviderapplication.utils.WarningUtilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ImportServiceExecutionOrdersController implements Initializable {

	private static final String CSV_EXTENSION = "*.csv";
	private static final String XML_EXTENSION = "*.xml";
	private static final String XLS_EXTENSION = "*.xls";

	@FXML
	private TextField txtPath;

	@FXML
	private Label lblImport;

	@FXML
	private Button btnImport;

	private File file;

	private String extension;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnImport.setDisable(true);
	}

	private void loadFileChooser(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		this.file = getOrdersFile(stage, fileChooser);

		if (file == null) {
			WarningUtilities.showLabelWarning(lblImport, "The file path is invalid", 2, 0.1f);
			return;
		}

		txtPath.setText(file.getAbsolutePath());

		String[] fields = file.getName().split("\\.");

		extension = "*." + fields[fields.length - 1];
		btnImport.setDisable(false);
	}

	private File getOrdersFile(Stage stage, FileChooser fileChooser) {
		fileChooser.setTitle("Choose the orders file");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All files", CSV_EXTENSION, XML_EXTENSION, XLS_EXTENSION),
				new FileChooser.ExtensionFilter(CSV_EXTENSION, CSV_EXTENSION),
				new FileChooser.ExtensionFilter(XML_EXTENSION, XLS_EXTENSION),
				new FileChooser.ExtensionFilter(XLS_EXTENSION, XLS_EXTENSION));

		return fileChooser.showOpenDialog(stage);
	}

	private void importFileContent() {
		ServiceExecutionOrderIO serviceExecutionOrderIO = getServiceExecutionOrderIO(extension);

		List<SimpleServiceExecutionOrder> orders;

		try {
			orders = serviceExecutionOrderIO.importServiceExecutionOrders(file.getAbsolutePath());
		} catch (IOException e) {
			WarningUtilities.showLabelWarning(lblImport, "The file is invalid", 2, 0.1f);
			return;
		}

		for (SimpleServiceExecutionOrder order : orders)
			ServiceProviderApplication.getInstance().getSimpleServiceExecutionOrderManager().add(order);

		Application.getSceneManager().loadWarningPopUp("Success", "Service execution orders loaded with success", "/images/warnings/success.png");
	}

	private ServiceExecutionOrderIO getServiceExecutionOrderIO(String extension) {
		ServiceExecutionOrderIO serviceExecutionOrderIO;

		switch (extension) {
			case CSV_EXTENSION:
				serviceExecutionOrderIO = new CSVOrders();
				break;
			case XML_EXTENSION:
				serviceExecutionOrderIO = new XMLOrdersAdapter();
				break;
			case XLS_EXTENSION:
				serviceExecutionOrderIO = new XLSOrdersAdapter();
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + extension);
		}
		return serviceExecutionOrderIO;
	}

	public void onSelectFile(ActionEvent actionEvent) {
		loadFileChooser(new Stage());
	}

	public void onImport(ActionEvent actionEvent) {
		importFileContent();
	}
}
