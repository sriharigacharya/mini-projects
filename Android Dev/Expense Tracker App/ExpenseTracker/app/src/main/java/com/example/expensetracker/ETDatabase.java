package com.example.expensetracker;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class, Category.class, User.class, RecurringExpense.class, ExportLog.class}, version = 1)
public abstract class ETDatabase extends RoomDatabase {

    public abstract ExpenseDAO getExpenseDAO();
    public abstract CategoryDAO getCategoryDAO();
    public abstract UserDAO getUserDAO();
    public abstract RecurringExpenseDAO getRecurringExpenseDAO();
    public abstract ExportLogDAO getExportLogDAO();

    private static ETDatabase instance;

    public static synchronized ETDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ETDatabase.class,
                            "expense_tracker_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
