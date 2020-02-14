package com.example.taskmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.businessorder.BusinessOrderCheckActivity;
import com.example.taskmanagement.data.ProcessStatusData;
import com.example.taskmanagement.holder.ProcessingViewHolder;

import java.util.List;

public class ProcessingAdapter extends RecyclerView.Adapter<ProcessingViewHolder> {

    private Context context;
    private List<ProcessStatusData> data;
    private String timeStamp;
    private int status;


    public ProcessingAdapter(Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public ProcessingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_order_item, null );
        view.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        ));

        ProcessingViewHolder processingViewHolder = new ProcessingViewHolder(view);

        return processingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessingViewHolder holder, int position) {

        holder.title.setText(data.get(position).getTitle());
        holder.companyTv.setText(data.get(position).getCompanyTv());
        holder.nameTv.setText(data.get(position).getNameTv());
        holder.startDayTv.setText(data.get(position).getStartDayTv());
        holder.endDayTv.setText(data.get(position).getEndDayTv());
        holder.approvalStateTv.setVisibility(View.INVISIBLE);
        holder.receiveCircle.setVisibility(View.INVISIBLE);
        timeStamp = String.valueOf(data.get(position).getTimeStamp());
        status = data.get(position).getStatus();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BusinessOrderCheckActivity.class);
                intent.putExtra("box", 1);
                intent.putExtra("endDay", data.get(position).getEndDayTv());
                intent.putExtra("name", data.get(position).getNameTv());
                intent.putExtra("timeStamp", data.get(position).getTimeStamp());
                intent.putExtra("status", data.get(position).getStatus());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setData(List<ProcessStatusData> data){
        this.data = data;
    }
}
