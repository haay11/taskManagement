package com.example.taskmanagement.reasonpopup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.taskmanagement.R;
import com.example.taskmanagement.createbusiness.CreateNewBusinessActivity;
import com.example.taskmanagement.data.TaskRequest;
import com.example.taskmanagement.storage.TaskDataFirebase;
import com.example.taskmanagement.storage.TaskStorage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ReasonPopupActivity extends Activity {

    private EditText reasonContents;
    private Button reasonCancelBtn, reasonCheckBtn;
    private String pUid, timeStamp, contents, approvalStateTv;
    private TaskStorage taskStorage;
    private String mUid = FirebaseAuth.getInstance().getUid();
    public TextView reasonTitleTv;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount= 0.5f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.activity_reason_popup);

        reasonContents = findViewById(R.id.reason_contents);
        reasonCancelBtn = findViewById(R.id.reason_cancel_btn);
        reasonCheckBtn = findViewById(R.id.reason_check_btn);
        reasonTitleTv = findViewById(R.id.reason_title_tv);

        intent = getIntent();
        if (intent!=null){
            timeStamp = intent.getExtras().getString("timeStamp");
            pUid = intent.getExtras().getString("pUid");
            approvalStateTv = intent.getExtras().getString("approvalStateTv");
        }
        reasonTitleTv.setText(approvalStateTv + " 사유");
        setElements();

        reasonCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        reasonCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contents = reasonContents.getText().toString();
                TaskStorage taskStorage = new TaskDataFirebase();
                if (timeStamp!=null&&pUid!=null&&contents!=null) {
                    taskStorage.insertReason(pUid, timeStamp, contents);
                    setElements();
                    finish();
                }
                if (reasonCheckBtn.getText().toString().equals("재요청")){
                    Intent intent1 = new Intent(ReasonPopupActivity.this, CreateNewBusinessActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });

    }

    //바깥영역 클릭 방지와 백 버튼 차단
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
    // 저장 후 요소 다시 셋팅
    public void setElements(){
        taskStorage = new TaskDataFirebase();
        taskStorage.getOrderBoxPubl(pUid, timeStamp, new TaskStorage.OnDataPublisherReceivedListener() {
            @Override
            public void OnDataReceived(List<TaskRequest> taskRequest) {
                for (TaskRequest request : taskRequest) {
                    if (pUid.equals(request.getUid()) && timeStamp.equals(request.getTimeStamp())) {
                        if (request.getReason() != null) {
                            reasonContents.setText(request.getReason());
                            reasonContents.setEnabled(false);
                            reasonCancelBtn.setText("확인");
                        }
                        if (!pUid.equals(mUid) && request.getReason() != null) {
                            reasonCheckBtn.setVisibility(View.GONE);
                        } else if (pUid.equals(mUid)) {
                            reasonCheckBtn.setText("재요청");
                            reasonContents.setEnabled(false);

                        }
                    }
                }
            }
        });
    }

}
