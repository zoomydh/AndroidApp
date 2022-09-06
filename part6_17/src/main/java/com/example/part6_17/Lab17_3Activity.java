package com.example.part6_17;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class Lab17_3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab17_3);

        ViewPager2 pager = findViewById(R.id.lab2_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(this);
        pager.setAdapter(pagerAdapter);
    }

    static class MyPagerAdapter extends FragmentStateAdapter
    {
        ArrayList<Fragment> fragments;
        public MyPagerAdapter(FragmentActivity fa)
        {
            super(fa);
            fragments = new ArrayList<>();
            fragments.add(new OneFragment());
            fragments.add(new ThreeFragment());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}