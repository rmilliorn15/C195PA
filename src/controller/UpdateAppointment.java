package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/** * @author Richard Milliorn */

public class UpdateAppointment implements Initializable {
    public TextField appointmentID;
    public TextField addTitle;
    public TextField addDescript;
    public TextField addLocation;
    public TextField addType;
    public ComboBox addContact;
    public DatePicker dateSelect;
    public TextField addStart;
    public TextField addEnd;
    public TextField addCust;
    public TextField userIdText;

    int index;


    /**
     * Gets information from main appointment screen and sends to update screen
     *
     * @param send selecred from main screen
     * @throws SQLException
     */
    public void sendAppointment(Appointment send) throws SQLException {
        appointmentID.setText(String.valueOf(send.getAppointmentID()));
        addTitle.setText(send.getTitle());
        addDescript.setText(send.getDescription());
        addLocation.setText(send.getLocation());
        addType.setText(send.getType());
        addContact.setValue(send.getContactName());
        addContact.setItems(ContactDB.getContactName());
        addEnd.setText(String.valueOf(send.getEndTime().toLocalDateTime().toLocalTime()));
        addStart.setText(String.valueOf(send.getStartTime().toLocalDateTime().toLocalTime()));
        addCust.setText(String.valueOf(send.getCustomerID()));
        dateSelect.setValue(send.getStartTime().toLocalDateTime().toLocalDate());
        userIdText.setText(String.valueOf(send.getUserID()));
    }

    /**
     * Gets the index used for updating the table view.
     *
     * @param selectedIndex index of selected item.
     */
    public void sendIndex(int selectedIndex) {
        index = selectedIndex;
    }

