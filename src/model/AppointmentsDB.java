package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
                LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
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
     * adds new appointment to database.
     *
     * @param title         title
     * @param description   description
     * @param location      location
     * @param type          type
     * @param start         start time
     * @param end           end time
     * @param createdDate   created date
     * @param createdBy     user that created appointment
     * @param lastUpdate    last update for appointment. should be the same as created for Insert
     * @param lastUpdatedBy same as created for insert
     * @param customerID    customer ID
     * @param userID        user ID
     * @param contactID     Contact ID
     * @throws SQLException .
     */
    public static void insert(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createdDate,
                              String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {

        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start , End, Create_Date, Created_By, " +
                " Last_Update, Last_Updated_BY, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ? ,? ,? , ?, ?, ?, ? )";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setString(7, String.valueOf(createdDate));
        ps.setString(8, createdBy);
        ps.setString(9, String.valueOf(lastUpdate));
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);


        ps.executeUpdate();
    }

    /**
     * deletes selected item from Database
     *
     * @param appointmentID selected appointment's id
     * @throws SQLException
     */
    public static void deleteSelectedAppointment(int appointmentID) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        ps.executeUpdate();
    }

    /**
     * updates the selected appointment
     *
     * @param appointmentId appointment id
     * @param title         title
     * @param description   description
     * @param location      location
     * @param type          type
     * @param start         start time
     * @param end           end time
     * @param lastUpdate    last updated time
     * @param lastUpdatedBy updated by
     * @param customerId    customer id
     * @param userId        user id
     * @param contactId     contact id.
     * @throws SQLException
     */
    public static void updateAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start,
                                         LocalDateTime end, LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ? , Type = ?, Start = ?, End = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setString(7, String.valueOf(lastUpdate));
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, appointmentId);

        ps.executeUpdate();
    }

    /**
     * returns the highest in use Id number.
     *
     * @return highest id in use.
     * @throws SQLException
     */
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

    /**
     * resets Id auto increment counter when deleting items.
     *
     * @throws SQLException
     */
    public static void resetAutoIncrement() throws SQLException {
        String sql = "ALTER TABLE APPOINTMENTS AUTO_INCREMENT = 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.executeUpdate();
    }

    /**
     * checks appointment list to see if selected customer is assigned to an appointment.
     *
     * @param customerID selcted customer
     * @return true if appointment found with customer id assigned.
     * @throws SQLException
     */
    public static boolean hasAppointment(int customerID) throws SQLException {
        boolean hasAppointment = false;
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            hasAppointment = true;
        }
        return hasAppointment;
    }

    /**
     * used for appointment overlap
     *
     * @param custID   for new/updated appointment
     * @return list of appointments matching the selected customer on selected day.
     */
    public static ObservableList<Appointment> getApptByID(int custID) throws SQLException {
        ObservableList<Appointment> custAppointmentList = FXCollections.observableArrayList();


        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, custID);
        ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = ContactDB.getContactNameID(contactID);


                Appointment appointment = new Appointment(appointmentID, title, description, location, type
                        , startDate, endDate, customerID, userID, contactID, contactName);
                custAppointmentList.add(appointment);
            }
            return custAppointmentList;
    }
}
