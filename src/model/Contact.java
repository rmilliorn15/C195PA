package model;

public class Contact {

    private static int contactID;
    private static String contactName;
    private static String email;

    /**
     * creates new contact object.
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
     * getter for contact Id
     * @return ID
     */
    public static int getID(String contactName) {
        return contactID;
    }

    /**
     * getter for contact name
     * @return name
     */
    public static String getContactName() {
        return contactName;
    }

    /**
     * getter for contact email
     * @return email
     */
    public String getEmail() {
        return email;
    }
}
