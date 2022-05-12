package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("/view/loginScreen.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {

        JDBC.openConnection();
      //  Locale france = Locale.FRENCH;
       // Locale.setDefault(france);

      //  ResourceBundle rb = ResourceBundle.getBundle("helper/french_fr", Locale.getDefault());
       // if (Locale.getDefault().getLanguage().equals("fr"))
         //   System.out.println(rb.getString("userNameLabel"));
          //  System.out.println(rb.getString("passwordLabel"));


        launch(args);


        JDBC.closeConnection();
    }
}
