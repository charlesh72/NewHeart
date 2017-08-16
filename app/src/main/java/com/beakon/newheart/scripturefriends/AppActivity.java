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

package com.beakon.newheart.scripturefriends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.beakon.newheart.BuildConfig;
import com.beakon.newheart.R;
import com.beakon.newheart.scripturefriends.settings.ReminderPreference;
import com.beakon.newheart.scripturefriends.settings.ScriptureSettingsActivity;


/**
 * Superclass to be extended by major activities in the app. This allows all superclasses to have
 * these actions built into the App Bar:
 * "Settings"
 * "Help"
 * "Send Feedback"
 * "About"
 *
 * Created by Dan on 11/16/2015.
 */
public abstract class AppActivity extends AppCompatActivity {
    private static final String KEY_VERSION = "APP_VERSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize settings
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int prefsVersion = prefs.getInt(KEY_VERSION, 0);
        if (prefsVersion < BuildConfig.VERSION_CODE) { // This is true after an install or update
            // Set up default preferences for prefs that haven't been set
            PreferenceManager.setDefaultValues(this, R.xml.settings, false);

            if (prefs.getBoolean(getString(R.string.prefkey_weekly_reminder), false)) {
                // Set weekly reminder to be on if it should be
                String key = getString(R.string.prefkey_weekly_reminder_time);
                String setting = prefs.getString(key,
                        ReminderPreference.DEFAULT_VALUE);
                ReminderPreference.Util u = new ReminderPreference.Util(this, key);
                u.parseValue(setting);
                u.setupAlarm();
            }

            prefs.edit().putInt(KEY_VERSION, BuildConfig.VERSION_CODE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_universal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_settings) {
            startActivity(new Intent(this, ScriptureSettingsActivity.class));
            return true;
        } else if (i == R.id.action_help) { // TODO: 8/4/2017 Merge with uhabits menu so everything is in the same place, also check menu_universal.xml
            Uri url = Uri.parse("https://github.com/charlesh72/NewHeart/wiki/Help");
            Intent help = new Intent(Intent.ACTION_VIEW, url);
            startActivity(help);
            return true;
        } else if (i == R.id.accountability_friends) {
            Intent intent = new Intent(this, AccountabilityFriendsActivity.class);
            startActivity(intent);
        }
//        else if (i == R.id.action_feedback) {
//            launchFeedbackDialog();
//            return true;
//        } else if (i == R.id.action_about) {
//            startActivity(new Intent(this, AboutActivity.class));
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void launchFeedbackDialog() {
        AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setMessage(R.string.feedback_dialog_message);
        AlertDialog.OnClickListener l = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("onclick", "fired");
                launchFeedbackEmailIntent();
            }
        };
        db.setPositiveButton(R.string.feedback_dialog_confirm, l);
        db.setNegativeButton(R.string.feedback_dialog_cancel, null);
        db.show();
    }

    // Called by the feedback dialog to start an activity via an email intent.
    private void launchFeedbackEmailIntent() {
        String version = null;
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // Never going to happen.
            e.printStackTrace();
        }
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"danmercerdev@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT,
                "Feedback for Ponderizer (" + version + ")");
        i.putExtra(Intent.EXTRA_TEXT, "Please describe the problem you are having or the feature " +
                "you would like to see. Thanks for supporting the Ponderizer app!\n\n");
        startActivity(i);
    }
}
