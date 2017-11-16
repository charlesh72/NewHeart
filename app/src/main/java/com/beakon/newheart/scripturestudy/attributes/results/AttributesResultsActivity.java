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

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.beakon.newheart.HabitsApplication;
import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityModule;
import com.beakon.newheart.activities.BaseActivity;

/**
 * Created by Charles on 11/15/2017.
 */

public class AttributesResultsActivity extends BaseActivity {

    private AttributesResultsComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HabitsApplication app = (HabitsApplication) getApplicationContext();

        component = DaggerAttributesResultsComponent
                .builder()
                .appComponent(app.getComponent())
                .activityModule(new ActivityModule(this))
                .build();

        AttributesResultsRootView rootView = component.getRootView();
        AttributesResultsScreen screen = component.getScreen();
        AttributesResultsController controller = component.getController();

        setScreen(screen);
        screen.setMenu(component.getMenu());
        screen.setController(controller);

    }

}
