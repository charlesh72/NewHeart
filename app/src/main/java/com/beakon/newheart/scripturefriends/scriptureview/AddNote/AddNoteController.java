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

package com.beakon.newheart.scripturefriends.scriptureview.AddNote;

import android.support.annotation.NonNull;

import com.beakon.newheart.activities.ActivityContext;
import com.beakon.newheart.activities.ActivityScope;
import com.beakon.newheart.models.Habit;
import com.beakon.newheart.models.HabitList;
import com.beakon.newheart.utils.DateUtils;

import javax.inject.Inject;

/**
 * Created by Charles on 8/23/2017.
 */

@ActivityScope
public class AddNoteController {

    @NonNull
    private final HabitList habitList;

    @Inject
    public AddNoteController(@NonNull HabitList habitList) {
        this.habitList = habitList;
    }

    /**
     * Toggles todays repetition for the insights shared default habit
     */
    public void toggleTodaysShareGoal() {
        Habit habit = habitList.getById(Habit.ID_INSIGHTS_SHARED);
        habit.getRepetitions().addTimestamp(DateUtils.getStartOfToday());
    }

}
