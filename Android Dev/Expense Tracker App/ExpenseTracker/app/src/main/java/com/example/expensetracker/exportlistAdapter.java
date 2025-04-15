package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class exportlistAdapter extends RecyclerView.Adapter<exportlistViewHolder> {
    List<ExportLog> exportLogs;
    Context context;

    public exportlistAdapter(Context context,List<ExportLog> exportLogs) {
        this.exportLogs = exportLogs;
        this.context = context;
    }

    @NonNull
    @Override
    public exportlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new exportlistViewHolder(LayoutInflater.from(context).inflate(R.layout.exportlogpagelist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull exportlistViewHolder holder, int position) {
        ExportLog exportLog=exportLogs.get(position);
        holder.name.setText(exportLog.getFileName());
        holder.daterange.setText(exportLog.getDaterange());
        holder.expdate.setText(DateUtil.dbToDisplay(exportLog.getExportDate()));
    }

    @Override
    public int getItemCount() {
        return exportLogs.size();
    }
}
