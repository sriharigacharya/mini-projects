package com.example.expensetracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Calendar;

public class NewExpenseActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button datepickerbutton,savebutton;
    EditText amount,desc;
    ArrayList<Category> categoryArrayList;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> stringArrayAdapter;
    Expense existing_expense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        amount=findViewById(R.id.amount);
        desc=findViewById(R.id.description);
        savebutton=findViewById(R.id.savenewexpense);
        Intent intentreceive=getIntent();
        MyRepository myRepository=new MyRepository(getApplication());
        categoryArrayList=myRepository.getAllCategories();


        datepickerbutton=findViewById(R.id.datepicker);
        datePickerDialog=DateUtil.initdatepicker(this,datepickerbutton);

        datepickerbutton.setText(DateUtil.gettodaysdate());
        datepickerbutton.setOnClickListener(v -> {
            DateUtil.opendatepicker(datePickerDialog);
        });


        if(categoryArrayList.size()==0){
            myRepository.addCategory(new Category("Food and Drinks"));
            myRepository.addCategory(new Category("Travel"));
            myRepository.addCategory(new Category("Utilities"));
            categoryArrayList=myRepository.getAllCategories();
        }
        String[] catlist=new String[categoryArrayList.size()+1];
        catlist[0]="Add new Category";

        for(int i=0;i<categoryArrayList.size();i++){
            catlist[i+1]=categoryArrayList.get(i).getCategoryName();
        }

        autoCompleteTextView=findViewById(R.id.categoryautocomplete);
        stringArrayAdapter=new ArrayAdapter<String>(this,R.layout.dropdownlistitems,catlist);
        autoCompleteTextView.setAdapter(stringArrayAdapter);
        int pos[]=new int[1];

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              String item=stringArrayAdapter.getItem(position).toString();
                if(position==0){
                    Intent intent=new Intent(getApplicationContext(),NewCategoryActivity.class);
                    startActivity(intent);
                    finish();
                }
                pos[0]=position-1;
            }
        });



        if(intentreceive.hasExtra("ExpenseID")){
            int id=intentreceive.getIntExtra("ExpenseID",-1);
            if(id!=-1){
                existing_expense=myRepository.getexpensefromid(id);
                amount.setText(""+existing_expense.getAmount());
                desc.setText(existing_expense.getDescription());
                autoCompleteTextView.setText(myRepository.getcatnamefromid(existing_expense.getCategoryId()));
                datepickerbutton.setText(existing_expense.getDateinDisplayFormat());

            }
        }
        savebutton.setOnClickListener(v -> {
            if(amount.getText().toString().isEmpty()){
                Toast.makeText(this, "Amount cannot be empty",Toast.LENGTH_LONG).show();
            }
            else {
                if(existing_expense!=null){
                    existing_expense.setAmount(Double.parseDouble(amount.getText().toString()));
                    existing_expense.setDate(getDateInDbFormat(datepickerbutton.getText().toString()));
                    existing_expense.setCategoryId(categoryArrayList.get(pos[0]).getId());
                    existing_expense.setDescription(desc.getText().toString());
                    myRepository.updateexpense(existing_expense);
                }
                else {
                    myRepository.addExpense(new Expense(Double.parseDouble(amount.getText().toString()),
                            categoryArrayList.get(pos[0]).getId(),
                            desc.getText().toString(),
                            getDateInDbFormat(datepickerbutton.getText().toString())));
                }
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(this,
                        "Added Rs: "+amount.getText().toString()+" to "+
                                myRepository.getcatnamefromid(categoryArrayList.get(pos[0]).getId()),
                        Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        });



    }

//    private String gettodaysdate() {
//        Calendar calendar=Calendar.getInstance();
//        int month=calendar.get(Calendar.MONTH);
//        int year=calendar.get(Calendar.YEAR);
//        int day=calendar.get(Calendar.DAY_OF_MONTH);
//        return DateUtil.makedatestring(day,month+1,year);
//
//    }

//    void initdatepicker(Context context,Button dp){
//        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month=month+1;
//                String date=makedatestring(dayOfMonth, month,year);
//                dp.setText(date);
//            }
//
//
//        };
//
//        Calendar calendar=Calendar.getInstance();
//        int month=calendar.get(Calendar.MONTH);
//        int year=calendar.get(Calendar.YEAR);
//        int day=calendar.get(Calendar.DAY_OF_MONTH);
//
//        datePickerDialog=new DatePickerDialog(context, AlertDialog.THEME_HOLO_DARK,dateSetListener,year,month,day);
//
//    }

//    private String makedatestring(int dayOfMonth, int month, int year) {
////        maindate = String.format("%04d-%02d-%02d", year, month, dayOfMonth);
//        return dayOfMonth + " " + getmonthname(month) + " " + year;
//    }

//    private String getmonthname(int month) {
//        switch(month){
//            case 1:return "Jan";
//            case 2:return "Feb";
//            case 3:return "Mar";
//            case 4:return "Apr";
//            case 5:return "May";
//            case 6:return "Jun";
//            case 7:return "Jul";
//            case 8:return "Aug";
//            case 9:return "Sep";
//            case 10:return "Oct";
//            case 11:return "Nov";
//            case 12:return "Dec";
//        }
//        return "Error";
//    }

    private String getDateInDbFormat(String displayDate) {
        try {
            java.text.SimpleDateFormat displayFormat = new java.text.SimpleDateFormat("d MMM yyyy", java.util.Locale.ENGLISH);
            java.text.SimpleDateFormat dbFormat = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
            java.util.Date date = displayFormat.parse(displayDate);
            return dbFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // or handle fallback
        }
    }


//    private void opendatepicker(View view){
//        datePickerDialog.show();
//    }
}