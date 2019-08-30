package com.example.root.moviestwo.fragments.setting;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.root.moviestwo.R;
import com.example.root.moviestwo.reminder.DailyReminder;
import com.example.root.moviestwo.reminder.ReleaseTodayReminder;

public class SettingPreferenceFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private String DAILY_REMINDER;
    private String RELEASE_REMINDER;
    private String CHANGE_LANGUAGE;
    private SwitchPreference mSwitchReminder;
    private SwitchPreference mSwitchRelease;
    private Preference mPreference;

    private ReleaseTodayReminder mReleaseTodayReminder;
    private DailyReminder mDailyReminder;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.setting_preferences);

        initializationPreferences();
        mSwitchRelease.setOnPreferenceChangeListener(this);
        mSwitchReminder.setOnPreferenceChangeListener(this);
        mPreference.setOnPreferenceClickListener(this);

        mReleaseTodayReminder = new ReleaseTodayReminder();
        mDailyReminder = new DailyReminder();
    }

    private void initializationPreferences() {
        DAILY_REMINDER = getResources().getString(R.string.daily_reminder);
        RELEASE_REMINDER = getResources().getString(R.string.release_reminder);
        CHANGE_LANGUAGE = getResources().getString(R.string.changes_language);

        mSwitchReminder = (SwitchPreference) findPreference(DAILY_REMINDER);
        mSwitchRelease = (SwitchPreference) findPreference(RELEASE_REMINDER);
        mPreference = findPreference(CHANGE_LANGUAGE);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object mValues) {
        String mKey = preference.getKey();
        boolean mSetValue = (boolean) mValues;

        if (mKey.equals(DAILY_REMINDER)) {
            if (mSetValue) {
                mDailyReminder.setRepeatingAlarm(getActivity(), DailyReminder.TYPE_REPEATING, "07:00", getString(R.string.reminder_notification));
                Toast.makeText(getActivity(), getResources().getString(R.string.reminder_daily_on), Toast.LENGTH_SHORT).show();
            } else {
                mDailyReminder.cancelAlarm(getActivity(), DailyReminder.TYPE_REPEATING);
                Toast.makeText(getActivity(), getResources().getString(R.string.reminder_daily_of), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (mSetValue) {
                mReleaseTodayReminder.setRepeatingAlarm(getActivity(), ReleaseTodayReminder.TYPE_RELEASE, "08:00");
                Toast.makeText(getActivity(), getResources().getString(R.string.reminder_release_on), Toast.LENGTH_SHORT).show();
            } else {
                mReleaseTodayReminder.cancelAlarmReminder(getActivity(), ReleaseTodayReminder.TYPE_RELEASE);
                Toast.makeText(getActivity(), getResources().getString(R.string.reminder_release_of), Toast.LENGTH_SHORT).show();
            }
        }

        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String mKey = preference.getKey();
        if (mKey.equals(CHANGE_LANGUAGE)) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return false;
    }
}
