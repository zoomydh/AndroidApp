package com.example.part6_18;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class Lab18_2Activity extends AppCompatActivity implements View.OnClickListener{
    ViewPager2 viewPager2;
    FloatingActionButton fab;
    RelativeLayout relativeLayout;
    private String title[] = new String[]{"TAB1","TAB2","TAB3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab18_2);

        relativeLayout = findViewById(R.id.lab2_container);
        viewPager2 = findViewById(R.id.lab2_viewpager);
        viewPager2.setAdapter(new MyPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.lab2_tabs);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(title[position])).attach();

        fab = findViewById(R.id.lab2_fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Snackbar.make(relativeLayout, "I am SnackBar", Snackbar.LENGTH_LONG).setAction("MoreAction", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).show();
    }

    class MyPagerAdapter extends FragmentStateAdapter
    {
        List<Fragment> fragments = new ArrayList<Fragment>();

        public MyPagerAdapter (FragmentActivity fa)
        {
            super(fa);
            fragments.add(new OneFragment());
            fragments.add(new TwoFragment());
            fragments.add(new ThreeFragment());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return this.fragments.size();
        }
    }


}