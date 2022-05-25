package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;
import model.CustomerDB;
import model.User;
import model.firstLevelDivisionDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/** * @author Richard Milliorn */
public class addCustomerController implements Initializable {

    public TextField customerName;
    public TextField customerPhone;
    public TextField customerStreet;
    public ComboBox<String> customerCountry;
    public TextField customerZip;
    public ComboBox<String> customerCityState;
    public TextField customerId;


    /**
     * Gets text fields and adds new customer to database
     * then returns to main screen.
     * @param actionEvent save button clicked.
     */
    public void saveBtnAction(ActionEvent actionEvent) {

        try {
            boolean custAdded = false;
            int id;
            String name;
            String address;
            String zipCode;
            String phoneNumber;
            int customerDivision = 0;
            LocalDate nowDate = LocalDate.now();
            LocalTime nowtime = LocalTime.now();
            LocalDateTime currentTime = LocalDateTime.of(nowDate,nowtime);

            // gets values and inserts to DB
            id = Integer.parseInt(customerId.getText());
            name = customerName.getText();
            address= customerStreet.getText();
            zipCode = customerZip.getText();
            phoneNumber = customerPhone.getText();
            customerDivision = firstLevelDivisionDB.getDivisionId(customerCityState.getValue());

            String user = User.getUserName();
            if (name.isBlank()){
                alertSwitch(1);
            } else if (address.isBlank()) {
                alertSwitch(2);
            } else if (zipCode.isBlank()) {
                alertSwitch(3);
            } else if (phoneNumber.isBlank()) {
                alertSwitch(4);
            }else if (customerDivision == 0){
                alertSwitch(5);
            } else {
                Customer newCustomer = new Customer(id, name, address, zipCode, phoneNumber,customerCityState.getValue(),customerDivision,customerCountry.getValue());
                CustomerDB.insert(name, address, zipCode, phoneNumber, currentTime, user, currentTime, user, customerDivision);
                Customer.addCustomers(newCustomer);

                custAdded = true;

            }
            if (custAdded) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/mainCustomer.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Customers");
                stage.setScene(new Scene(root));
                stage.show();

            }
        }catch (NumberFormatException e){
            alertSwitch(6);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Clears input information from text boxes.
     * @param actionEvent clear button clicked.
     */
    public void clearBtnAction(ActionEvent actionEvent) {
        customerName.setText("");
        customerStreet.setText("");
        customerPhone.setText("");
        customerZip.setText("");
        customerCountry.setValue("");
        customerCityState.setDisable(true);

    }

    /**
     * Discards changes and returns to main screen.
     * @param actionEvent cancel button clicked.
     * @throws IOException .
     */
    public void cancelBtnAction(ActionEvent actionEvent) throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Are you sure you want to cancel");
        confirm.setHeaderText("All changes will be lost. Press ok to cancel and return to main screen");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainCustomer.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Customers");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * When country is selected enables state/province selector and populates with info from Database.
     * @param actionEvent country selected from combo box.
     */
    public void countrySelect(ActionEvent actionEvent) {

        customerCityState.setDisable(false);
        try {

            customerCityState.setItems(firstLevelDivisionDB.getDivision(customerCountry.getValue()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * LAMBDA EXPRESSION: takes int and returns appropriate alert text.
     * Switch used for my alerts on the customer table.
     *
     * @param alertNumber int indicating which error
     */
    public void alertSwitch(int alertNumber){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (alertNumber) {
            case 1 -> {
                alert.setTitle("Please Enter Name");
                alert.setHeaderText("Please enter customer name");
                alert.show();
            }
            case 2 -> {
                alert.setTitle("Please Enter Address");
                alert.setHeaderText("Please enter customer address");
                alert.show();
            }
            case 3 -> {
                alert.setTitle("Please Enter Zip/Postal code");
                alert.setHeaderText("Please enter customer Zip or postal code");
                alert.show();
            }
            case 4 -> {
                alert.setTitle("Please Enter Phone Number");
                alert.setHeaderText("Please enter customer Phone Number");
                alert.show();
            }
            case 5 -> {
                alert.setTitle("Please select customer state/province");
                alert.setHeaderText("Please select a state or province.");
                alert.show();
            }
            case 6 -> {
                alert.setTitle("Number Format Exception");
                alert.setHeaderText("Issue converting string to numbers. Please check if added and try again.");
                alert.show();
            }
        }
    }


    /**
     * Sets combo boxes when loaded.
     * @param url .
     * @param resourceBundle .
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerId.setText(String.valueOf(CustomerDB.getMaxID() + 1));

            customerCountry.setItems(firstLevelDivisionDB.getCountries());

            if (customerCountry.getSelectionModel().getSelectedItem() == null) {
                customerCityState.setDisable(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}