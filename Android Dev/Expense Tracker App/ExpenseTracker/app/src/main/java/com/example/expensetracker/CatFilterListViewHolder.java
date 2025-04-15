package com.example.expensetracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CatFilterListViewHolder extends RecyclerView.ViewHolder {

    TextView catfilterlistname,catfilterlistamt;
    public CatFilterListViewHolder(@NonNull View itemView) {
        super(itemView);
        catfilterlistname=itemView.findViewById(R.id.catfilterlistname);
        catfilterlistamt=itemView.findViewById(R.id.catfilterlistamt);
    }

    public void bind(POJOCatFilterList item, CatFilterListAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener.onItemClick(item);
            }
        });
    }

}
