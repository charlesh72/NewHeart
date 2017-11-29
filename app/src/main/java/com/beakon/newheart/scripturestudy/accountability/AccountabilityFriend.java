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

    boolean shareActive;
    boolean helpActive;

    public AccountabilityFriend() {

    }

    public AccountabilityFriend(String name, String phone, boolean helpActive, boolean shareActive) {
        this.name = name;
        this.phone = phone;
        this.helpActive = helpActive;
        this.shareActive = shareActive;
    }

    public void setShareActive(boolean shareActive) {
        Realm.getDefaultInstance().beginTransaction();
        this.shareActive = shareActive;
        Realm.getDefaultInstance().commitTransaction();
    }

    public void setHelpActive(boolean helpActive) {
        Realm.getDefaultInstance().beginTransaction();
        this.helpActive = helpActive;
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

    public static List<AccountabilityFriend> getAllShareActive() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<AccountabilityFriend> results = realm.where(AccountabilityFriend.class).equalTo("shareActive", true).findAll();
        List<AccountabilityFriend> list = new ArrayList<>();
        for (AccountabilityFriend a : results) {
            list.add(a);
        }
        return list;
    }

    public static List<AccountabilityFriend> getAllHelpActive() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<AccountabilityFriend> results = realm.where(AccountabilityFriend.class).equalTo("helpActive", true).findAll();
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
        RealmResults<AccountabilityFriend> results = realm.where(AccountabilityFriend.class)
                .equalTo("shareActive", false).equalTo("helpActive", false).findAll();
        realm.beginTransaction();
        for (AccountabilityFriend a : results) {
            a.deleteFromRealm();
        }
        realm.commitTransaction();
    }
}
