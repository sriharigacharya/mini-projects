package com.example.expensetracker;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "export_log_table")
public class ExportLog {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "file_name")
    private String fileName;

    @ColumnInfo(name = "export_date")
    private String exportDate;

    public ExportLog(String fileName, String exportDate) {
        this.fileName = fileName;
        this.exportDate = exportDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getExportDate() { return exportDate; }
    public void setExportDate(String exportDate) { this.exportDate = exportDate; }
}
