package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.expensetracker.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.example.expensetracker.FragmentTags;

import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    MyRepository myRepository;
    private DrawerLayout drawerLayout;
    private ImageButton menuIcon;
    private NavigationView navigationView;
    ActivityMainBinding binding;
    public BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView=binding.contentmain.bottomNavigationView;
        frameLayout=findViewById(R.id.framelayout);
        menuIcon = findViewById(R.id.optionbutton);
        drawerLayout=findViewById(R.id.main);
        navigationView=findViewById(R.id.navigationpane);
        myRepository = new MyRepository(getApplication());

        Intent receivedintent=getIntent();
        if(receivedintent.hasExtra("Page")){
            switch(receivedintent.getIntExtra("Page",0)){
                case -1:
                    switchfragment(new RecDepFragment(),FragmentTags.getRecdepfragtag());
//                    binding.contentmain.bottomNavigationView.setSelectedItemId(R.id.recdepbutton);
                    break;
//                case 1:
//                    switchfragment(new FilterPageFragment());
//                    binding.contentmain.bottomNavigationView.setSelectedItemId(R.id.homebutton);
//                    break;
                default:
                    switchfragment(new HomeFragment(),FragmentTags.getHomefragtag());
//                    binding.contentmain.bottomNavigationView.setSelectedItemId(R.id.catsortbutton);

            }
        }
        else{
            switchfragment(new HomeFragment(),FragmentTags.getHomefragtag());
//            binding.contentmain.bottomNavigationView.setSelectedItemId(R.id.homebutton);
        }


        TextView headerdatevar = navigationView.getHeaderView(0).findViewById(R.id.headerdate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        headerdatevar.setText(sdf.format(new Date()));


        menuIcon.setOnClickListener(v -> {
            drawerLayout.open();
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.profile) {
                Toast.makeText(this,"You pressed Profile",Toast.LENGTH_LONG).show();
            } else if(id == R.id.managecategories) {
                Intent intentformanagecat=new Intent(this,ManageCategoryPage.class);
                startActivity(intentformanagecat);
            } else if(id == R.id.export) {
                Intent intentforexport=new Intent(this, ExportLogActivity.class);
                startActivity(intentforexport);
                Toast.makeText(this,"You pressed Export",Toast.LENGTH_LONG).show();
            }
            return true;
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId()==R.id.homebutton) {
                switchfragment(new HomeFragment(),FragmentTags.getHomefragtag(),false);
            }
            else if (item.getItemId()==R.id.recdepbutton) {
                switchfragment(new RecDepFragment(),FragmentTags.getRecdepfragtag(),false);
            }
            else if(item.getItemId()==R.id.catsortbutton) {
                switchfragment(getcatogorypagefragment(),FragmentTags.getCatfragtag(),false);
            }
            else if(item.getItemId()==R.id.monthlyfilterbutton){
                switchfragment(getmonthlyfilterfragment(-1,false),FragmentTags.getMonfilterfragtag(false),false);
            }
            return true;
        });






    }

    public FilterPageResusableFragment getcatogorypagefragment(){
        FilterPageResusableFragment fragment=FilterPageResusableFragment.newInstance("Categories","linear",1);
        fragment.setAdapter(new CatFilterListAdapter(this,
                myRepository.getexpensesgroupedbycatogory(
                        DateUtil.addDaysToDbDate(DateUtil.displayToDb(DateUtil.gettodaysdate()), -30),
                        DateUtil.displayToDb(DateUtil.gettodaysdate())),
                new CatFilterListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(POJOCatFilterList pojoCatFilterList) {
                        switchfragment(getmonthlyfilterfragment(myRepository.getidofcatname(pojoCatFilterList.getCatogory()),true),FragmentTags.getMonfilterfragtag(true));
                    }
                }));
        return fragment;
    }


    public FilterPageResusableFragment getmonthlyfilterfragment(int catogoryid,boolean is_itforcat){
        FilterPageResusableFragment fragment=FilterPageResusableFragment.newInstance("Monthly Report","grid",
                2);
        fragment.setAdapter(new MonthlyFilterGridAdapter(MainActivity.this,
                myRepository.getTotalByMonthAndCategory(catogoryid),


                new MonthlyFilterGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(POJOMonthlyExpense pojomonthlyexpense) {
                switchfragment(getbydayfilteredfragment(pojomonthlyexpense,catogoryid,is_itforcat),FragmentTags.getDailyFrag(is_itforcat));
            }
        }));
        return fragment;
    }


    public FilterPageResusableFragment getbydayfilteredfragment(POJOMonthlyExpense pojomonthlyexpense,int catogoryid,boolean is_itforcat){
        FilterPageResusableFragment fragment2=FilterPageResusableFragment.newInstance(DateUtil.makedatestringmonthly(pojomonthlyexpense.getMonth()),
                "grid",2);
        fragment2.setAdapter(new PerDayItemAdapter(MainActivity.this,
                myRepository.getperdayexpensebymonthandcat(pojomonthlyexpense.getMonth(),
                        catogoryid),

                new PerDayItemAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(POJOPerDayList pojoPerDayList) {
                        FilterPageResusableFragment fragment3=FilterPageResusableFragment.newInstance(DateUtil.dbToDisplay(pojoPerDayList.perdaydate),
                                "Linear",1);
                        fragment3.setAdapter(new HomeListAdapter(MainActivity.this,
                                myRepository.getexpensebydateandcategory(pojoPerDayList.perdaydate,catogoryid)));
                        switchfragment(fragment3,FragmentTags.getexpensefragtag(is_itforcat));
                    }
                }));
        return (fragment2);
    }






    public void switchfragment(Fragment fragment,String tag,Boolean manualbswitch){
        FragmentManager fragmentManager=getSupportFragmentManager();
        boolean found = false;
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
            if (tag.equals(entry.getName())) {
                found = true;
                break;
            }
        }

        if (found) {
            fragmentManager.popBackStack(tag, 0);
        } else {
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout, fragment, tag);
            if(!tag.equals("home_frag") ||
                    !tag.equals("rec_dep_frag") ||
                    !tag.equals("category_frag") ||
                    !tag.equals("month_filter_frag")) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commit();
        }
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.framelayout,fragment,tag);
//        fragmentTransaction.commit();
    }

    public void switchfragment(Fragment fragment,String tag){
        if(bottomNavigationView!=null && tag!=null){
                switch(tag){
                    case "home_frag":
                        bottomNavigationView.setSelectedItemId(R.id.homebutton);
                        break;
                    case "rec_dep_frag":
                        bottomNavigationView.setSelectedItemId(R.id.recdepbutton);
                        break;
                    case "category_frag":
                        bottomNavigationView.setSelectedItemId(R.id.catsortbutton);
                        break;
                    case "month_filter_frag":
                        bottomNavigationView.setSelectedItemId(R.id.monthlyfilterbutton);
                        break;
                    default:
//                        Toast.makeText(this,"Fucker",Toast.LENGTH_LONG).show();
                }}

        FragmentManager fragmentManager=getSupportFragmentManager();
        boolean found = false;
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
            if (tag.equals(entry.getName())) {
                found = true;
                break;
            }
        }

        if (found) {
            fragmentManager.popBackStack(tag, 0);
        } else {
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framelayout, fragment, tag);
            if(!tag.equals("home_frag") ||
            !tag.equals("rec_dep_frag") ||
            !tag.equals("category_frag") ||
            !tag.equals("month_filter_frag")) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commit();
        }
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.framelayout,fragment,tag);
//        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.framelayout);

        if (currentFragment != null && currentFragment.getTag() != null) {
            String tag = currentFragment.getTag();

            if(tag.equals("home_frag") ||
                    tag.equals("rec_dep_frag") ||
                    tag.equals("category_frag") ||
                    tag.equals("month_filter_frag")) {
                finish();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }



}
