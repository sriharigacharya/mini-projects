package com.example.expensetracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecDeplistViewHolder extends RecyclerView.ViewHolder {

    TextView amt,lastpaiddate,nxtduedate,cat,desc,markaspaidbtn;
    public RecDeplistViewHolder(@NonNull View itemView) {
        super(itemView);
        amt=itemView.findViewById(R.id.recdepamt);
        lastpaiddate=itemView.findViewById(R.id.recdeplastpaid);
        nxtduedate=itemView.findViewById(R.id.recdepnxtdue);
        cat=itemView.findViewById(R.id.recdepcat);
        desc=itemView.findViewById(R.id.recdepdesc);
        markaspaidbtn=itemView.findViewById(R.id.recdepbutton);
    }
}
