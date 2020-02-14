package com.example.taskmanagement.businessorder;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.taskmanagement.R;
import com.example.taskmanagement.adapter.FragmentAdapter;
import com.example.taskmanagement.createbusiness.CreateNewBusinessActivity;
import com.example.taskmanagement.data.TaskRequest;
import com.example.taskmanagement.data.TaskUserInfo;
import com.example.taskmanagement.storage.TaskDataFirebase;
import com.example.taskmanagement.storage.TaskStorage;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class BusinessOrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView toolbarImg;
    private TextView toolbarTv, countBell;
    private TabLayout tabLayout;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private TaskStorage taskStorage;
    private String recipient;

    private LinearLayout createTask;
    private Intent intent;
    private int taskCount = 0;
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
        switch (item.getItemId()) {
            case R.id.action_notification_bell: {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_order_box);

        toolbarImg = findViewById(R.id.toolbar_title_img);
        toolbarTv = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarTv.setVisibility(View.INVISIBLE);
        toolbarImg.setVisibility(View.VISIBLE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tabLayout = findViewById(R.id.tab_layout);
        createTask = findViewById(R.id.order_box_create_task);

        loadTabName();
        setViewPager();

        //업무 생성
        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(BusinessOrderActivity.this, CreateNewBusinessActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setUpBadge() {
        taskStorage = new TaskDataFirebase();
        taskStorage.getOrderBoxPubl(uid,null ,new TaskStorage.OnDataPublisherReceivedListener() {
            @Override
            public void OnDataReceived(List<TaskRequest> taskRequest) {
                taskCount = 0;
                taskStorage = new TaskDataFirebase();
                taskStorage.getUserInfo(uid, new TaskStorage.OnDataUserInfoReceivedListener() {
                    @Override
                    public void OnDataReceived(List<TaskUserInfo> userNameList) {
                        for (TaskUserInfo t : userNameList) {
                            if (uid.equals(t.getUid())) {
                                recipient = t.getUserName();
                            }
                        }
                        for (TaskRequest s : taskRequest) {
                            if ((s.getApprovalStatus() == 0) && recipient.equals(s.getRecipient())) {
                                taskCount++;

                            }
                        }
                        if (taskCount == 0) {
                            menuItem.setActionView(null);
                        } else {
                            menuItem.setActionView(R.layout.custom_notification_layout);
                            View view = menuItem.getActionView();
                            countBell = view.findViewById(R.id.notification_badge);
                            countBell.setText(String.valueOf(taskCount));
                        }
                    }
                });


            }
        });

    }
    private void loadTabName () {
            tabLayout.addTab(tabLayout.newTab().setText("업무수신함"));
            tabLayout.addTab(tabLayout.newTab().setText("업무지시함"));
        }
    private void setViewPager () {
            FragmentPagerAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
            final ViewPager viewPager = findViewById(R.id.view_pager_order_box);
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition() != viewPager.getCurrentItem()) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    if (tab.getPosition() != viewPager.getCurrentItem()) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }
                }
            });

        }


}