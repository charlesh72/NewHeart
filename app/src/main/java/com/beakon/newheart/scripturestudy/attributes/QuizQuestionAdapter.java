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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.beakon.newheart.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Charles on 10/19/2017.
 */

public class QuizQuestionAdapter extends ArrayAdapter<ChristlikeQuizQuestion> {

    int layoutResourceId;
    List<ChristlikeQuizQuestion> questions;

    public QuizQuestionAdapter(Context context, List<ChristlikeQuizQuestion> questions) {
        super(context, 0, questions);
        layoutResourceId = R.layout.christlike_quiz_question;
        this.questions = questions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, null);
            holder = new ViewHolder(row);
            row.setTag(holder);
            // Set a listener to update the data in your questions list when the RadioGroup is clicked
            holder.rowRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {

                // **get which item this RadioGroup refers to**
                int pos = (Integer) group.getTag();

                Log.d("onCheckedChanged", "pos = " + pos + ", checkedId = " + checkedId);

                // Saves the selection live as at happens
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                questions.get(pos).setCheckedRadioButtonPos(checkedId);
                realm.commitTransaction();
            });
        } else {
            holder = (ViewHolder) row.getTag();
        }

        // **Tag each RadioGroup so we know what item it refers to**
        holder.rowRadioGroup.setTag(position);

        // setup both views from the values stored in your questions list
        holder.rowTextView.setText(questions.get(position).getQuestionText());
        if (questions.get(position).getCheckedRadioButtonId() != -1) {
            holder.rowRadioGroup.check(questions.get(position).getCheckedRadioButtonId());
        } else {
            holder.rowRadioGroup.clearCheck();
        }



        return row;
    }

    public boolean quizComplete() {
        boolean isComplete = true;
        for (ChristlikeQuizQuestion q : questions) {
            if (q.getCheckedRadioButtonPos() == -1) {
                isComplete = false;
            }
        }
        return isComplete;
    }

    public List<ChristlikeQuizQuestion> getQuestions() {
        return questions;
    }

    static class ViewHolder {
        TextView rowTextView = null;
        RadioGroup rowRadioGroup = null;

        ViewHolder(View row) {
            this.rowTextView = (TextView) row.findViewById(R.id.quizTVquestion);
            this.rowRadioGroup = (RadioGroup) row.findViewById(R.id.quizRadioGroup);
        }
    }
}
