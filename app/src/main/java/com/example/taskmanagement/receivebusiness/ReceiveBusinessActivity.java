package com.example.taskmanagement.receivebusiness;

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
import com.example.taskmanagement.reasonpopup.ReasonPopupActivity;

public class ReceiveBusinessActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView toolbarImg;
    private TextView toolbarTv;
    private Button returnBtn, holdBtn, approvalBtn;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_business);


        toolbar = findViewById(R.id.toolbar);
        toolbarImg = findViewById(R.id.toolbar_title_img);
        toolbarTv = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbarImg.setVisibility(View.INVISIBLE);
        toolbarTv.setText(R.string.receive_business);
        returnBtn = findViewById(R.id.business_order_return_btn);
        holdBtn = findViewById(R.id.business_order_hold_btn);
        approvalBtn = findViewById(R.id.business_order_approval_btn);


        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(ReceiveBusinessActivity.this, ReasonPopupActivity.class);
                startActivity(intent);

            }
        });

    }
}
