package com.example.expensetracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class exportlistViewHolder extends RecyclerView.ViewHolder {
    TextView name,daterange,expdate;
    public exportlistViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.exportlistfilename);
        daterange=itemView.findViewById(R.id.exportlistdaterange);
        expdate=itemView.findViewById(R.id.exportlistexportdate);

    }
}
