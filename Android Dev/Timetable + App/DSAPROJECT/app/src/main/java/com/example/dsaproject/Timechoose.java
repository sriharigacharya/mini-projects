package com.example.dsaproject;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import androidx.fragment.app.DialogFragment;

import com.github.chrisbanes.photoview.PhotoView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

class datamodel{
    int state;
    String classname,subject,teachername,starttime;
    datamodel(){
        state=-1;
        teachername="";
        classname="";
        subject="";
        starttime="";
    }
}



public class Timechoose extends AppCompatActivity {
    int option;
    int dow=0,ts=0,cats=0;
    int countoccupied;
    TextView header,dp,tp,catp,done;
    int timeslots=7,rooms=54,nodays=5;
    datamodel z[][][]=new datamodel[nodays][timeslots][rooms];//no of days,no of time slots, number of rooms


    String roomnames[]=new String[]{"CL","PL","AL1","AL2","AL3","AL4",
            "CL1","CL2","CL3","CL4","CL5","CL6","CL7","CL8",
            "IL1","IL2","IL3","IL4","IL5","IL6","IL7","IL8",
            "101","106","107","108","109",
            "201","202","203","204","205","208","209",
            "301","302","303","304","305","306","307","308","309","310",
            "401","402","403","404","405","406","407","408","409","410"};
    String[] daylist=new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday"};
    String[] tslist=new String[]{"09:00AM - 10:00AM","10:00AM - 11:00AM","11:30AM - 12:30PM",
            "12:30AM - 01:30AM","01:30PM - 02:30PM","02:30PM - 03:30PM","03:30PM - 04:30PM"};

    String[] deptlist=new String[]{"CSE DEPARTMENT","ISE DEPARTMENT"};
    String[] yearlist=new String[]{"1st-year","2nd-year"};
    String[] sectionlist=new String[]{"SECTION-A","SECTION-B","SECTION-C","SECTION-D","SECTION-E","SECTION-F"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timechoose);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        option=intent.getIntExtra("option",1);
        done=findViewById(R.id.done);
        header=findViewById(R.id.headtxttitle);
        tp=findViewById(R.id.timep);
        dp=findViewById(R.id.datep);
        catp=findViewById(R.id.catpick);


        initiater();
        dataloader();

        if(countoccupied!=0)
            done.setText(done.getText()+Integer.toString(countoccupied));


