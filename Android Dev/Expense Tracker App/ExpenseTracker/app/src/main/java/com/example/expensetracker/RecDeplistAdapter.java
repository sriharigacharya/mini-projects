package com.example.expensetracker;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecDeplistAdapter extends RecyclerView.Adapter<RecDeplistViewHolder> {
    MyRepository myRepository=new MyRepository(new Application()) ;
    Context context;
    List<RecurringExpense> recurringExpenseList;

    public RecDeplistAdapter(Context context, List<RecurringExpense> recurringExpenseList) {
        this.context = context;
        this.recurringExpenseList = recurringExpenseList;

    }


    @NonNull
    @Override
    public RecDeplistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new RecDeplistViewHolder(LayoutInflater.from(context).inflate(R.layout.recurringexpensepageitems,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecDeplistViewHolder holder, int position) {
        RecurringExpense recurringExpense=recurringExpenseList.get(position);
        holder.amt.setText(""+recurringExpense.getAmount());
        holder.cat.setText(myRepository.getcatnamefromid(recurringExpense.getCategoryId()));
        holder.desc.setText(recurringExpense.getExpenseName());
        holder.nxtduedate.setText(DateUtil.dbToDisplay(recurringExpense.getNextDueDate()));
        holder.lastpaiddate.setText(DateUtil.dbToDisplay(recurringExpense.getLastpaiddate()));
        holder.markaspaidbtn.setOnClickListener(v -> {
            myRepository.addExpense(new Expense(recurringExpense.getAmount(),
                    recurringExpense.getCategoryId(),
                    recurringExpense.getExpenseName(),
                    DateUtil.displayToDb(DateUtil.gettodaysdate())));
            recurringExpense.setLastpaiddate(DateUtil.displayToDb(DateUtil.gettodaysdate()));
            recurringExpense.setNextDueDate(DateUtil.addDaysToDbDate(recurringExpense.getNextDueDate(),31));
            myRepository.updaterecdepexpense(recurringExpense);
            Toast.makeText(context,"Marked "+recurringExpense.getExpenseName()+" as paid. Added to Expense",Toast.LENGTH_LONG).show();
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return recurringExpenseList.size();
    }
}