    /**
     * Saves changes to seleceted appointment and returns to main appointments if successful
     *
     * @param actionEvent save clicked
     * @throws SQLException
     * @throws IOException
     */
    public void saveBtnAction(ActionEvent actionEvent) throws SQLException, IOException {
        boolean apptAdded = false;
        int id;
        String title;
        String description;
        String location;
        String type;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        int customerID;
        String userName;
        int userID;
        int contactID;
        String contactName;
        LocalDate selectedDate;
        LocalTime startTime;
        LocalTime endTime;
        ZonedDateTime zonedStart;
        ZonedDateTime zonedEnd;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        id = Integer.parseInt(appointmentID.getText());
        title = addTitle.getText();
        description = addDescript.getText();
        location = addLocation.getText();
        type = addType.getText();
        selectedDate = dateSelect.getValue();
        startTime = LocalTime.parse(addStart.getText(), formatter);
        endTime = LocalTime.parse(addEnd.getText(), formatter);
        startDateTime = LocalDateTime.of(selectedDate, startTime);
        endDateTime = LocalDateTime.of(selectedDate, endTime);
        zonedStart = ZonedDateTime.of(startDateTime, loginToDB.getUserZoneID());
        zonedEnd = ZonedDateTime.of(endDateTime, loginToDB.getUserZoneID());
        customerID = Integer.parseInt(addCust.getText());
        userID = Integer.parseInt(userIdText.getText());
        userName = UserDB.getUserName(userID);
        contactName = String.valueOf(addContact.getValue());
        contactID = ContactDB.getContactId(contactName);


        if (title.isBlank()) {
            alertSwitch(1);
        } else if (description.isBlank()) {
            alertSwitch(2);
        } else if (location.isBlank()) {
            alertSwitch(3);
        } else if (type.isBlank()) {
            alertSwitch(4);
        } else if (startDateTime.toString().isBlank()) {
            alertSwitch(5);
        } else if (endDateTime.toString().isBlank()) {
            alertSwitch(6);
        } else if (contactName.isBlank()) {
            alertSwitch(7);
        } else if (customerID > CustomerDB.getMaxID()) {
            alertSwitch(12);
        } else if (userName.equals("Default")) {
            alertSwitch(13);

        }
        else{


            if (Appointment.checkBusinessHours(startDateTime) && Appointment.checkBusinessHours(endDateTime)) {
                if (startDateTime.toLocalTime().isBefore(endDateTime.toLocalTime())) {
                    if( Appointment.checkOverlap(customerID, selectedDate, startTime,endTime, id)){
                        alertSwitch( 11);
                    }
                    else {


                        // converts to UTC to store in DB
                        zonedStart = zonedStart.withZoneSameInstant(ZoneOffset.UTC);
                        zonedEnd = zonedEnd.withZoneSameInstant(ZoneOffset.UTC);
                        startDateTime = zonedStart.toLocalDateTime();
                        endDateTime = zonedEnd.toLocalDateTime();



                        Appointment updateAppt = new Appointment(id, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID, contactName);
                        Appointment.updateAppointment(index, updateAppt);
                        AppointmentsDB.updateAppointment(id, title, description, location, type, startDateTime, endDateTime, now, userName, customerID, userID, contactID);
                        apptAdded = true;
                    }
                    if (apptAdded) {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/mainAppointments.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setTitle("Appointments");
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                } else {
                    alertSwitch(9);
                }
            } else {
                alertSwitch(10);
            }
        }
    }

    /**
     * Cancels changes and returns to main appointments screen
     * @param actionEvent cancel clicked
     * @throws IOException
     */
    public void cancelBtnAction (ActionEvent actionEvent) throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Are you sure you want to cancel");
        confirm.setHeaderText("All changes will be lost. Press ok to cancel and return to main screen");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainAppointments.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Appointments");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     *  Alert switch for appointments table
     *  LAMBDA EXPRESSION auto changed by IDE
     * @param alertNumber alert assigned a number.
     */
    public void alertSwitch(int alertNumber){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (alertNumber) {
            case 1 -> {
                alert.setTitle("Please Enter Title");
                alert.setHeaderText("Please enter appointment title");
                alert.show();
            }
            case 2 -> {
                alert.setTitle("Please Enter Description");
                alert.setHeaderText("Please enter appointment description");
                alert.show();
            }
            case 3 -> {
                alert.setTitle("Please Enter Location");
                alert.setHeaderText("Please enter appointment location");
                alert.show();
            }
            case 4 -> {
                alert.setTitle("Please Enter Type");
                alert.setHeaderText("Please enter appointment type");
                alert.show();
            }
            case 5 -> {
                alert.setTitle("Please select appointment Start Date / Time");
                alert.setHeaderText("Please select a date and time to start appointment");
                alert.show();
            }
            case 6 -> {
                alert.setTitle("Please select appointment End Date / Time");
                alert.setHeaderText("Please select a date and time to End appointment");
                alert.show();
            }
            case 7 -> {
                alert.setTitle("Please select a Contact");
                alert.setHeaderText("Please select a contact for the appointment.");
                alert.show();
            }
            case 8 -> {
                alert.setTitle("Number Format Exception");
                alert.setHeaderText("Issue converting string to numbers. Please check if added and try again.");
                alert.show();
            }
            case 9 -> {
                alert.setTitle("Invalid Start or end Time");
                alert.setHeaderText("Please enter an End time that is after the Start time.");
                alert.show();
            }
            case 10 -> {
                alert.setTitle("Invalid Start or end Time");
                alert.setHeaderText("Please enter Start and End time between 8am and 10pm EST.");
                alert.show();
            }
            case 11 -> {
                alert.setTitle("Overlapping appointment");
                alert.setHeaderText("Customer has overlapping appointments. Please Select different time.");
                alert.show();
            }
            case 12 -> {
                alert.setTitle("Customer Not Found.");
                alert.setHeaderText("Please enter Valid Customer ID Number");
                alert.show();
            }
            case 13 -> {
                alert.setTitle("User Not Found.");
                alert.setHeaderText("Please enter Valid User ID Number");
                alert.show();
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
