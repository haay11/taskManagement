package com.example.taskmanagement.storage;

import androidx.annotation.Nullable;

import com.example.taskmanagement.data.TaskRequest;
import com.example.taskmanagement.data.TaskUserInfo;

import java.util.List;

public interface TaskStorage {

    void getUserList(OnDataReceivedListener listener);
    void getNewTaskUserInfo(String userEmail, @Nullable String userName, OnDataTaskUserInfoReceivedListener listener);
    void getUserName(OnDataNameReceivedListener listener);
    void getOrderBoxPubl(@Nullable String uid,@Nullable String timeStamp, OnDataPublisherReceivedListener listener);
    void insertBusinessOrder(TaskRequest taskRequest);
    void insertUserInfo(TaskUserInfo taskUserInfo);
    void updateApprovalStatus(String uid, String timeStamp, int approvalStatus, String sender);
    void passPushTokenToServer();
    void getUserInfo(String uid, OnDataUserInfoReceivedListener listener);
    void updateStatus(String uid, String timeStamp, int status);
    void insertReason(String senderUid, String timeStamp, String reason);


    interface OnDataTaskUserInfoReceivedListener{
        void OnDataReceived(String[] newTaskUserInfos);
    }
    interface OnDataReceivedListener{
        void OnDataReceived(List<String> userEmailList);
    }
    interface OnDataNameReceivedListener{
        void OnDataReceived(List<String> userNameList);
    }
    interface OnDataPublisherReceivedListener{
        void OnDataReceived(List<TaskRequest> taskRequest);
    }
    interface OnDataUserInfoReceivedListener {
        void OnDataReceived(List<TaskUserInfo> userNameList);
    }
}
