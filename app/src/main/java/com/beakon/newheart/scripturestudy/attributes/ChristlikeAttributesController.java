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
import android.util.Log;
import android.widget.Toast;

import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.activities.BaseSystem;

import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by Charles on 8/7/2017.
 */

@ActivityScope
public class ChristlikeAttributesController {

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


    public void populateQuiz() {
        rootView.initListView(ChristlikeQuizQuestion.loadQuizQuestions());
    }

    public void finishQuiz() {
        QuizQuestionAdapter adapter = rootView.getAdapter();
        // Show a message if the quiz is not complete
        int[] results = ChristlikeQuizQuestion.results(adapter.questions);
        Log.i("CLATTRCONTROLLER", "Results:" + Arrays.toString(results));
        Log.i("CLATTRCONTROLLER", "Perfect:" + Arrays.toString(ChristlikeQuizQuestion.perfectScore()));
        Log.i("CLATTRCONTROLLER", "Percentage:" + Arrays.toString(ChristlikeQuizQuestion.resultsAsPercent(results)));

        if (!adapter.quizComplete()) {
            Toast.makeText(rootView.getContext(), "Unable to use results unless you complete the enitre quiz.", Toast.LENGTH_SHORT).show();
        } else
            {
            screen.showResultsScreen();
        }


    }
}