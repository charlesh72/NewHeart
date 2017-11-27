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

package com.beakon.newheart.scripturestudy.accountability;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.beakon.newheart.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountabilityFriendsActivity extends AppCompatActivity {

    public static final String NAME_KEY = "name";
    public static final String PHONE_KEY = "phone";
    public static final String SHARE_KEY = "share";

    @BindView(R.id.accFriendsETName)
    EditText name;

    @BindView(R.id.accFriendsETPhone)
    EditText phone;

    @BindView(R.id.accFriendsLVfriends)
    ListView listView;

    AccountabilityFriendAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountability_friends);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initListView();
    }

    private void initListView() {
        List<AccountabilityFriend> list = AccountabilityFriend.getAll();

        adapter = new AccountabilityFriendAdapter(this, list);

        listView.setAdapter(adapter);
    }

    /**
     * Sets the current accountability friend using the arguments
     */
    private void saveAndFinish() {

        String nameEntry = name.getText().toString();
        String phoneEntry = phone.getText().toString();

        String errorMessage = "";
        boolean error = false;
        if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneEntry)) {
            errorMessage = errorMessage + "The phone number is not valid.";
            error = true;
        }
        if (nameEntry.length() < 1) {
            errorMessage = errorMessage + "You didn't enter a name.";
            error = true;
        }
        if (error) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        AccountabilityFriend friend = new AccountabilityFriend(nameEntry, phoneEntry, true);

        AccountabilityFriend.addFriend(friend);

        finish();
    }

    private void removeInactive() {
        AccountabilityFriend.removeInactive();
        adapter = new AccountabilityFriendAdapter(this, AccountabilityFriend.getAll());
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acc_friends, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_done) {
            saveAndFinish();
            return true;
        } else if (id == R.id.action_remove_inactive){
            removeInactive();
        }

        return super.onOptionsItemSelected(item);
    }
}
