package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class firstLevelDivisionDB {

    /**
     * Scans DB and adds divisions with country code matching selected country to array
     * populates as objects. need to convert to string to display correctly in combobox.
     * <p>
     * RUNTIME ERROR
     * Make sure that you have your fxid the same and didnt change it. spent 2 hours looking for reason it wouldnt populate and it was because i removed the fxid after creating that gui.
     *
     * @throws SQLException
     */
    public static ObservableList<String> getDivision(String countryName) throws SQLException {
        int countryID;
        if (countryName.equals("U.S")) {
            countryID = 1;
        } else if (countryName.equals("UK")) {
            countryID = 2;
        } else {
            countryID = 3;
        }
        ObservableList<String> countryDivisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Country_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, countryID);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            String divisionName = resultSet.getString("Division");
            countryDivisions.add(divisionName);
        }
        return countryDivisions;
    }

    /**
     * Gets country name from database and adds to table used for combobox in customer screens.
     *
     * @return list of countries.
     * @throws SQLException
     */
    public static ObservableList<String> getCountries() throws SQLException {
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        String sql = "SELECT * FROM COUNTRIES ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            String countryName = resultSet.getString("Country");
            countryNames.add(countryName);
        }
        return countryNames;
    }

    /**
     * Takes string and searches DB for match and returns country ID
     * @param divisionName from selection box.
     * @return country ID number from DB
     * @throws SQLException
     */
    public static int getDivisionId(String divisionName) throws SQLException {
        int divisionId = 0;



        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, divisionName);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            divisionId = resultSet.getInt("Division_ID");
        }
        return divisionId;
    }
}




