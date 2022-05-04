package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentsDB {

    /**
     * connects to database and gets all appointments.
     *
     * @return all appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();


        try {

            String sql = "SELECT apt.Appointment_ID, apt.Title, apt.Description, apt.Location, apt.Type, apt.Start, apt.End, apt.Customer_ID, u.User_ID, c.Contact_ID, c.Contact_Name" +
                    " FROM APPOINTMENTS as apt JOIN CUSTOMERS as cust on apt.Customer_ID = cust.Customer_ID JOIN USERS as u on apt.User_ID = u.User_ID" +
                    " JOIN CONTACTS as c on apt.Contact_ID = c.Contact_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

           ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                String startDate = resultSet.getString("Start");
                String endDate = resultSet.getString("End");
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");


                Appointment appointment = new Appointment(appointmentID, title, description, location, type
                        , startDate, endDate, customerID, userID, contactID, contactName);
                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * returns appointments within the next week
     * @return  array of appointments in the next week
     */
    public static ObservableList<Appointment> getWeekAppointments() {
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();


        return weeklyAppointments;
    }

    /**
     * returns an array of appointments in the next month
     * @return array of appointments in the next month
     */
    public static ObservableList<Appointment> getMonthAppointments() {
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();


        return monthlyAppointments;
    }

    /**
     * adds new appointment to database.
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start start time
     * @param end end time
     * @param createdDate created date
     * @param createdBy user that created appointment
     * @param lastUpdate last update for appointment. should be the same as created for Insert
     * @param lastUpdatedBy same as created for insert
     * @param customerID customer ID
     * @param userID user ID
     * @param contactID Contact ID
     * @throws SQLException .
     */
    public static void insert(String title, String description, String location, String type, String start, String end, LocalDateTime createdDate,
                              String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID ) throws SQLException {

        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start , End, Create_Date, Created_By, " +
                " Last_Update, Last_Updated_BY, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ? ,? ,? , ?, ?, ?, ? )";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setString(5, String.valueOf(start));
        ps.setString(6, String.valueOf(end));
        ps.setString(7, String.valueOf(createdDate));
        ps.setString(8,createdBy);
        ps.setString(9, String.valueOf(lastUpdate));
        ps.setString(10,lastUpdatedBy);
        ps.setInt(11,customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);


        ps.executeUpdate();
    }

    public static void deleteSelectedAppointment(int appointmentID) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ps.executeUpdate();
    }

    public static void updateAppointment( int appointmentId, String title, String description, String type, LocalDateTime start,
                                          LocalDateTime end, LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ? , Type = ?, Start = ?, End = ?, " +
                "Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3,type);
        ps.setTimestamp(4, Timestamp.valueOf(start));
        ps.setTimestamp(5, Timestamp.valueOf(end));
        ps.setTimestamp(6,Timestamp.valueOf(lastUpdate) );
        ps.setString(7,lastUpdatedBy);
        ps.setInt(8,customerId);
        ps.setInt(9,userId);
        ps.setInt(10,contactId);
        ps.setInt(11,appointmentId);

        ps.executeUpdate();
    }

    public static int getMaxID() throws SQLException {
        int nextId = 0;
        String sql = "SELECT max(Appointment_ID) AS Max_Appt_ID FROM appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            nextId = resultSet.getInt("Max_Appt_ID");
        }
        return nextId;
    }

    public static void resetAutoIncrement() throws SQLException {
        String sql ="ALTER TABLE APPOINTMENTS AUTO_INCREMENT = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.executeUpdate();
    }

    public static boolean hasAppointment(int customerID) throws SQLException {
        boolean hasAppointment = false;
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1,customerID);
       ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            hasAppointment = true;
        }
        return hasAppointment;
    }

}
