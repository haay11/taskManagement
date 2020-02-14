package com.example.taskmanagement.data;

import java.io.Serializable;

public class TaskUserInfo implements Serializable {

    public String userEmail;
    public String userName;
    public String userCompany;
    public String userPosition;
    public String userDepartment;
    public String uid;
    public String pushToken;

    public TaskUserInfo(){

    }

    public TaskUserInfo(String uid, String userEmail, String userName, String userCompany, String userPosition, String userDepartment) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userCompany = userCompany;
        this.userPosition = userPosition;
        this.userDepartment = userDepartment;
        this.uid = uid;
    }

    // getter

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public String getUid() {
        return uid;
    }

    public String getPushToken() {
        return pushToken;
    }

    // setter

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }
}
