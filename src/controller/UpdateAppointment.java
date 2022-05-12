package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

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

    int index;


    /**
     * gets information from main appointment screen and sends to update screen
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
    }

    /**
     * gets the index used for updating the table view.
     * @param selectedIndex index of selected item.
     */
    public void sendIndex(int selectedIndex) {
        index = selectedIndex;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /**
     * saves changes to seleceted appointment and returns to main appointments if successful
     * @param actionEvent save clicked
     * @throws SQLException
     * @throws IOException
     */
    public void saveBtnAction(ActionEvent actionEvent) throws SQLException, IOException {
        boolean apptAdded= false;
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
        String contactName;
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

        System.out.println("create alerts for invalid inputs update appointment line 108");
        Appointment updateAppt = new Appointment(id, title,description,location,type,startDateTime,endDateTime,customerID, userID, contactID, contactName);
        Appointment.updateAppointment(index, updateAppt);
        AppointmentsDB.updateAppointment(id, title,description,location,type,startDateTime,endDateTime,now,userName,customerID,userID,contactID);
        apptAdded = true;

        if (apptAdded){
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainAppointments.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Appointments");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * cancels changes and returns to main appointments screen
     * @param actionEvent cancel clicked
     * @throws IOException
     */
    public void cancelBtnAction(ActionEvent actionEvent) throws IOException {
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
}
