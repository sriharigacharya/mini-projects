package com.example.expensetracker;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class NewRecDepActivity extends AppCompatActivity {
    MyRepository myRepository;

    ArrayList<Category> categoryArrayList;
    EditText recdepamt,recdepname;
    Button datepicker,newrecdepbutton;
    AutoCompleteTextView recdepcat;
    ArrayAdapter<String> stringArrayAdapter;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_rec_dep);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recdepamt=findViewById(R.id.newrecdepamount);
        recdepname=findViewById(R.id.newrecdepexname);
        datepicker=findViewById(R.id.newrecdepdatepicker);
        newrecdepbutton=findViewById(R.id.savenewrecdep);
        recdepcat=findViewById(R.id.newrecdepcategoryautocomplete);
        myRepository=new MyRepository(new Application());


        datePickerDialog=DateUtil.initdatepicker(this,datepicker);
        datepicker.setText(DateUtil.gettodaysdate());
        datepicker.setOnClickListener(v -> {
            DateUtil.opendatepicker(datePickerDialog);
        });

        categoryArrayList=myRepository.getAllCategories();
        String[] catlist=new String[categoryArrayList.size()+1];
        catlist[0]="Add new Category";

        for(int i=0;i<categoryArrayList.size();i++){
            catlist[i+1]=categoryArrayList.get(i).getCategoryName();
        }

        stringArrayAdapter=new ArrayAdapter<String>(this,R.layout.dropdownlistitems,catlist);
        recdepcat.setAdapter(stringArrayAdapter);
        int pos[]=new int[1];
        recdepcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        //Add on click for save beware o pos[0] for getting catid;
        newrecdepbutton.setOnClickListener(v -> {
            if (recdepname.getText().toString().isEmpty() || Double.parseDouble(recdepamt.getText().toString())<=0) {
                Toast.makeText(this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
            }else {
                myRepository.addRecurringExpense(new RecurringExpense(
                        recdepname.getText().toString(),
                        myRepository.getidofcatname(recdepcat.getText().toString()),
                        Double.parseDouble(recdepamt.getText().toString()),
                        "-",
                        DateUtil.displayToDb(datepicker.getText().toString())
                ));
                Intent intent=new Intent(this,MainActivity.class);
                intent.putExtra("Page",-1);
                startActivity(intent);
                finish();
            }

        });


    }


}