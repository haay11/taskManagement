package com.example.taskmanagement.createbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.taskmanagement.calendar.CalendarActivity;
import com.example.taskmanagement.R;
import com.example.taskmanagement.data.NotificationModel;
import com.example.taskmanagement.data.TaskRequest;
import com.example.taskmanagement.data.TaskUserInfo;
import com.example.taskmanagement.storage.TaskDataFirebase;
import com.example.taskmanagement.storage.TaskStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.marcohc.robotocalendar.RobotoCalendarView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class CreateNewBusinessActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle, companyName, issueDayTv, calendarDeadlineDate;
    public TextView publisherName;
    private Spinner receiverName;
    private EditText instructionTitle, instructionContents;
    private RobotoCalendarView robotoCalendarView;
    private Intent intent;
    private Calendar c;
    private String userSignInEmail;
    private Button cancelBtn, createBusinessBtn;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private TaskStorage taskStorage = new TaskDataFirebase();
    private TaskUserInfo taskUserInfo;

    private String sender, recipient, title, contents, dayOfIssue, deadLineDate, company, timeStamp, rUid;
    private int approvalStatus = 0, workStatus = 0;


    // 요청 코드 상수 정의
    public static final int REQUEST_CODE = 1;
    // 응답 코드 상수 정의
    public static final int RESULT_CODE = 2;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null) {
            if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE){

                try {
                    toolbarTitle.setText("업무생성");
                    deadLineDate = data.getExtras().getString("date");

                    calendarDeadlineDate.setText(deadLineDate);
                    taskStorage.getUserInfo(uid, new TaskStorage.OnDataUserInfoReceivedListener() {
                        @Override
                        public void OnDataReceived(List<TaskUserInfo> userNameList) {
                            for (TaskUserInfo s : userNameList){
                                if (uid.equals(s.uid)) {
                                    publisherName.setText(s.userName + " " + s.userPosition);
                                    sender = s.userName;
                                    company = s.userCompany;
                                    companyName.setText(s.userCompany);
                                    Log.e("setInfo ", "start");
                                }
                            }
                        }
                    });

//                    setInfo();

                }catch (NullPointerException e){
                    return;
                }
            }

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_business);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        toolbarTitle.setText("업무생성");

        publisherName = findViewById(R.id.publisher_name);
        companyName = findViewById(R.id.company_name);
        issueDayTv = findViewById(R.id.issue_day_tv);
        instructionTitle = findViewById(R.id.instruction_title);
        instructionContents = findViewById(R.id.instruction_contents);
        calendarDeadlineDate = findViewById(R.id.calendar_deadline_date);
        receiverName = findViewById(R.id.receiver_name);
        cancelBtn = findViewById(R.id.create_business_cancel_btn);
        createBusinessBtn = findViewById(R.id.create_business_btn);

        c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        issueDayTv.setText(mYear+"."+(mMonth+1)+"."+mDay);
        dayOfIssue = issueDayTv.getText().toString();
        intent = getIntent();
        taskStorage.getUserInfo(uid, new TaskStorage.OnDataUserInfoReceivedListener() {
            @Override
            public void OnDataReceived(List<TaskUserInfo> userNameList) {
                for (TaskUserInfo s : userNameList){
                    if (uid.equals(s.uid)) {
                        publisherName.setText(s.userName + " " + s.userPosition);
                        sender = s.userName;
                        company = s.userCompany;
                        companyName.setText(s.userCompany);
                    }
                }
            }
        });


        // Dropdown layout style
        final ArrayAdapter<String> dataAdapter= new ArrayAdapter(CreateNewBusinessActivity.this, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.add("이름 선택");
        receiverName.setAdapter(dataAdapter);
        taskStorage.getUserName(new TaskStorage.OnDataNameReceivedListener() {
            @Override
            public void OnDataReceived(List<String> userNameList) {
                dataAdapter.addAll(userNameList);
                dataAdapter.notifyDataSetChanged();
            }
        });


        //attaching data adapter to spinner
        receiverName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("이름 선택")){
                    Toast.makeText(CreateNewBusinessActivity.this, "수신인 이름을 선택해 주세요.", LENGTH_SHORT);


                }else {
                    recipient = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CreateNewBusinessActivity.this, "수신인 이름을 선택해 주세요.", LENGTH_SHORT);
            }
        });

        calendarDeadlineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewBusinessActivity.this, CalendarActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("contents", contents);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createBusinessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = instructionTitle.getText().toString();
                contents = instructionContents.getText().toString();
                timeStamp = String.valueOf(System.currentTimeMillis());
                if (deadLineDate != null || !recipient.equals("이름 선택") || !recipient.equals(recipient) || title != null || contents != null) {
                    taskStorage.getUserInfo(recipient, new TaskStorage.OnDataUserInfoReceivedListener() {
                        @Override
                        public void OnDataReceived(List<TaskUserInfo> userNameList) {
                            for (TaskUserInfo info : userNameList) {
                                if (recipient.equals(info.getUserName())) {
                                    rUid = info.getUid();
                                    taskStorage.insertBusinessOrder(new TaskRequest(uid, rUid, sender, recipient, approvalStatus,
                                            title, contents, dayOfIssue, deadLineDate, timeStamp, company, 0, null));
                                    sendFCM();
                                }
                            }
                        }
                    });

                    Toast.makeText(CreateNewBusinessActivity.this, "Task를 생성하였습니다.", LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void setInfo(){
        taskStorage.getUserInfo(uid, new TaskStorage.OnDataUserInfoReceivedListener() {
            @Override
            public void OnDataReceived(List<TaskUserInfo> userNameList) {
                for (TaskUserInfo s : userNameList){
                    if (uid.equals(s.uid)) {
                        publisherName.setText(s.userName + " " + s.userPosition);
                        sender = s.userName;
                        company = s.userCompany;
                        companyName.setText(s.userCompany);
                    }
                }
            }
        });
    }

    public void sendFCM(){
        Gson gson = new Gson();
        taskStorage.getUserInfo(recipient, new TaskStorage.OnDataUserInfoReceivedListener() {
            @Override
            public void OnDataReceived(List<TaskUserInfo> userNameList) {
                for (TaskUserInfo t : userNameList){
                    if (recipient.equals(t.userName)){
                        NotificationModel notificationModel = new NotificationModel();
                        notificationModel.to = t.pushToken;
                        notificationModel.notification.title = title;
                        notificationModel.notification.text = publisherName.getText().toString();

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
                    }
                }
            }
        });

    }

}
