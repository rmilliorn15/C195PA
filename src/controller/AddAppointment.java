package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentID.setText(String.valueOf(AppointmentsDB.getMaxID() + 1));
            addContact.setItems(ContactDB.getContactName());




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveBtnAction(ActionEvent actionEvent) throws IOException, SQLException {
        boolean apptAdded = false;
        int id;
        String title;
        String description;
        String location;
        String type;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime;
        LocalDateTime  endDateTime;
        int customerID;
        String userName;
        int userID;
        int contactID;
        String contactName = null;
        LocalDate selectedDate;
        LocalTime startTime;
        LocalTime endTime;
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
        customerID = Integer.parseInt(addCust.getText());
        userName = User.getUserName();
        userID = User.getUserId();
        contactName = String.valueOf(addContact.getValue());
        contactID = ContactDB.getContactId(contactName);

        if(Appointment.checkBusinessHours(startDateTime.toLocalTime())) {
            if (startDateTime.toLocalTime().isBefore(endDateTime.toLocalTime())) {

                //FIXME!!!!!!!!!!!!!!!!! create alert for input issues. CHANGE TO UTC TO STORE TIMES.

                Appointment newAppt = new Appointment(id, title, description, location, type, startDateTime, endDateTime, customerID, userID, contactID, contactName);
                Appointment.addAppointment(newAppt);
                AppointmentsDB.insert(title, description, location, type, startDateTime, endDateTime, now, userName, now, userName, customerID, userID, contactID);

                apptAdded = true;
                if (apptAdded) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/mainAppointments.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setTitle("Appointments");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Start or end Time");
                alert.setHeaderText("Please enter an End time that is after the Start time.");
                alert.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Start or end Time");
            alert.setHeaderText("Please enter Start and End time between 8am and 10pm EST.");
            alert.show();
        }

    }

    public void cancelBtnAction(ActionEvent actionEvent) throws IOException {
        System.out.println("create alert for cancel");


        Parent root = FXMLLoader.load(getClass().getResource("/view/mainAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
