package com.example.taskmanagement.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

public class BusinessOrderBoxData<timeStamp> implements Serializable {

    private String title, companyTv,
            nameTv, startDayTv,
            endDayTv, approvalStateTv, timeStamp, uid, rUid;
    private int receiveCircle = 0;
    private int status;


    BusinessOrderBoxData(){

    }

    public BusinessOrderBoxData(String title, String companyTv, String nameTv, String startDayTv
            , String endDayTv,@Nullable String approvalStateTv, @Nullable Integer receiveCircle
            , @Nullable String timeStamp, String uid, String rUid, int status) {
        this.title = title;
        this.companyTv = companyTv;
        this.nameTv = nameTv;
        this.startDayTv = startDayTv;
        this.endDayTv = endDayTv;
        this.approvalStateTv = approvalStateTv;
        this.receiveCircle = receiveCircle;
        this.timeStamp = timeStamp;
        this.uid = uid;
        this.rUid = rUid;
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

    public String getApprovalStateTv() {
        return approvalStateTv;
    }

    public Integer getReceiveCircle() {
        return receiveCircle;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public String getrUid() {
        return rUid;
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

    public void setApprovalStateTv(String approvalStateTv) {
        this.approvalStateTv = approvalStateTv;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setrUid(String rUid) {
        this.rUid = rUid;
    }

    public void setReceiveCircle(int receiveCircle) {
        this.receiveCircle = receiveCircle;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
