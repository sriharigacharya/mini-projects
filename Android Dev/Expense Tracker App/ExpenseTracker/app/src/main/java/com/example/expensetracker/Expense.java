package com.example.expensetracker;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_table")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    private String date;

    public Expense(double amount, int categoryId, String description, String date) {
        this.amount = amount;
        this.categoryId = categoryId;
        this.description = description;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }

    public String getDateinDisplayFormat() {
        try {
            String[] parts = date.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            String monthName = "";
            switch(month) {
                case 1: monthName = "Jan"; break;
                case 2: monthName = "Feb"; break;
                case 3: monthName = "Mar"; break;
                case 4: monthName = "Apr"; break;
                case 5: monthName = "May"; break;
                case 6: monthName = "Jun"; break;
                case 7: monthName = "Jul"; break;
                case 8: monthName = "Aug"; break;
                case 9: monthName = "Sep"; break;
                case 10: monthName = "Oct"; break;
                case 11: monthName = "Nov"; break;
                case 12: monthName = "Dec"; break;
                default: monthName = "Err";
            }

            return String.format("%02d %s %d", day, monthName, year);
        } catch (Exception e) {
            return date;
        }
    }
    public void setDate(String date) { this.date = date; }
}

