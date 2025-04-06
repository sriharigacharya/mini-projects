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
    private final BudgetDAO budgetDAO;
    private final RecurringExpenseDAO recurringExpenseDAO;
    private final ExportLogDAO exportLogDAO;

    ExecutorService executor;
    Handler handler;

    public MyRepository(Application application) {
        ETDatabase db = ETDatabase.getInstance(application);
        this.expenseDAO = db.getExpenseDAO();
        this.categoryDAO = db.getCategoryDAO();
        this.userDAO = db.getUserDAO();
        this.budgetDAO = db.getBudgetDAO();
        this.recurringExpenseDAO = db.getRecurringExpenseDAO();
        this.exportLogDAO = db.getExportLogDAO();

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    // Expense Methods
    public void addExpense(Expense expense) {
        executor.execute(() -> expenseDAO.insert(expense));
    }

    public void delExpense(Expense expense) {
        executor.execute(() -> expenseDAO.delete(expense));
    }

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

    // Budget Methods
    public void addBudget(Budget budget) {
        executor.execute(() -> budgetDAO.insert(budget));
    }

    public void delBudget(Budget budget) {
        executor.execute(() -> budgetDAO.delete(budget));
    }

    public ArrayList<Budget> getAllBudgets() {
        return executeQuery(() -> new ArrayList<>(budgetDAO.getAllBudgets()));
    }

    // Recurring Expense Methods
    public void addRecurringExpense(RecurringExpense recurringExpense) {
        executor.execute(() -> recurringExpenseDAO.insert(recurringExpense));
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
