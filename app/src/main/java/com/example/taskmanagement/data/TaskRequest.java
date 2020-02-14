package com.example.taskmanagement.data;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class TaskRequest implements Serializable {

    String sender;
    String recipient;
    int approvalStatus, status;
    String title;
    String contents;
    String dayOfIssue;
    String deadLineDate;
    String timeStamp;
    String userCompany;
    String uid, rUid, reason;


    public TaskRequest() {
    }

    public TaskRequest(String uid, String rUid, String sender, String recipient, int approvalStatus,
                       String title, String contents, String dayOfIssue, String deadLineDate, String timeStamp,
                       String userCompany, @Nullable int status, @Nullable String reason) {
        this.uid = uid;
        this.rUid = rUid;
        this.sender = sender;
        this.recipient = recipient;
        this.approvalStatus = approvalStatus;
        this.title = title;
        this.contents = contents;
        this.dayOfIssue = dayOfIssue;
        this.deadLineDate = deadLineDate;
        this.timeStamp = timeStamp;
        this.userCompany = userCompany;
        this.status = status;
        this.reason = reason;
    }

    // getter

    public String getSender() {
        return sender;
    }

    public String getrUid() {
        return rUid;
    }

    public String getRecipient() {
        return recipient;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getDayOfIssue() {
        return dayOfIssue;
    }

    public String getDeadLineDate() {
        return deadLineDate;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public String getUid() {
        return uid;
    }

    public int getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    // setter

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setrUid(String rUid) {
        this.rUid = rUid;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDayOfIssue(String dayOfIssue) {
        this.dayOfIssue = dayOfIssue;
    }

    public void setDeadLineDate(String deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
