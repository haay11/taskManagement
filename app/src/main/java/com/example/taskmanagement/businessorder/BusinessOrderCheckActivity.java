package com.example.taskmanagement.businessorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.taskmanagement.R;
import com.example.taskmanagement.data.NotificationModel;
import com.example.taskmanagement.data.TaskRequest;
import com.example.taskmanagement.data.TaskUserInfo;
import com.example.taskmanagement.reasonpopup.ReasonPopupActivity;
import com.example.taskmanagement.storage.TaskDataFirebase;
import com.example.taskmanagement.storage.TaskStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.taskmanagement.R.drawable.hold_btn_777777;

public class BusinessOrderCheckActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView toolbarImg;
    private TextView toolbarTv, publisherName, receiverName, companyName,
            instructionTitle, instructionContents, calendarDeadlineDate,
            issueDayTv;
    private Button businessOrderReturnBtn, businessOrderHoldBtn, businessOrderApprovalBtn
            , businessOrderReasonBtn;
    private Intent intent;
    private String timeStamp, approvalStateTv, publisher, publisherUid, pUid, rUid;
    private TaskStorage taskStorage;
    private int approvalStatus, box, status;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_business);

        // toolbar
        toolbarImg = findViewById(R.id.toolbar_title_img);
        toolbarTv = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTv.setText("업무 수신");
        toolbarTv.setVisibility(View.VISIBLE);
        toolbarImg.setVisibility(View.INVISIBLE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        publisherName = findViewById(R.id.publisher_name);
        receiverName = findViewById(R.id.receiver_name);
        companyName = findViewById(R.id.company_name);
        instructionTitle = findViewById(R.id.instruction_title);
        instructionContents = findViewById(R.id.instruction_contents);
        calendarDeadlineDate = findViewById(R.id.calendar_deadline_date);
        issueDayTv = findViewById(R.id.issue_day_tv);
        businessOrderReturnBtn = findViewById(R.id.business_order_return_btn);
        businessOrderHoldBtn = findViewById(R.id.business_order_hold_btn);
        businessOrderApprovalBtn = findViewById(R.id.business_order_approval_btn);
        businessOrderReasonBtn = findViewById(R.id.business_order_reason_btn);

        intent = getIntent();

        if(intent!=null){
            // box == 0 -> task box, box == 1 -> processing box
            box = intent.getExtras().getInt("box");
            if (box==0){
                timeStamp = intent.getExtras().getString("timeStamp");
                approvalStateTv = intent.getExtras().getString("approvalStateTv");
                publisher = intent.getExtras().getString("name");
                rUid = intent.getExtras().getString("rUid");
                pUid = intent.getExtras().getString("uid");
                status = intent.getExtras().getInt("status");

            }else if (box==1){
                timeStamp = intent.getExtras().getString("timeStamp");
                publisher = intent.getExtras().getString("name");
                status = intent.getExtras().getInt("status");


            }
        }

        taskStorage = new TaskDataFirebase();
        taskStorage.getOrderBoxPubl(publisher, timeStamp, new TaskStorage.OnDataPublisherReceivedListener() {
            @Override
            public void OnDataReceived(List<TaskRequest> taskRequest) {
                setElements(publisher, timeStamp, taskRequest);}
        });
        checkButton();

        businessOrderApprovalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskStorage = new TaskDataFirebase();
                publisher = publisherName.getText().toString();
                taskStorage.getOrderBoxPubl(publisher, timeStamp, new TaskStorage.OnDataPublisherReceivedListener() {
                    @Override
                    public void OnDataReceived(List<TaskRequest> taskRequest) {
                        if (businessOrderApprovalBtn.getText().equals("승인되었습니다.")){

                        }else {
                            updateValue(publisher, timeStamp,1, taskRequest);
                            approvalStateTv = "승인";
                            sendFCM();

                            businessOrderApprovalBtn.setText("승인되었습니다.");
                            businessOrderApprovalBtn.setBackground(getResources().getDrawable(hold_btn_777777));
                            businessOrderHoldBtn.setVisibility(View.GONE);
                            businessOrderReturnBtn.setVisibility(View.GONE);
                            businessOrderReasonBtn.setVisibility(View.GONE);


                        }
                    }

                });
            }
        });
        businessOrderHoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskStorage = new TaskDataFirebase();
                publisher = publisherName.getText().toString();
                taskStorage.getOrderBoxPubl(publisher,timeStamp, new TaskStorage.OnDataPublisherReceivedListener() {
                    @Override
                    public void OnDataReceived(List<TaskRequest> taskRequest) {
                        updateValue(publisher, timeStamp,2, taskRequest);
                        approvalStateTv = "보류";
                        setButton(approvalStateTv);
                        intent = new Intent(BusinessOrderCheckActivity.this, ReasonPopupActivity.class);
                        intent.putExtra("timeStamp", timeStamp);
                        intent.putExtra("pUid", pUid);
                        intent.putExtra("approvalStateTv", approvalStateTv);
                        startActivity(intent);
                    }
                });
            }
        });
        businessOrderReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskStorage = new TaskDataFirebase();
                publisher = publisherName.getText().toString();
                taskStorage.getOrderBoxPubl(publisher, timeStamp, new TaskStorage.OnDataPublisherReceivedListener() {
                    @Override
                    public void OnDataReceived(List<TaskRequest> taskRequest) {
                        updateValue(publisher, timeStamp,3, taskRequest);
                        approvalStateTv = "반려";
                        setButton(approvalStateTv);
                        intent = new Intent(BusinessOrderCheckActivity.this, ReasonPopupActivity.class);
                        intent.putExtra("timeStamp", timeStamp);
                        intent.putExtra("pUid", pUid);
                        intent.putExtra("approvalStateTv", approvalStateTv);
                        sendFCM();
                        startActivity(intent);
                    }
                });
            }
        });
        businessOrderReasonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (box==1&&!(businessOrderReasonBtn.getText().toString().equals("Task가 완료되었습니다."))){
                    businessOrderReasonBtn.setText("Task가 완료되었습니다.");
                    businessOrderReasonBtn.setBackground(getResources().getDrawable(hold_btn_777777));
                    status = 1;
                    taskStorage = new TaskDataFirebase();
                    publisher = publisherName.getText().toString();
                    taskStorage.getOrderBoxPubl(publisher, timeStamp, new TaskStorage.OnDataPublisherReceivedListener() {
                        @Override
                        public void OnDataReceived(List<TaskRequest> taskRequest) {
                            updateStatus(publisher, timeStamp, status, taskRequest);
                            sendFCM();
                        }
                    });

                } else if (businessOrderReasonBtn.getText().toString().equals("보류 사유 확인")||businessOrderReasonBtn.getText().toString().equals("반려 사유 확인")){
                    intent = new Intent(BusinessOrderCheckActivity.this, ReasonPopupActivity.class);
                    intent.putExtra("timeStamp", timeStamp);
                    intent.putExtra("pUid", pUid);
                    intent.putExtra("approvalStateTv", approvalStateTv);
                    startActivity(intent);
                }
            }
        });

    }
    public void setElements(String publisher, String timeStamp, List<TaskRequest> taskRequest){

        for (TaskRequest s : taskRequest) {

            if (timeStamp.equals(s.getTimeStamp())){
                publisherName.setText(s.getSender());
                receiverName.setText(s.getRecipient());
                companyName.setText(s.getUserCompany());
                instructionTitle.setText(s.getTitle());
                instructionContents.setText(s.getContents());
                calendarDeadlineDate.setText(s.getDeadLineDate());
                issueDayTv.setText(s.getDayOfIssue());
                approvalStatus = s.getApprovalStatus();
            }
        }
    }
    public void updateValue(String publisherName, String timeStamp, int approvalStatus, List<TaskRequest> taskRequest){
        for (TaskRequest s : taskRequest) {
            if (timeStamp.equals(s.getTimeStamp())){
                taskStorage.getUserInfo(publisherName, new TaskStorage.OnDataUserInfoReceivedListener() {
                    @Override
                    public void OnDataReceived(List<TaskUserInfo> userNameList) {
                        for (TaskUserInfo taskUserInfo : userNameList){
                            if (publisherName.equals(taskUserInfo.getUserName())){
                                publisherUid = taskUserInfo.getUid();
                                taskStorage.updateApprovalStatus(publisherUid, timeStamp, approvalStatus, s.getSender());
                                break;
                            }
                        }
                    }
                });
            }
        }
    }
    public void updateStatus(String publisherName, String timeStamp, int status, List<TaskRequest> taskRequest){
        for (TaskRequest s : taskRequest) {
            if (timeStamp.equals(s.getTimeStamp())){
                taskStorage.getUserInfo(publisherName, new TaskStorage.OnDataUserInfoReceivedListener() {
                    @Override
                    public void OnDataReceived(List<TaskUserInfo> userNameList) {
                        for (TaskUserInfo taskUserInfo : userNameList){
                            if (publisherName.equals(taskUserInfo.getUserName())){
                                publisherUid = taskUserInfo.getUid();
                                taskStorage.updateStatus(publisherUid, timeStamp, status);
                                break;
                            }
                        }
                    }
                });
            }

        }
    }


    public void setButton(String approvalStateTv){
        switch (approvalStateTv){
            case "승인":
                businessOrderApprovalBtn.setText("승인되었습니다.");
                businessOrderApprovalBtn.setBackground(getResources().getDrawable(hold_btn_777777));
                businessOrderHoldBtn.setVisibility(View.GONE);
                businessOrderReturnBtn.setVisibility(View.GONE);
                break;
            case "보류":
                businessOrderApprovalBtn.setVisibility(View.GONE);
                businessOrderHoldBtn.setVisibility(View.GONE);
                businessOrderReturnBtn.setVisibility(View.GONE);
                businessOrderReasonBtn.setVisibility(View.VISIBLE);
                businessOrderReasonBtn.setText("보류 사유 확인");
                break;
            case "반려":
                businessOrderApprovalBtn.setVisibility(View.GONE);
                businessOrderHoldBtn.setVisibility(View.GONE);
                businessOrderReturnBtn.setVisibility(View.GONE);
                businessOrderReasonBtn.setVisibility(View.VISIBLE);
                businessOrderReasonBtn.setText("반려 사유 확인");
                break;
        }

    }
    public void checkButton(){
        if ((box == 0)&&uid.equals(pUid)&&approvalStateTv.equals("미확인")){
            businessOrderApprovalBtn.setVisibility(View.GONE);
            businessOrderHoldBtn.setVisibility(View.GONE);
            businessOrderReturnBtn.setVisibility(View.GONE);
            businessOrderReasonBtn.setVisibility(View.GONE);
            businessOrderReasonBtn.setVisibility(View.GONE);
        } else if ((box == 0)&&uid.equals(pUid)&&approvalStateTv.equals("승인")){
            switch (status){
                case 0: // TASK 진행중
                    businessOrderApprovalBtn.setVisibility(View.GONE);
                    businessOrderHoldBtn.setVisibility(View.GONE);
                    businessOrderReturnBtn.setVisibility(View.GONE);
                    businessOrderReasonBtn.setVisibility(View.VISIBLE);
                    businessOrderReasonBtn.setText("TASK가 진행중 입니다.");
                    break;
                case 1: // TASK 종료
                    businessOrderApprovalBtn.setVisibility(View.GONE);
                    businessOrderHoldBtn.setVisibility(View.GONE);
                    businessOrderReturnBtn.setVisibility(View.GONE);
                    businessOrderReasonBtn.setVisibility(View.VISIBLE);
                    businessOrderReasonBtn.setText("TASK가 완료되었습니다.");
                    businessOrderReasonBtn.setBackground(getResources().getDrawable(hold_btn_777777));
                    break;
            }
        } else if ((box == 0)&&(approvalStateTv.equals("보류")||approvalStateTv.equals("반려"))){
            switch (approvalStateTv){
                case "보류":
                    businessOrderApprovalBtn.setVisibility(View.GONE);
                    businessOrderHoldBtn.setVisibility(View.GONE);
                    businessOrderReturnBtn.setVisibility(View.GONE);
                    businessOrderReasonBtn.setVisibility(View.VISIBLE);
                    businessOrderReasonBtn.setText("보류 사유 확인");
                    break;
                case "반려":
                    businessOrderApprovalBtn.setVisibility(View.GONE);
                    businessOrderHoldBtn.setVisibility(View.GONE);
                    businessOrderReturnBtn.setVisibility(View.GONE);
                    businessOrderReasonBtn.setVisibility(View.VISIBLE);
                    businessOrderReasonBtn.setText("반려 사유 확인");
                    break;
            }
        } else if ((box==1)||((box == 0)&&uid.equals(rUid)&&approvalStateTv.equals("승인"))){
            switch (status){
                case 0: // TASK 진행중
                    businessOrderApprovalBtn.setVisibility(View.GONE);
                    businessOrderHoldBtn.setVisibility(View.GONE);
                    businessOrderReturnBtn.setVisibility(View.GONE);
                    businessOrderReasonBtn.setVisibility(View.VISIBLE);
                    businessOrderReasonBtn.setText("TASK 완료");
                    break;
                case 1: // TASK 종료
                    businessOrderApprovalBtn.setVisibility(View.GONE);
                    businessOrderHoldBtn.setVisibility(View.GONE);
                    businessOrderReturnBtn.setVisibility(View.GONE);
                    businessOrderReasonBtn.setVisibility(View.VISIBLE);
                    businessOrderReasonBtn.setText("TASK가 완료되었습니다.");
                    businessOrderReasonBtn.setBackground(getResources().getDrawable(hold_btn_777777));
                    break;
            }
        }
    }
    public void sendFCM(){
        Gson gson = new Gson();
        if (box==1||box==0) {
            taskStorage.getUserInfo(publisher, new TaskStorage.OnDataUserInfoReceivedListener() {
                @Override
                public void OnDataReceived(List<TaskUserInfo> userNameList) {
                    for (TaskUserInfo info : userNameList){
                        if (publisher.equals(info.getUserName())){

                            NotificationModel notificationModel = new NotificationModel();
                            notificationModel.to = info.pushToken;
                            if (status==1){
                                notificationModel.notification.title = "요청하신 TASK가 완료 되었습니다.";
                            } else {
                                notificationModel.notification.title = "요청하신 TASK가" + approvalStateTv + "되었습니다.";
                            }
                            notificationModel.notification.text = instructionTitle.getText().toString();

                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"), gson.toJson(notificationModel));
                            Request request = new Request.Builder().header("Content-Type", "application/json")
                                    .addHeader("Authorization", "key=AIzaSyD2uQqPPjVCx7P_F7PAi7otjYELUjE5cV0")
                                    .url("https://fcm.googleapis.com/fcm/send")
                                    .post(requestBody)
                                    .build();
                            OkHttpClient okHttpClient = new OkHttpClient();
                            okHttpClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                }
                            });
                            break;
                        }
                    }

                }
            });
        }

    }


}

