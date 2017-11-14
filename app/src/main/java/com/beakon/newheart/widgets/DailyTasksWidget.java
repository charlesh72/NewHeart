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

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.service.ActOfService;
import com.beakon.newheart.activities.service.DaysActsOfService;
import com.beakon.newheart.utils.DateUtils;

import java.util.GregorianCalendar;

import io.realm.Realm;

/**
 * Created by Charles on 11/10/2017.
 */

public class DailyTasksWidget extends AppWidgetProvider {



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

            remoteViews.setTextViewText(R.id.widgetDTTVLabel, text);


            Intent refreshIntent = new Intent(context, DailyTasksWidget.class);
            refreshIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widgetDTBRefresh, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
