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
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Charles on 8/7/2017.
 */

@ActivityScope
public class ChristlikeAttributesController {

    private static int ATTRIBUTES_ID = R.array.christlike_attributes;
    private static int[] QUESTION_IDS = {R.array.cl_attr_faith, R.array.cl_attr_hope, R.array.cl_attr_charity_love, R.array.cl_attr_virtue, R.array.cl_attr_knowledge, R.array.cl_attr_patience, R.array.cl_attr_humility, R.array.cl_attr_diligence, R.array.cl_attr_obedience};
    public static int NUM_ATTRIBUTES = QUESTION_IDS.length;
    private static final int NUM_QUESTIONS = 56;

    private static final String PREFS_KEY = "QuizData";
    private static final String RESULTS_KEY = "results";

    @NonNull
    private final ChristlikeAttributesScreen screen;

    @NonNull
    private final BaseSystem system;
    @NonNull
    private final ChristlikeAttributesRootView rootView;

    @Inject
    public ChristlikeAttributesController(@NonNull ChristlikeAttributesScreen screen,
                                          @NonNull BaseSystem system,
                                          @NonNull ChristlikeAttributesRootView rootView) {
        this.screen = screen;
        this.system = system;
        this.rootView = rootView;
    }

    public void initializeQuizQuestions(Context context) {
        Resources res = context.getResources();
        String[] clAttributes = res.getStringArray(ATTRIBUTES_ID);

        ArrayList<ChristlikeQuizQuestion> arrayOfQs = new ArrayList<>();
        for (int i = 0; i < NUM_ATTRIBUTES; i++) {
            String[] questions = res.getStringArray(QUESTION_IDS[i]);

            for (int j = 0; j < questions.length; j++) {
                int attrCategory = i;
                String question = questions[j];
                int id = i + j + 1;
                ChristlikeQuizQuestion quizQuestion =
                        new ChristlikeQuizQuestion(id, question, attrCategory);
                quizQuestion.save();
                arrayOfQs.add(quizQuestion);
            }
        }
        rootView.initListView(arrayOfQs);
    }

    public void loadQuizQuestions(Context context) {
        try {
            List<ChristlikeQuizQuestion> arrayOfQs = ChristlikeQuizQuestion.getAll();
            rootView.initListView(arrayOfQs);
        } catch (NullPointerException e) {
            e.printStackTrace();
            // If the getAll() fails due to NullPointerException,
            // initialize the questions with no radio button selected
            initializeQuizQuestions(context);
        }
    }

    public void finishQuiz() {
        QuizQuestionAdapter adapter = rootView.getAdapter();
        // Show a message if the quiz is not complete
        if (!adapter.quizComplete()) {
            Toast.makeText(rootView.getContext(), "Unable to use results unless you complete the enitre quiz.", Toast.LENGTH_SHORT).show();
        } else { //If it is complete save the question data and the results
            adapter.saveAll();
            // Finally bring us back to the home screen
            screen.showHomeScreen();
        }

        Log.i("CLATTRCONTROLLER", "Results:" + Arrays.toString(ChristlikeQuizQuestion.results(adapter.questions)));


    }
}