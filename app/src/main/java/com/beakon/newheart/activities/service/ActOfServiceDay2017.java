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

package com.beakon.newheart.activities.service;

import android.content.res.Resources;

import com.beakon.newheart.HabitsApplication;
import com.beakon.newheart.R;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Charles on 11/10/2017.
 */

public class ActOfServiceDay2017 extends RealmObject implements ActOfServiceDay {

    @Ignore
    public static int LIGHT_TITLES_ID = R.array.light_the_world_titles_2017;
    @Ignore
    public static int LIGHT_REFS_ID = R.array.light_the_world_ref_2017;
    @Ignore
    public static int LIGHT_ACTS_ID = R.array.light_the_world_acts_2017;

    @PrimaryKey
    public int day;

    public String title;

    public String ref;

    // Three choices for service.
    public ActOfService act1, act2, act3;
    public boolean cBox1, cBox2, cBox3;

    public ActOfServiceDay2017() {
    }

    public ActOfServiceDay2017(int day, String title, String ref, ActOfService act1, ActOfService act2, ActOfService act3) {
        this.day = day;
        this.title = title;
        this.act1 = act1;
        this.act2 = act2;
        this.act3 = act3;
        this.ref = ref;
        cBox3 = false;
        cBox1 = false;
        cBox2 = false;
    }

    public GregorianCalendar getDate() {
        GregorianCalendar date = new GregorianCalendar();
        date.set(GregorianCalendar.DAY_OF_MONTH, day);
        return date;
    }

    private static List<ActOfServiceDay2017> createActsList() {
        Resources res = HabitsApplication.context.getResources();
        String[] titles = res.getStringArray(LIGHT_TITLES_ID);
        String[] refs = res.getStringArray(LIGHT_REFS_ID);
        String[] acts = res.getStringArray(LIGHT_ACTS_ID);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        int days = 25;
        ArrayList<ActOfServiceDay2017> serviceDays = new ArrayList<>(25);
        for (int i = 0; i < days; i++) {
            int day = i + 1;
            String title = titles[i];
            String ref = refs[i];
            ActOfService act1 = realm.copyToRealm(new ActOfService(acts[i * 3]));
            ActOfService act2 = realm.copyToRealm(new ActOfService(acts[i * 3 + 1]));
            ActOfService act3 = realm.copyToRealm(new ActOfService(acts[i * 3 + 2]));
            ActOfServiceDay2017 actOfServiceDay =
                    new ActOfServiceDay2017(day, title, ref, act1, act2, act3);
            serviceDays.add(actOfServiceDay);
        }

        final List<ActOfServiceDay2017> managedArray = realm.copyToRealmOrUpdate(serviceDays);
        realm.commitTransaction();
        return managedArray;
    }

    public static List<ActOfServiceDay> getActsList() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ActOfServiceDay2017> results = realm.where(ActOfServiceDay2017.class).findAll().sort("day");
        List<ActOfServiceDay> list = new ArrayList<>(25);
        if (results.size() != 25) {
            createActsList();
            results = realm.where(ActOfServiceDay2017.class).findAll().sort("day");
        }

        for (int i = 0; i < 25; i++) {
            list.add(results.get(i));
        }
        return list;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getRef() {
        return ref;
    }

    @Override
    public ActOfService getAct(int actNum) {
        ActOfService act;
        switch (actNum) {
            case 1:
                act = act1;
                break;
            case 2:
                act = act2;
                break;
            case 3:
                act = act3;
                break;
            default:
                act = null;
        }
        return act;
    }

    @Override
    public boolean getCBox(int cBoxNum) {
        // Get the current list of currently active acts for the day
        DaysActsOfService day = DaysActsOfService.findDay(
                DaysActsOfService.findId(getDay()));
        boolean checked = false;
        switch (cBoxNum) {
            case 1:
                checked = day.contains(act1);
                break;
            case 2:
                checked = day.contains(act2);
                break;
            case 3:
                checked = day.contains(act3);
                break;
        }
        return checked;
    }

    @Override
    public void setCBox(int cBoxNum, boolean checked) {
        switch (cBoxNum) {
            case 1:
                DaysActsOfService.modifyAct(getDay(), act1, checked);
                break;
            case 2:
                DaysActsOfService.modifyAct(getDay(), act2, checked);
                break;
            case 3:
                DaysActsOfService.modifyAct(getDay(), act3, checked);
                break;
        }
    }
}
