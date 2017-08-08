/*
 * Copyright (C) 2017 Charles Hancock
 *
 * NewHeart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * NewHeart is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.beakon.newheart.activities.home.settings;

import android.app.backup.BackupManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.home.HomeScreen;
import com.beakon.newheart.utils.RingtoneUtils;

public class SettingsFragment extends PreferenceFragmentCompat
    implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private static int RINGTONE_REQUEST_CODE = 1;

    private static int CONTACT_REQUEST_CODE = 2;

    private SharedPreferences prefs;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CONTACT_REQUEST_CODE) {
            updateAccountabilityFriend(data);
        }
        else if (requestCode == RINGTONE_REQUEST_CODE)
        {
            RingtoneUtils.parseRingtoneData(getContext(), data);
            updateRingtoneDescription();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        setResultOnPreferenceClick("bugReport", HomeScreen.RESULT_BUG_REPORT);

//        updateRingtoneDescription();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s)
    {
        // NOP
    }

    @Override
    public void onPause()
    {
        prefs.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference)
    {
        String key = preference.getKey();
        if (key == null) return false;

        if (key.equals("reminderSound"))
        {
            RingtoneUtils.startRingtonePickerActivity(this,
                RINGTONE_REQUEST_CODE);
            return true;
        }

        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        prefs = getPreferenceManager().getSharedPreferences();
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key)
    {
        BackupManager.dataChanged("com.beakon.newheart");
    }

    private void setResultOnPreferenceClick(String key, final int result)
    {
        Preference pref = findPreference(key);
        pref.setOnPreferenceClickListener(preference -> {
            getActivity().setResult(result);
            getActivity().finish();
            return true;
        });
    }

    private void updateAccountabilityFriend(Intent data) {

    }

    private void updateRingtoneDescription()
    {
        String ringtoneName = RingtoneUtils.getRingtoneName(getContext());
        if (ringtoneName == null) return;
        Preference ringtonePreference = findPreference("reminderSound");
        ringtonePreference.setSummary(ringtoneName);
    }
}