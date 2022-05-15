package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Appointment;
import model.loginToDB;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class ScreenSelector implements Initializable {



    /**
     * opens to customer page
     * @param actionEvent  Customer button clicked.
     * @throws IOException .
     */
    public void customerBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * loads appointments page
     * @param actionEvent appointment button clicked
     * @throws IOException .
     */
    public void appointmentsBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainAppointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * exits the program with code 0
     * @param actionEvent exi clicked
     */
    public void exitBtnAction(ActionEvent actionEvent) {
        System.out.println("************ need to figure out the time change parts. ");
        System.exit(0);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
