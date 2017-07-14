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

package com.beakon.newheart;

import android.content.*;

import com.beakon.newheart.activities.habits.list.model.*;
import com.beakon.newheart.commands.*;
import com.beakon.newheart.intents.*;
import com.beakon.newheart.io.*;
import com.beakon.newheart.models.*;
import com.beakon.newheart.models.sqlite.*;
import com.beakon.newheart.notifications.*;
import com.beakon.newheart.preferences.*;
import com.beakon.newheart.tasks.*;
import com.beakon.newheart.utils.*;
import com.beakon.newheart.widgets.*;

import dagger.*;

@AppScope
@Component(modules = {
    AppModule.class, AndroidTaskRunner.class, SQLModelFactory.class
})
public interface AppComponent
{
    CommandRunner getCommandRunner();

    @AppContext
    Context getContext();

    CreateHabitCommandFactory getCreateHabitCommandFactory();

    DirFinder getDirFinder();

    EditHabitCommandFactory getEditHabitCommandFactory();

    GenericImporter getGenericImporter();

    HabitCardListCache getHabitCardListCache();

    HabitList getHabitList();

    HabitLogger getHabitsLogger();

    IntentFactory getIntentFactory();

    IntentParser getIntentParser();

    ModelFactory getModelFactory();

    NotificationTray getNotificationTray();

    PendingIntentFactory getPendingIntentFactory();

    Preferences getPreferences();

    ReminderScheduler getReminderScheduler();

    TaskRunner getTaskRunner();

    WidgetPreferences getWidgetPreferences();

    WidgetUpdater getWidgetUpdater();
}
