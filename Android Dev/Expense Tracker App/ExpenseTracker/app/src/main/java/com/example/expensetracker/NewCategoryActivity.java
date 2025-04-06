package com.example.expensetracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewCategoryActivity extends AppCompatActivity {

    private MyRepository myRepository;
    private EditText catname,catlimit;
    private Button addcatbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myRepository=new MyRepository(getApplication());

        catname=findViewById(R.id.newcategoryname);
        catlimit=findViewById(R.id.newcategorylimit);
        addcatbutton=findViewById(R.id.newcategorysave);

        addcatbutton.setOnClickListener(v -> {
            if(catname.getText().toString().isEmpty()){
                Toast.makeText(this,"Category name cannot be empty",Toast.LENGTH_LONG).show();
            } else if (myRepository.doescategoryexist(catname.getText().toString())) {
                Toast.makeText(this,"Category Already Exists",Toast.LENGTH_LONG).show();
            } else{
                if((catlimit.getText().toString().isEmpty())) {
                    myRepository.addCategory(new Category(catname.getText().toString()));
                    finish();
                }
                else{
                    if(Double.parseDouble(catlimit.getText().toString())>99.0) {
                        myRepository.addCategory(new Category(catname.getText().toString(),
                                Double.parseDouble(catlimit.getText().toString())));
                        finish();
                    }
                    else{
                        Toast.makeText(this,"Category limit should be more than Rs.100",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
}