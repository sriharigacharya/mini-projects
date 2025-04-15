package com.example.expensetracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDAO {
    @Insert
    void insert(Expense expense);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);

    @Query("SELECT * FROM expense_table WHERE id=:id")
    List<Expense> getexpense(int id);

    @Query("SELECT * FROM expense_table WHERE description LIKE '%' || :query || '%' OR amount LIKE '%' || :query || '%' ORDER BY date DESC")
    List<Expense> searchExpenses(String query);

    @Query("SELECT * FROM expense_table WHERE (date<=:todate) AND (date>=:fromdate)")
    List<Expense> getexpenseindaterange(String fromdate,String todate);

    @Query("SELECT c.category_name AS categoryname, SUM(e.amount) AS totalamount " +
            "FROM expense_table e " +
            "JOIN category_table c ON e.category_id = c.id " +
            "WHERE e.date BETWEEN :startDate AND :endDate " +
            "GROUP BY c.category_name")
    List<POJOCatFilterList> getTotalByCategoryNameInRange(String startDate, String endDate);


    @Query("SELECT strftime('%Y-%m', e.date) AS month, " +
            "SUM(e.amount) AS totalAmount, " +
            "ROUND(SUM(e.amount) * 1.0 / CAST(strftime('%d', date(e.date, 'start of month', '+1 month', '-1 day')) AS INTEGER), 2) AS dailyAverage " +
            "FROM expense_table e " +
            "WHERE (e.category_id = :categoryId OR :categoryId = -1) " +
            "GROUP BY strftime('%Y-%m', e.date) " +
            "ORDER BY month DESC")
    List<POJOMonthlyExpense> getTotalByMonthAndCategory(int categoryId);


    @Query("SELECT date AS perdaydate, SUM(amount) AS perdayexpense " +
            "FROM expense_table " +
            "WHERE strftime('%Y-%m', date) = :month " +
            "AND (category_id = :categoryId OR :categoryId=-1) " +
            "GROUP BY date " +
            "ORDER BY date DESC")
    List<POJOPerDayList> getPerDayExpenseByMonthAndCategory(String month, int categoryId);



    @Query("SELECT * FROM expense_table "+
            "WHERE (date=:date) AND "+
            "(category_id = :categoryid OR :categoryid=-1)")
    List<Expense> getexpensebydateandcategory(String date,int categoryid);







//    @Query("SELECT strftime('%Y-%m', e.date) AS month, " +
//            "SUM(e.amount) AS totalAmount, " +
//            "ROUND(SUM(e.amount) * 1.0 / COUNT(DISTINCT e.date), 2) AS dailyAverage " +
//            "FROM expense_table e " +
//            "WHERE (e.category_id = :categoryId OR :categoryId = -1) " +
//            "GROUP BY strftime('%Y-%m', e.date) " +
//            "ORDER BY month DESC")
//    List<POJOMonthlyExpense> getTotalByMonthAndCategory(int categoryId);






//    @Query("SELECT * FROM expense_table " +
//            "WHERE (:fromDate IS NULL OR date >= :fromDate) " +
//            "AND (:toDate IS NULL OR date <= :toDate) " +
//            "AND (description LIKE '%' || :query || '%' OR amount LIKE '%' || :query || '%') " +
//            "ORDER BY date DESC")
//    List<Expense> searchExpensesWithDate(String query, String fromDate, String toDate);


    @Query("SELECT * FROM expense_table ORDER BY date DESC")
    List<Expense> getAllExpenses();
}
