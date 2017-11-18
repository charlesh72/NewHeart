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

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Charles on 11/10/2017.
 */

public class ActOfService extends RealmObject {

    @PrimaryKey
    public String text;

    @LinkingObjects("acts")
    private final RealmResults<DaysActsOfService> days = null;

    public ActOfService() {

    }

    public ActOfService(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActOfService){
            return text.equals(((ActOfService) obj).text);
        }
        return super.equals(obj);
    }
}
