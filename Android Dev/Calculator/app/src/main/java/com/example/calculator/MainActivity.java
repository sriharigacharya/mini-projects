package com.example.calculator;
import java.util.*;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public float num1=0,num2=0,ans=0;
    public char tempch='+',op;
    public int errorcode=0,state;
    TextView txt1,txt2,txt3;
Button temp,E,C,div,mul,per,add,sub,one1,two2,three3,four4,five5,six6,seven7,eight8,nine9,zero0,eq,dott,nothing;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txt1=findViewById(R.id.store);
        txt2=findViewById(R.id.answer);
        txt3=findViewById(R.id.opera);
        E=findViewById(R.id.erase);
        C=findViewById(R.id.clear);
        div=findViewById(R.id.divide);
        mul=findViewById(R.id.multi);
        per=findViewById(R.id.percen);
        add=findViewById(R.id.plus);
        sub=findViewById(R.id.minus);
        one1=findViewById(R.id.one);
        two2=findViewById(R.id.two);
        three3=findViewById(R.id.three);
        four4=findViewById(R.id.four);
        five5=findViewById(R.id.five);
        six6=findViewById(R.id.six);
        seven7=findViewById(R.id.seven);
        eight8=findViewById(R.id.eight);
        nine9=findViewById(R.id.nine);
        zero0=findViewById(R.id.zero);
        eq=findViewById(R.id.equal);
        dott=findViewById(R.id.dot);
//        nothing=findViewById(R.id.empty);


        E.setOnClickListener(this);
        C.setOnClickListener(this);
        div.setOnClickListener(this);
        mul.setOnClickListener(this);
        per.setOnClickListener(this);
        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        one1.setOnClickListener(this);
        two2.setOnClickListener(this);
        three3.setOnClickListener(this);
        four4.setOnClickListener(this);
        five5.setOnClickListener(this);
        six6.setOnClickListener(this);
        seven7.setOnClickListener(this);
        eight8.setOnClickListener(this);
        nine9.setOnClickListener(this);
        zero0.setOnClickListener(this);
        eq.setOnClickListener(this);
        dott.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        temp=findViewById(id);
        if (id == R.id.one || id == R.id.two || id == R.id.three ||
                id == R.id.four || id == R.id.five || id == R.id.six ||
                id == R.id.seven || id == R.id.eight || id == R.id.nine || id==R.id.zero || id==R.id.dot){
            String p = new String((String) txt2.getText());
            if(id==R.id.dot){
                for(int i=0;i<p.length();i++){
                    if(p.charAt(i)=='.'){
                        Toast t=Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
                        return;
                    }
                }
            }
            if((p.length()==1&&p.charAt(0)=='0')||state==-1||p.equals("Error"))
            {
                txt2.setText(temp.getText());

                num1=0;
                num2=0;
                tempch='+';
                errorcode=0;
                state=0;
            }
            else {
                p += temp.getText();
                txt2.setText(p);
            }
        }

        else if(id==R.id.plus||id==R.id.minus||id==R.id.multi||id==R.id.divide||id==R.id.percen){
            op=temp.getText().charAt(0);
            if(txt2.getText().toString().isEmpty()){
                num2=0;
            }
            else {
                num2 = Float.parseFloat((String) txt2.getText());
            }
            calc(tempch);
            if(errorcode==-1)
            {
                txt2.setText("Error");
                return;
            }else {
                num1 = ans;
                num2 = 0;
                tempch = op;
                txt1.setText(Float.toString(ans));
                txt2.setText("");
                txt3.setText(" " + op);
            }



        } else if (id == R.id.equal) {
            if(txt2.getText().toString().isEmpty()){
                return;
            }else{
                op = temp.getText().charAt(0);
                num2 = Float.parseFloat((String) txt2.getText());
                calc(tempch);
                if(errorcode==-1)
                {
                    txt2.setText("Error");
                    ans=0;
                    return;
                }else {
                    num1 = 0;

                    tempch = '+';
                    txt2.setText(Float.toString(ans));

                    txt3.setText(" " + op);
                    state = -1;
                }
            }

        } else if (id==R.id.clear) {
            num1=0;
            tempch='+';
            txt2.setText("0");
            txt1.setText("");
            txt3.setText("");
            errorcode=0;

        } else if (id==R.id.erase) {
            String s = new String((String) txt2.getText());
            txt2.setText(s.substring(0,s.length()-1));
        }


    }

    public void calc(char c){
        switch (c) {
            case '+':
                ans = num1 + num2;
                break;

            case '-':
                ans = num1 - num2;
                break;

            case 'X':
                ans = num1 * num2;
                break;

            case '/':
                if (num2 != 0) {
                    ans = num1 / num2;
                } else {
                    errorcode=-1;
                }
                break;

            case '%':
                if (num2 != 0) {
                    ans = num1 % num2;
                } else {
                    errorcode=-1;
                }
                break;

        }
    }
}