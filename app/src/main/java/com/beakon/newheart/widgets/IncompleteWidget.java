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
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Charles on 11/10/2017.
 */

public class IncompleteWidget extends BaseWidget {

    public IncompleteWidget(@NonNull Context context, int id) {
        super(context, id);
    }

    @Override
    public PendingIntent getOnClickPendingIntent(Context context) {
        return null;
    }

    @Override
    public void refreshData(View widgetView) {

    }

    @Override
    protected View buildView() {
        return null;
    }

    @Override
    protected int getDefaultHeight() {
        return 125;
    }

    @Override
    protected int getDefaultWidth() {
        return 250;
    }
}
