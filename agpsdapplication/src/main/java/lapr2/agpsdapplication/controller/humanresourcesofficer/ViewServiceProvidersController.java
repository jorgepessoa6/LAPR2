package lapr2.agpsdapplication.controller.humanresourcesofficer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.homeservices.HomeServices;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewServiceProvidersController implements Initializable {


    @FXML
    private TableView serviceProviderTableView;

    @FXML
    private TableColumn mechanographicalNumberColumn;

    @FXML
    private TableColumn fullNameColumn;

    @FXML
    private TableColumn abbreviatedNameColumn;

    @FXML
    private TableColumn emailColumn;

    @FXML
    private TableColumn postcodeColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ServiceProviderManager serviceProviderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceProviderManager.class);
        mechanographicalNumberColumn.setCellValueFactory(new PropertyValueFactory<>("mechanographicalNumber"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("completeName"));
        abbreviatedNameColumn.setCellValueFactory(new PropertyValueFactory<>("abbreviatedName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));

        serviceProviderTableView.getItems().addAll(serviceProviderManager.getElements());
    }
}
