/*
 * Copyright (C) 2016 Álinson Santos Xavier <isoron@gmail.com>
 *
 * This file is part of Loop Habit Tracker.
 *
 * Loop Habit Tracker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Loop Habit Tracker is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.beakon.newheart.receivers;

import android.content.*;
import android.util.*;

import com.beakon.newheart.*;
import com.beakon.newheart.models.*;
import com.beakon.newheart.utils.*;

import dagger.*;

import static android.content.ContentUris.*;

/**
 * The Android BroadcastReceiver for Loop Habit Tracker.
 * <p>
 * All broadcast messages are received and processed by this class.
 */
public class ReminderReceiver extends BroadcastReceiver
{
    public static final String ACTION_DISMISS_REMINDER =
        "com.beakon.newheart.ACTION_DISMISS_REMINDER";

    public static final String ACTION_SHOW_REMINDER =
        "com.beakon.newheart.ACTION_SHOW_REMINDER";

    public static final String ACTION_SNOOZE_REMINDER =
        "com.beakon.newheart.ACTION_SNOOZE_REMINDER";

    private static final String TAG = "ReminderReceiver";

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        HabitsApplication app =
            (HabitsApplication) context.getApplicationContext();

        ReminderComponent component = DaggerReminderReceiver_ReminderComponent
            .builder()
            .appComponent(app.getComponent())
            .build();

        HabitList habits = app.getComponent().getHabitList();
        ReminderController reminderController =
            component.getReminderController();

        Log.i(TAG, String.format("Received intent: %s", intent.toString()));

        Habit habit = null;
        long today = DateUtils.getStartOfToday();

        if (intent.getData() != null)
            habit = habits.getById(parseId(intent.getData()));
        final Long timestamp = intent.getLongExtra("timestamp", today);
        final Long reminderTime = intent.getLongExtra("reminderTime", today);

        try
        {
            switch (intent.getAction())
            {
                case ACTION_SHOW_REMINDER:
                    if (habit == null) return;
                    reminderController.onShowReminder(habit, timestamp,
                        reminderTime);
                    break;

                case ACTION_DISMISS_REMINDER:
                    if (habit == null) return;
                    reminderController.onDismiss(habit);
                    break;

                case ACTION_SNOOZE_REMINDER:
                    if (habit == null) return;
                    reminderController.onSnooze(habit);
                    break;

                case Intent.ACTION_BOOT_COMPLETED:
                    reminderController.onBootCompleted();
                    break;
            }
        }
        catch (RuntimeException e)
        {
            Log.e(TAG, "could not process intent", e);
        }
    }

    @ReceiverScope
    @Component(dependencies = AppComponent.class)
    interface ReminderComponent
    {
        ReminderController getReminderController();
    }
}
