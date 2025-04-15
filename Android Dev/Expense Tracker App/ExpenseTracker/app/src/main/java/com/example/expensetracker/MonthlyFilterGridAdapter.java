package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MonthlyFilterGridAdapter extends RecyclerView.Adapter<MonthlyFilterGridViewHolder> {
    Context context;
    List<POJOMonthlyExpense> monthlyExpenseList;
    OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(POJOMonthlyExpense pojomonthlyexpense);
    }

    public MonthlyFilterGridAdapter(Context context, List<POJOMonthlyExpense> monthlyExpenseList,OnItemClickListener listener) {
        this.context = context;
        this.monthlyExpenseList = monthlyExpenseList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MonthlyFilterGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MonthlyFilterGridViewHolder(LayoutInflater.from(context).inflate(R.layout.monthlylistitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyFilterGridViewHolder holder, int position) {
        POJOMonthlyExpense monthlyExpense=monthlyExpenseList.get(position);
        holder.month_name.setText(DateUtil.makedatestringmonthly(monthlyExpense.getMonth()));
        holder.month_total.setText(""+monthlyExpense.getTotalAmount());
        holder.daily_avg.setText(""+monthlyExpense.getDailyAverage());
        holder.bind(monthlyExpense,listener);

    }

    @Override
    public int getItemCount() {
        return monthlyExpenseList.size();
    }
}
