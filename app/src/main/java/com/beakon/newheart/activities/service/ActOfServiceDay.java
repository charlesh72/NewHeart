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

import java.util.GregorianCalendar;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Charles on 11/1/2017.
 */

public class ActOfServiceDay extends RealmObject{

    @Ignore
    public static int LIGHT_TITLES_ID = R.array.light_the_world_titles;
    @Ignore
    public static int LIGHT_ACTS_ID = R.array.light_the_world_acts;

    @PrimaryKey
    public int day;

    public String title;

    // Three choices for service.
    public String act1, act2, act3;
    public boolean cBox1, cBox2, cBox3;

    public ActOfServiceDay() {
    }

    public ActOfServiceDay(int day, String title, String act1, String act2, String act3) {
        this.day = day;
        this.title = title;
        this.act1 = act1;
        this.act2 = act2;
        this.act3 = act3;
        cBox3 = false;
        cBox1 = false;
        cBox2 = false;
    }

    public GregorianCalendar getDate() {
        GregorianCalendar date = new GregorianCalendar(2017, 12, day);
        return date;
    }

    public static void createActsList() {
        Resources res = HabitsApplication.context.getResources();
        String[] titles = res.getStringArray(LIGHT_TITLES_ID);
        String[] acts = res.getStringArray(LIGHT_ACTS_ID);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        int days = 25;
        for (int i = 0; i < days; i++) {
            int day = i + 1;
            String title = titles[i];
            String act1 = acts[i * 3];
            String act2 = acts[i * 3 + 1];
            String act3 = acts[i * 3 + 2];
            ActOfServiceDay actOfServiceDay =
                    new ActOfServiceDay(day, title, act1, act2, act3);
            realm.copyToRealm(actOfServiceDay);
        }
        realm.commitTransaction();
    }

    public static RealmResults<ActOfServiceDay> getActsList() {
        return Realm.getDefaultInstance().where(ActOfServiceDay.class).findAll().sort("day");
    }
}