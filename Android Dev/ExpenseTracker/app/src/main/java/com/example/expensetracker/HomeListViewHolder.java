package com.example.expensetracker;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeListViewHolder extends RecyclerView.ViewHolder {

    TextView amt,dsc,cat,date;
    ImageButton editexpensebutton;
    public HomeListViewHolder(@NonNull View itemView) {
        super(itemView);
        amt=itemView.findViewById(R.id.itemamount);
        dsc=itemView.findViewById(R.id.itemdesc);
        cat=itemView.findViewById(R.id.itemcategory);
        date=itemView.findViewById(R.id.itemdate);
        editexpensebutton=itemView.findViewById(R.id.editbutton);
    }
}
