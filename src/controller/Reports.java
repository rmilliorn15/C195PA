package controller;

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
import model.ContactDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** * @author Richard Milliorn */
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
    public ComboBox<String> monthComboBox;

    public ComboBox<String> monthComboBox2;
    public ComboBox<String> typeComboBox;

    public String selectedMonth;
    public String selectedType;
    public Label appointmentNumber;
    public Label monthLabel;
    public Label numberApptsMonth;


    /**
     * Gets the selected contact and populates table with appointments assigned to them.
     * @param actionEvent contact selected.
     * @throws SQLException
     */
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

    /**
     * Populates tables and combo boxes upon loading.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            selectContact.setItems(ContactDB.getContactName());
            typeComboBox.setItems(AppointmentsDB.getAllApptType());
            monthComboBox.setItems(AppointmentsDB.getApptMonths());
            monthComboBox2.setItems(AppointmentsDB.getApptMonths());
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

    /**
     * Goes back to screen selector when clicked.
     * @param actionEvent back clicked/
     * @throws IOException
     */
    public void backBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/screenSelector.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("View");
        stage.setScene(new Scene(root));
        stage.show();;
    }

    /**
     * Gets selected month and stores it.
     * @param actionEvent
     */
    public void appointmentsMonth(ActionEvent actionEvent) {
        selectedMonth = monthComboBox.getValue();


    }

    /**
     * Gets selected type and stores it.
     * @param actionEvent
     */
    public void appointmentsType(ActionEvent actionEvent) {
        selectedType = typeComboBox.getValue();
    }


    /**
     * Takes selected type and month and checks to see how many appointments matching there are and returns a number.
     * @param actionEvent search clicked.
     * @throws SQLException
     */
    public void searchBtnAction(ActionEvent actionEvent) throws SQLException {
        int apptNumber = AppointmentsDB.getApptByType(selectedType, selectedMonth).size();
        appointmentNumber.setText(String.valueOf(apptNumber));
    }

    /**
     * Shows total number of appointments by month
     * @param actionEvent month selected.
     * @throws SQLException
     */
    public void monthCount(ActionEvent actionEvent) throws SQLException {
        String monthSelect = monthComboBox2.getValue();
        int apptNumber = AppointmentsDB.getApptByMonth(monthSelect).size();

        monthLabel.setText(monthSelect + " is : ");
        numberApptsMonth.setText(String.valueOf(apptNumber));
    }
}
