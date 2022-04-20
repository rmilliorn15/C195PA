package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class customerController implements Initializable {
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> customerIdcolumn;
    public TableColumn<Customer, String> nameColumn;
    public TableColumn<Customer, String> streetColumn;
    public TableColumn<Customer, String> cityStateColumn;
    public TableColumn<Customer, String> countryColumn;
    public TableColumn<Customer, String> zipColumn;
    public TableColumn<Customer, String> phoneColumn;
    public TextField customerName;
    public TextField customerPhone;
    public TextField customerStreet;
    public ComboBox customerCountry;
    public TextField customerZip;
    public TextField customerIDBox;
    public ComboBox customerCityState;

    private static int customerID = 1;



    public void saveBtnAction(ActionEvent actionEvent) {
        try {
            int id = customerID;
            String name;
            String address;
            String zipCode;
            String country;
            String phoneNumber;
            String customerDivision;

            name = customerName.getText();
            address= customerStreet.getText();
            zipCode = customerZip.getText();
            phoneNumber = customerPhone.getText();
            country = (String) customerCountry.getSelectionModel().getSelectedItem();
            customerDivision = (String) customerCityState.getSelectionModel().getSelectedItem();

            Customer newCustomer = new Customer(id, name,address,zipCode,country,phoneNumber,customerDivision);



            Customer.addCustomers(newCustomer);
            customerID++;
        }catch (NumberFormatException e){}

    }

    public void clearBtnAction(ActionEvent actionEvent) {
    }

    public void cancelBtnAction(ActionEvent actionEvent) {
    }

    public void updateBtnAction(ActionEvent actionEvent) {


        customerID++;
    }

    public void deleteBtnAction(ActionEvent actionEvent) {
       Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
       Customer.removeCustomer(selectedCustomer);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDBox.setText(String.valueOf(customerID));


    }
}
