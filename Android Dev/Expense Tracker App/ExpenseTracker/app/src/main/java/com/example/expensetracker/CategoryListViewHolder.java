package com.example.expensetracker;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListViewHolder extends RecyclerView.ViewHolder {
    TextView categorytxt;
    EditText categorylmt;
    ImageButton savecategorybutton;
    public CategoryListViewHolder(@NonNull View itemView) {
        super(itemView);
        categorytxt=itemView.findViewById(R.id.categorytext);
        categorylmt=itemView.findViewById(R.id.categorylimit);
        savecategorybutton=itemView.findViewById(R.id.categorysave);
    }
}
