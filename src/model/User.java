package model;


public class User {

    private static String userName;
    private static int userId;



    /**
     * user constructor
     * @param userName
     * @param userId
     */
    public User(int userId,String userName){
        this.userName = userName;
        this.userId=userId;
    }

    /**
     * getter for User ID
     * @return User ID
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * getter for User Name
     * @return user Name
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * setter for userID
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }


    /**
     * setter for user name
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