        if(option==1) {
            header.setText("Pick the Class:");
            dp.setText("1-year");
            tp.setText("CSE DEPARTMENT");
            catp.setText("SECTION-A");
        }
        else if(option==2) {
            header.setText("Pick date and time:");
            dp.setText("Monday");
            tp.setText("09:00AM - 10:00AM");
            catp.setText("Both Labs and Class");
        }
        else {
            header.setText("Pick Teacher and Class:");
            dp.setText("Pick");
            tp.setText("Pick");
            catp.setVisibility(View.INVISIBLE);
        }


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (option==1) {
                    String op;
                    op=Integer.toString(returnindex(yearlist,dp.getText().toString())+1);
                    op=op+Integer.toString(returnindex(deptlist,tp.getText().toString())+1);
                    op=op+Integer.toString(returnindex(sectionlist,catp.getText().toString())+1);
                    op="i"+op;
//                    done.setText(op);

                    showimage(getResources().getIdentifier(op,"drawable",getPackageName()));
                }
                else if(option==2) {

                    dow = returnindex(daylist, dp.getText().toString());
                    ts = returnindex(tslist, tp.getText().toString());
                    cats = catp.getText().toString().equals("Both Labs and Class") ? 0 : catp.getText().toString().equals("Classes Only") ? 1 : 2;
//                done.setText("Day="+dow+" Ts="+ts);

                    ArrayList<String> availist = checkavail(dow, ts, cats);
                    showArrayListDialog(availist);

                }
            }
        });


        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option==1)
                    showPopupMenu(v,R.menu.years,dp);
                else if(option==2)
                    showPopupMenu(v,R.menu.days,dp);

            }

        });

        tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option==1)
                    showPopupMenu(v,R.menu.dept,tp);
                else if(option==2)
                    showPopupMenu(v,R.menu.timeslot,tp);
            }
        });

        catp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option==1)
                    showPopupMenu(v,R.menu.sectionls,catp);
                else if(option==2)
                    showPopupMenu(v,R.menu.catogory,catp);
            }
        });


    }

    int returnindex(String[] list,String key){
        for(int i=0;i<list.length;i++){
            if(list[i].equals(key))
                return i;
        }
        //done.setText("return index error");
        return 0;
    }
    void initiater(){
        int i,j,k;
        for(i=0;i<nodays;i++)
            for(j=0;j<timeslots;j++)
                for(k=0;k<rooms;k++)
                    z[i][j][k]=new datamodel();
    }

    public void datadeclare(String clsnm,String sub,String tname,String daynm,String time,String roomname){
        int room=returnindex(roomnames,roomname);
        int timeslot=returnindex(tslist,time);
        int day=returnindex(daylist,daynm);
        if (z[day][timeslot][room].state==1){
            countoccupied++;
        }
        z[day][timeslot][room].state=1;
        z[day][timeslot][room].classname=clsnm;
        z[day][timeslot][room].teachername=tname;
        z[day][timeslot][room].subject=sub;
        z[day][timeslot][room].starttime=tslist[timeslot];


    }
    private void showPopupMenu(View view, int pathid, TextView temp) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(pathid, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                temp.setText(item.getTitle());
                Toast.makeText(Timechoose.this, "Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popupMenu.show();

    }



    private ArrayList<String> checkavail(int day,int timeslot,int catogory){
        ArrayList<String> availist=new ArrayList<>();
        for(int i=0;i<rooms;i++){
            if(z[day][timeslot][i].state==-1) {
                if(catogory==1&&roomnames[i].contains("L"))
                    continue;
                if(catogory==2&&(!(roomnames[i].contains("L"))))
                    continue;
                availist.add(roomnames[i]);
            }
        }
        return availist;

    }

    void showimage(int imgid){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Timetable:");
        PhotoView imageView=new PhotoView(this);
        //ImageView imageView = new ImageView(this);
        imageView.setImageResource(imgid);
        imageView.setPadding(50, 20, 50, 20);
        builder.setView(imageView);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void showArrayListDialog(ArrayList<String> arrayList) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        StringBuilder message = new StringBuilder();
        if(option==2) {
            for (String num : arrayList) {
                message.append(num).append("\n");
            }
            builder.setTitle("Room Numbers");
        }
        else if(option==3){
            for (String num : arrayList) {
                message.append(num).append("\n");
            }
            builder.setTitle("");
        }
        builder.setMessage(message.toString());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }


    private void dataloader() {
        datadeclare("2CSEA","DDCO","MS.PADMAVATHI K","Monday","09:00AM - 10:00AM","302");
        datadeclare("2CSEA","MATHS","DR.RAJU D S","Monday","10:00AM - 11:00AM","302");
        datadeclare("2CSEA","DDCO LAB","MS.PADMAVATHI K","Tuesday","09:00AM - 10:00AM","CL7");
        datadeclare("2CSEA","DDCO LAB","MS.PADMAVATHI K","Tuesday","10:00AM - 11:00AM","CL7");
        datadeclare("2CSEA","JAVA LAB","MS.CHAITRA M","Tuesday","09:00AM - 10:00AM","CL2");
        datadeclare("2CSEA","JAVA LAB","MS.CHAITRA M","Tuesday","10:00AM - 11:00AM","CL2");
        datadeclare("2CSEA","DSC LAB","MS.SMITHA B","Wednesday","09:00AM - 10:00AM","CL1");
        datadeclare("2CSEA","DSC LAB","MS.SMITHA B","Wednesday","10:00AM - 11:00AM","CL1");
        datadeclare("2CSEA","OS LAB","DR.REKHA K S","Wednesday","09:00AM - 10:00AM","CL4");
        datadeclare("2CSEA","OS LAB","DR.REKHA K S","Wednesday","10:00AM - 11:00AM","CL4");
        datadeclare("2CSEA","DSC LAB","MS.SMITHA B","Thursday","09:00AM - 10:00AM","CL1");
        datadeclare("2CSEA","DSC LAB","MS.SMITHA B","Thursday","10:00AM - 11:00AM","CL1");
        datadeclare("2CSEA","OS LAB","DR.REKHA K S","Thursday","09:00AM - 10:00AM","CL4");
        datadeclare("2CSEA","OS LAB","DR.REKHA K S","Thursday","10:00AM - 11:00AM","CL4");
        datadeclare("2CSEA","SCR","MR.G B JANARDHANA SWAMY","Friday","09:00AM - 10:00AM","302");
        datadeclare("2CSEA","DVP","MS.VEENA MOHAN","Friday","10:00AM - 11:00AM","302");
        datadeclare("2CSEA","DSC","MS.SMITHA B","Monday","11:30AM - 12:30PM","302");
        datadeclare("2CSEA","OS","DR.REKHA K S","Tuesday","11:30AM - 12:30PM","302");
        datadeclare("2CSEA","DDCO","MS.PADMAVATHI K","Wednesday","11:30AM - 12:30PM","302");
        datadeclare("2CSEA","DSC","MS.SMITHA B","Thursday","11:30AM - 12:30PM","302");
        datadeclare("2CSEA","JAVA","MS.CHAITRA M","Friday","11:30AM - 12:30PM","302");
        datadeclare("2CSEA","OS","DR.REKHA K S","Monday","01:30PM - 02:30PM","302");
        datadeclare("2CSEA","DDCO","MS.PADMAVATHI K","Tuesday","01:30PM - 02:30PM","302");
        datadeclare("2CSEA","JAVA","MS.CHAITRA M","Wednesday","01:30PM - 02:30PM","302");
        datadeclare("2CSEA","OS","DR.REKHA K S","Thursday","01:30PM - 02:30PM","302");
        datadeclare("2CSEA","MATHS","DR.RAJU D S","Friday","01:30PM - 02:30PM","302");
        datadeclare("2CSEA","MATHS","DR.RAJU D S","Tuesday","02:30PM - 03:30PM","302");
        datadeclare("2CSEA","MATHS","DR.RAJU D S","Tuesday","03:30PM - 04:30PM","302");
        datadeclare("2CSEA","MATHS","DR.RAJU D S","Wednesday","02:30PM - 03:30PM","302");
        datadeclare("2CSEA","DDCO LAB","MS.PADMAVATHI K","Thursday","02:30PM - 03:30PM","CL7");
        datadeclare("2CSEA","DDCO LAB","MS.PADMAVATHI K","Thursday","03:30PM - 04:30PM","CL7");
        datadeclare("2CSEA","JAVA LAB","MS.CHAITRA M","Thursday","02:30PM - 03:30PM","CL2");
        datadeclare("2CSEA","JAVA LAB","MS.CHAITRA M","Thursday","03:30PM - 04:30PM","CL2");
        datadeclare("2CSEA","DSC","MS.SMITHA B","Friday","02:30PM - 03:30PM","302");


        datadeclare("2CSEB","DDCO LAB","MS.VEENA MOHAN","Monday","09:00AM - 10:00AM","CL7");
        datadeclare("2CSEB","DDCO LAB","MS.VEENA MOHAN","Monday","10:00AM - 11:00AM","CL7");
        datadeclare("2CSEB","JAVA LAB","MS.SOUJANYA K V","Monday","09:00AM - 10:00AM","CL2");
        datadeclare("2CSEB","JAVA LAB","MS.SOUJANYA K V","Monday","10:00AM - 11:00AM","CL2");
        datadeclare("2CSEB","MATHS","MS.SUMITHRA K S","Tuesday","09:00AM - 10:00AM","303");
        datadeclare("2CSEB","DDCO","MS.VEENA MOHAN","Tuesday","10:00AM - 11:00AM","303");
        datadeclare("2CSEB","DDCO LAB","MS.VEENA MOHAN","Wednesday","09:00AM - 10:00AM","CL7");
        datadeclare("2CSEB","DDCO LAB","MS.VEENA MOHAN","Wednesday","10:00AM - 11:00AM","CL7");
        datadeclare("2CSEB","JAVA LAB","MS.SOUJANYA K V","Wednesday","09:00AM - 10:00AM","CL2");
        datadeclare("2CSEB","JAVA LAB","MS.SOUJANYA K V","Wednesday","10:00AM - 11:00AM","CL2");
        datadeclare("2CSEB","OS","MS.VIDYA N L","Thursday","09:00AM - 10:00AM","303");
        datadeclare("2CSEB","JAVA","MS.SOUJANYA K V","Thursday","10:00AM - 11:00AM","303");
        datadeclare("2CSEB","OS","MS.VIDYA N L","Friday","09:00AM - 10:00AM","303");
        datadeclare("2CSEB","MATHS","MS.SUMITHRA K S","Friday","10:00AM - 11:00AM","303");
        datadeclare("2CSEB","MATHS","MS.SUMITHRA K S","Monday","11:30AM - 12:30PM","303");
        datadeclare("2CSEB","OS","MS.VIDYA N L","Tuesday","11:30AM - 12:30PM","303");
        datadeclare("2CSEB","DDCO","MS.VEENA MOHAN","Wednesday","11:30AM - 12:30PM","303");
        datadeclare("2CSEB","DDCO","MS.VEENA MOHAN","Thursday","11:30AM - 12:30PM","303");
        datadeclare("2CSEB","DSC","DR.NARENDAR M","Friday","11:30AM - 12:30PM","303");
        datadeclare("2CSEB","OS LAB","MS.VIDYA N L","Monday","01:30PM - 02:30PM","CL5");
        datadeclare("2CSEB","OS LAB","MS.VIDYA N L","Monday","02:30PM - 03:30PM","CL5");
        datadeclare("2CSEB","DSC","DR.NARENDAR M","Tuesday","01:30PM - 02:30PM","303");
        datadeclare("2CSEB","DSC","DR.NARENDAR M","Wednesday","01:30PM - 02:30PM","303");
        datadeclare("2CSEB","DVP","MR.MAHESHA A M","Thursday","01:30PM - 02:30PM","303");
        datadeclare("2CSEB","SCR","MR.MAHESHA A M","Friday","01:30PM - 02:30PM","303");
        datadeclare("2CSEB","MATHS","MS.SUMITHRA K S","Tuesday","02:30PM - 03:30PM","303");
        datadeclare("2CSEB","MATHS","MS.SUMITHRA K S","Tuesday","03:30PM - 04:30PM","303");
        datadeclare("2CSEB","DSC LAB","DR.NARENDAR M","Wednesday","02:30PM - 03:30PM","AL1");
        datadeclare("2CSEB","DSC LAB","DR.NARENDAR M","Wednesday","03:30PM - 04:30PM","AL1");
        datadeclare("2CSEB","OS LAB","MS.VIDYA N L","Thursday","02:30PM - 03:30PM","CL5");
        datadeclare("2CSEB","OS LAB","MS.VIDYA N L","Thursday","03:30PM - 04:30PM","CL5");
        datadeclare("2CSEB","DSC LAB","DR.NARENDAR M","Thursday","02:30PM - 03:30PM","AL1");
        datadeclare("2CSEB","DSC LAB","DR.NARENDAR M","Thursday","03:30PM - 04:30PM","AL1");
        datadeclare("2CSEB","JAVA","MS.SOUJANYA K V","Friday","02:30PM - 03:30PM","303");


        datadeclare("2CSEC","DSC","DR.NARENDAR M","Monday","09:00AM - 10:00AM","304");
        datadeclare("2CSEC","MATHS","MR.VEERANAYAK","Monday","10:00AM - 11:00AM","304");
        datadeclare("2CSEC","MATHS","MR.VEERANAYAK","Tuesday","09:00AM - 10:00AM","304");
        datadeclare("2CSEC","DSC","DR.NARENDAR M","Tuesday","10:00AM - 11:00AM","304");
        datadeclare("2CSEC","MATHS","MR.VEERANAYAK","Wednesday","09:00AM - 10:00AM","304");
        datadeclare("2CSEC","MATHS","MR.VEERANAYAK","Wednesday","10:00AM - 11:00AM","304");
        datadeclare("2CSEC","JAVA","MS.PRIYANKA R V","Thursday","09:00AM - 10:00AM","304");
        datadeclare("2CSEC","DSC","DR.NARENDAR M","Thursday","10:00AM - 11:00AM","304");
        datadeclare("2CSEC","DDCO","DR.S LOKESH","Friday","10:00AM - 11:00AM","304");
        datadeclare("2CSEC","JAVA","MS.PRIYANKA R V","Monday","11:30AM - 12:30PM","304");
        datadeclare("2CSEC","DDCO","DR.S LOKESH","Tuesday","11:30AM - 12:30PM","108");
        datadeclare("2CSEC","OS","MR.BALAJI V","Wednesday","11:30AM - 12:30PM","107");
        datadeclare("2CSEC","MATHS","MR.VEERANAYAK","Thursday","11:30AM - 12:30PM","101");
        datadeclare("2CSEC","SCR","MRS.NITHYA M R","Friday","11:30AM - 12:30PM","304");
        datadeclare("2CSEC","OS","MR.BALAJI V","Monday","01:30PM - 02:30PM","304");
        datadeclare("2CSEC","DVP","MS.ZAIBA FARHEEN","Wednesday","01:30PM - 02:30PM","201");
        datadeclare("2CSEC","DDCO","DR.S LOKESH","Thursday","01:30PM - 02:30PM","304");
        datadeclare("2CSEC","OS","MR.BALAJI V","Friday","01:30PM - 02:30PM","304");
        datadeclare("2CSEC","DDCO LAB","DR.S LOKESH","Monday","02:30PM - 03:30PM","CL7");
        datadeclare("2CSEC","DDCO LAB","DR.S LOKESH","Monday","03:30PM - 04:30PM","CL7");
        datadeclare("2CSEC","JAVA LAB","MS.PRIYANKA R V","Monday","02:30PM - 03:30PM","CL2");
        datadeclare("2CSEC","JAVA LAB","MS.PRIYANKA R V","Monday","03:30PM - 04:30PM","CL2");
        datadeclare("2CSEC","DDCO LAB","DR.S LOKESH","Wednesday","02:30PM - 03:30PM","CL7");
        datadeclare("2CSEC","DDCO LAB","DR.S LOKESH","Wednesday","03:30PM - 04:30PM","CL7");
        datadeclare("2CSEC","JAVA LAB","MS.PRIYANKA R V","Wednesday","02:30PM - 03:30PM","CL2");
        datadeclare("2CSEC","JAVA LAB","MS.PRIYANKA R V","Wednesday","03:30PM - 04:30PM","CL2");
        datadeclare("2CSEC","OS LAB","MR.BALAJI V","Thursday","02:30PM - 03:30PM","CL4");
        datadeclare("2CSEC","OS LAB","MR.BALAJI V","Thursday","03:30PM - 04:30PM","CL4");
        datadeclare("2CSEC","DSC LAB","DR.NARENDAR M","Thursday","02:30PM - 03:30PM","CL1");
        datadeclare("2CSEC","DSC LAB","DR.NARENDAR M","Thursday","03:30PM - 04:30PM","CL1");
        datadeclare("2CSEC","OS LAB","MR.BALAJI V","Friday","02:30PM - 03:30PM","CL4");
        datadeclare("2CSEC","OS LAB","MR.BALAJI V","Friday","03:30PM - 04:30PM","CL4");
        datadeclare("2CSEC","DSC LAB","DR.NARENDAR M","Friday","02:30PM - 03:30PM","CL1");
        datadeclare("2CSEC","DSC LAB","DR.NARENDAR M","Friday","03:30PM - 04:30PM","CL1");


        datadeclare("2CSED","OS LAB","ZAIBA FARHEEN","Monday","09:00AM - 10:00AM","CL4");
        datadeclare("2CSED","OS LAB","ZAIBA FARHEEN","Monday","10:00AM - 11:00AM","CL4");
        datadeclare("2CSED","DSC LAB","NITHYA M R","Monday","09:00AM - 10:00AM","CL1");
        datadeclare("2CSED","DSC LAB","NITHYA M R","Monday","10:00AM - 11:00AM","CL1");
        datadeclare("2CSED","OS LAB","ZAIBA FARHEEN","Tuesday","09:00AM - 10:00AM","CL4");
        datadeclare("2CSED","OS LAB","ZAIBA FARHEEN","Tuesday","10:00AM - 11:00AM","CL4");
        datadeclare("2CSED","DSC LAB","NITHYA M R","Tuesday","09:00AM - 10:00AM","CL1");
        datadeclare("2CSED","DSC LAB","NITHYA M R","Tuesday","10:00AM - 11:00AM","CL1");
        datadeclare("2CSED","JAVA","JYOTHISHREE","Wednesday","10:00AM - 11:00AM","308");
        datadeclare("2CSED","SCR","G B JANARDHANA SWAMY","Thursday","09:00AM - 10:00AM","308");
        datadeclare("2CSED","MATHS","TEJAS","Thursday","10:00AM - 11:00AM","308");
        datadeclare("2CSED","DDCO LAB","PADMAVATHI K","Friday","09:00AM - 10:00AM","CL7");
        datadeclare("2CSED","DDCO LAB","PADMAVATHI K","Friday","10:00AM - 11:00AM","CL7");
        datadeclare("2CSED","JAVA LAB","JYOTHISHREE","Friday","09:00AM - 10:00AM","CL2");
        datadeclare("2CSED","JAVA LAB","JYOTHISHREE","Friday","10:00AM - 11:00AM","CL2");
        datadeclare("2CSED","DSC","NITHYA M R","Monday","11:30AM - 12:30PM","308");
        datadeclare("2CSED","MATHS","TEJAS","Tuesday","11:30AM - 12:30PM","308");
        datadeclare("2CSED","OS","ZAIBA FARHEEN","Wednesday","11:30AM - 12:30PM","308");
        datadeclare("2CSED","DDCO","PADMAVATHI K","Thursday","11:30AM - 12:30PM","308");
        datadeclare("2CSED","DDCO","PADMAVATHI K","Friday","11:30AM - 12:30PM","308");
        datadeclare("2CSED","JAVA","JYOTHISHREE","Monday","01:30PM - 02:30PM","303");
        datadeclare("2CSED","DSC","NITHYA M R","Tuesday","01:30PM - 02:30PM","304");
        datadeclare("2CSED","MATHS","TEJAS","Wednesday","01:30PM - 02:30PM","308");
        datadeclare("2CSED","OS","ZAIBA FARHEEN","Friday","01:30PM - 02:30PM","308");
        datadeclare("2CSED","OS","ZAIBA FARHEEN","Monday","02:30PM - 03:30PM","303");
        datadeclare("2CSED","DVP","DIVYASHREE R","Monday","03:30PM - 04:30PM","303");
        datadeclare("2CSED","DDCO LAB","PADMAVATHI K","Tuesday","02:30PM - 03:30PM","CL7");
        datadeclare("2CSED","DDCO LAB","PADMAVATHI K","Tuesday","03:30PM - 04:30PM","CL7");
        datadeclare("2CSED","JAVA LAB","JYOTHISHREE","Tuesday","02:30PM - 03:30PM","CL2");
        datadeclare("2CSED","JAVA LAB","JYOTHISHREE","Tuesday","03:30PM - 04:30PM","CL2");
        datadeclare("2CSED","DDCO","PADMAVATHI K","Wednesday","02:30PM - 03:30PM","308");
        datadeclare("2CSED","DSC","NITHYA M R","Wednesday","03:30PM - 04:30PM","308");
        datadeclare("2CSED","MATHS","TEJAS","Friday","02:30PM - 03:30PM","308");
        datadeclare("2CSED","MATHS","TEJAS","Friday","03:30PM - 04:30PM","308");



        datadeclare("2CSEE","OS","BALAJI V","Monday","09:00AM - 10:00AM","308");
        datadeclare("2CSEE","DSC","NAVEEN S PAGAD","Monday","10:00AM - 11:00AM","308");
        datadeclare("2CSEE","DSC","NAVEEN S PAGAD","Tuesday","09:00AM - 10:00AM","302");
        datadeclare("2CSEE","DDCO","S LOKESH","Tuesday","10:00AM - 11:00AM","302");
        datadeclare("2CSEE","OS","BALAJI V","Wednesday","09:00AM - 10:00AM","302");
        datadeclare("2CSEE","JAVA","CHAITRA M","Wednesday","10:00AM - 11:00AM","302");
        datadeclare("2CSEE","DDCO LAB","S LOKESH","Thursday","09:00AM - 10:00AM","CL7");
        datadeclare("2CSEE","DDCO LAB","S LOKESH","Thursday","10:00AM - 11:00AM","CL7");
        datadeclare("2CSEE","JAVA LAB","CHAITRA M","Thursday","09:00AM - 10:00AM","CL2");
        datadeclare("2CSEE","JAVA LAB","CHAITRA M","Thursday","10:00AM - 11:00AM","CL2");
        datadeclare("2CSEE","OS LAB","BALAJI V","Friday","09:00AM - 10:00AM","CL4");
        datadeclare("2CSEE","OS LAB","BALAJI V","Friday","10:00AM - 11:00AM","CL4");
        datadeclare("2CSEE","DSC LAB","NAVEEN S PAGAD","Friday","09:00AM - 10:00AM","CL1");
        datadeclare("2CSEE","DSC LAB","NAVEEN S PAGAD","Friday","10:00AM - 11:00AM","CL1");
        datadeclare("2CSEE","DDCO","S LOKESH","Monday","11:30AM - 12:30PM","309");
        datadeclare("2CSEE","OS","BALAJI V","Tuesday","11:30AM - 12:30PM","309");
        datadeclare("2CSEE","DVP","VISHNUKANTH","Wednesday","11:30AM - 12:30PM","309");
        datadeclare("2CSEE","JAVA","CHAITRA M","Thursday","11:30AM - 12:30PM","309");
        datadeclare("2CSEE","DSC","NAVEEN S PAGAD","Friday","11:30AM - 12:30PM","309");
        datadeclare("2CSEE","MATHS","PRATHIBA L","Monday","01:30PM - 02:30PM","309");
        datadeclare("2CSEE","MATHS","PRATHIBA L","Tuesday","01:30PM - 02:30PM","309");
        datadeclare("2CSEE","MATHS","PRATHIBA L","Wednesday","01:30PM - 02:30PM","309");
        datadeclare("2CSEE","MATHS","PRATHIBA L","Friday","01:30PM - 02:30PM","309");
        datadeclare("2CSEE","MATHS","PRATHIBA L","Monday","02:30PM - 03:30PM","309");
        datadeclare("2CSEE","SCR","MAHESHA A M","Monday","03:30PM - 04:30PM","309");
        datadeclare("2CSEE","OS LAB","BALAJI V","Tuesday","02:30PM - 03:30PM","CL4");
        datadeclare("2CSEE","OS LAB","BALAJI V","Tuesday","03:30PM - 04:30PM","CL4");
        datadeclare("2CSEE","DSC LAB","NAVEEN S PAGAD","Tuesday","02:30PM - 03:30PM","CL1");
        datadeclare("2CSEE","DSC LAB","NAVEEN S PAGAD","Tuesday","03:30PM - 04:30PM","CL1");
        datadeclare("2CSEE","DDCO","S LOKESH","Wednesday","02:30PM - 03:30PM","309");
        datadeclare("2CSEE","DDCO LAB","S LOKESH","Friday","02:30PM - 03:30PM","CL7");
        datadeclare("2CSEE","DDCO LAB","S LOKESH","Friday","03:30PM - 04:30PM","CL7");
        datadeclare("2CSEE","JAVA LAB","CHAITRA M","Friday","02:30PM - 03:30PM","CL2");
        datadeclare("2CSEE","JAVA LAB","CHAITRA M","Friday","03:30PM - 04:30PM","CL2");



        datadeclare("2CSEF","DSC","SMITHA B","Monday","09:00AM - 10:00AM","309");
        datadeclare("2CSEF","DDCO","RASHMI M R","Monday","10:00AM - 11:00AM","309");
        datadeclare("2CSEF","MATHS","DARSHAN N S","Tuesday","09:00AM - 10:00AM","309");
        datadeclare("2CSEF","JAVA","PRIYANKA R V","Tuesday","10:00AM - 11:00AM","309");
        datadeclare("2CSEF","MATHS","DARSHAN N S","Wednesday","09:00AM - 10:00AM","309");
        datadeclare("2CSEF","MATHS","DARSHAN N S","Wednesday","10:00AM - 11:00AM","309");
        datadeclare("2CSEF","MATHS","DARSHAN N S","Thursday","09:00AM - 10:00AM","309");
        datadeclare("2CSEF","DSC","SMITHA B","Thursday","10:00AM - 11:00AM","309");
        datadeclare("2CSEF","DDCO LAB","RASHMI M R","Friday","09:00AM - 10:00AM","IL8");
        datadeclare("2CSEF","DDCO LAB","RASHMI M R","Friday","10:00AM - 11:00AM","IL8");
        datadeclare("2CSEF","JAVA LAB","PRIYANKA R V","Friday","09:00AM - 10:00AM","AL1");
        datadeclare("2CSEF","JAVA LAB","PRIYANKA R V","Friday","10:00AM - 11:00AM","AL1");
        datadeclare("2CSEF","OS","JAYASRI B S","Monday","11:30AM - 12:30PM","402");
        datadeclare("2CSEF","DDCO","RASHMI M R","Tuesday","11:30AM - 12:30PM","304");
        datadeclare("2CSEF","OS","JAYASRI B S","Wednesday","11:30AM - 12:30PM","304");
        datadeclare("2CSEF","JAVA","PRIYANKA R V","Thursday","11:30AM - 12:30PM","304");
        datadeclare("2CSEF","MATHS","DARSHAN N S","Friday","11:30AM - 12:30PM","204");
        datadeclare("2CSEF","DVP","MOHAMMAD ADNAN","Monday","01:30PM - 02:30PM","308");
        datadeclare("2CSEF","OS","JAYASRI B S","Tuesday","01:30PM - 02:30PM","308");
        datadeclare("2CSEF","DSC","SMITHA B","Wednesday","01:30PM - 02:30PM","304");
        datadeclare("2CSEF","DDCO","RASHMI M R","Thursday","01:30PM - 02:30PM","309");
        datadeclare("2CSEF","SCR","PUSHPALATHA","Friday","01:30PM - 02:30PM","204");
        datadeclare("2CSEF","OS LAB","JAYASRI B S","Monday","02:30PM - 03:30PM","CL4");
        datadeclare("2CSEF","OS LAB","JAYASRI B S","Monday","03:30PM - 04:30PM","CL4");
        datadeclare("2CSEF","DSC LAB","SMITHA B","Monday","02:30PM - 03:30PM","CL1");
        datadeclare("2CSEF","DSC LAB","SMITHA B","Monday","03:30PM - 04:30PM","CL1");
        datadeclare("2CSEF","OS LAB","JAYASRI B S","Wednesday","02:30PM - 03:30PM","CL4");
        datadeclare("2CSEF","OS LAB","JAYASRI B S","Wednesday","03:30PM - 04:30PM","CL4");
        datadeclare("2CSEF","DSC LAB","SMITHA B","Wednesday","02:30PM - 03:30PM","CL1");
        datadeclare("2CSEF","DSC LAB","SMITHA B","Wednesday","03:30PM - 04:30PM","CL1");
        datadeclare("2CSEF","DDCO LAB","RASHMI M R","Friday","02:30PM - 03:30PM","IL8");
        datadeclare("2CSEF","DDCO LAB","RASHMI M R","Friday","03:30PM - 04:30PM","IL8");
        datadeclare("2CSEF","JAVA LAB","PRIYANKA R V","Friday","02:30PM - 03:30PM","AL1");
        datadeclare("2CSEF","JAVA LAB","PRIYANKA R V","Friday","03:30PM - 04:30PM","AL1");



        datadeclare("2ISEA","DDCO LAB","LAMMIYA HUDA","Monday","09:00AM - 10:00AM","IL8");
        datadeclare("2ISEA","DDCO LAB","LAMMIYA HUDA","Monday","10:00AM - 11:00AM","IL8");
        datadeclare("2ISEA","OS LAB","SUHAS B R","Monday","09:00AM - 10:00AM","IL7");
        datadeclare("2ISEA","OS LAB","SUHAS B R","Monday","10:00AM - 11:00AM","IL7");
        datadeclare("2ISEA","DSC LAB","MANASA K B","Tuesday","09:00AM - 10:00AM","IL2");
        datadeclare("2ISEA","DSC LAB","MANASA K B","Tuesday","10:00AM - 11:00AM","IL2");
        datadeclare("2ISEA","JAVA LAB","SUHAAS K P","Tuesday","09:00AM - 10:00AM","IL6");
        datadeclare("2ISEA","JAVA LAB","SUHAAS K P","Tuesday","10:00AM - 11:00AM","IL6");
        datadeclare("2ISEA","SCR","LAMMIYA HUDA","Wednesday","09:00AM - 10:00AM","305");
        datadeclare("2ISEA","DVP","B M NANDINI","Wednesday","10:00AM - 11:00AM","305");
        datadeclare("2ISEA","DSC","MANASA K B","Thursday","09:00AM - 10:00AM","305");
        datadeclare("2ISEA","DDCO","LAMMIYA HUDA","Thursday","10:00AM - 11:00AM","305");
        datadeclare("2ISEA","MATHS","VEERANAYAK","Friday","09:00AM - 10:00AM","305");
        datadeclare("2ISEA","MATHS","VEERANAYAK","Friday","10:00AM - 11:00AM","305");
        datadeclare("2ISEA","OS","SUHAS B R","Monday","11:30AM - 12:30PM","305");
        datadeclare("2ISEA","MATHS","VEERANAYAK","Tuesday","11:30AM - 12:30PM","305");
        datadeclare("2ISEA","OS","SUHAS B R","Wednesday","11:30AM - 12:30PM","305");
        datadeclare("2ISEA","OS","SUHAS B R","Thursday","11:30AM - 12:30PM","305");
        datadeclare("2ISEA","DDCO","LAMMIYA HUDA","Friday","11:30AM - 12:30PM","305");
        datadeclare("2ISEA","DSC","MANASA K B","Monday","01:30PM - 02:30PM","305");
        datadeclare("2ISEA","JAVA","SUHAAS K P","Thursday","01:30PM - 02:30PM","305");
        datadeclare("2ISEA","MATHS","VEERANAYAK","Friday","01:30PM - 02:30PM","305");
        datadeclare("2ISEA","DDCO LAB","LAMMIYA HUDA","Monday","02:30PM - 03:30PM","IL8");
        datadeclare("2ISEA","DDCO LAB","LAMMIYA HUDA","Monday","03:30PM - 04:30PM","IL8");
        datadeclare("2ISEA","OS LAB","SUHAS B R","Monday","02:30PM - 03:30PM","IL7");
        datadeclare("2ISEA","OS LAB","SUHAS B R","Monday","03:30PM - 04:30PM","IL7");
        datadeclare("2ISEA","DSC LAB","MANASA K B","Tuesday","02:30PM - 03:30PM","IL2");
        datadeclare("2ISEA","DSC LAB","MANASA K B","Tuesday","03:30PM - 04:30PM","IL2");
        datadeclare("2ISEA","JAVA LAB","SUHAAS K P","Tuesday","02:30PM - 03:30PM","IL4");
        datadeclare("2ISEA","JAVA LAB","SUHAAS K P","Tuesday","03:30PM - 04:30PM","IL4");
        datadeclare("2ISEA","DDCO","LAMMIYA HUDA","Wednesday","02:30PM - 03:30PM","305");
        datadeclare("2ISEA","MATHS","VEERANAYAK","Wednesday","03:30PM - 04:30PM","305");
        datadeclare("2ISEA","JAVA","SUHAAS K P","Thursday","02:30PM - 03:30PM","305");
        datadeclare("2ISEA","DSC","MANASA K B","Friday","02:30PM - 03:30PM","305");



        datadeclare("2ISEB","DDCO","PRATHIBA B S","Monday","09:00AM - 10:00AM","306");
        datadeclare("2ISEB","OS","H P GIRISH","Monday","10:00AM - 11:00AM","306");
        datadeclare("2ISEB","DDCO LAB","PRATHIBA B S","Tuesday","09:00AM - 10:00AM","IL8");
        datadeclare("2ISEB","DDCO LAB","PRATHIBA B S","Tuesday","10:00AM - 11:00AM","IL8");
        datadeclare("2ISEB","OS LAB","H P GIRISH","Tuesday","09:00AM - 10:00AM","IL7");
        datadeclare("2ISEB","OS LAB","H P GIRISH","Tuesday","10:00AM - 11:00AM","IL7");
        datadeclare("2ISEB","MATHS","TEJAS","Wednesday","09:00AM - 10:00AM","306");
        datadeclare("2ISEB","DSC","P DEVAKI","Wednesday","10:00AM - 11:00AM","306");
        datadeclare("2ISEB","JAVA","PRADEEP KUMAR H S","Thursday","09:00AM - 10:00AM","306");
        datadeclare("2ISEB","DSC","P DEVAKI","Thursday","10:00AM - 11:00AM","306");
        datadeclare("2ISEB","DSC LAB","P DEVAKI","Friday","09:00AM - 10:00AM","IL2");
        datadeclare("2ISEB","DSC LAB","P DEVAKI","Friday","10:00AM - 11:00AM","IL2");
        datadeclare("2ISEB","JAVA LAB","PRADEEP KUMAR H S","Friday","09:00AM - 10:00AM","IL6");
        datadeclare("2ISEB","JAVA LAB","PRADEEP KUMAR H S","Friday","10:00AM - 11:00AM","IL6");
        datadeclare("2ISEB","DDCO","PRATHIBA B S","Monday","11:30AM - 12:30PM","306");
        datadeclare("2ISEB","SCR","MEGHANA N R","Wednesday","11:30AM - 12:30PM","306");
        datadeclare("2ISEB","OS","H P GIRISH","Thursday","11:30AM - 12:30PM","306");
        datadeclare("2ISEB","MATHS","TEJAS","Monday","01:30PM - 02:30PM","306");
        datadeclare("2ISEB","DVP","B M NANDINI","Wednesday","01:30PM - 02:30PM","306");
        datadeclare("2ISEB","MATHS","TEJAS","Thursday","01:30PM - 02:30PM","306");
        datadeclare("2ISEB","DSC","P DEVAKI","Monday","02:30PM - 03:30PM","306");
        datadeclare("2ISEB","JAVA","PRADEEP KUMAR H S","Monday","03:30PM - 04:30PM","306");
        datadeclare("2ISEB","MATHS","TEJAS","Tuesday","02:30PM - 03:30PM","306");
        datadeclare("2ISEB","MATHS","TEJAS","Tuesday","03:30PM - 04:30PM","306");
        datadeclare("2ISEB","DDCO","PRATHIBA B S","Wednesday","02:30PM - 03:30PM","306");
        datadeclare("2ISEB","OS","H P GIRISH","Wednesday","03:30PM - 04:30PM","306");
        datadeclare("2ISEB","DDCO LAB","PRATHIBA B S","Thursday","02:30PM - 03:30PM","IL8");
        datadeclare("2ISEB","DDCO LAB","PRATHIBA B S","Thursday","03:30PM - 04:30PM","IL8");
        datadeclare("2ISEB","OS LAB","H P GIRISH","Thursday","02:30PM - 03:30PM","IL7");
        datadeclare("2ISEB","OS LAB","H P GIRISH","Thursday","03:30PM - 04:30PM","IL7");
        datadeclare("2ISEB","DSC LAB","P DEVAKI","Friday","02:30PM - 03:30PM","IL2");
        datadeclare("2ISEB","DSC LAB","P DEVAKI","Friday","03:30PM - 04:30PM","IL2");
        datadeclare("2ISEB","JAVA LAB","PRADEEP KUMAR H S","Friday","02:30PM - 03:30PM","IL4");
        datadeclare("2ISEB","JAVA LAB","PRADEEP KUMAR H S","Friday","03:30PM - 04:30PM","IL4");



        datadeclare("2ISEC","DDCO","LAMMIYA HUDA","Monday","09:00AM - 10:00AM","307");
        datadeclare("2ISEC","OS","N RAJESH","Monday","10:00AM - 11:00AM","307");
        datadeclare("2ISEC","DSC","MANASA K B","Tuesday","09:00AM - 10:00AM","307");
        datadeclare("2ISEC","OS","N RAJESH","Tuesday","10:00AM - 11:00AM","307");
        datadeclare("2ISEC","DDCO LAB","LAMMIYA HUDA","Wednesday","09:00AM - 10:00AM","IL8");
        datadeclare("2ISEC","DDCO LAB","LAMMIYA HUDA","Wednesday","10:00AM - 11:00AM","IL8");
        datadeclare("2ISEC","OS LAB","N RAJESH","Wednesday","09:00AM - 10:00AM","IL7");
        datadeclare("2ISEC","OS LAB","N RAJESH","Wednesday","10:00AM - 11:00AM","IL7");
        datadeclare("2ISEC","DSC LAB","MANASA K B","Thursday","09:00AM - 10:00AM","IL2");
        datadeclare("2ISEC","DSC LAB","MANASA K B","Thursday","10:00AM - 11:00AM","IL2");
        datadeclare("2ISEC","JAVA LAB","SUHAAS K P","Thursday","09:00AM - 10:00AM","IL6");
        datadeclare("2ISEC","JAVA LAB","SUHAAS K P","Thursday","10:00AM - 11:00AM","IL6");
        datadeclare("2ISEC","DDCO","LAMMIYA HUDA","Friday","09:00AM - 10:00AM","307");
        datadeclare("2ISEC","MATHS","DARSHAN N S","Friday","10:00AM - 11:00AM","307");
        datadeclare("2ISEC","DSC","MANASA K B","Monday","11:30AM - 12:30PM","307");
        datadeclare("2ISEC","MATHS","DARSHAN N S","Tuesday","11:30AM - 12:30PM","307");
        datadeclare("2ISEC","OS","N RAJESH","Wednesday","11:30AM - 12:30PM","307");
        datadeclare("2ISEC","DSC","MANASA K B","Thursday","11:30AM - 12:30PM","307");
        datadeclare("2ISEC","JAVA","SUHAAS K P","Monday","01:30PM - 02:30PM","307");
        datadeclare("2ISEC","JAVA","SUHAAS K P","Tuesday","01:30PM - 02:30PM","307");
        datadeclare("2ISEC","MATHS","DARSHAN N S","Wednesday","01:30PM - 02:30PM","307");
        datadeclare("2ISEC","DDCO","LAMMIYA HUDA","Thursday","01:30PM - 02:30PM","307");
        datadeclare("2ISEC","SCR","MANASA U","Monday","02:30PM - 03:30PM","307");
        datadeclare("2ISEC","DVP","PADMA M T","Monday","03:30PM - 04:30PM","307");
        datadeclare("2ISEC","MATHS","DARSHAN N S","Tuesday","02:30PM - 03:30PM","307");
        datadeclare("2ISEC","MATHS","DARSHAN N S","Tuesday","03:30PM - 04:30PM","307");
        datadeclare("2ISEC","DDCO LAB","LAMMIYA HUDA","Wednesday","02:30PM - 03:30PM","IL8");
        datadeclare("2ISEC","DDCO LAB","LAMMIYA HUDA","Wednesday","03:30PM - 04:30PM","IL8");
        datadeclare("2ISEC","OS LAB","N RAJESH","Wednesday","02:30PM - 03:30PM","IL7");
        datadeclare("2ISEC","OS LAB","N RAJESH","Wednesday","03:30PM - 04:30PM","IL7");
        datadeclare("2ISEC","DSC LAB","MANASA K B","Thursday","02:30PM - 03:30PM","IL2");
        datadeclare("2ISEC","DSC LAB","MANASA K B","Thursday","03:30PM - 04:30PM","IL2");
        datadeclare("2ISEC","JAVA LAB","SUHAAS K P","Thursday","02:30PM - 03:30PM","IL4");
        datadeclare("2ISEC","JAVA LAB","SUHAAS K P","Thursday","03:30PM - 04:30PM","IL4");



        datadeclare("2ISED","MATHS","SHIVARAJ KUMAR","Monday","09:00AM - 10:00AM","305");
        datadeclare("2ISED","MATHS","SHIVARAJ KUMAR","Monday","10:00AM - 11:00AM","305");
        datadeclare("2ISED","MATHS","SHIVARAJ KUMAR","Tuesday","09:00AM - 10:00AM","306");
        datadeclare("2ISED","DSC","P DEVAKI","Tuesday","10:00AM - 11:00AM","306");
        datadeclare("2ISED","DSC LAB","P DEVAKI","Wednesday","09:00AM - 10:00AM","IL2");
        datadeclare("2ISED","DSC LAB","P DEVAKI","Wednesday","10:00AM - 11:00AM","IL2");
        datadeclare("2ISED","JAVA LAB","PRADEEP KUMAR H S","Wednesday","09:00AM - 10:00AM","IL6");
        datadeclare("2ISED","JAVA LAB","PRADEEP KUMAR H S","Wednesday","10:00AM - 11:00AM","IL6");
        datadeclare("2ISED","DDCO LAB","SHALPARNI","Thursday","09:00AM - 10:00AM","IL8");
        datadeclare("2ISED","DDCO LAB","SHALPARNI","Thursday","10:00AM - 11:00AM","IL8");
        datadeclare("2ISED","OS LAB","PRADEEP KUMAR H S","Thursday","09:00AM - 10:00AM","IL7");
        datadeclare("2ISED","OS LAB","PRADEEP KUMAR H S","Thursday","10:00AM - 11:00AM","IL7");
        datadeclare("2ISED","DDCO","SHALPARNI","Friday","09:00AM - 10:00AM","306");
        datadeclare("2ISED","OS","N RAJESH","Friday","10:00AM - 11:00AM","306");
        datadeclare("2ISED","JAVA","PRADEEP KUMAR H S","Tuesday","11:30AM - 12:30PM","306");
        datadeclare("2ISED","MATHS","SHIVARAJ KUMAR","Wednesday","11:30AM - 12:30PM","IL5");
        datadeclare("2ISED","DSC","P DEVAKI","Friday","11:30AM - 12:30PM","306");
        datadeclare("2ISED","OS","N RAJESH","Tuesday","01:30PM - 02:30PM","306");
        datadeclare("2ISED","DSC","P DEVAKI","Wednesday","01:30PM - 02:30PM","IL5");
        datadeclare("2ISED","DDCO","SHALPARNI","Friday","01:30PM - 02:30PM","306");
        datadeclare("2ISED","OS","N RAJESH","Monday","02:30PM - 03:30PM","305");
        datadeclare("2ISED","MATHS","SHIVARAJ KUMAR","Monday","03:30PM - 04:30PM","305");
        datadeclare("2ISED","DDCO LAB","SHALPARNI","Tuesday","02:30PM - 03:30PM","IL8");
        datadeclare("2ISED","DDCO LAB","SHALPARNI","Tuesday","03:30PM - 04:30PM","IL8");
        datadeclare("2ISED","OS LAB","PRADEEP KUMAR H S","Tuesday","02:30PM - 03:30PM","IL7");
        datadeclare("2ISED","OS LAB","PRADEEP KUMAR H S","Tuesday","03:30PM - 04:30PM","IL7");
        datadeclare("2ISED","DSC LAB","P DEVAKI","Wednesday","02:30PM - 03:30PM","IL2");
        datadeclare("2ISED","DSC LAB","P DEVAKI","Wednesday","03:30PM - 04:30PM","IL2");
        datadeclare("2ISED","JAVA LAB","PRADEEP KUMAR H S","Wednesday","02:30PM - 03:30PM","IL4");
        datadeclare("2ISED","JAVA LAB","PRADEEP KUMAR H S","Wednesday","03:30PM - 04:30PM","IL4");
        datadeclare("2ISED","DDCO","SHALPARNI","Thursday","02:30PM - 03:30PM","307");
        datadeclare("2ISED","JAVA","PRADEEP KUMAR H S","Thursday","03:30PM - 04:30PM","307");
        datadeclare("2ISED","SCR","MANASA U","Friday","02:30PM - 03:30PM","306");
        datadeclare("2ISED","DVP","PADMA M T","Friday","03:30PM - 04:30PM","306");

/**

        datadeclare("1CSEA","ELECTRONICS","LOKESH M","Monday","09:00AM - 10:00AM","");
        datadeclare("1CSEA","MECHANICAL","MAHESH M","Monday","09:00AM - 10:00AM","201");
        datadeclare("1CSEA","MATHS","PRATHIBA L","Monday","10:00AM - 11:00AM","201");
        datadeclare("1CSEA","PHY LAB","NALINI K B","Monday","11:30AM - 12:30PM","PL");
        datadeclare("1CSEA","PHY LAB","NALINI K B","Monday","12:30AM - 01:30AM","PL");
        //datadeclare("1CSEA","PPC LAB","SUMANA K M","Monday","11:30AM - 12:30PM","");
        //datadeclare("1CSEA","PPC LAB","SUMANA K M","Monday","12:30AM - 01:30AM","");
        datadeclare("1CSEA","PHY ","NALINI K B","Monday","02:30PM - 03:30PM","201");
        datadeclare("1CSEA","CYBER SECURITY","LAVANYA K B","Tuesday","09:00AM - 10:00AM","201");
//        datadeclare("1CSEA","ELECTRONICS","LOKESH M","Tuesday","10:00AM - 11:00AM","");
        datadeclare("1CSEA","MECHANICAL","MAHESH M","Tuesday","10:00AM - 11:00AM","201");
        datadeclare("1CSEA","PPC  ","SUMANA K M","Tuesday","11:30AM - 12:30PM","201");
        datadeclare("1CSEA","SK","MADHU M S","Tuesday","12:30AM - 01:30AM","201");
        datadeclare("1CSEA","CE","SANTOSH B K","Tuesday","02:30PM - 03:30PM","201");
        datadeclare("1CSEA","PHY","NALINI K B","Wednesday","09:00AM - 10:00AM","201");
        datadeclare("1CSEA","PHY","NALINI K B","Wednesday","10:00AM - 11:00AM","201");
        datadeclare("1CSEA","MATHS","PRATHIBA L","Wednesday","11:30AM - 12:30PM","201");
        //datadeclare("1CSEA","SKILL LAB","","Wednesday","02:30PM - 03:30PM","");
        //datadeclare("1CSEA","SKILL LAB","","Wednesday","03:30PM - 04:30PM","");
        datadeclare("1CSEA","MATHS","PRATHIBA L","Thursday","09:00AM - 10:00AM","201");
        datadeclare("1CSEA","MATHS","PRATHIBA L","Thursday","10:00AM - 11:00AM","201");
        datadeclare("1CSEA","PHY LAB","NALINI K B","Thursday","11:30AM - 12:30PM","PL");
        datadeclare("1CSEA","PHY LAB","NALINI K B","Thursday","12:30AM - 01:30AM","PL");
//        datadeclare("1CSEA","PPC LAB","SUMANA K M","Thursday","11:30AM - 12:30PM","");
//        datadeclare("1CSEA","PPC LAB","SUMANA K M","Thursday","12:30AM - 01:30AM","");
        datadeclare("1CSEA","PPC","SUMANA K M","Thursday","02:30PM - 03:30PM","201");
        datadeclare("1CSEA","CYBER SECURITY","LAVANYA K B","Thursday","03:30PM - 04:30PM","201");
//        datadeclare("1CSEA","ELECTRONICS","LOKESH M","Friday","09:00AM - 10:00AM","");
        datadeclare("1CSEA","MECHANICAL","MAHESH M","Friday","09:00AM - 10:00AM","201");
        datadeclare("1CSEA","MATHS","PRATHIBA L","Friday","10:00AM - 11:00AM","201");
        datadeclare("1CSEA","PHY","NALINI K B","Friday","11:30AM - 12:30PM","201");
        datadeclare("1CSEA","CYBER SECURITY","LAVANYA K B","Friday","12:30AM - 01:30AM","201");
        datadeclare("1CSEA","BK","MADHU M S","Friday","02:30PM - 03:30PM","201");
        datadeclare("1CSEA","IDT","S N PRASAD","Friday","03:30PM - 04:30PM","201");
        datadeclare("1CSEB","ELECTRONICS","LOKESH M","Monday","09:00AM - 10:00AM","202");
//        datadeclare("1CSEB","MECHANICAL","MAHESH M","Monday","09:00AM - 10:00AM","");
        datadeclare("1CSEB","IDT","VAIBHAV SANJAY DESPHANDE","Monday","10:00AM - 11:00AM","202");
        datadeclare("1CSEB","CYBER SECURITY","MAHE MUBEEN AKTHAR","Monday","11:30AM - 12:30PM","202");
//        datadeclare("1CSEB","SKILL LAB","","Monday","02:30PM - 03:30PM","");
//        datadeclare("1CSEB","SKILL LAB","","Monday","03:30PM - 04:30PM","");
        datadeclare("1CSEB","PHY","B M SANKARSHAN","Tuesday","09:00AM - 10:00AM","202");
        datadeclare("1CSEB","ELECTRONICS","LOKESH M","Tuesday","10:00AM - 11:00AM","202");
//        datadeclare("1CSEB","MECHANICAL","MAHESH M","Tuesday","10:00AM - 11:00AM","");
        datadeclare("1CSEB","MATHS","SHIVARAJ KUMAR","Tuesday","11:30AM - 12:30PM","202");
        datadeclare("1CSEB","MATHS","SHIVARAJ KUMAR","Tuesday","12:30AM - 01:30AM","202");
        datadeclare("1CSEB","PHY LAB","B M SANKARSHAN","Tuesday","02:30PM - 03:30PM","PL");
        datadeclare("1CSEB","PHY LAB","B M SANKARSHAN","Tuesday","03:30PM - 04:30PM","PL");
//        datadeclare("1CSEB","PPC LAB","SUSHMA M K","Tuesday","02:30PM - 03:30PM","");
//        datadeclare("1CSEB","PPC LAB","SUSHMA M K","Tuesday","03:30PM - 04:30PM","");
        datadeclare("1CSEB","PHY","B M SANKARSHAN","Wednesday","09:00AM - 10:00AM","202");
        datadeclare("1CSEB","PHY","B M SANKARSHAN","Wednesday","10:00AM - 11:00AM","202");
        datadeclare("1CSEB","PHY LAB","B M SANKARSHAN","Wednesday","11:30AM - 12:30PM","PL");
        datadeclare("1CSEB","PHY LAB","B M SANKARSHAN","Wednesday","12:30AM - 01:30AM","PL");
//        datadeclare("1CSEB","PPC LAB","SUSHMA M K","Wednesday","11:30AM - 12:30PM","");
//        datadeclare("1CSEB","PPC LAB","SUSHMA M K","Wednesday","12:30AM - 01:30AM","");
        datadeclare("1CSEB","MATHS","SHIVARAJ KUMAR","Wednesday","02:30PM - 03:30PM","202");
        datadeclare("1CSEB","MATHS","SHIVARAJ KUMAR","Thursday","09:00AM - 10:00AM","202");
        datadeclare("1CSEB","PPC","SUSHMA M K","Thursday","10:00AM - 11:00AM","202");
        datadeclare("1CSEB","CYBER SECURITY","MAHE MUBEEN AKTHAR","Thursday","11:30AM - 12:30PM","202");
        datadeclare("1CSEB","PHY","B M SANKARSHAN","Thursday","12:30AM - 01:30AM","202");
        datadeclare("1CSEB","CE","SANTOSH B K","Thursday","02:30PM - 03:30PM","202");
        datadeclare("1CSEB","SK","MADHU M S","Thursday","03:30PM - 04:30PM","202");
        datadeclare("1CSEB","ELECTRONICS","LOKESH M","Friday","09:00AM - 10:00AM","202");
//        datadeclare("1CSEB","MECHANICAL","MAHESH M","Friday","09:00AM - 10:00AM","");
        datadeclare("1CSEB","MATHS","SHIVARAJ KUMAR","Friday","10:00AM - 11:00AM","202");
        datadeclare("1CSEB","PPC","SUSHMA M K","Friday","11:30AM - 12:30PM","202");
        datadeclare("1CSEB","CYBER SECURITY","MAHE MUBEEN AKTHAR","Friday","12:30AM - 01:30AM","202");
        datadeclare("1CSEB","BK","MADHU M S","Friday","02:30PM - 03:30PM","202");

*/
    }
}