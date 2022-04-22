package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {
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
            String name;
            String address;
            String zipCode;
            String phoneNumber;
            int customerDivision;
            LocalDate nowDate = LocalDate.now();
            LocalTime nowtime = LocalTime.now();
            LocalDateTime currentTime = LocalDateTime.of(nowDate,nowtime);
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
        customerName.setText("");
        customerStreet.setText("");
        customerCityState.setValue(1);
        customerPhone.setText("");
        customerZip.setText("");

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
