package com.example.e_commerce;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);


        ViewPager viewPager= (ViewPager) findViewById(R.id.viewPager2);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout=(TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);


    }
}
