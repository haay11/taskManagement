package com.example.taskmanagement.data;

public class NewTaskUserInfo {

    private String userEmail;
    private String userName;
    private String userPosition;
    private String userCompany;

    public NewTaskUserInfo() {
    }

    public NewTaskUserInfo(String userEmail, String userName, String userPosition, String userCompany) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPosition = userPosition;
        this.userCompany = userCompany;
    }

    // getter

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public String getUserCompany() {
        return userCompany;
    }

    // setter


    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }
}
