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

package com.beakon.newheart.activities.service;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseActivity;
import com.beakon.newheart.activities.BaseMenu;

import javax.inject.Inject;

/**
 * Created by Charles on 11/17/2017.
 */

@ActivityScope
public class ServiceMenu extends BaseMenu {

    @NonNull
    private final ServiceScreen screen;

    @NonNull
    private final ServiceController controller;

    @Inject
    public ServiceMenu(@NonNull BaseActivity activity,
                       @NonNull ServiceScreen screen,
                       @NonNull ServiceController controller) {
        super(activity);
        this.screen = screen;
        this.controller = controller;
    }

    @Override
    public boolean onItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                controller.done();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_service;
    }
}
