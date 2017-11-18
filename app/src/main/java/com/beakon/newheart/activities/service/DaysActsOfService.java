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

import android.util.Log;

import com.beakon.newheart.utils.DateUtils;

import java.util.GregorianCalendar;

import io.realm.MutableRealmInteger;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Charles on 11/10/2017.
 */

public class DaysActsOfService extends RealmObject {

    @PrimaryKey
    long date;

    public RealmList<ActOfService> acts;

    public final MutableRealmInteger cur = MutableRealmInteger.valueOf(0);

    public DaysActsOfService() {
    }

    public DaysActsOfService(long date) {
        this.date = DateUtils.getStartOfDay(date);
        acts = new RealmList<>();
    }

    public void addActOfService(ActOfService act) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ActOfService managedAct = realm.copyToRealmOrUpdate(act);
        acts.add(managedAct);
        realm.commitTransaction();
    }

    public boolean contains(ActOfService act) {
        return acts.contains(act);
    }

    public ActOfService getNext() {
        if (acts == null || acts.size() == 0) {
            return null;
        }
        ActOfService act;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        if (cur.get() >= acts.size()) {
            cur.set(0);
        }
        act = acts.get(cur.get().intValue());
        cur.increment(1);
        realm.commitTransaction();
        return act;
    }

    public boolean removeAct(ActOfService act) {
        if (acts.contains(act)) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            acts.remove(act);
            realm.commitTransaction();
            return true;
        }
        return false;
    }

    public static long findId(int day) {
        GregorianCalendar calendar = new GregorianCalendar();
//        int dayOfMonth = calendar.get(GregorianCalendar.DAY_OF_MONTH);
//        // Set the act of service for the next month if the day has already passed
//        if (day < dayOfMonth) {
//            calendar.add(GregorianCalendar.MONTH, 1);
//        }

        calendar.set(GregorianCalendar.DAY_OF_MONTH, day);

        long id = DateUtils.getStartOfDay(calendar.getTimeInMillis());

        return id;
    }

    public static DaysActsOfService findDay(long date) {
        Realm realm = Realm.getDefaultInstance();
        DaysActsOfService target = realm.where(DaysActsOfService.class).equalTo("date", date).findFirst();

        realm.beginTransaction();
        if (target == null) {
            target = new DaysActsOfService(date);
            target = realm.copyToRealm(target);
        }
        realm.commitTransaction();
        return target;
    }

    public static void addAct(int day, ActOfService act) {
        long date = findId(day);
        DaysActsOfService target = findDay(date);

        target.addActOfService(act);
        for (ActOfService e : target.acts) {
            Log.i("ACTS: ", "id: " + target.date + " act: " + e.text);
        }
    }

    private static void removeAct(int day, ActOfService act) {
        long date = findId(day);
        DaysActsOfService target = findDay(date);

        if (target == null) {
            Log.e("TODAYSACT", "Target should exists here");
            return;
        }
        boolean removed = target.removeAct(act);
        if (!removed) {
            Log.e("TODAYSACT", "Trying to remove act that doesn't exist");
        }
    }

    public static void modifyAct(int day, ActOfService act, boolean add) {
        if (add) {
            addAct(day, act);
        } else {
            removeAct(day, act);
        }
    }
}