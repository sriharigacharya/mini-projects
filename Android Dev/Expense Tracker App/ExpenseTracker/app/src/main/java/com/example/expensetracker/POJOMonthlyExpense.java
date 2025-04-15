package com.example.expensetracker;
public class POJOMonthlyExpense {
    String month;
    double totalAmount;
    double dailyAverage;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getDailyAverage() {
        return dailyAverage;
    }

    public void setDailyAverage(double dailyAverage) {
        this.dailyAverage = dailyAverage;
    }
}
