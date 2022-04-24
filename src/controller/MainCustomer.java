package controller;

import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.CustomerDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainCustomer implements Initializable {
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> customerIdColumn;
    public TableColumn<Customer, String> nameColumn;
    public TableColumn<Customer, String> streetColumn;
    public TableColumn<Customer, String> cityStateColumn;
    public TableColumn<Customer, String> countryColumn;
    public TableColumn<Customer, String> zipColumn;
    public TableColumn<Customer, String> phoneColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerTable.setItems(Customer.getAllCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityStateColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
    }

    public void addBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customerInfo.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void updateBtnAction(ActionEvent actionEvent) {
        customerTable.getSelectionModel().getSelectedItem();

    }

    /**
     * deletes customer from Database and from the customer list array.
     * @param actionEvent
     * @throws SQLException
     */
    public void deleteBtnAction(ActionEvent actionEvent) throws SQLException {
        Customer deleteCustomer = customerTable.getSelectionModel().getSelectedItem();
        if(deleteCustomer == null) {
            System.out.println("Create alert for select cust to delete.");
        } else {
            int deleteCustomerId = deleteCustomer.getId();
            CustomerDB.deleteSelectedCustomer(deleteCustomerId);
            Customer.removeCustomer(deleteCustomer);
        }
    }

    public void exitBtnAction(ActionEvent actionEvent) {
        System.out.println("work on update button main customer screen next. Then appointments");
        System.exit(0);


    }

    public void searchCustomer(ActionEvent actionEvent) {
    }
}
