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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityContext;
import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseRootView;
import com.beakon.newheart.intents.IntentFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Charles on 7/14/2017.
 */

@ActivityScope
public class ChristlikeAttributesRootView extends BaseRootView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.christlikeLVquestionList)
    ListView listView;

    private final IntentFactory intents;

    @Inject
    public ChristlikeAttributesRootView(@NonNull @ActivityContext Context context,
                                        @NonNull IntentFactory intents) {
        super(context);
        this.intents = intents;

        addView(inflate(getContext(), R.layout.christlike_attributes, null));
        ButterKnife.bind(this);

        initToolbar();
    }

    public void initListView(ArrayList<ChristlikeQuizQuestion> list) {
        //Create the adapter to convert our array to views
        QuizQuestionAdapter adapter = new QuizQuestionAdapter(this.getContext(), list);

        //Attach the custom adapter to the ListView
        listView.setAdapter(adapter);
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