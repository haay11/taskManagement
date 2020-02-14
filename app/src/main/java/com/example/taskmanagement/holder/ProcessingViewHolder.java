package com.example.taskmanagement.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.storage.ViewHolderInterface;

public class ProcessingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView title, companyTv,
            nameTv, startDayTv,
            endDayTv, approvalStateTv;
    public ImageView receiveCircle;
    public CardView cardView;


    private ViewHolderInterface.ItemClickListener itemClickListener;

    public ProcessingViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.receive_title);
        companyTv = itemView.findViewById(R.id.receive_company_tv);
        nameTv = itemView.findViewById(R.id.receive_name_tv);
        startDayTv = itemView.findViewById(R.id.receive_start_day_tv);
        endDayTv = itemView.findViewById(R.id.receive_end_day_tv);
        approvalStateTv = itemView.findViewById(R.id.approval_state_tv);
        receiveCircle = itemView.findViewById(R.id.receive_circle);
        cardView = itemView.findViewById(R.id.card_view_receive);

        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }
    void setItemClickListener(ViewHolderInterface.ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
