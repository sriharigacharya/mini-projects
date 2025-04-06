package com.example.expensetracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BudgetDAO {
    @Insert
    void insert(Budget budget);

    @Delete
    void delete(Budget budget);

    @Query("SELECT * FROM budget_table")
    List<Budget> getAllBudgets();
}
