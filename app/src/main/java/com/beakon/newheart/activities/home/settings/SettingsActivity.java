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

import android.os.Bundle;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.BaseActivity;
import com.beakon.newheart.activities.BaseScreen;
import com.beakon.newheart.utils.StyledResources;

/**
 * Activity that allows the user to view and modify the app settings.
 */
public class SettingsActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        setupActionBarColor();
    }

    private void setupActionBarColor()
    {
        StyledResources res = new StyledResources(this);
        int color = BaseScreen.getDefaultActionBarColor(this);

        if (res.getBoolean(R.attr.useHabitColorAsPrimary))
            color = res.getColor(R.attr.aboutScreenColor);

        BaseScreen.setupActionBarColor(this, color);
    }
}
