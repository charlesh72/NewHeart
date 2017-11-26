package com.beakon.newheart.scripturestudy.accountability;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Charles on 11/25/2017.
 */

public class AccountabilityFriend extends RealmObject {

    @PrimaryKey
    String phone;

    String name;

    boolean active;

    public AccountabilityFriend() {

    }

    public AccountabilityFriend(String name, String phone, boolean active) {
        this.name = name;
        this.phone = phone;
        this.active = active;
    }

    public static List<AccountabilityFriend> getAllActive() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<AccountabilityFriend> results = realm.where(AccountabilityFriend.class).equalTo("active", true).findAll();
        List<AccountabilityFriend> list = new ArrayList<>();
        for (AccountabilityFriend a : results) {
            list.add(a);
        }
        return list;
    }

    public void setActive(boolean active) {
        Realm.getDefaultInstance().beginTransaction();
        this.active = active;
        Realm.getDefaultInstance().commitTransaction();
    }
}
