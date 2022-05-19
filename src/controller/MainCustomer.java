package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AppointmentsDB;
import model.Customer;
import model.CustomerDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
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
    public TextField custSearch;



    /**
     * Opens add customer screen.
     * @param actionEvent add button clicked
     *
     * @throws IOException .
     */
    public void addBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customerInfo.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Gets selected customer and sends to update customer screen.
     * @param actionEvent update button clicked.
     */
    public void updateBtnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/updateCustomer.fxml"));
            loader.load();

            UpdateCustomer updateCust = loader.getController();
            updateCust.sendCustomer(customerTable.getSelectionModel().getSelectedItem());
            updateCust.sendIndex(customerTable.getSelectionModel().getSelectedIndex());


            Parent root = loader.getRoot();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Update Customer");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException | IOException e){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Invalid selection");
            error.setContentText("Please select customer to delete.");
            error.show();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes customer from Database and from the customer list array.
     * also resets the auto increment if the highest id number is deleted. at least it should.
     * also checks appointment screen to see if any appointments are assigned to this customer before allowing delete.
     * @param actionEvent delete clicked.
     * @throws SQLException .
     */
    public void deleteBtnAction(ActionEvent actionEvent) throws SQLException {

        Customer deleteCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (deleteCustomer == null) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Invalid selection");
            error.setContentText("Please select customer to delete.");
            error.show();
        } else {
            int deleteCustomerId = deleteCustomer.getId();
            if (AppointmentsDB.hasAppointment(deleteCustomerId)) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setHeaderText("Customer " + deleteCustomer.getName() + " not deleted.");
                warning.setContentText("Customer not deleted. Please Remove appointments \n assigned to "
                        + deleteCustomer.getName() +" and try again.");
                warning.showAndWait();
            } else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText("Are you sure you want to delete " + deleteCustomer.getName());
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    Alert inform = new Alert(Alert.AlertType.INFORMATION);
                    inform.setHeaderText("Customer " + deleteCustomer.getName() + " Deleted");

                    CustomerDB.deleteSelectedCustomer(deleteCustomerId);
                    CustomerDB.resetAutoIncrement();
                    Customer.removeCustomer(deleteCustomer);
                    inform.show();
                }
            }
        }
    }

    /**
     * Exits program with code 0
     * @param actionEvent exit clicked.
     */
    public void backBtnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/screenSelector.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("View");
        stage.setScene(new Scene(root));
        stage.show();


    }

    /**
     * Allows search by name or ID and returns array of matching items.
     * @param actionEvent enter pressed while in search field.
     */
    public void searchCustomer(ActionEvent actionEvent) {
        try {
            String search = custSearch.getText();
            ObservableList<Customer> customer = Customer.lookupCustomer(search);
            if (customer.size() == 0) {
                int id = Integer.parseInt(search);
                Customer searchCustomer = Customer.lookupCustomer(id);
                if (searchCustomer != null) {
                    customer.add(searchCustomer);
                }
            }
            customerTable.setItems(customer);
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Search");
            error.setContentText("No matching customers found.");
            error.show();
        }
    }


    /**
     * Swaps to appointments screen
     * @param actionEvent appointments button clicked
     *
     * @throws IOException .
     */
    public void appointmentBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("View Customers");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Loads screen and populates customer table from DB
     * @param url .
     * @param resourceBundle .
     */
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
}
