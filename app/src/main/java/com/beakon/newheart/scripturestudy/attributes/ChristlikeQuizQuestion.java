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

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.beakon.newheart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charles on 10/18/2017.
 */

@Table(name = "ChristlikeQuizQuestions")
public class ChristlikeQuizQuestion extends Model{

    public static final int ATTR_FAITH = 0;
    public static final int ATTR_HOPE = 1;
    public static final int ATTR_CHARITY_LOVE = 2;
    public static final int ATTR_VIRTUE = 3;
    public static final int ATTR_KNOWLEDGE = 4;
    public static final int ATTR_PATIENCE = 5;
    public static final int ATTR_HUMILITY = 6;
    public static final int ATTR_DILIGENCE = 7;
    public static final int ATTR_OBEDIENCE = 8;

    @Column(name = "q_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int id;

    @Column(name = "Question_Text")
    public String questionText;

    @Column(name = "Checked_Radio_Button_Pos")
    public int checkedRadioButtonPos;

    @Column(name = "Attribute_Category")
    public int attributeCategory;


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

    /**
     * Calculates and returns the results from the completed quiz
     * @return an array with length 9 corresponding to the attributes
     * in {@link ChristlikeQuizQuestion}
     */
    public static int[] results(List<ChristlikeQuizQuestion> questions) {
        int[] result = {0,0,0,0,0,0,0,0,0};
        for (ChristlikeQuizQuestion q: questions) {
            result[q.attributeCategory] += q.checkedRadioButtonPos;
        }
        return result;
    }

    public static List<ChristlikeQuizQuestion> getAll() {
        // This is how you execute a query
        return new Select()
                .from(ChristlikeQuizQuestion.class)
                .orderBy("q_id ASC")
                .execute();
    }
}