package model;

/** * @author Richard Milliorn */
public class Contact {

    private static int contactID;
    private static String contactName;
    private static String email;

    /**
     * Creates new contact object.
     * @param contactID id
     * @param contactName name
     * @param email email.
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Getter for contact Id
     * @return ID
     */
    public static int getID() {
        return contactID;
    }

    /**
     * Getter for contact name
     * @return name
     */
    public static String getContactName() {
        return contactName;
    }

    /**
     * Getter for contact email
     * @return email
     */
    public String getEmail() {
        return email;
    }

}
