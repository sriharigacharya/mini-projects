package com.example.expensetracker;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyRepository {

    private final ExpenseDAO expenseDAO;
    private final CategoryDAO categoryDAO;
    private final UserDAO userDAO;
    private final RecurringExpenseDAO recurringExpenseDAO;
    private final ExportLogDAO exportLogDAO;

    ExecutorService executor;
    Handler handler;

    public MyRepository(Application application) {
        ETDatabase db = ETDatabase.getInstance(application);
        this.expenseDAO = db.getExpenseDAO();
        this.categoryDAO = db.getCategoryDAO();
        this.userDAO = db.getUserDAO();

        this.recurringExpenseDAO = db.getRecurringExpenseDAO();
        this.exportLogDAO = db.getExportLogDAO();

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    // Expense Methods
    public void addExpense(Expense expense) {
        executor.execute(() -> expenseDAO.insert(expense));
    }

    public void updateexpense(Expense expense){
        executor.execute(() -> expenseDAO.update(expense));
    }

    public void delExpense(Expense expense) {
        executor.execute(() -> expenseDAO.delete(expense));
    }

    public Expense getexpensefromid(int id) {
        List<Expense> expenseList = executeQuery(() -> expenseDAO.getexpense(id));
        if (expenseList != null && !expenseList.isEmpty()) {
            return expenseList.get(0);
        } else {
            return null;
        }
    }

    public List<Expense> searchexpenses(String searchquery) {
        if (searchquery == null || searchquery.trim().isEmpty()) {
            return getAllExpenses();
        }
        return executeQuery(() -> new ArrayList<>(expenseDAO.searchExpenses(searchquery)));
    }

    public List<Expense> getexpensebydate(String fromdate,String todate){
        return executeQuery(()-> new ArrayList<>(expenseDAO.getexpenseindaterange(fromdate,todate)));
    }
    public ArrayList<POJOCatFilterList> getexpensesgroupedbycatogory(String startdate,String enddate){
        return executeQuery(()-> new ArrayList<>(expenseDAO.getTotalByCategoryNameInRange(startdate,enddate)));
    }

    public List<POJOMonthlyExpense> getTotalByMonthAndCategory(int categoryId) {
        return executeQuery(() -> new ArrayList<>(expenseDAO.getTotalByMonthAndCategory(categoryId)));
    }

    public List<POJOPerDayList> getperdayexpensebymonthandcat(String month,int categoryid){
        return executeQuery(()-> new ArrayList<>(expenseDAO.getPerDayExpenseByMonthAndCategory(month,categoryid)));
    }

    public List<Expense> getexpensebydateandcategory(String date,int categoryid){
        return executeQuery(()-> new ArrayList<>(expenseDAO.getexpensebydateandcategory(date,categoryid)));
    }

//    public List<Expense> searchExpenseswithdate(String searchQuery, String fromDate, String toDate) {
//        if ((searchQuery == null || searchQuery.trim().isEmpty()) &&
//                (fromDate == null || fromDate.trim().isEmpty()) &&
//                (toDate == null || toDate.trim().isEmpty())) {
//            return getAllExpenses();
//        }
//
//        return executeQuery(() -> new ArrayList<>(
//                expenseDAO.searchExpensesWithDate(searchQuery,
//                        fromDate.isEmpty() ? null : fromDate,
//                        toDate.isEmpty() ? null : toDate)));
//    }

    public ArrayList<Expense> getAllExpenses() {
        return executeQuery(() -> new ArrayList<>(expenseDAO.getAllExpenses()));
    }

    // Category Methods
    public void addCategory(Category category) {
        executor.execute(() -> categoryDAO.insert(category));
    }

    public void delCategory(Category category) {
        executor.execute(() -> categoryDAO.delete(category));
    }

    public void updatecategory(Category category){
        executor.execute(() -> categoryDAO.update(category));
    }
    public ArrayList<Category> getAllCategories() {
        return executeQuery(() -> new ArrayList<>(categoryDAO.getAllCategories()));
    }

    public int getidofcatname(String catname){
        final int[] idd=new int[1];
        executeQuery(() ->idd[0]=(categoryDAO.getidfromname(catname)));
        return idd[0];
    }



    public String getcatnamefromid(int idd){
        final String[] name=new String[1];
        executeQuery(() ->name[0]=(categoryDAO.getcatnamefromid(idd)));
        return name[0];
    }

    public boolean doescategoryexist(String catname){
        final int[] k=new int[1];
        executeQuery(() ->k[0]=categoryDAO.doesCategoryExist(catname));
        return k[0] != 0;
    }



    // User Methods
    public void addUser(User user) {
        executor.execute(() -> userDAO.insert(user));
    }

    public void delUser(User user) {
        executor.execute(() -> userDAO.delete(user));
    }

    public ArrayList<User> getAllUsers() {
        return executeQuery(() -> new ArrayList<>(userDAO.getAllUsers()));
    }



    // Recurring Expense Methods
    public void addRecurringExpense(RecurringExpense recurringExpense) {
        executor.execute(() -> recurringExpenseDAO.insert(recurringExpense));
    }

    public void updaterecdepexpense(RecurringExpense recurringExpense){
        executor.execute(() -> recurringExpenseDAO.update(recurringExpense));
    }

    public void delRecurringExpense(RecurringExpense recurringExpense) {
        executor.execute(() -> recurringExpenseDAO.delete(recurringExpense));
    }

    public ArrayList<RecurringExpense> getAllRecurringExpenses() {
        return executeQuery(() -> new ArrayList<>(recurringExpenseDAO.getAllRecurringExpenses()));
    }


    // Export Log Methods
    public void addExportLog(ExportLog exportLog) {
        executor.execute(() -> exportLogDAO.insert(exportLog));
    }

    public void delExportLog(ExportLog exportLog) {
        executor.execute(() -> exportLogDAO.delete(exportLog));
    }

    public ArrayList<ExportLog> getAllExportLogs() {
        return executeQuery(() -> new ArrayList<>(exportLogDAO.getAllExportLogs()));
    }


    private <T> T executeQuery(Callable<T> query) {
        Future<T> future = executor.submit(query);
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}









//package com.example.expensetracker;
//
//
//
//import android.app.Application;
//import android.os.Handler;
//import android.os.Looper;
//
//import androidx.lifecycle.LiveData;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class MyRepository {
//
//    private final ExpenseDAO expenseDAO;
//    private final CategoryDAO categoryDAO;
//    private final UserDAO userDAO;
//    private final BudgetDAO budgetDAO;
//    private final RecurringExpenseDAO recurringExpenseDAO;
//    private final ExportLogDAO exportLogDAO;
//
//    ExecutorService executor;
//    Handler handler;
//
//    public MyRepository(Application application) {
//        ETDatabase db = ETDatabase.getInstance(application);
//        this.expenseDAO = db.getExpenseDAO();
//        this.categoryDAO = db.getCategoryDAO();
//        this.userDAO = db.getUserDAO();
//        this.budgetDAO = db.getBudgetDAO();
//        this.recurringExpenseDAO = db.getRecurringExpenseDAO();
//        this.exportLogDAO = db.getExportLogDAO();
//
//        executor = Executors.newSingleThreadExecutor();
//        handler = new Handler(Looper.getMainLooper());
//    }
//
//    // Expense Methods
//    public void addExpense(Expense expense) {
//        executor.execute(() -> expenseDAO.insert(expense));
//    }
//
//    public void delExpense(Expense expense) {
//        executor.execute(() -> expenseDAO.delete(expense));
//    }
//
//    public LiveData<List<Expense>> getAllExpenses() {
//        return expenseDAO.getAllExpenses();
//    }
//
//    // Category Methods
//    public void addCategory(Category category) {
//        executor.execute(() -> categoryDAO.insert(category));
//    }
//
//    public void delCategory(Category category) {
//        executor.execute(() -> categoryDAO.delete(category));
//    }
//
//    public LiveData<List<Category>> getAllCategories() {
//        return categoryDAO.getAllCategories();
//    }
//
//    // User Methods
//    public void addUser(User user) {
//        executor.execute(() -> userDAO.insert(user));
//    }
//
//    public void delUser(User user) {
//        executor.execute(() -> userDAO.delete(user));
//    }
//
//    public LiveData<List<User>> getAllUsers() {
//        return userDAO.getAllUsers();
//    }
//
//    // Budget Methods
//    public void addBudget(Budget budget) {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                budgetDAO.insert(budget);
//            }
//        });
//
//    }
//
//    public void delBudget(Budget budget) {
//        executor.execute(() -> budgetDAO.delete(budget));
//    }
//
//    public LiveData<List<Budget>> getAllBudgets() {
//        return budgetDAO.getAllBudgets();
//    }
//
//    // Recurring Expense Methods
//    public void addRecurringExpense(RecurringExpense recurringExpense) {
//        executor.execute(() -> recurringExpenseDAO.insert(recurringExpense));
//    }
//
//    public void delRecurringExpense(RecurringExpense recurringExpense) {
//        executor.execute(() -> recurringExpenseDAO.delete(recurringExpense));
//    }
//
//    public LiveData<List<RecurringExpense>> getAllRecurringExpenses() {
//        return recurringExpenseDAO.getAllRecurringExpenses();
//    }
//
//    // Export Log Methods
//    public void addExportLog(ExportLog exportLog) {
//        executor.execute(() -> exportLogDAO.insert(exportLog));
//    }
//
//    public void delExportLog(ExportLog exportLog) {
//        executor.execute(() -> exportLogDAO.delete(exportLog));
//    }
//
//    public LiveData<List<ExportLog>> getAllExportLogs() {
//        return exportLogDAO.getAllExportLogs();
//    }
//}
//
