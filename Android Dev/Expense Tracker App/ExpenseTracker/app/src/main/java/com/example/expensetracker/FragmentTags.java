package com.example.expensetracker;

public class FragmentTags {
    private static String homefragtag="home_frag";
    private static String recdepfragtag="rec_dep_frag";
    private static String catfragtag="category_frag";
    private static String monfilterfragtag="month_filter_frag";
    private static String catmonthlytag="cat_mon_frag";



    private static String catdailyfrag="cat_daily_tag";

    private static String catdailyexpense="cat_daily_expense";

    private static String monthdailyfrag="month_by_day";
    private static String monthdailyexpense="month_daily_expense";

    public static String getHomefragtag() {
        return homefragtag;
    }

    public static String getRecdepfragtag() {
        return recdepfragtag;
    }

    public static String getCatfragtag() {
        return catfragtag;
    }

    public static String getMonfilterfragtag(boolean is_itforcat) {
        return is_itforcat?catmonthlytag:monfilterfragtag;
    }

    public static String getDailyFrag(boolean is_itforcategory){
        return is_itforcategory?catdailyfrag:monthdailyfrag;
    }

    public static String getexpensefragtag(boolean is_itforcategory){
        return is_itforcategory?catdailyexpense:monthdailyexpense;
    }
}
