package com.example.taskmanagement.storage;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taskmanagement.data.BusinessOrderBoxData;
import com.example.taskmanagement.data.NewTaskUserInfo;
import com.example.taskmanagement.data.TaskRequest;
import com.example.taskmanagement.data.TaskUserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDataFirebase implements TaskStorage {


    // emailCheck
    @Override
    public void getUserList( final OnDataReceivedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("userEmail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> userEmails = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    TaskUserInfo taskUserInfo = dataSnapshot1.getValue(TaskUserInfo.class);
                    userEmails.add(String.valueOf(taskUserInfo.getUserEmail()));

                }
                listener.OnDataReceived(userEmails);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }

    @Override
    public void getNewTaskUserInfo(String userEmail, String userName, final OnDataTaskUserInfoReceivedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("userEmail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<String> list = new ArrayList<>();
                String[] list1 = new String[3];
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    NewTaskUserInfo newTaskUserInfo1 = dataSnapshot1.getValue(NewTaskUserInfo.class);

                }
                listener.OnDataReceived(list1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    // get 회원 이름(spinner)
    @Override
    public void getUserName(final OnDataNameReceivedListener listener) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("userEmail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> userNames = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    TaskUserInfo taskUserInfo = dataSnapshot1.getValue(TaskUserInfo.class);
                    userNames.add(String.valueOf(taskUserInfo.getUserName()));
                    Log.e("dataSnapshot  1", String.valueOf(userNames));

                }
                listener.OnDataReceived(userNames);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // orderBox publisher
    @Override
    public void getOrderBoxPubl(@Nullable String uid, @Nullable String timeStamp, OnDataPublisherReceivedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("/taskList/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<BusinessOrderBoxData> businessOrderBoxData = new ArrayList<>();
                List<TaskRequest> taskRequests = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Log.e("dataSnapshot111", dataSnapshot1.getValue().toString());
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                        Log.e("dataSnapshot222", dataSnapshot2.getValue().toString());
                        taskRequests.add(dataSnapshot2.getValue(TaskRequest.class));
                    }
                }   listener.OnDataReceived(taskRequests);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    // userInfo 가져오기
    @Override
    public void getUserInfo(String uid, final OnDataUserInfoReceivedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("userEmail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TaskUserInfo> taskUserInfos = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    taskUserInfos.add(dataSnapshot1.getValue(TaskUserInfo.class));

                } listener.OnDataReceived(taskUserInfos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    // Task 진행 상황 update
    @Override
    public void updateStatus(String uid, String timeStamp, int status) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("/taskList/");
        reference.child(uid).child(timeStamp).child("/status/").setValue(status);
    }
    // 사유 저장
    @Override
    public void insertReason(String senderUid, String timeStamp, String reason) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("/taskList/");
        reference.child(senderUid).child(timeStamp).child("/reason/").setValue(reason);
    }

    // 회원 정보 저장
    @Override
    public void insertUserInfo(TaskUserInfo taskUserInfo) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> map = new HashMap<>();
        map.put("pushToken", token);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("/userEmail/");

        reference.child(uid).setValue(taskUserInfo);
        reference.child(uid).child("uid").setValue(uid);
        reference.child(uid).updateChildren(map);
    }

    // 승인상태 update
    @Override
    public void updateApprovalStatus(String uid, String timeStamp,int approvalStatus, String sender) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("/taskList/");
        reference.child(uid).child(timeStamp).child("/approvalStatus/").setValue(approvalStatus);
    }

    // token pass
    @Override
    public void passPushTokenToServer() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> map = new HashMap<>();
        map.put("pushToken", token);

        FirebaseDatabase.getInstance().getReference().child("/userEmail/").child(uid).updateChildren(map);
    }
    // Task 생성
    @Override
    public void insertBusinessOrder(TaskRequest taskRequest) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("/taskList/");

        reference.child(uid).child(taskRequest.getTimeStamp()).setValue(taskRequest);
    }


}



