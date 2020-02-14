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
import com.example.taskmanagement.data.BusinessOrderBoxData;
import com.example.taskmanagement.holder.MyViewHolder;

import java.util.List;

public class ReceiveMyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<BusinessOrderBoxData> data;
    private String timeStamp, rUid, uid;
    private int status;

    public ReceiveMyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_order_item, null );
        view.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        ));
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(data.get(position).getTitle());
        holder.companyTv.setText(data.get(position).getCompanyTv());
        holder.nameTv.setText(data.get(position).getNameTv());
        holder.startDayTv.setText(data.get(position).getStartDayTv());
        holder.endDayTv.setText(data.get(position).getEndDayTv());
        holder.approvalStateTv.setText(data.get(position).getApprovalStateTv());
        holder.receiveCircle.setImageResource(data.get(position).getReceiveCircle());
        timeStamp = String.valueOf(data.get(position).getTimeStamp());
        rUid = data.get(position).getrUid();
        uid = data.get(position).getUid();
        status = data.get(position).getStatus();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BusinessOrderCheckActivity.class);
                intent.putExtra("title", data.get(position).getTitle());
                intent.putExtra("name", data.get(position).getNameTv());
                intent.putExtra("approvalStateTv", data.get(position).getApprovalStateTv());
                intent.putExtra("receiveCircle", data.get(position).getReceiveCircle());
                intent.putExtra("timeStamp", data.get(position).getTimeStamp());
                intent.putExtra("box", 0);
                intent.putExtra("rUid", data.get(position).getrUid());
                intent.putExtra("uid", data.get(position).getUid());
                intent.putExtra("status", data.get(position).getStatus());
                    v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setData(List<BusinessOrderBoxData> data){
        this.data = data;
    }
}
