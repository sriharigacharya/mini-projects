package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CatFilterListAdapter extends RecyclerView.Adapter<CatFilterListViewHolder> {
    Context context;
    List<POJOCatFilterList> pojoCatFilterLists;



    OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(POJOCatFilterList pojoCatFilterList);
    }

    public CatFilterListAdapter(Context context, List<POJOCatFilterList> pojoCatFilterLists,OnItemClickListener listener) {
        this.context = context;
        this.pojoCatFilterLists = pojoCatFilterLists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CatFilterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CatFilterListViewHolder(LayoutInflater.from(context).inflate(R.layout.catogoryfilterlistitems,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CatFilterListViewHolder holder, int position) {
        POJOCatFilterList pojoCatFilterListitem=pojoCatFilterLists.get(position);
        holder.catfilterlistname.setText(""+pojoCatFilterListitem.getCatogory());
        holder.catfilterlistamt.setText(""+pojoCatFilterListitem.getTotalamount());
        holder.bind(pojoCatFilterListitem,listener);

    }

    @Override
    public int getItemCount() {
        return pojoCatFilterLists.size();
    }

}
