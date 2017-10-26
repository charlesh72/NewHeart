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
import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
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
                ChristlikeQuizQuestion quizQuestion =
                        new ChristlikeQuizQuestion(question, attrCategory);
                arrayOfQs.add(quizQuestion);
            }
        }
        rootView.initListView(arrayOfQs);
    }

    public void loadQuizQuestions(Context context) {
        Resources res = context.getResources();
        String[] clAttributes = res.getStringArray(ATTRIBUTES_ID);

        // Load up preference file we are using to store the quiz data
        SharedPreferences quizPrefs = rootView.getContext().getSharedPreferences("QuizData", Context.MODE_PRIVATE);
        Set<String> set = quizPrefs.getStringSet(RESULTS_KEY, null);

        if (set != null) {
            ArrayList<ChristlikeQuizQuestion> arrayOfQs = setToQuestionConversion(set);
            rootView.initListView(arrayOfQs);
        } else {
            initializeQuizQuestions(context);
        }
    }

    public void finishQuiz() {
        QuizQuestionAdapter adapter = rootView.getAdapter();
        // Show a message is the quiz is not complete
        if (!adapter.quizComplete()) {
            Toast.makeText(rootView.getContext(), "Unable to use results unless you complete the enitre quiz.", Toast.LENGTH_SHORT).show();
        }

        Log.i("CLATTRCONTROLLER", "Results:" + Arrays.toString(adapter.results()));

        // Load up preference file we are using to store the quiz data
        SharedPreferences quizPrefs = rootView.getContext().getSharedPreferences("QuizData", Context.MODE_PRIVATE);


        Set<String> resultsSet = questionToSetConversion(adapter.getQuestions());

        // Preference editor is needed to edit the data in the preference file
        SharedPreferences.Editor editor = quizPrefs.edit();
        editor.putStringSet(RESULTS_KEY, resultsSet);

        // Finally bring us back to the home screen
        screen.showHomeScreen();
    }

    /**
     * Converts the arraylist of questions into a string set to be stored in a preference file
     * @param questions the arraylist to be converted
     * @return the string set
     * */
    private Set<String> questionToSetConversion(ArrayList<ChristlikeQuizQuestion> questions) {
        Set<String> set = new LinkedHashSet<>(56);
        for (ChristlikeQuizQuestion q: questions) {
            set.add(q.toString());
            Log.i("CLATTRCONTROLLER", q.toString());
        }
        return null;
    }

    /**
     * Converts the string set into an arraylist of questions to be stored in a preference file
     * @param set the string set to be converted
     * @return the arraylist of questions
     */
    private ArrayList<ChristlikeQuizQuestion> setToQuestionConversion(Set<String> set) {
        ArrayList<ChristlikeQuizQuestion> questions = new ArrayList<>();
        for (String s: set) {
            questions.add(new ChristlikeQuizQuestion(s));
        }
        return questions;
    }
}