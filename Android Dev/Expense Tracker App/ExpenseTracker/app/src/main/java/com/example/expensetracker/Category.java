package com.example.expensetracker;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//@Entity(tableName = "category_table",indices = {@Index(value = "category_name",unique = true)})
//@Entity(tableName = "category_table")
@Entity(tableName = "category_table",indices = {@Index(value = "category_name",unique = true)})

public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category_name")
    private String categoryName;
    @ColumnInfo(name = "budget_limit",defaultValue = "0")
    private double budget_limit;


    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.budget_limit=0;
    }
    public Category(String categoryName, double limit) {
        this.categoryName = categoryName;
        this.budget_limit=limit;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getBudget_limit() {
        return budget_limit;
    }

    public void setBudget_limit(double budget_limit) {
        this.budget_limit = budget_limit;
    }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
