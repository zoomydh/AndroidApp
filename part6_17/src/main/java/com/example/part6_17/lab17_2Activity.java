package com.example.part6_17;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class lab17_2Activity extends AppCompatActivity {

    public static final String TARGET_SETTING_PAGE ="target";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingPreferenceFragment settingFragment = new SettingPreferenceFragment();
        Intent intent = getIntent();
        if (intent != null)
        {
            String rootkey = intent.getStringExtra(TARGET_SETTING_PAGE);
            if (rootkey != null)
            {
                Bundle args = new Bundle();
                args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, rootkey);
                settingFragment.setArguments(args);
            }
        }

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, settingFragment, null).commit();
    }
}