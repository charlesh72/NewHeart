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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityContext;
import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseRootView;
import com.beakon.newheart.activities.service.ActOfService;
import com.beakon.newheart.activities.service.DaysActsOfService;
import com.beakon.newheart.intents.IntentFactory;
import com.beakon.newheart.utils.DateUtils;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Charles on 11/16/2017.
 */

@ActivityScope
class ServiceManagerRootView extends BaseRootView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.serviceManagerLVacts)
    ListView actsLV;

    private ActOfServiceAdapter adapter;

    private final IntentFactory intents;

    @Inject
    public ServiceManagerRootView(@NonNull @ActivityContext Context context,
                                     @NonNull IntentFactory intents) {
        super(context);
        this.intents = intents;

        addView(inflate(getContext(), R.layout.service_manager, null));
        ButterKnife.bind(this);

        initListView();

        initToolbar();
    }

    private void initListView() {
        Realm realm = Realm.getDefaultInstance();
        GregorianCalendar calendar = new GregorianCalendar();
        long todaysID = DateUtils.getStartOfDay(calendar.getTimeInMillis());
        DaysActsOfService result = realm.where(DaysActsOfService.class)
                .equalTo("date", todaysID).findFirst();

        // If the day class for the list is null create an empty one
        if (result == null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(new DaysActsOfService(todaysID));
            realm.commitTransaction();
        }
        // If there are no acts, give a message and return
        if (result.acts == null || result.acts.size() == 0) {
            Toast.makeText(getContext(), "No Acts selected for today", Toast.LENGTH_SHORT).show();
            return;
        }

        List<ActOfService> acts = new ArrayList<>();
        for (ActOfService act: result.acts) {
            acts.add(act);
        }

        adapter = new ActOfServiceAdapter(getContext(), acts);

        actsLV.setAdapter(adapter);
    }


    @NonNull
    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        toolbar.setTitle(getResources().getString(R.string.service_manager_title));
    }
}
