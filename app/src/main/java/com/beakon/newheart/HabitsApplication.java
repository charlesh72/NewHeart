/*
 * Copyright (C) 2016 Álinson Santos Xavier <isoron@gmail.com>
 *
 * This file is part of Loop Habit Tracker.
 *
 * Loop Habit Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Loop Habit Tracker is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.beakon.newheart;

import android.app.*;
import android.content.*;
import android.util.Log;

import com.activeandroid.*;

import com.beakon.newheart.models.sqlite.*;
import com.beakon.newheart.notifications.*;
import com.beakon.newheart.preferences.*;
import com.beakon.newheart.tasks.*;
import com.beakon.newheart.utils.*;
import com.beakon.newheart.widgets.*;

import java.io.*;
import java.util.GregorianCalendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * The Android application for Loop Habit Tracker.
 */
public class HabitsApplication extends Application
{
    public static Context context;

    private static AppComponent component;

    private WidgetUpdater widgetUpdater;

    private ReminderScheduler reminderScheduler;

    private NotificationTray notificationTray;

    public AppComponent getComponent()
    {
        return component;
    }

    private PendingIntent alarmService;

    public static void setComponent(AppComponent component)
    {
        HabitsApplication.component = component;
    }

    public static boolean isTestMode()
    {
        try
        {
            Class.forName ("com.beakon.newheart.BaseAndroidTest");
            return true;
        }
        catch (final ClassNotFoundException e)
        {
            return false;
        }
    }

    @Override
    public void onCreate()
    {
        Log.d("APPLICATION", "onCreate()");
        super.onCreate();
        context = this;

        component = DaggerAppComponent
            .builder()
            .appModule(new AppModule(context))
            .build();

        if (isTestMode())
        {
            File db = DatabaseUtils.getDatabaseFile(context);
            if (db.exists()) db.delete();
        }

        try
        {
            DatabaseUtils.initializeActiveAndroid(context);
        }
        catch (InvalidDatabaseVersionException e)
        {
            File db = DatabaseUtils.getDatabaseFile(context);
            db.renameTo(new File(db.getAbsolutePath() + ".invalid"));
            DatabaseUtils.initializeActiveAndroid(context);
        }

        initializeRealm();

        initializeAlarm();

        widgetUpdater = component.getWidgetUpdater();
        widgetUpdater.startListening();

        reminderScheduler = component.getReminderScheduler();
        reminderScheduler.startListening();

        notificationTray = component.getNotificationTray();
        notificationTray.startListening();

        Preferences prefs = component.getPreferences();
        prefs.initialize();
        prefs.updateLastAppVersion();

        TaskRunner taskRunner = component.getTaskRunner();
        taskRunner.execute(() -> {
            reminderScheduler.scheduleAll();
            widgetUpdater.updateWidgets();
        });
    }

    private void initializeRealm() {
        // Initialize Realm
        Realm.init(context);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }

    private void initializeAlarm() {
        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                new Intent(context, DailyTasksAlarmService.class),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp)
        {
            Log.d("myTag", "Alarm is already active");
        } else {
            final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            final GregorianCalendar calendar = new GregorianCalendar();
            // TODO: 11/20/2017 Make sure it's not going off in the middle of the night
            calendar.add(GregorianCalendar.SECOND, 30);
            long time = calendar.getTimeInMillis();
            final Intent alarmIntent = new Intent(context, DailyTasksAlarmService.class);
            if (alarmService == null) {
                alarmService = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            }
            m.set(AlarmManager.RTC, time, alarmService);
        }
    }

    @Override
    public void onTerminate()
    {
        context = null;
        ActiveAndroid.dispose();

        reminderScheduler.stopListening();
        widgetUpdater.stopListening();
        notificationTray.stopListening();
        super.onTerminate();
    }
}
