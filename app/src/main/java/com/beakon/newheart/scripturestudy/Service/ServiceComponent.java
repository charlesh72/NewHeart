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

package com.beakon.newheart.scripturestudy.Service;

import com.beakon.newheart.AppComponent;
import com.beakon.newheart.activities.ActivityModule;
import com.beakon.newheart.activities.ActivityScope;

import dagger.Component;

/**
 * Created by Charles on 8/23/2017.
 */

@ActivityScope
@Component(modules = {ActivityModule.class},
            dependencies = {AppComponent.class})
public interface ServiceComponent {
    ServiceController getController();
    ServiceRootView getRootView();
}
