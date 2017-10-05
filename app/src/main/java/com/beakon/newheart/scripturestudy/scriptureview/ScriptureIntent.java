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

package com.beakon.newheart.scripturestudy.scriptureview;

import android.content.Context;
import android.content.Intent;

import com.beakon.newheart.scripturestudy.Scripture;

/**
 * Created by Dan on 11/7/2015.
 */
public class ScriptureIntent extends Intent {
    // Used when putting a Scripture into an intent as a parcelable extra
    public static final String EXTRA_SCRIPTURE = "SCRIPTURE";

    public ScriptureIntent(Context packageContext, Class<?> cls, Scripture s) {
        super(packageContext, cls);
        putExtra(EXTRA_SCRIPTURE, s);
    }
}
