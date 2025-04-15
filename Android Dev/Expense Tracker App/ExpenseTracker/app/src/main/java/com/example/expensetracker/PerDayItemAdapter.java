package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PerDayItemAdapter extends RecyclerView.Adapter<PerDayItemViewHolder> {
    Context context;
    List<POJOPerDayList> pojoPerDayLists;
    OnItemClickListener listener;

    public PerDayItemAdapter(Context context, List<POJOPerDayList> pojoPerDayLists, OnItemClickListener listener) {
        this.context = context;
        this.pojoPerDayLists = pojoPerDayLists;
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(POJOPerDayList pojoPerDayList);
    }
    @NonNull
    @Override
    public PerDayItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PerDayItemViewHolder(LayoutInflater.from(context).inflate(R.layout.perdaylistitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PerDayItemViewHolder holder, int position) {
        POJOPerDayList pojoPerDayListitem=pojoPerDayLists.get(position);
        holder.perdayname.setText(DateUtil.dbToDisplay(pojoPerDayListitem.perdaydate).substring(0,6));
        holder.perdayamt.setText(""+pojoPerDayListitem.getPerdayexpense());
        holder.bind(pojoPerDayListitem,listener);
    }

    @Override
    public int getItemCount() {
        return pojoPerDayLists.size();
    }
}
