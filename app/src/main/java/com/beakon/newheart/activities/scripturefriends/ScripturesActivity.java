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

package com.beakon.newheart.activities.scripturefriends;

import android.os.Bundle;

import com.beakon.newheart.activities.BaseActivity;
import com.beakon.newheart.activities.BaseScreen;
import com.beakon.newheart.activities.home.HomeRootView;
import com.beakon.newheart.intents.IntentFactory;

/**
 * Created by beakon on 7/25/2017.
 */

public class ScripturesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScripturesRootView rootView = new ScripturesRootView(this, new IntentFactory());
        BaseScreen screen = new BaseScreen(this);
        screen.setRootView(rootView);
        setScreen(screen);
    }
}
