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

package com.beakon.newheart.widgets;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.service.ActOfService;
import com.beakon.newheart.activities.service.DaysActsOfService;
import com.beakon.newheart.utils.DateUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.realm.Realm;

/**
 * Created by Charles on 11/10/2017.
 */

public class DailyTasksWidget extends AppWidgetProvider {

    private PendingIntent service = null;

    public DailyTasksWidget() {
        super();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_daily_tasks);

            String text = getTaskText();

            remoteViews.setTextViewText(R.id.widgetDTTVLabel, text);

//            // Intent for our service
//            final Intent alarmIntent = new Intent(context, DailyTasksWidget.class);
//            final PendingIntent pending = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            alarm.cancel(pending);
//            long interval = 1000*100;
//            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),interval, pending);


            // Click listener for button
            Intent refreshIntent = new Intent(context, DailyTasksWidget.class);
            refreshIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widgetDTBRefresh, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);


            final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            final Calendar TIME = Calendar.getInstance();
            TIME.set(Calendar.MINUTE, 0);
            TIME.set(Calendar.SECOND, 0);
            TIME.set(Calendar.MILLISECOND, 0);

            final Intent alarmIntent = new Intent(context, DailyTasksWidgetService.class);

            if (service == null)
            {
                service = PendingIntent.getService(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            }

            // TODO: 11/14/2017 Use one time alarms as described in setRepeating documentation
            m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), 1000 * 60, service);
        }
    }

    @Override
    public void onDisabled(Context context)
    {
        final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        m.cancel(service);
    }

    public static String getTaskText() {
        Realm realm = Realm.getDefaultInstance();
        GregorianCalendar calendar = new GregorianCalendar();
        long todaysID = DateUtils.getStartOfDay(calendar.getTimeInMillis());
        DaysActsOfService result = realm.where(DaysActsOfService.class)
                .equalTo("date", todaysID).findFirst();

        ActOfService act;
        if (result != null) {
            act = result.getNext();
        } else {
            act = null;
        }

        String text;
        if (act == null) {
            text = "No Acts of Service selected for today!";
        } else {
            text = act.text;
        }
        return text;
    }
}
