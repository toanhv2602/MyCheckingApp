package vn.skymapglobal.checkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.skymapglobal.checkingapp.CheckLogPackage.Check_log_SharedViewModel;


public class MainActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private ViewPagerAdapter adapter = new ViewPagerAdapter(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Check_log_SharedViewModel checklogSharedViewModel = new ViewModelProvider(this).get(Check_log_SharedViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);

        viewPager2.setAdapter(adapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.action_home){
                    viewPager2.setCurrentItem(0);
                }
                if (item.getItemId()==R.id.action_checklog){

                    viewPager2.setCurrentItem(1);
                }
                if (item.getItemId()==R.id.action_profile){
                    viewPager2.setCurrentItem(2);
                }

                return true;
            }


        });


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0 :
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1 :
                        bottomNavigationView.getMenu().findItem(R.id.action_checklog).setChecked(true);
                        break;
                    case 2 :
                        bottomNavigationView.getMenu().findItem(R.id.action_profile).setChecked(true);
                        break;

                }
                super.onPageSelected(position);
            }
        });

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String userid = intent.getStringExtra("userid");
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("userid",userid);
        adapter.setDataBundle(bundle);


    }

}