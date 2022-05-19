package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScreenSelector implements Initializable {



    /**
     * Opens to customer page
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
     * Loads appointments page
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
     * Exits the program with code 0
     * @param actionEvent exi clicked
     */
    public void exitBtnAction(ActionEvent actionEvent) {
        System.out.println(" Need customer report and report for by type and date \n Lambda expressions. \n JavaDocs & README and then submit. ");
        System.exit(0);
    }

    /**
     * Opens the reports screen.
     * @param actionEvent reports button clicked.
     * @throws IOException
     */
    public void reportsBtnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reports");
        stage.setScene(new Scene(root));
        stage.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }



}
