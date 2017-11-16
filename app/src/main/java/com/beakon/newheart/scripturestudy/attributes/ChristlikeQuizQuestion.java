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

import android.content.res.Resources;

import com.beakon.newheart.HabitsApplication;
import com.beakon.newheart.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Charles on 10/18/2017.
 */

public class ChristlikeQuizQuestion extends RealmObject{

    public static final int ATTR_FAITH = 0;
    public static final int ATTR_HOPE = 1;
    public static final int ATTR_CHARITY_LOVE = 2;
    public static final int ATTR_VIRTUE = 3;
    public static final int ATTR_KNOWLEDGE = 4;
    public static final int ATTR_PATIENCE = 5;
    public static final int ATTR_HUMILITY = 6;
    public static final int ATTR_DILIGENCE = 7;
    public static final int ATTR_OBEDIENCE = 8;

    private static final int ATTRIBUTES_ID = R.array.christlike_attributes;
    private static final int[] QUESTION_IDS = {R.array.cl_attr_faith, R.array.cl_attr_hope, R.array.cl_attr_charity_love, R.array.cl_attr_virtue, R.array.cl_attr_knowledge, R.array.cl_attr_patience, R.array.cl_attr_humility, R.array.cl_attr_diligence, R.array.cl_attr_obedience};
    public static final int NUM_ATTRIBUTES = QUESTION_IDS.length;
    private static final int NUM_QUESTIONS = 56;

    @PrimaryKey
    public int id;

    private String questionText;

    private int checkedRadioButtonPos;

    private int attributeCategory;

    public ChristlikeQuizQuestion() {
        questionText = "empty";
        checkedRadioButtonPos = -1;
        attributeCategory = -1;
    }

    public ChristlikeQuizQuestion(int id, String question, int attributeCategory) {
        this.questionText = question;
        this.id = id;
        this.attributeCategory = attributeCategory;
        // Set 0 as default (nothing checked)
        checkedRadioButtonPos = 0;
    }


    /**
     * Uses the radio button ids to find the score for the question
     * @param checkedRadioButtonId
     */
    public void setCheckedRadioButtonPos(int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
            case R.id.quizRB1:
                checkedRadioButtonPos = 1;
                break;
            case R.id.quizRB2:
                checkedRadioButtonPos = 2;
                break;
            case R.id.quizRB3:
                checkedRadioButtonPos = 3;
                break;
            case R.id.quizRB4:
                checkedRadioButtonPos = 4;
                break;
            case R.id.quizRB5:
                checkedRadioButtonPos = 5;
                break;
            default:
                checkedRadioButtonPos = -1;
        }
    }

    /**
     * Finds the ID of the radio button corresponding to the store pos
     * @return
     */
    public int getCheckedRadioButtonId() {
        int checkedId = -1;
        switch (checkedRadioButtonPos) {
            case 1:
                checkedId = R.id.quizRB1;
                break;
            case 2:
                checkedId = R.id.quizRB2;
                break;
            case 3:
                checkedId = R.id.quizRB3;
                break;
            case 4:
                checkedId = R.id.quizRB4;
                break;
            case 5:
                checkedId = R.id.quizRB5;
                break;
        }
        return checkedId;
    }

    public int getCheckedRadioButtonPos() {
        return checkedRadioButtonPos;
    }

    /**
     * Calculates and returns the results from the completed quiz
     * @return an array with length 9 corresponding to
     * each of the 9 attributes
     * in {@link ChristlikeQuizQuestion}
     */
    public static int[] results(List<ChristlikeQuizQuestion> questions) {
        int[] result = {0,0,0,0,0,0,0,0,0};
        for (ChristlikeQuizQuestion q: questions) {
            result[q.attributeCategory] += q.checkedRadioButtonPos;
        }
        return result;
    }

    public static double[] resultsAsPercent(int[] results) {
        double[] perc = {0,0,0,0,0,0,0,0,0};
        int[] perfect = perfectScore();
        for (int i = 0; i < results.length; i++) {
            perc[i] = ((double)results[i]) / perfect[i];
        }
        return perc;
    }

    /**
     * Simply returns a perfect score array matching the results
     * @return an array containing the highest score for each attribute category
     */
    public static int[] perfectScore(){
        int[] perf = {45, 20, 50, 30, 25, 30, 30, 30, 20};
        return perf;
    }

    public String getQuestionText() {
        return questionText;
    }

    public static List<ChristlikeQuizQuestion> initializeQuizQuestions() {
        Resources res = HabitsApplication.context.getResources();
        String[] clAttributes = res.getStringArray(ATTRIBUTES_ID);

        ArrayList<ChristlikeQuizQuestion> arrayOfQs = new ArrayList<>();
        for (int i = 0; i < NUM_ATTRIBUTES; i++) {
            String[] questions = res.getStringArray(QUESTION_IDS[i]);

            for (int j = 0; j < questions.length; j++) {
                int attrCategory = i;
                String question = questions[j];
                int id = arrayOfQs.size();
                ChristlikeQuizQuestion quizQuestion =
                        new ChristlikeQuizQuestion(id, question, attrCategory);
                arrayOfQs.add(quizQuestion);
            }
        }
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final List<ChristlikeQuizQuestion> managedArray = realm.copyToRealmOrUpdate(arrayOfQs);
        realm.commitTransaction();
        return managedArray;
    }

    public static List<ChristlikeQuizQuestion> loadQuizQuestions() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ChristlikeQuizQuestion> questions = realm.where(ChristlikeQuizQuestion.class).findAll();
        List<ChristlikeQuizQuestion> arrayOfQs;
        if (questions.size() != NUM_QUESTIONS) {
            arrayOfQs = initializeQuizQuestions();
        } else {
            arrayOfQs = new ArrayList<>(NUM_QUESTIONS);
            questions = questions.sort("id");
            for (int i = 0; i < NUM_QUESTIONS; i++) {
                arrayOfQs.add(questions.get(i));
            }
        }
        return arrayOfQs;
    }
}