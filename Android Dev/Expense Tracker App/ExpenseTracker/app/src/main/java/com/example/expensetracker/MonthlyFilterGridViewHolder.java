package com.example.expensetracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MonthlyFilterGridViewHolder extends RecyclerView.ViewHolder {

    TextView month_name,month_total,daily_avg;
    public MonthlyFilterGridViewHolder(@NonNull View itemView) {
        super(itemView);
        month_name=itemView.findViewById(R.id.monthlistname);
        month_total=itemView.findViewById(R.id.monthlistamt);
        daily_avg=itemView.findViewById(R.id.monthlistdailyavg);

    }

    public void bind(POJOMonthlyExpense item,MonthlyFilterGridAdapter.OnItemClickListener listener){
        itemView.setOnClickListener(v -> {
            int position=getAdapterPosition();
            if(listener!=null && position!=RecyclerView.NO_POSITION){
                listener.onItemClick(item);
            }
        });
    }
}
