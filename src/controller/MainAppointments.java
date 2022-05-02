package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentsDB;
import model.Customer;
import model.CustomerDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class MainAppointments implements Initializable {
    public TableView<Appointment> appointmentTableView;
    public TableColumn<Appointment, Integer> idColumn;
    public TableColumn<Appointment, String> titleColumn;
    public TableColumn<Appointment, String> descriptionColumn;
    public TableColumn<Appointment, String> locationColumn;
    public TableColumn<Appointment, String> contactColumn;
    public TableColumn<Appointment, String> typeColumn;
    public TableColumn<Appointment, ZonedDateTime> startColumn;
    public TableColumn<Appointment, ZonedDateTime> endColumn;
    public TableColumn<Appointment, Integer> customerIdColumn;
    public ToggleGroup viewAppointment;

    /**
     * opens add appointment page
     * @param actionEvent add button clicked
     */
    public void addBtnAction(ActionEvent actionEvent) {
    }

    /**
     * opens update appointment page
     * @param actionEvent update button clicked.
     */
    public void updateBtnAction(ActionEvent actionEvent) {
    }

    /**
     * makes sure that an appointment is selected from the table and confirms before deleting the item.
     * @param actionEvent delete button clicked.
     */
    public void deleteBtnAction(ActionEvent actionEvent) throws SQLException {
       Appointment deleteAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if(deleteAppointment == null) {
            System.out.println("Create alert for select appointment to delete.");
        } else {
            int deleteCustomerId = deleteAppointment.getAppointmentID();
           AppointmentsDB.deleteSelectedAppointment(deleteCustomerId);
            Appointment.removeAppointment(deleteAppointment);
        }


    }

    /**
     * returns to the selector screen
     * @param actionEvent back button clicked
     * @throws IOException
     */
    public void backBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/screenSelector.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("View");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * removes search by day or week when selected
     * @param actionEvent Default radio button chlicked.
     */
    public void defaultRadio(ActionEvent actionEvent) {
    }

    /**
     * filters by  appointments in the next month
     * @param actionEvent month radio button selected
     */
    public void monthRadio(ActionEvent actionEvent) {
    }

    /**
     * filters by appointments within the next week.
     * @param actionEvent week radio button selected.
     */
    public void weekRadio(ActionEvent actionEvent) {
    }

    /**
     * swaps to customers screen without having to return to main screen.
     * @param actionEvent go to customer clicked.
     * @throws IOException .
     */
    public void customerBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("View Customers");
        stage.setScene(new Scene(root));
        stage.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTableView.setItems(Appointment.getAllAppointments());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

    }
}
