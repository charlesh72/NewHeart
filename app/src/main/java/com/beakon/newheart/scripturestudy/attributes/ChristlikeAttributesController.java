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

package com.beakon.newheart.scripturestudy.attributes;

import android.support.annotation.NonNull;

import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseSystem;

import javax.inject.Inject;

/**
 * Created by Charles on 8/7/2017.
 */

@ActivityScope
public class ChristlikeAttributesController {

    @NonNull
    private final ChristlikeAttributesScreen screen;

    @NonNull
    private final BaseSystem system;

    @Inject
    public ChristlikeAttributesController(@NonNull ChristlikeAttributesScreen screen,
                                          @NonNull BaseSystem system) {
        this.screen = screen;
        this.system = system;
    }
}
