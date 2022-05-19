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

public class UpdateCustomer implements Initializable {


    public TextField customerId;
    public ComboBox<String> customerCityState;
    public TextField customerName;
    public TextField customerPhone;
    public TextField customerStreet;
    public ComboBox<String> customerCountry;
    public TextField customerZip;

    int Index;

    /**
     * Updates customer information and sends to database and main customer screen.
     * If succeccful returns to main screen.
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
            String user = User.getUserName();

            // gets values and inserts to DB
            id = Integer.parseInt(customerId.getText());
            name = customerName.getText();
            address= customerStreet.getText();
            zipCode = customerZip.getText();
            phoneNumber = customerPhone.getText();
            customerDivision = firstLevelDivisionDB.getDivisionId(customerCityState.getValue());


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

                CustomerDB.updateCustomer( id, name, address, zipCode, phoneNumber,currentTime, user , customerDivision);
                Customer.updateCustomer(Index,newCustomer);
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
     * Removes text from input boxes.
     * @param actionEvent clear button clicked.
     */
    public void clearBtnAction(ActionEvent actionEvent) {
        customerName.setText("");
        customerStreet.setText("");
        customerCityState.getItems().clear();
        customerPhone.setText("");
        customerZip.setText("");
    }

    /**
     * Cancels changes and returns to the main customer screen.
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
     * Used to get selected customer from main screen and send to the update page.
     * @param send selected customer from main customer table
     */
    public void sendCustomer(Customer send) throws SQLException {
        customerId.setText(String.valueOf(send.getId()));
        customerName.setText(send.getName());
        customerPhone.setText(send.getPhoneNumber());
        customerStreet.setText(send.getAddress());
        customerZip.setText(send.getZipCode());
        customerCityState.setValue(send.getCustomerDivision());
        customerCountry.setValue(send.getCustomerCountry());
        customerCountry.setItems(firstLevelDivisionDB.getCountries());
        customerCityState.setItems(firstLevelDivisionDB.getDivision(customerCountry.getValue()));
    }

    /**
     * Gets index from selected item for updating table view.
     * @param selectedIndex selected on main screen.
     */
    public void sendIndex(int selectedIndex) {
        Index = selectedIndex;
    }

    /**
     * Switch to hold alerts instead of cluttering code.
     * @param alertNumber int for error to display.
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
     * Populates the state province box when it is changed to new country.
     * @param actionEvent new country selected from combobox
     */
    public void countrySelect(ActionEvent actionEvent) {
        try {
            customerCityState.setItems(firstLevelDivisionDB.getDivision(customerCountry.getValue()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
