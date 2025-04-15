package com.example.expensetracker;

public class POJOCatFilterList {
    String categoryname;
    double totalamount;

    public String getCatogory() {
        return categoryname;
    }

    public void setCatogory(String catogory) {
        this.categoryname = catogory;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }
}
