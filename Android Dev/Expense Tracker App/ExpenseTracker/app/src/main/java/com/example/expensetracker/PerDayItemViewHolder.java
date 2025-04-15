package com.example.expensetracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PerDayItemViewHolder extends RecyclerView.ViewHolder {
    TextView perdayname,perdayamt;
    public PerDayItemViewHolder(@NonNull View itemView) {
        super(itemView);
        perdayname=itemView.findViewById(R.id.perdaydate);
        perdayamt=itemView.findViewById(R.id.perdayamt);
    }

    public void bind(POJOPerDayList item,PerDayItemAdapter.OnItemClickListener listener){
        itemView.setOnClickListener(v -> {
            int position=getAdapterPosition();
            if(listener!=null&&position!=RecyclerView.NO_POSITION){
                listener.onItemClick(item);
            }
        });
    }
}
