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

import android.widget.RemoteViewsService;

import com.beakon.newheart.utils.DateUtils;

import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.MutableRealmInteger;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Charles on 11/10/2017.
 */

public class TodaysActsOfService extends RealmObject {

    @PrimaryKey
    long date;

    public RealmList<ActOfService> acts;

    public final MutableRealmInteger cur = MutableRealmInteger.valueOf(0);

    public TodaysActsOfService() {
    }

    public TodaysActsOfService(GregorianCalendar calendar) {
        date = DateUtils.getStartOfDay(calendar.getTime().getTime());
        acts = new RealmList<>();
    }

    public void addActOfService(ActOfService act) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(act);
        realm.commitTransaction();
    }

    public ActOfService getNext() {
        if (acts == null) {
            return null;
        }
        if (cur.get() >= acts.size()) {
            cur.set(0);
        }
        ActOfService act = acts.get(cur.get().intValue());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        cur.increment(1);
        realm.commitTransaction();
        return act;
    }


    /**
     * Marks an act of service complete by removing it from the list
     * @param act The ActOfService that was marked complete
     * @return true if it was in the list and removed, false otherwise
     */
    public boolean setComplete(ActOfService act) {
        if (acts.contains(act)) {
            acts.remove(act);
            return true;
        }
        return false;
    }
}