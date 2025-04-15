package com.example.expensetracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {


    private static final SimpleDateFormat DISPLAY_FORMAT = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
    private static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


    public static DatePickerDialog initdatepicker(Context context, Button dp){
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=makedatestring(dayOfMonth, month,year);
                dp.setText(date);
            }


        };

        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(context, AlertDialog.THEME_HOLO_DARK,dateSetListener,year,month,day);

    }

    public static String makedatestring(int dayOfMonth, int month, int year) {
//        maindate = String.format("%04d-%02d-%02d", year, month, dayOfMonth);
        return dayOfMonth + " " + getmonthname(month) + " " + year;
    }

    public static String makedatestringmonthly(String date){
        return (getmonthname(Integer.parseInt(date.substring(5,7)))+" "+date.substring(0,4));
    }


    public static String getmonthname(int month) {
        switch(month){
            case 1:return "Jan";
            case 2:return "Feb";
            case 3:return "Mar";
            case 4:return "Apr";
            case 5:return "May";
            case 6:return "Jun";
            case 7:return "Jul";
            case 8:return "Aug";
            case 9:return "Sep";
            case 10:return "Oct";
            case 11:return "Nov";
            case 12:return "Dec";
        }
        return "Error";
    }

    public static void opendatepicker(DatePickerDialog datePickerDialog){
        datePickerDialog.show();
    }
    public static String dbToDisplay(String dbDate) {
        try {
            Date date = DB_FORMAT.parse(dbDate);
            return DISPLAY_FORMAT.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dbDate; // fallback to original
        }
    }

    public static String getCurrentMonthYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return String.format("%04d-%02d", year, month);
    }


    public static String addDaysToDbDate(String dbDate, int daysToAdd) {
        try {
            Date date = DB_FORMAT.parse(dbDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
            return DB_FORMAT.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return dbDate;
        }
    }

    public static String gettodaysdate() {
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        return DateUtil.makedatestring(day,month+1,year);

    }

    public static String displayToDb(String displayDate) {
        try {
            Date date = DISPLAY_FORMAT.parse(displayDate);
            return DB_FORMAT.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static String getTodayDisplayDate() {
//        Calendar calendar = Calendar.getInstance();
//        return DISPLAY_FORMAT.format(calendar.getTime());
//    }

    public static String makeDisplayDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return DISPLAY_FORMAT.format(calendar.getTime());
    }
}
