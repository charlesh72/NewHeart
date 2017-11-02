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

package com.beakon.newheart.scripturestudy.Service;

import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Charles on 11/1/2017.
 */

public class ActOfServiceDay extends RealmObject{

    public GregorianCalendar date;

    public String title;

    // Three choices for service.
    public String act1, act2, act3;

    public ActOfServiceDay(GregorianCalendar date, String title, String act1, String act2, String act3) {
        this.date = date;
        this.title = title;
        this.act1 = act1;
        this.act2 = act2;
        this.act3 = act3;
    }

    public static RealmResults<ActOfServiceDay> createActsList() {
        int days = 25;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 0; i < days; i++) {
            GregorianCalendar day = new GregorianCalendar(2017, 12, i+1);

        }
        realm.commitTransaction();
        return realm.where(ActOfServiceDay.class).findAll();
    }
}