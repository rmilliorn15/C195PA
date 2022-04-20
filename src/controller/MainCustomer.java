package controller;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainCustomer implements Initializable {
    public TableView customerTable;
    public TableColumn customerIdColumn;
    public TableColumn nameColumn;
    public TableColumn streetColumn;
    public TableColumn cityStateColumn;
    public TableColumn countryColumn;
    public TableColumn zipColumn;
    public TableColumn phoneColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(Customer.getAllCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityStateColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    }

    public void addBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customerInfo.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void updateBtnAction(ActionEvent actionEvent) {
    }

    public void deleteBtnAction(ActionEvent actionEvent) {
    }

    public void exitBtnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void searchCustomer(ActionEvent actionEvent) {
    }
}
