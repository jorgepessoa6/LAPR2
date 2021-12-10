package lapr2.agpsdapplication.controller.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postaladdress.PostalAddressList;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.client.ClientManager;
import lapr2.framework.company.user.user.User;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewPostalAddressesController implements Initializable {

    @FXML
    public TableView postalAddressTableView;

    @FXML
    public TableColumn addressColumn;

    @FXML
    public TableColumn districtColumn;

    @FXML
    public TableColumn municipalityColumn;

    @FXML
    public TableColumn localityColumn;

    @FXML
    public TableColumn postcodeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = HomeServices.getInstance().getSession().getUser();
        Client client = HomeServices.getInstance().getCompany().getDataManager().get(ClientManager.class).getClient(user.getEmail());
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        districtColumn.setCellValueFactory(new PropertyValueFactory<>("district"));
        municipalityColumn.setCellValueFactory(new PropertyValueFactory<>("municipality"));
        localityColumn.setCellValueFactory(new PropertyValueFactory<>("locality"));
        postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postcode"));
        PostalAddressList postalAddressList = client.getPostalAddressList();

        postalAddressList.getElements().forEach(postalAddress -> postalAddressTableView.getItems().add(new TablePostalAddress(postalAddress)));
    }

    public class TablePostalAddress {

        @Getter
        private String address;

        @Getter
        private String district;

        @Getter
        private String municipality;

        @Getter
        private String locality;

        @Getter
        private String postcode;

        public TablePostalAddress(PostalAddress postalAddress) {
            this.address = postalAddress.getAddress();
            this.district = postalAddress.getPostCode().getDistrict();
            this.municipality = postalAddress.getPostCode().getMunicipality();
            this.locality = postalAddress.getPostCode().getLocality();
            this.postcode = postalAddress.getPostCode().getPostCodeDesignation();
        }
    }
}
