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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityContext;
import com.beakon.newheart.activities.BaseRootView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Charles on 11/1/2017.
 */

class ServiceRootView extends BaseRootView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    public ServiceRootView(@NonNull @ActivityContext Context context) {
        super(context);

        addView(inflate(getContext(), R.layout.service_list, null));
        ButterKnife.bind(this);

        initToolbar();
    }

    @NonNull
    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setTitle(getResources().getString(R.string.main_activity_title));
    }
}
