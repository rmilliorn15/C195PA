package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class firstLevelDivision {
    private int divisionID;
    private static String divisionName;
    private int countryID;

    private static ObservableList<firstLevelDivision> getAllDivisions = FXCollections.observableArrayList();

    /**
     * created new division object.
     *
     * @param divisionID   id from table
     * @param divisionName state/province name
     * @param countryID    country name from table.
     */
    public firstLevelDivision(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.divisionName = divisionName;
    }

    /**
     * getter for selected item division ID
     *
     * @return division ID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * getter for selected Division country ID
     *
     * @return country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * returns selected Division name
     *
     * @return division name
     */
    public static String getDivisionName() {
        return divisionName;
    }

    /**
     * setter for Division ID
     *
     * @param divisionID .
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * setter for countryID
     *
     * @param countryID .
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * setter for Division Name
     *
     * @param divisionName .
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * returns first level divisions
     * @return first level divisions.
     */
    public static ObservableList<firstLevelDivision> getGetAllDivisions() {
        return getAllDivisions;
    }
}
