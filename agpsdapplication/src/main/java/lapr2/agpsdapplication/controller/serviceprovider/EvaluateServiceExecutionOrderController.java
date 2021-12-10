package lapr2.agpsdapplication.controller.serviceprovider;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lapr2.agpsdapplication.Application;
import lapr2.agpsdapplication.controller.interfaces.ChildrenController;
import lapr2.agpsdapplication.utils.WarningUtilities;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrderManager;
import lapr2.framework.company.serviceexecutionorder.issues.Issue;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.ResourceBundle;

public class EvaluateServiceExecutionOrderController implements Initializable, ChildrenController {


    @FXML
    public Button btnEvaluate;

    @FXML
    public ComboBox<String> evaluationComboBox;

    @FXML
    public ComboBox<ServiceExecutionOrder> serviceComboBox;

    @FXML
    public TextField txtIssueDescription;

    @FXML
    public TextField txtTroubleshootingDescription;

    @FXML
    public Label warningsLabel;

    private ServiceProviderPageController serviceProviderPageController;

    private final ObservableList<String> list = FXCollections.observableArrayList("As Stipulated", "Something happened");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        evaluationComboBox.setItems(list);
        String email = HomeServices.getInstance().getSession().getUser().getEmail();
        ServiceProviderManager serviceProviderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class);
        ServiceExecutionOrderManager serviceExecutionOrderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceExecutionOrderManager.class);
        ServiceProvider serviceProvider = serviceProviderManager.getServiceProvider(email);
        serviceComboBox.setItems(FXCollections.observableArrayList(serviceExecutionOrderManager.getServicesWaitingEvaluation(serviceProvider)));
    }

    public void onEvaluate() {
        ServiceExecutionOrder serviceExecutionOrder = serviceComboBox.getValue();
        String issueDescription = txtIssueDescription.getText().trim();
        String troubleshootingDescription = txtTroubleshootingDescription.getText().trim();
        if (serviceExecutionOrder != null) {
            if (evaluationComboBox.getValue() != null) {
                if (evaluationComboBox.getValue().equals("Something happened")) {
                    if (issueDescription.isEmpty()) {
						WarningUtilities.showLabelWarning(warningsLabel, "The issue description cannot be empty", 2, 0.1d);
                        txtIssueDescription.requestFocus();
                    } else if (troubleshootingDescription.isEmpty()) {
						WarningUtilities.showLabelWarning(warningsLabel, "The troubleshooting description cannot be empty", 2, 0.1d);
                        txtTroubleshootingDescription.requestFocus();
                    } else {
                        Issue issue = serviceExecutionOrder.createIssue(issueDescription, troubleshootingDescription);
                        serviceExecutionOrder.setIssue(issue);
                        serviceExecutionOrder.setState(ServiceExecutionOrder.State.EVALUATED);
                        Application.getSceneManager().loadWarningPopUp("Success", "The operation was concluded with success", "/images/warnings/success.png");
                        serviceProviderPageController.onHome(null);
                    }
                } else {
                    serviceExecutionOrder.setState(ServiceExecutionOrder.State.EVALUATED);
                    Application.getSceneManager().loadWarningPopUp("Success", "The operation was concluded with success", "/images/warnings/success.png");
                    serviceProviderPageController.onHome(null);
                }
            } else {
				WarningUtilities.showLabelWarning(warningsLabel, "Choose an option of how the service ran", 2, 0.1d);
                evaluationComboBox.requestFocus();
            }
        } else {
			WarningUtilities.showLabelWarning(warningsLabel, "Choose a service", 2, 0.1d);
            serviceComboBox.requestFocus();
        }
    }

    @Override
    public void setParentController(Initializable initializable) {
        this.serviceProviderPageController = (ServiceProviderPageController) initializable;
    }
}
