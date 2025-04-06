package com.example.expensetracker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListViewHolder> {


    Context context;
    List<Expense> expenseList;
    MyRepository myRepository = new MyRepository(new Application());

    public HomeListAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public HomeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeListViewHolder(LayoutInflater.from(context).inflate(R.layout.homelistitems,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeListViewHolder holder, int position) {
        holder.amt.setText("\u20B9"+expenseList.get(position).getAmount());
        holder.cat.setText(myRepository.getcatnamefromid(expenseList.get(position).getCategoryId()));
        holder.dsc.setText(expenseList.get(position).getDescription());
        holder.date.setText(expenseList.get(position).getDate());

        holder.editexpensebutton.setOnClickListener(v -> {
            Intent intent=new Intent(context,NewExpenseActivity.class);
            intent.putExtra("Position",position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }
}
