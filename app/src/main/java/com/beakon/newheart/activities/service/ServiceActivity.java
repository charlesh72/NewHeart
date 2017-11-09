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

import android.os.Bundle;

import com.beakon.newheart.HabitsApplication;
import com.beakon.newheart.activities.ActivityModule;
import com.beakon.newheart.activities.BaseActivity;

/**
 * Created by Charles on 10/11/2017.
 */

public class ServiceActivity extends BaseActivity {

    private ServiceComponent component;
    private ServiceController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HabitsApplication app = (HabitsApplication) getApplicationContext();

        component = DaggerServiceComponent
                .builder()
                .appComponent(app.getComponent())
                .activityModule(new ActivityModule(this))
                .build();

        controller = component.getController();
        ServiceScreen screen = component.getScreen();

        setScreen(screen);
        screen.setController(controller);

    }

}
