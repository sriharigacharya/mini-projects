package com.example.expensetracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recurring_expense_table")
public class RecurringExpense {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "expense_name")
    private String expenseName;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "next_due_date")
    private String nextDueDate;


    @ColumnInfo(name = "last_paid_date")
    private String lastpaiddate;



    public RecurringExpense(String expenseName, int categoryId, double amount, String lastpaiddate, String nextDueDate) {
        this.expenseName = expenseName;
        this.categoryId = categoryId;
        this.amount = amount;
        this.lastpaiddate=lastpaiddate;
        this.nextDueDate = nextDueDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getExpenseName() { return expenseName; }
    public void setExpenseName(String expenseName) { this.expenseName = expenseName; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }


    public String getLastpaiddate() {
        return lastpaiddate;
    }

    public void setLastpaiddate(String lastpaiddate) {
        this.lastpaiddate = lastpaiddate;
    }

    public String getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(String nextDueDate) { this.nextDueDate = nextDueDate; }
}
