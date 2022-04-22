package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class firstLevelDivisionDB {

    /**
     * scans DB and adds divisions with country code matching selected country to array
     * populates as objects. need to convert to string to display correctly in combobox.
     * @throws SQLException
     */
    public static ObservableList<firstLevelDivision> getDivision() {
        int countryID = 1;
        ObservableList<firstLevelDivision> allDivision = FXCollections.observableArrayList();
        try { String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryId = resultSet.getInt("Country_ID");
                firstLevelDivision division = new firstLevelDivision(divisionId,divisionName,countryId);
                allDivision.add(division);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allDivision;
    }
}
