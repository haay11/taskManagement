package com.example.taskmanagement.data;

import androidx.annotation.Nullable;

public class ProcessStatusData {

    private String title, companyTv,
            nameTv, startDayTv, endDayTv, uid, timeStamp;
    private int status;

    ProcessStatusData(){

    }

    public ProcessStatusData(String title, String companyTv, String nameTv, String startDayTv, String endDayTv, String uid, String timeStamp, int status) {
        this.title = title;
        this.companyTv = companyTv;
        this.nameTv = nameTv;
        this.startDayTv = startDayTv;
        this.endDayTv = endDayTv;
        this.uid = uid;
        this.timeStamp = timeStamp;
        this.status = status;
    }

    //Getter

    public String getTitle() {
        return title;
    }

    public String getCompanyTv() {
        return companyTv;
    }

    public String getNameTv() {
        return nameTv;
    }

    public String getStartDayTv() {
        return startDayTv;
    }

    public String getEndDayTv() {
        return endDayTv;
    }

    public String getUid() {
        return uid;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public int getStatus() {
        return status;
    }

    //Setter


    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompanyTv(String companyTv) {
        this.companyTv = companyTv;
    }

    public void setNameTv(String nameTv) {
        this.nameTv = nameTv;
    }

    public void setStartDayTv(String startDayTv) {
        this.startDayTv = startDayTv;
    }

    public void setEndDayTv(String endDayTv) {
        this.endDayTv = endDayTv;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
