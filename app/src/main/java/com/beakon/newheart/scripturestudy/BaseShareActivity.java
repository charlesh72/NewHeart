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

package com.beakon.newheart.scripturestudy;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;

import com.beakon.newheart.HabitsApplication;
import com.beakon.newheart.activities.BaseActivity;
import com.beakon.newheart.scripturestudy.accountability.AccountabilityFriend;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Charles on 10/11/2017.
 */

@RuntimePermissions
public class BaseShareActivity extends BaseActivity {

    private BaseShareComponent component;
    private BaseShareController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HabitsApplication app = (HabitsApplication) getApplicationContext();

        component = DaggerBaseShareComponent
                .builder()
                .appComponent(app.getComponent())
                .build();

        controller = component.getController();
    }

    public void share(String text, String ref) {
        BaseShareActivityPermissionsDispatcher.shareEntryWithCheck(this, text, ref);
    }

    /**
     * Takes the note content and the scripture reference and shares it
     * with the current active share accountability friends.
     * @param text The contents of the note
     * @param ref The reference to the scripture
     */
    @NeedsPermission(Manifest.permission.SEND_SMS)
    public void shareEntry(String text, String ref) {
        // TODO: 8/12/2017 Doesn't send text when first asking for the permission.
        // Add scripture reference to beginning of message
        text = ref.concat(" " + text);

        // Send the text
        SmsManager smsManager = SmsManager.getDefault();

        int length = text.length();

        List<AccountabilityFriend> list = AccountabilityFriend.getAllShareActive();
        for (AccountabilityFriend a : list) {
            controller.toggleTodaysShareGoal();
            // Check length of text, if greater than sms message limit then split it up.
            if (length > 160) {
                ArrayList<String> messagelist = smsManager.divideMessage(text);

                smsManager.sendMultipartTextMessage(a.phone, null, messagelist, null, null);
            } else {
                smsManager.sendTextMessage(a.phone, null, text, null, null);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        BaseShareActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
