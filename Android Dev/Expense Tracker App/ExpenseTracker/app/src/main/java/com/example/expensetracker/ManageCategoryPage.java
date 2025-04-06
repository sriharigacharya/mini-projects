package com.example.expensetracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ManageCategoryPage extends AppCompatActivity {

    MyRepository myRepository;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    List<Category> categoryList;
    CategoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_category_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myRepository=new MyRepository(getApplication());
        categoryList=myRepository.getAllCategories();
        floatingActionButton=findViewById(R.id.addnewcategorybutton);
        recyclerView=findViewById(R.id.categorylistrecyclerview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter=new CategoryListAdapter(getApplicationContext(),categoryList);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewCategoryActivity.class);
                startActivity(intent);
            }
        });
    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            new AlertDialog.Builder(ManageCategoryPage.this)
                    .setTitle("Delete Expense")
                    .setMessage("Are you sure you want to delete this Category?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        Category category = categoryList.get(position);
                        myRepository.delCategory(category);
                        categoryList.remove(position);
                        adapter.notifyItemRemoved(position);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        adapter.notifyItemChanged(position);
                    })
                    .setCancelable(false)
                    .show();
        }

    }).attachToRecyclerView(recyclerView);
    }

    public void onResume(){
        super.onResume();
        categoryList.clear();
        categoryList.addAll(myRepository.getAllCategories());
        adapter.notifyDataSetChanged();
    }

    public void onBackPressed(){
        finish();
        super.onBackPressed();
    }
}