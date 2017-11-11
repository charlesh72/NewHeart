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
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.service.ActOfServiceDay;
import com.beakon.newheart.activities.service.ActOfServiceDay2016;
import com.beakon.newheart.activities.service.ActOfServiceDay2017;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Charles on 11/10/2017.
 */

public class DailyTasksWidget extends AppWidgetProvider {


    List<String> actsOfService;

    public DailyTasksWidget() {
        super();
        actsOfService = new ArrayList<>();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_daily_tasks);

            Realm realm = Realm.getDefaultInstance();
            GregorianCalendar today = new GregorianCalendar();
            int day = today.get(GregorianCalendar.DAY_OF_MONTH);
            ActOfServiceDay result = realm.where(ActOfServiceDay2017.class)
                    .equalTo("day", day).findFirst();

            for (int j = 0; j < 3; j++) {
                String act = result.getAct(j+1);
                if (result.getCBox(j+1) && !actsOfService.contains(act)) {
                    actsOfService.add(act);
                }
            }


            String text = "No Acts of Service selected for today!";
            int size = actsOfService.size();
            if (size > 0) {
                text = actsOfService.get(new Random().nextInt(size));
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
