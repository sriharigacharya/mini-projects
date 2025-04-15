package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExportLogActivity extends AppCompatActivity {
    private MyRepository myRepository=new MyRepository(getApplication());;
    private FloatingActionButton addnewexport;
    DatePickerDialog exportfromdate,exporttodate;
    Button fromdatepicker,todatepicker,savenewexport;
    EditText exportfilename;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_export_log);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView=findViewById(R.id.exportlogrecyclerview);
        exportlistAdapter adapter=new exportlistAdapter(this,myRepository.getAllExportLogs());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        addnewexport=findViewById(R.id.addnewexportfab);


        LayoutInflater inflater=getLayoutInflater();
        View newexportdialog=inflater.inflate(R.layout.newexportdialogbox,null);
        fromdatepicker=newexportdialog.findViewById(R.id.exportfromdate);
        todatepicker=newexportdialog.findViewById(R.id.exporttodate);
        exportfilename=newexportdialog.findViewById(R.id.exportfilename);

        exportfromdate=DateUtil.initdatepicker(newexportdialog.getContext(),fromdatepicker);
        exporttodate=DateUtil.initdatepicker(newexportdialog.getContext(),todatepicker);
        fromdatepicker.setOnClickListener(v -> {
            DateUtil.opendatepicker(exportfromdate);
        });
        todatepicker.setOnClickListener(v -> {
            DateUtil.opendatepicker(exporttodate);
        });

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(newexportdialog)
                .setPositiveButton("Export", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(exportfilename.getText().toString().isEmpty()||
                        fromdatepicker.getText().toString().isEmpty()||
                        todatepicker.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_LONG).show();
                        }
                        else{
                                List<Expense> expenseList = myRepository.getexpensebydate(
                                        DateUtil.displayToDb(fromdatepicker.getText().toString()),
                                        DateUtil.displayToDb(todatepicker.getText().toString()));
                                String csvContent = convertExpensesToCSV(expenseList);
                                saveCSVToDownloads(getApplicationContext(),
                                        exportfilename.getText().toString()+".csv", csvContent,
                                        fromdatepicker.getText().toString()+" - "+todatepicker.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        addnewexport.setOnClickListener(v -> {
            dialog.show();
        });

    }





    private String convertExpensesToCSV(List<Expense> expenses) {
        StringBuilder csvBuilder = new StringBuilder();

        // Header
        csvBuilder.append("Description,Amount,CategoryName,Date\n");

        // Data rows
        for (Expense expense : expenses) {
            csvBuilder.append("\"").append(expense.getDescription().replace("\"", "\"\"")).append("\",");
            csvBuilder.append(expense.getAmount()).append(",");
            csvBuilder.append(myRepository.getcatnamefromid(expense.getCategoryId())).append(",");
            csvBuilder.append(expense.getDate()).append("\n");
        }
        return csvBuilder.toString();
    }


    //File export:
    private void saveCSVToDownloads(Context context, String fileName, String csvContent,String daterange) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
        contentValues.put(MediaStore.Downloads.IS_PENDING, 1);
        ContentResolver resolver = context.getContentResolver();
        Uri collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        Uri fileUri = resolver.insert(collection, contentValues);

        if (fileUri != null) {
            try (OutputStream out = resolver.openOutputStream(fileUri)) {
                if (out != null) {
                    try {
                        out.write(csvContent.getBytes());
                        out.flush();
                    } catch (IOException e) {

                    }
                }

                // Mark as not pending (visible to user)
                contentValues.clear();
                contentValues.put(MediaStore.Downloads.IS_PENDING, 0);
                resolver.update(fileUri, contentValues, null, null);

                Toast.makeText(context, "CSV saved in Downloads", Toast.LENGTH_LONG).show();
                myRepository.addExportLog(new ExportLog(fileName,daterange,DateUtil.displayToDb(DateUtil.gettodaysdate())));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to save CSV", Toast.LENGTH_SHORT).show();
            }
        }
    }

}