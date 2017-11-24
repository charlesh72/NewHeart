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
import com.beakon.newheart.activities.home.HomeActivity;
import com.beakon.newheart.scripturestudy.HelpActivity;
import com.beakon.newheart.scripturestudy.memorize.MemorizeActivity;
import com.beakon.newheart.scripturestudy.scriptureview.ScriptureIntent;

/**
 * Created by Charles on 11/22/2017.
 */

public class HelpWidget extends AppWidgetProvider {
    public HelpWidget() {
        super();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_help);

            // Click listener for button
            // TODO: 11/22/2017 Set up code for sending a help message
            Intent hIntent = new Intent(context, HelpActivity.class);
            PendingIntent helpIntent = PendingIntent.getActivity(context, widgetId,
                    hIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widgetBHelp, helpIntent);



            // Updates the views(if there is anything to update with)
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }


}
