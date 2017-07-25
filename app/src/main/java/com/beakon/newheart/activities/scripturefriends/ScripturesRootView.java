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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.BaseRootView;
import com.beakon.newheart.intents.IntentFactory;
import com.beakon.newheart.utils.StyledResources;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by beakon on 7/25/2017.
 */

public class ScripturesRootView extends BaseRootView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private final IntentFactory intents;

    public ScripturesRootView(Context context, IntentFactory intents) {
        super(context);
        this.intents = intents;

        addView(inflate(getContext(), R.layout.scripture_friends, null));
        ButterKnife.bind(this);

        initToolbar();
    }

    @Override
    public boolean getDisplayHomeAsUp()
    {
        return true;
    }

    @NonNull
    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setTitle(getResources().getString(R.string.scripture_friends));
    }
}