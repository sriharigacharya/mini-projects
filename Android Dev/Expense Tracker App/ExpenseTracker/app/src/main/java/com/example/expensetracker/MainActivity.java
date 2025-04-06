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

import com.example.expensetracker.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

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
        frameLayout=findViewById(R.id.framelayout);
        switchfragment(new HomeFragment());
        menuIcon = findViewById(R.id.optionbutton);
        drawerLayout=findViewById(R.id.main);
        navigationView=findViewById(R.id.navigationpane);

        myRepository = new MyRepository(getApplication());




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
                Intent intent=new Intent(this,ManageCategoryPage.class);
                startActivity(intent);
            } else if(id == R.id.export) {
                Toast.makeText(this,"You pressed Export",Toast.LENGTH_LONG).show();
            }
            return true;
        });

        binding.contentmain.bottomNavigationView.setSelectedItemId(R.id.homebutton);
        binding.contentmain.bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId()==R.id.homebutton) {
                switchfragment(new HomeFragment());
            }
            else if (item.getItemId()==R.id.recdepbutton) {
                switchfragment(new RecDepFragment());
            }
            else if(item.getItemId()==R.id.sortbutton) {
                switchfragment(new FilterPageFragment());
            }

            return true;
        });




    }

    private void switchfragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }


}
