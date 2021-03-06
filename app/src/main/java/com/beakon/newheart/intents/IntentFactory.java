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

package com.beakon.newheart.intents;

import android.content.*;
import android.net.*;
import android.support.annotation.*;

import com.beakon.newheart.*;
import com.beakon.newheart.activities.BaseActivity;
import com.beakon.newheart.activities.about.*;
import com.beakon.newheart.activities.habits.list.ListHabitsActivity;
import com.beakon.newheart.activities.habits.show.*;
import com.beakon.newheart.activities.home.HomeActivity;
import com.beakon.newheart.activities.intro.*;
import com.beakon.newheart.activities.service.manager.ServiceManagerActivity;
import com.beakon.newheart.activities.settings.*;
import com.beakon.newheart.models.*;

import com.beakon.newheart.activities.service.ServiceActivity;
import com.beakon.newheart.scripturestudy.attributes.ChristlikeAttributesActivity;
import com.beakon.newheart.scripturestudy.attributes.results.AttributesResultsActivity;
import com.beakon.newheart.scripturestudy.list.ScriptureListActivity;

import javax.inject.*;

public class IntentFactory
{
    @Inject
    public IntentFactory()
    {
    }

    public Intent helpTranslate(Context context)
    {
        String url = context.getString(R.string.translateURL);
        return buildViewIntent(url);
    }

    public Intent rateApp(Context context)
    {
        String url = context.getString(R.string.playStoreURL);
        return buildViewIntent(url);
    }

    public Intent sendFeedback(Context context)
    {
        String url = context.getString(R.string.feedbackURL);
        return buildSendToIntent(url);
    }

    public Intent startAboutActivity(Context context)
    {
        return new Intent(context, AboutActivity.class);
    }

    public Intent startIntroActivity(Context context)
    {
        return new Intent(context, IntroActivity.class);
    }

    public Intent startSettingsActivity(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    public Intent startHomeActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    public Intent startShowHabitActivity(Context context, Habit habit)
    {
        Intent intent = new Intent(context, ShowHabitActivity.class);
        intent.setData(habit.getUri());
        return intent;
    }

    public Intent startListHabitsActivity(Context context) {
        Intent intent = new Intent(context, ListHabitsActivity.class);
        return intent;
    }

    public Intent startScriptureStudyActivity(Context context) {
        Intent intent = new Intent(context, ScriptureListActivity.class);
        return intent;
    }

    public Intent startAttributesQuizActivity(Context context) {
        Intent intent = new Intent(context, ChristlikeAttributesActivity.class);
        return intent;
    }

    public Intent startServiceActivity(Context context) {
        Intent intent = new Intent(context, ServiceActivity.class);
        return intent;
    }

    public Intent startResultsActivity(Context context) {
        Intent intent = new Intent(context, AttributesResultsActivity.class);
        return intent;
    }

    public Intent startServiceManagerActivity(Context context) {
        Intent intent = new Intent(context, ServiceManagerActivity.class);
        return intent;
    }

    public Intent viewFAQ(Context context)
    {
        String url = context.getString(R.string.helpURL);
        return buildViewIntent(url);
    }

    public Intent viewSourceCode(Context context)
    {
        String url = context.getString(R.string.sourceCodeURL);
        return buildViewIntent(url);
    }

    @NonNull
    private Intent buildSendToIntent(String url)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(url));
        return intent;
    }

    @NonNull
    private Intent buildViewIntent(String url)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        return intent;
    }
}
