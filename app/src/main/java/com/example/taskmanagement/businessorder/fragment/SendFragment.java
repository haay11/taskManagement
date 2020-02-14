package com.example.taskmanagement.businessorder.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taskmanagement.adapter.ReceiveMyAdapter;
import com.example.taskmanagement.R;
import com.example.taskmanagement.data.BusinessOrderBoxData;
import com.example.taskmanagement.data.TaskRequest;
import com.example.taskmanagement.storage.TaskDataFirebase;
import com.example.taskmanagement.storage.TaskStorage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    public List<BusinessOrderBoxData> businessOrderBoxData;
    private ReceiveMyAdapter adapter;
    public String signInEmail, signInName;
    public String title, companyTv, nameTv, startDayTv, endDayTv, approvalStateTv
            , timeStamp, rUid, pUid;
    public int receiveCircle, status;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();;
    private TaskStorage taskStorage;
    private int notice = 0;


    public SendFragment() {
        // Required empty public constructor
    }

    public static SendFragment newInstance(){
        SendFragment sendFragment = new SendFragment();
        return sendFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_list, container, false);
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.receive_recycler_view);

        adapter = new ReceiveMyAdapter(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(adapter);

        getData();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("로그로그 send", "onResume");
        getData();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("로그로그 send", "onstart");
        getData();
    }


    private void getData(){
        taskStorage = new TaskDataFirebase();
        taskStorage.getOrderBoxPubl(uid,null, new TaskStorage.OnDataPublisherReceivedListener() {
            @Override
            public void OnDataReceived(List<TaskRequest> taskRequest) {
                businessOrderBoxData = new ArrayList<>();
                for (TaskRequest s : taskRequest) {
                    if (uid.equals(s.getUid())) {
                        switch (s.getApprovalStatus()) {
                            case 0:
                                approvalStateTv = "미확인";
                                receiveCircle = R.drawable.ic_circle_unapproved;
                                Log.e("datadat 미확인", approvalStateTv);
                                break;
                            case 1:
                                approvalStateTv = "승인";
                                receiveCircle = R.drawable.ic_circle_pproval;
                                Log.e("datadat 승인", approvalStateTv);
                                break;
                            case 2:
                                approvalStateTv = "보류";
                                receiveCircle = R.drawable.ic_circle_hold;
                                Log.e("datadat 보류", approvalStateTv);
                                break;
                            case 3:
                                approvalStateTv = "반려";
                                receiveCircle = R.drawable.ic_circle_return;
                                Log.e("datadat 반려", approvalStateTv);
                                break;

                            default:
                                throw new IllegalStateException("datadat value: " + s.getApprovalStatus());
                        }

                        title = s.getTitle();
                        companyTv = s.getUserCompany();
                        nameTv = s.getRecipient();
                        startDayTv = s.getDayOfIssue();
                        endDayTv = s.getDeadLineDate();
                        timeStamp = s.getTimeStamp();
                        rUid = s.getrUid();
                        pUid = s.getUid();
                        status = s.getStatus();

                        businessOrderBoxData.add(new BusinessOrderBoxData(title, companyTv, nameTv
                                , startDayTv, endDayTv, approvalStateTv, receiveCircle, timeStamp, pUid, rUid, status));
                    }
                }
                adapter.setData(businessOrderBoxData);
                adapter.notifyDataSetChanged();
            }

        });

    }


}
