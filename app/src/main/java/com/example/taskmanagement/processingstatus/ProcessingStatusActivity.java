package com.example.taskmanagement.processingstatus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.taskmanagement.adapter.ProcessingFragmentAdapter;
import com.example.taskmanagement.R;
import com.example.taskmanagement.createbusiness.CreateNewBusinessActivity;
import com.example.taskmanagement.data.TaskRequest;
import com.example.taskmanagement.data.TaskUserInfo;
import com.example.taskmanagement.storage.TaskDataFirebase;
import com.example.taskmanagement.storage.TaskStorage;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProcessingStatusActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView toolbarImg, receiveCircle;
    private TextView toolbarTv, approvalStateTv, countBell;
    private TabLayout tabLayout;
    private LinearLayout statusCreateTask;
    private Intent intent;
    private TaskStorage taskStorage;
    private String recipient;
    public String mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private int count = 0;
    public MenuItem menuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);

        menuItem = menu.findItem(R.id.action_notification_bell);
        countBell = findViewById(R.id.notification_badge);

        setUpBadge();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_status_box);

        toolbarImg = findViewById(R.id.toolbar_title_img);
        toolbarTv = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarTv.setVisibility(View.VISIBLE);
        toolbarTv.setText("처리현황");
        toolbarImg.setVisibility(View.INVISIBLE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        statusCreateTask = findViewById(R.id.status_create_task);
        tabLayout = findViewById(R.id.processing_status_tab_layout);

        //업무 생성
        statusCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ProcessingStatusActivity.this, CreateNewBusinessActivity.class);
                startActivity(intent);
            }
        });
        loadTabName();
        setViewPager();

    }


    private void loadTabName() {
        tabLayout.addTab(tabLayout.newTab().setText("종료"));
        tabLayout.addTab(tabLayout.newTab().setText("진행중"));

    }

    private void setViewPager(){
        FragmentPagerAdapter adapter = new ProcessingFragmentAdapter(getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager_status_box);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() != viewPager.getCurrentItem()){
                    viewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() != viewPager.getCurrentItem()){
                    viewPager.setCurrentItem(tab.getPosition());
                }
            }
        });

    }

    private void setUpBadge(){
        taskStorage = new TaskDataFirebase();
        taskStorage.getOrderBoxPubl(mUid, null, new TaskStorage.OnDataPublisherReceivedListener() {
            @Override
            public void OnDataReceived(List<TaskRequest> taskRequest) {
                count = 0;
                taskStorage.getUserInfo(mUid, new TaskStorage.OnDataUserInfoReceivedListener() {
                    @Override
                    public void OnDataReceived(List<TaskUserInfo> userNameList) {
                        for (TaskUserInfo info :  userNameList){
                            if (mUid.equals(info.uid)){
                                recipient = info.getUserName();
                            }
                        }
                        for (TaskRequest request : taskRequest){
                            if (recipient.equals(request.getRecipient())&&(request.getApprovalStatus()==1)
                                    && (request.getStatus()==0)){
                                count ++;
                            }
                        }
                        if (count == 0) {
                            menuItem.setActionView(null);
                        } else {
                            menuItem.setActionView(R.layout.custom_notification_layout);
                            View view = menuItem.getActionView();
                            countBell = view.findViewById(R.id.notification_badge);
                            countBell.setText(String.valueOf(count));
                        }
                    }
                });
            }
        });

    }

    public static String getToDay(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return dateFormat.format(new Date());
    }



}
