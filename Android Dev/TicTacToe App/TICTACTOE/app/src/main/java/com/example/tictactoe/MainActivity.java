package com.example.tictactoe;

import static com.example.tictactoe.R.drawable.clickedbutton;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    TextView p1score,p2score,newgame,start,reset,toplay,tlt,tt,trt,mlt,mt,mrt,blt,bt,brt;
    EditText p1name,p2name;
    int state=0,progress=0,p1scorenum=0,p2scorenum=0;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        p1name=findViewById(R.id.player1);
        p2name=findViewById(R.id.player2);
        p1score=findViewById(R.id.player1p);
        p2score=findViewById(R.id.player2p);
        newgame=findViewById(R.id.newgbutton);
        start=findViewById(R.id.startbutton);
        reset=findViewById(R.id.resetbutton);
        toplay=findViewById(R.id.toplayplayer);
        tlt=findViewById(R.id.topl);
        tt=findViewById(R.id.top);
        trt=findViewById(R.id.topr);
        mlt=findViewById(R.id.middlel);
        mt=findViewById(R.id.middle);
        mrt=findViewById(R.id.middler);
        blt=findViewById(R.id.bottoml);
        bt=findViewById(R.id.bottom);
        brt=findViewById(R.id.bottomr);




        p1score.setOnClickListener(this);
        p2score.setOnClickListener(this);
        newgame.setOnClickListener(this);
        start.setOnClickListener(this);
        reset.setOnClickListener(this);
        toplay.setOnClickListener(this);
        tlt.setOnClickListener(this);
        tt.setOnClickListener(this);
        trt.setOnClickListener(this);
        mlt.setOnClickListener(this);
        mt.setOnClickListener(this);
        mrt.setOnClickListener(this);
        blt.setOnClickListener(this);
        bt.setOnClickListener(this);
        brt.setOnClickListener(this);

        tileplayable(false);
        reset.setClickable(false);
        showCustomToast("Enter Player Names\nPress START!");

    }

    @Override
    public void onClick(View v) {
        TextView temp=findViewById(v.getId());

        if(temp.getId()==R.id.newgbutton)
        {
            Intent intent = getIntent();
            finish();
            startActivity(intent);

        }

        else if(temp.getId()==R.id.startbutton){
            start.setClickable(false);
            p1name.setEnabled(false);
            p2name.setEnabled(false);
            reset.setClickable(true);
            start.setBackgroundResource(R.drawable.buttonwithoutstroke);
            start.setTextColor(R.color.darkgrey);
            Random random = new Random();
            state=random.nextInt(2);

            tileplayable(true);

            if(state==0)
                toplay.setText("-"+p1name.getText()+"-");
            else
                toplay.setText("-"+p2name.getText()+"-");



        }

        else if(temp.getId()==R.id.resetbutton)
        {
            tlt.setText("-");
            tt.setText("-");
            trt.setText("-");
            mlt.setText("-");
            mt.setText("-");
            mrt.setText("-");
            blt.setText("-");
            bt.setText("-");
            brt.setText("-");
            tlt.setBackgroundResource(R.drawable.tlp);
            tt.setBackgroundResource(R.drawable.tlp);
            trt.setBackgroundResource(R.drawable.tlp);
            mlt.setBackgroundResource(R.drawable.tlp);
            mt.setBackgroundResource(R.drawable.tlp);
            mrt.setBackgroundResource(R.drawable.tlp);
            blt.setBackgroundResource(R.drawable.tlp);
            bt.setBackgroundResource(R.drawable.tlp);
            brt.setBackgroundResource(R.drawable.tlp);
            tileplayable(true);
        }
        else if((temp.getId()==R.id.topl)||(temp.getId()==R.id.top)||(temp.getId()==R.id.topr)||(temp.getId()==R.id.middlel)||(temp.getId()==R.id.middle)||(temp.getId()==R.id.middler)||(temp.getId()==R.id.bottoml)||(temp.getId()==R.id.bottomr)||(temp.getId()==R.id.bottom)){
            if(temp.getText().toString().charAt(0)=='-') {

                if (state % 2 == 0) {
                    temp.setBackgroundResource(R.drawable.tileso);

                    temp.setText("O");
                }
                else {
                    temp.setBackgroundResource(R.drawable.tilesx);

                    temp.setText("X");

                }
                winchecker();
                state+=1;
                if(state%2==0) {
                    toplay.setText("-"+p1name.getText()+"-");

                }
                else {
                    toplay.setText("-"+p2name.getText()+"-");

                }
            }
            else {
                return;
            }
        }

    }


    void winchecker()
    {
        if(((tlt.getText().equals(tt.getText())&&trt.getText().equals(tt.getText()))&&(tt.getText().toString().charAt(0)!='-'))
            ||((mlt.getText().equals(mt.getText())&&mrt.getText().equals(mt.getText()))&&(mt.getText().toString().charAt(0)!='-'))
            ||((blt.getText().equals(bt.getText())&&brt.getText().equals(bt.getText()))&&(bt.getText().toString().charAt(0)!='-'))
            ||((tlt.getText().equals(mlt.getText())&&blt.getText().equals(mlt.getText()))&&(mlt.getText().toString().charAt(0)!='-'))
            ||((tt.getText().equals(mt.getText())&&bt.getText().equals(mt.getText()))&&(mt.getText().toString().charAt(0)!='-'))
            ||((brt.getText().equals(mrt.getText())&&trt.getText().equals(mrt.getText()))&&(mrt.getText().toString().charAt(0)!='-'))
            ||((tlt.getText().equals(mt.getText())&&brt.getText().equals(mt.getText()))&&(mt.getText().toString().charAt(0)!='-'))
            ||((blt.getText().equals(mt.getText())&&trt.getText().equals(mt.getText())))&&(mt.getText().toString().charAt(0)!='-'))
        {


            if(state%2==0) {
                p1scorenum+=1;
                p1score.setText("-"+Integer.toString(p1scorenum)+"-");
                showCustomToast("Hurray!!\n"+p1name.getText().toString()+" wins!!");
                //TOAST
            }
            else {
                p2scorenum+=1;
                p2score.setText("-"+Integer.toString(p2scorenum)+"-");
                showCustomToast("Hurray!!\n"+p2name.getText().toString()+" wins!!");
                //TOAST
            }
            tileplayable(false);


        }
        else if ((!(tlt.getText().toString().charAt(0)=='-'))
                &&(!(tt.getText().toString().charAt(0)=='-'))
                &&(!(trt.getText().toString().charAt(0)=='-'))
                &&(!(mlt.getText().toString().charAt(0)=='-'))
                &&(!(mt.getText().toString().charAt(0)=='-'))
                &&(!(mrt.getText().toString().charAt(0)=='-'))
                &&(!(blt.getText().toString().charAt(0)=='-'))
                &&(!(bt.getText().toString().charAt(0)=='-'))
                &&(!(brt.getText().toString().charAt(0)=='-'))){
            showCustomToast("DRAW!!");

        }



    }
    void tileplayable(boolean x)
    {
        tlt.setClickable(x);
        tt.setClickable(x);
        trt.setClickable(x);
        mlt.setClickable(x);
        mt.setClickable(x);
        mrt.setClickable(x);
        blt.setClickable(x);
        bt.setClickable(x);
        brt.setClickable(x);
    }

    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialogbox, findViewById(R.id.toast_container));

        TextView toastMessage = layout.findViewById(R.id.toast_message);
        toastMessage.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}