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

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseActivity;
import com.beakon.newheart.activities.BaseScreen;
import com.beakon.newheart.intents.IntentFactory;

import javax.inject.Inject;

/**
 * Created by Charles on 8/4/2017.
 */

@ActivityScope
public class HomeScreen extends BaseScreen {

    @Nullable
    HomeController controller;

    @NonNull
    private final IntentFactory intentFactory;

    @Inject
    public HomeScreen(@NonNull BaseActivity activity,
                      @NonNull HomeRootView view,
                      @NonNull IntentFactory intentFactory)
    {
        super(activity);
        setRootView(view);
        this.intentFactory = intentFactory;
    }


    public void showFAQScreen() {
        Intent intent = intentFactory.viewFAQ(activity);
        activity.startActivity(intent);
    }

    public void setController(@Nullable HomeController controller) {
        this.controller = controller;
    }
}
