package controller;

import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.loginToDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    public Label mainLoginLabel;
    public Label userNameLabel;
    public Label passwordLabel;
    public Button loginButton;
    public Label userLocationLabel;
    public Button exitButton;
    public PasswordField passwordField;
    public TextField userNameField;
    private boolean loginSuccessful = false;

    /**
     * sets the users Locale
     * commented line is for testing setting to French.
     */
    private static Locale userLocale = Locale.getDefault();
    //  private Locale userLocale = Locale.FRENCH; //used to test french translation settings.

    /**
     * sets userZone ID
     *
     */
    private static ZoneId userZoneID = ZoneId.systemDefault();


    /**
     * Confirms user wants to exit then closes the program. DONE
     * @param actionEvent exit button clicked
     */
    public void exitBtnAction(ActionEvent actionEvent) {

        System.out.println("appointments created need to populate table and create getters for appointments.");

        if (userLocale.getCountry().equals("FR")) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Êtes-vous sûr de vouloir quitter?");
            confirm.setHeaderText("Cliquez sur OK pour quitter");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                JDBC.closeConnection();
                System.exit(0);

            }
        }
        else {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Are you sure you want to exit?");
            confirm.setHeaderText("Click Ok to exit");
            Optional<ButtonType> result = confirm.showAndWait();;
            if (result.isPresent() && result.get() == ButtonType.OK) {
                JDBC.closeConnection();
                System.exit(0);
            }
        }
    }

    /**
     * Gets information from username field
     * Gets text from the password field
     * compares to authorized users and allows login or denies login attempt.
     * @param actionEvent login button was clicked.
     */
    public void loginBtnAction(ActionEvent actionEvent) throws IOException, SQLException {
        String userName = userNameField.getText();//gets input username
        String password = passwordField.getText();//gets input password
        loginSuccessful = loginToDB.loggedIn(userName,password);

        if(loginSuccessful) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/screenSelector.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("View");
            stage.setScene(new Scene(root));
            stage.show();
            Appointment.nextAppt();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invaild User Name or Password.");
            alert.showAndWait();
        }



    }

    /**
     * holds the translated text areas for the login screem.
     * Translations from google translate.
     * Sets the buttons and labels to French when called.
     */
    public void setTextLanguage(){
        userNameLabel.setText("Nom d'utilisateur");
        passwordLabel.setText("Mot de passe");
        mainLoginLabel.setText("Connexion");
        exitButton.setText("Sortie");
        loginButton.setText("Connexion");

    }

    /**
     * initallizes and sets text fields to correct language.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (userLocale.getLanguage().equals("fr")) {
            setTextLanguage();
        }
        userLocationLabel.setText(String.valueOf(userZoneID));
    }
}
