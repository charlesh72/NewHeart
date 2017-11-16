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

package com.beakon.newheart.scripturestudy.attributes.results;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseActivity;
import com.beakon.newheart.activities.BaseMenu;
import com.beakon.newheart.activities.home.HomeScreen;

import javax.inject.Inject;

/**
 * Created by Charles on 11/15/2017.
 */

@ActivityScope
public class AttributesResultsMenu extends BaseMenu {

    @NonNull
    private final AttributesResultsScreen screen;

    @NonNull
    private final AttributesResultsController controller;

    @Inject
    public AttributesResultsMenu(@NonNull BaseActivity activity,
                                    @NonNull AttributesResultsScreen screen,
                                    @NonNull AttributesResultsController controller) {
        super(activity);
        this.screen = screen;
        this.controller = controller;
    }

    @Override
    public boolean onItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_done:
                return true;

            default:
                return false;
        }
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_christlike_attributes;
    }
}
