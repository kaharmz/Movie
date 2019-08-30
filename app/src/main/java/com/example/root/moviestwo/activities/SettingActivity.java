package com.example.root.moviestwo.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.root.moviestwo.R;
import com.example.root.moviestwo.fragments.setting.SettingPreferenceFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_setting, new SettingPreferenceFragment())
                .commit();
    }
}
