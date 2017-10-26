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

import com.beakon.newheart.R;

/**
 * Created by Charles on 10/18/2017.
 */

public class ChristlikeQuizQuestion {

    public static final int ATTR_FAITH = 0;
    public static final int ATTR_HOPE = 1;
    public static final int ATTR_CHARITY_LOVE = 2;
    public static final int ATTR_VIRTUE = 3;
    public static final int ATTR_KNOWLEDGE = 4;
    public static final int ATTR_PATIENCE = 5;
    public static final int ATTR_HUMILITY = 6;
    public static final int ATTR_DILIGENCE = 7;
    public static final int ATTR_OBEDIENCE = 8;

    public String questionText;

    public int checkedRadioButtonPos;

    public int attributeCategory;

    public ChristlikeQuizQuestion() {
        questionText = "";
        attributeCategory = -1;
        checkedRadioButtonPos = -1;
    }

    /**
     * COPY constructor that uses string as input
     * @param stringData
     */
    public ChristlikeQuizQuestion(String stringData) {
        copyFromString(stringData);
    }

    public ChristlikeQuizQuestion(String question, int attributeCategory) {
        this.questionText = question;
        this.attributeCategory = attributeCategory;
        // Set -1 as default (nothing checked)
        checkedRadioButtonPos = -1;
    }

    public void copyFromString(String data) {
        attributeCategory = Integer.parseInt(data.substring(0,1));
        checkedRadioButtonPos = Integer.parseInt(data.substring(2,3));
        questionText = data.substring(4);
    }

    public String toString() {
        return attributeCategory + " " + checkedRadioButtonPos + " " + questionText;
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
}