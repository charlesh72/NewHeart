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

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityContext;
import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseRootView;
import com.beakon.newheart.intents.IntentFactory;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Charles on 7/14/2017.
 */

@ActivityScope
public class HomeRootView extends BaseRootView {

    @BindView(R.id.homeTVHabits)
    TextView habitTV;

    @BindView(R.id.homeTVDailyScriptureStudy)
    TextView scriptureStudyTV;

    @BindView(R.id.homeTVScriptureFriends)
    TextView scriptureFriendsTV;

    @BindView(R.id.homeTVAttributesQuiz)
    TextView attributesTV;

    @BindView(R.id.homeTVService)
    TextView serviceTV;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private final IntentFactory intents;

    @Inject
    public HomeRootView(@NonNull @ActivityContext Context context,
                        @NonNull IntentFactory intents) {
        super(context);
        this.intents = intents;

        addView(inflate(getContext(), R.layout.home, null));
        ButterKnife.bind(this);

        initToolbar();
    }

    @OnClick(R.id.homeTVHabits)
    public void onClickHabits(){
        Intent intent = intents.startListHabitsActivity(getContext());
        getContext().startActivity(intent);
    }

    @OnClick(R.id.homeTVDailyScriptureStudy)
    public void onClickDailyScriptureStudy(){
        Intent intent = intents.startScriptureStudyActivity(getContext());
        getContext().startActivity(intent);
    }

    @OnClick(R.id.homeTVAttributesQuiz)
    public void onClickAttributesQuiz() {
        Intent intent = intents.startAttributesQuizActivity(getContext());
        getContext().startActivity(intent);
    }

    @OnClick(R.id.homeTVService)
    public void onClickActsOfService() {
        Intent intent = intents.startServiceActivity(getContext());
        getContext().startActivity(intent);
    }

    @OnClick(R.id.homeTVScriptureFriends)
    public void onClickScriptureFriends() {

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