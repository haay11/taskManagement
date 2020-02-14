package com.example.taskmanagement.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.taskmanagement.R;
import com.example.taskmanagement.businessorder.BusinessOrderActivity;
import com.example.taskmanagement.createbusiness.CreateNewBusinessActivity;
import com.example.taskmanagement.processingstatus.ProcessingStatusActivity;
import com.example.taskmanagement.storage.TaskDataFirebase;
import com.example.taskmanagement.storage.TaskStorage;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public TextView title, createBusinessTv;
    public ImageView titleImg;
    public FrameLayout createBusinessLayout, businessOrderBoxLayout, processingStatusLayout;
    public Intent intent;
    public ImageView icAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(null);
        title = findViewById(R.id.toolbar_title);
        title.setVisibility(View.INVISIBLE);
        titleImg = findViewById(R.id.toolbar_title_img);
        titleImg.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        createBusinessLayout = findViewById(R.id.create_business_layout);
        businessOrderBoxLayout = findViewById(R.id.business_order_box_layout);
        processingStatusLayout = findViewById(R.id.processing_status_layout);
        createBusinessTv = findViewById(R.id.create_business_tv);
        icAdd = findViewById(R.id.create_business_img);

        final int clickLayout = getResources().getColor(R.color.purpleish_blue);
        final int clickText = getResources().getColor(R.color.white_two);
        final int clickIcon = getResources().getColor(R.color.white_two);

        TaskStorage taskStorage = new TaskDataFirebase();
        taskStorage.passPushTokenToServer();


        createBusinessLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                createBusinessLayout.setBackgroundColor(clickLayout);
                createBusinessTv.setTextColor(clickText);
                icAdd.setColorFilter(clickIcon);
            }
        });

        businessOrderBoxLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                businessOrderBoxLayout.setBackgroundColor(clickLayout);

            }
        });

        
        createBusinessLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, CreateNewBusinessActivity.class);
                startActivity(intent);
            }
        });

        businessOrderBoxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, BusinessOrderActivity.class);
                startActivity(intent);
            }
        });


        processingStatusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, ProcessingStatusActivity.class);
                startActivity(intent);
            }
        });



    }


}
