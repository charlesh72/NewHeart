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

package com.beakon.newheart.activities.service.manager;

import android.os.Bundle;

import com.beakon.newheart.HabitsApplication;
import com.beakon.newheart.activities.ActivityModule;
import com.beakon.newheart.activities.BaseActivity;

/**
 * Created by Charles on 11/16/2017.
 */

public class ServiceManagerActivity extends BaseActivity {

    private ServiceManagerComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HabitsApplication app = (HabitsApplication) getApplicationContext();

        component = DaggerServiceManagerComponent
                .builder()
                .appComponent(app.getComponent())
                .activityModule(new ActivityModule(this))
                .build();

        ServiceManagerRootView rootView = component.getRootView();
        ServiceManagerScreen screen = component.getScreen();
        ServiceManagerController controller = component.getController();

        setScreen(screen);
//        screen.setMenu(component.getMenu());
        screen.setController(controller);

    }
}
