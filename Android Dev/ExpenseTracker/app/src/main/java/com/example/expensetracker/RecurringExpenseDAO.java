package com.example.expensetracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecurringExpenseDAO {
    @Insert
    void insert(RecurringExpense recurringExpense);

    @Delete
    void delete(RecurringExpense recurringExpense);

    @Query("SELECT * FROM recurring_expense_table")
    List<RecurringExpense> getAllRecurringExpenses();
}

