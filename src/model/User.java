package model;


public class User {

    private static String userName;
    private static int userId;



    /**
     * User constructor
     * @param userName
     * @param userId
     */
    public User(int userId,String userName){
        this.userName = userName;
        this.userId=userId;
    }

    /**
     * Getter for User ID
     * @return User ID
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * Getter for User Name
     * @return user Name
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Setter for userID
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }


    /**
     * Setter for user name
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
