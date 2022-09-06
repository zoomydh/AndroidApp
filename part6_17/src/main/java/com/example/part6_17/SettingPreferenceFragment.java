package com.example.part6_17;

import static com.example.part6_17.lab17_2Activity.TARGET_SETTING_PAGE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

public class SettingPreferenceFragment extends PreferenceFragmentCompat {

    SharedPreferences prefs;

    Preference soundPreference;
    Preference keywordSoundPreference;
    Preference keywordScreen;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.xml, rootKey);
        if (rootKey == null)
        {
            soundPreference = findPreference("sound_list");
            keywordSoundPreference = findPreference("keyword_sound_list");
            keywordScreen = findPreference("keyword_screen");

            prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

            if (!prefs.getString("sound_list", "").equals(""))
            {
                soundPreference.setSummary(prefs.getString("sound_list","Legend"));
            }
            if (!prefs.getString("keyword_sound_list", "").equals(""))
            {
                keywordSoundPreference.setSummary(prefs.getString("keyword_sound_list","Legend"));
            }
            if (prefs.getBoolean("keyword_screen", false))
            {
                keywordScreen.setSummary("사용");
            }

            prefs.registerOnSharedPreferenceChangeListener(prefListener);
        }
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("sound_list"))
            {
                soundPreference.setSummary(prefs.getString("sound_list","Legend"));
            }
            if (key.equals("keyword_sound_list"))
            {
                soundPreference.setSummary(prefs.getString("keyword_sound_list","Legend"));
            }
        }
    };

    @Override
    public void onNavigateToScreen(PreferenceScreen preferenceScreen) {
        Intent intent = new Intent(getActivity(), lab17_2Activity.class).putExtra(TARGET_SETTING_PAGE, preferenceScreen.getKey());
        startActivity(intent);
    }
}
