package com.example.taskmanagement.storage;

import androidx.annotation.Nullable;

import com.example.taskmanagement.data.TaskRequest;
import com.example.taskmanagement.data.TaskUserInfo;

import java.util.List;

public interface TaskStorage {

    public void getUserList(OnDataReceivedListener listener);
    public void getNewTaskUserInfo(String userEmail, @Nullable String userName, OnDataTaskUserInfoReceivedListener listener);
    public void getUserName(OnDataNameReceivedListener listener);
    public void getOrderBoxPubl(@Nullable String uid,@Nullable String timeStamp, OnDataPublisherReceivedListener listener);
    public void insertBusinessOrder(TaskRequest taskRequest);
    public void insertUserInfo(TaskUserInfo taskUserInfo);
    public void updateApprovalStatus(String uid, String timeStamp, int approvalStatus, String sender);
    public void passPushTokenToServer();
    public void getUserInfo(String uid, OnDataUserInfoReceivedListener listener);
    public void updateStatus(String uid, String timeStamp, int status);
    public void insertReason(String senderUid, String timeStamp, String reason);


    public interface OnDataTaskUserInfoReceivedListener{
        void OnDataReceived(String[] newTaskUserInfos);
    }
    public interface OnDataReceivedListener{
        void OnDataReceived(List<String> userEmailList);
    }
    public interface OnDataNameReceivedListener{
        void OnDataReceived(List<String> userNameList);
    }
    public interface OnDataPublisherReceivedListener{
        void OnDataReceived(List<TaskRequest> taskRequest);
    }
    public interface OnDataUserInfoReceivedListener {
        void OnDataReceived(List<TaskUserInfo> userNameList);
    }
}
