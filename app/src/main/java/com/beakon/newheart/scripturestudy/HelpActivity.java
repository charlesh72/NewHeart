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
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.Toast;

import com.beakon.newheart.R;
import com.beakon.newheart.scripturestudy.accountability.AccountabilityFriend;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;

/**
 * Created by Charles on 11/24/2017.
 */

public class HelpActivity extends BaseShareActivity {

    @BindView(R.id.widgetBHelp)
    Button help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_help);

        ButterKnife.bind(this);

        help.setEnabled(false);

        Toast.makeText(this, "Message(s) attempting to send", Toast.LENGTH_SHORT).show();

        shareHelp();
    }

    @NeedsPermission(Manifest.permission.SEND_SMS)
    private void shareHelp() {
        SmsManager smsManager = SmsManager.getDefault();

        String text = "Help! I'm about to do something stupid!";

        List<AccountabilityFriend> list = AccountabilityFriend.getAllHelpActive();
        for (AccountabilityFriend a : list) {
            smsManager.sendTextMessage(a.phone, null, text, null, null);
        }
    }


}
