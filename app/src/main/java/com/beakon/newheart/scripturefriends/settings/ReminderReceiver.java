package com.beakon.newheart.scripturefriends.settings;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.beakon.newheart.R;
import com.beakon.newheart.scripturefriends.AddScriptureInstructions;


public class ReminderReceiver extends BroadcastReceiver {
    public static String EXTRA_KEY = "com.danmercer.ReminderReceiver.KEY";

    public ReminderReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("ReminderReceiver", "ReminderReceiver fired!");
        if (intent == null || !intent.hasExtra(EXTRA_KEY)) {
            Log.e("ReminderReceiver", "Intent error!");
            return;
        }
        String key = intent.getStringExtra(EXTRA_KEY);

        // Create notification
        NotificationCompat.Builder nb = new NotificationCompat.Builder(context);
        nb.setContentTitle("Choose a new scripture for this week.");
        nb.setContentText("Tap to choose a scripture.");
        nb.setSmallIcon(R.drawable.ic_school_white_24dp);
        nb.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        nb.setAutoCancel(true);
        nb.setPriority(NotificationCompat.PRIORITY_LOW);

        // Set up notification sound
        Uri sound = Settings.System.DEFAULT_NOTIFICATION_URI;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String prefkey = context.getString(R.string.prefkey_notif_sound);
        String uriString = prefs.getString(prefkey, sound.toString());
        if (!uriString.isEmpty()) {
            sound = Uri.parse(uriString);
            nb.setSound(sound);
        }

        // Set up vibration ("Nostrils on the bus!")
        if (prefs.getBoolean(context.getString(R.string.prefkey_notif_vibrate), false)) {
            nb.setVibrate(new long[] {0,300,200,300});
        }

        // Set up notification intent
        Intent i = new Intent(context, AddScriptureInstructions.class);
        PendingIntent pi = PendingIntent.getActivity(context, key.hashCode(), i, 0);
        nb.setContentIntent(pi);

        // Set up "Reminder Settings" action
        String actionText = "Reminder Settings";
        Intent actionI = new Intent(context, ScriptureSettingsActivity.class);
        PendingIntent actionPI = PendingIntent.getActivity(context, key.hashCode(), actionI, 0);
        NotificationCompat.Action.Builder ab = new NotificationCompat.Action.Builder(
                R.drawable.ic_settings_white_24dp, actionText, actionPI);
        nb.addAction(ab.build());

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(key.hashCode(), nb.build());
    }
}
