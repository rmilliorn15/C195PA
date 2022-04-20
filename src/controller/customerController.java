package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Customer;
import model.CustomerDB;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
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




    public void saveBtnAction(ActionEvent actionEvent) {
        try {
            int id;
            String name;
            String address;
            String zipCode;
            String country;
            String phoneNumber;
           int customerDivision;
           Timestamp currentTime = new Timestamp(2012,12,12,12,12,12,12) ;

            name = customerName.getText();
            address= customerStreet.getText();
            zipCode = customerZip.getText();
            phoneNumber = customerPhone.getText();
            customerDivision = 3;
            String user = User.getUserName();
            CustomerDB.insert(name,address,zipCode,phoneNumber,currentTime,user,currentTime,user,customerDivision);







        }catch (NumberFormatException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearBtnAction(ActionEvent actionEvent) {
    }

    public void cancelBtnAction(ActionEvent actionEvent) {
    }

    public void updateBtnAction(ActionEvent actionEvent) {



    }

    public void deleteBtnAction(ActionEvent actionEvent) {
       Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
       Customer.removeCustomer(selectedCustomer);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
