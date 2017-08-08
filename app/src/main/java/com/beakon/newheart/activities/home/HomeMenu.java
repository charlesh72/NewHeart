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

package com.beakon.newheart.activities.home;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseActivity;
import com.beakon.newheart.activities.BaseMenu;
import com.beakon.newheart.activities.BaseScreen;

import javax.inject.Inject;

/**
 * Created by Charles on 8/4/2017.
 */

@ActivityScope
public class HomeMenu extends BaseMenu {

    @NonNull
    private final HomeScreen screen;

    @Inject
    public HomeMenu(@NonNull BaseActivity activity,
                    @NonNull HomeScreen screen) {
        super(activity);
        this.screen = screen;
    }

    @Override
    public boolean onItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_help:
                screen.showFAQScreen();
                return true;

            case R.id.action_settings:
                screen.showSettingsScreen();
                return true;

            default:
                return false;
        }
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.home;
    }
}
