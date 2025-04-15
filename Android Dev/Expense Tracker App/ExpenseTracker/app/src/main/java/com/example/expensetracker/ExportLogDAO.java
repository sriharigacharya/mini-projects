package com.example.expensetracker;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ExportLogDAO {
    @Insert
    void insert(ExportLog exportLog);

    @Delete
    void delete(ExportLog exportLog);

    @Query("SELECT * FROM export_log_table ORDER BY export_date DESC")
    List<ExportLog> getAllExportLogs();
}

