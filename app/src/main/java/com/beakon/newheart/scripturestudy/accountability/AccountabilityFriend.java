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
    public String phone;

    String name;

    boolean active;

    public AccountabilityFriend() {

    }

    public AccountabilityFriend(String name, String phone, boolean active) {
        this.name = name;
        this.phone = phone;
        this.active = active;
    }

    public void setActive(boolean active) {
        Realm.getDefaultInstance().beginTransaction();
        this.active = active;
        Realm.getDefaultInstance().commitTransaction();
    }

    public static List<AccountabilityFriend> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<AccountabilityFriend> results = realm.where(AccountabilityFriend.class).findAll();
        List<AccountabilityFriend> list = new ArrayList<>();
        for (AccountabilityFriend a : results) {
            list.add(a);
        }
        return list;
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

    public static void addFriend(AccountabilityFriend friend) {
        Realm realm = Realm.getDefaultInstance();
        Realm.getDefaultInstance().beginTransaction();
        realm.copyToRealmOrUpdate(friend);
        Realm.getDefaultInstance().commitTransaction();
    }

    public static void removeInactive() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<AccountabilityFriend> results = realm.where(AccountabilityFriend.class).equalTo("active", false).findAll();
        realm.beginTransaction();
        for (AccountabilityFriend a : results) {
            a.deleteFromRealm();
        }
        realm.commitTransaction();
    }
}
