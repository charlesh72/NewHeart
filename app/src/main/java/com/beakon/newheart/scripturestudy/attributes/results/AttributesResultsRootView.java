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

package com.beakon.newheart.scripturestudy.attributes.results;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityContext;
import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseRootView;
import com.beakon.newheart.intents.IntentFactory;
import com.beakon.newheart.scripturestudy.attributes.ChristlikeQuizQuestion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Charles on 11/15/2017.
 */

@ActivityScope
public class AttributesResultsRootView extends BaseRootView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.attrResultsLLContainer)
    LinearLayout lLayout;

    private final IntentFactory intents;

    @Inject
    public AttributesResultsRootView(@NonNull @ActivityContext Context context,
                                        @NonNull IntentFactory intents) {
        super(context);
        this.intents = intents;

        addView(inflate(getContext(), R.layout.attributes_results, null));
        ButterKnife.bind(this);

        loadResults();

        initToolbar();
    }

    private void loadResults() {
        List<ChristlikeQuizQuestion> list = ChristlikeQuizQuestion.loadQuizQuestions();
        int[] results = ChristlikeQuizQuestion.results(list);
        double[] percentages = ChristlikeQuizQuestion.resultsAsPercent(results);
        String[] attrs = ChristlikeQuizQuestion.getAttributes();

        // Find the lowest scoring
        double low = 1;
        int lowId = 0;
        for (int i = 0; i < percentages.length; i++) {
            if (percentages[i] < low) {
                low = percentages[i];
                lowId = i;
            }
        }

        for (int i = 0; i < attrs.length; i++) {
            TextView tv = new TextView(getContext());
            DecimalFormat df = new DecimalFormat("##%");
            String formattedPercent = df.format(percentages[i]);
            tv.setText(attrs[i] + " - " + formattedPercent);
            tv.setId(i);
            tv.setTextSize(18);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT)); // Add weight param to fill height of screen
            // Set the lowest scoring attribute to a different color
            if (i == lowId) {
                tv.setTextColor(Color.BLUE);
            }
            lLayout.addView(tv);
        }
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