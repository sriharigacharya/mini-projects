package com.example.expensetracker;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListViewHolder> {

    Context context;
    List<Category> categoryList;
    MyRepository myRepository=new MyRepository(new Application());

    public CategoryListAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryListViewHolder(LayoutInflater.from(context).inflate(R.layout.categorylistitems,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListViewHolder holder, int position) {
        holder.categorytxt.setText(categoryList.get(position).getCategoryName());
        holder.categorylmt.setText(""+
                (categoryList.get(position).getBudget_limit()==0?"":categoryList.get(position).getBudget_limit()));
        holder.savecategorybutton.setOnClickListener(v -> {
            if(!(holder.categorylmt.getText().toString().isEmpty()) && Double.parseDouble(holder.categorylmt.getText().toString())<=99){
                Toast.makeText(v.getContext(), "Limit cannot be less than Rs. 100", Toast.LENGTH_SHORT).show();
                holder.categorylmt.setText(""+categoryList.get(position).getBudget_limit());
            }
            else {
                double l=0;
                if(holder.categorylmt.getText().toString().isEmpty()){
                    l=0.0;
                }
                else {
                    l=Double.parseDouble(holder.categorylmt.getText().toString());
                }
                categoryList.get(position).setBudget_limit(l);
                myRepository.updatecategory(categoryList.get(position));
                String string;
                if(holder.categorylmt.getText().toString().isEmpty()){
                    string="Removed limit from "+holder.categorytxt.getText();
                }else {
                    string = "Changed the limit of " + holder.categorytxt.getText() + " to Rs. " + Double.parseDouble(holder.categorylmt.getText().toString());
                }
                Toast.makeText(v.getContext(),string
                        , Toast.LENGTH_SHORT).show();
                holder.categorylmt.clearFocus();
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
