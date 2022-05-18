package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AppointmentsDB;
import model.ContactDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Reports implements Initializable {
    public TableView appointmentTableView;
    public TableColumn idColumn;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn locationColumn;
    public TableColumn typeColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerIdColumn;
    public ComboBox selectContact;
    public ComboBox monthComboBox;
    public ComboBox typeComboBox;

    public void selectContact(ActionEvent actionEvent) throws SQLException {
        String contactName = String.valueOf(selectContact.getValue());
        int contactId = ContactDB.getContactId(contactName);
        if(AppointmentsDB.getApptByContact(contactId).isEmpty()){
            appointmentTableView.setItems(AppointmentsDB.getApptByContact(contactId));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Appointments found");
            alert.setHeaderText("No appointments for selected Contact Found");
            alert.show();
        }
        else
            appointmentTableView.setItems(AppointmentsDB.getApptByContact(contactId));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            selectContact.setItems(ContactDB.getContactName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));


    }

    public void backBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/screenSelector.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("View");
        stage.setScene(new Scene(root));
        stage.show();;
    }

    public void appointmentsMonth(ActionEvent actionEvent) {
    }

    public void appointmentsType(ActionEvent actionEvent) {
    }
}
