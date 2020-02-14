
package com.example.taskmanagement.processingstatus.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.adapter.ProcessingAdapter;
import com.example.taskmanagement.R;
import com.example.taskmanagement.data.ProcessStatusData;
import com.example.taskmanagement.data.TaskRequest;
import com.example.taskmanagement.data.TaskUserInfo;
import com.example.taskmanagement.storage.TaskDataFirebase;
import com.example.taskmanagement.storage.TaskStorage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcessingStatusFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private ProcessingAdapter adapter;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();;
    private TaskStorage taskStorage;
    private List<ProcessStatusData> processStatusData;
    private String recipient, title, companyTv, nameTv, startDayTv, endDayTv, timeStamp;
    private int status;

    public ProcessingStatusFragment() {
        // Required empty public constructor
    }

    public static ProcessingStatusFragment newInstance(){
        ProcessingStatusFragment processingStatusFragment = new ProcessingStatusFragment();
        return processingStatusFragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_list, container, false);
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.receive_recycler_view);
        adapter = new ProcessingAdapter(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(adapter);

        getData();

        return view;
    }

    private void getData(){
        taskStorage = new TaskDataFirebase();
        taskStorage.getUserInfo(uid, new TaskStorage.OnDataUserInfoReceivedListener() {
            @Override
            public void OnDataReceived(List<TaskUserInfo> userNameList) {
                for (TaskUserInfo t : userNameList){
                    if (uid.equals(t.uid)){
                        recipient = t.getUserName();
                    }
                } getName(recipient);
                taskStorage.getOrderBoxPubl(recipient,null, new TaskStorage.OnDataPublisherReceivedListener() {
                    @Override
                    public void OnDataReceived(List<TaskRequest> taskRequest) {
                        processStatusData = new ArrayList<>();
                        for (TaskRequest s : taskRequest) {
                            if (recipient.equals(s.getRecipient())&&(s.getApprovalStatus()==1)&&(s.getStatus()==0)) {

                                title = s.getTitle();
                                companyTv = s.getUserCompany();
                                nameTv = s.getRecipient();
                                startDayTv = s.getDayOfIssue();
                                endDayTv = s.getDeadLineDate();
                                timeStamp = s.getTimeStamp();
                                status = s.getStatus();

                                processStatusData.add(new ProcessStatusData(title, companyTv, nameTv, startDayTv, endDayTv, uid,timeStamp, status));

                            }
                        }
                        adapter.setData(processStatusData);
                        adapter.notifyDataSetChanged();
                    }

                });

            }
        });
    }

    public String getName(String recipient){

        this.recipient = recipient;
        return recipient;
    }
}

