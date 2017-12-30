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
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.beakon.newheart.R;
import com.beakon.newheart.scripturestudy.accountability.AccountabilityFriend;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Charles on 11/24/2017.
 */

@RuntimePermissions
public class HelpActivity extends BaseShareActivity {

    public static final String HELP_PREF = "help_activity";

    SharedPreferences myPrefs;

    @BindView(R.id.helpBgetImage)
    Button getImage;

    @BindView(R.id.helpIVimage)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activty_layout);

        ButterKnife.bind(this);

        HelpActivityPermissionsDispatcher.shareHelpWithCheck(this);

        myPrefs = getSharedPreferences(HELP_PREF, 0);

        try {
            final Uri imageUri = Uri.parse(myPrefs.getString("image", "defaultString"));
            HelpActivityPermissionsDispatcher.setImageWithCheck(this, imageUri);

        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @OnClick(R.id.helpBgetImage)
    public void getImage() {
        pickImage();
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void setImage(Uri imageUri) {
        try {
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(selectedImage);
        } catch (Exception e) {
            Toast.makeText(this, "Your photo can not be found.", Toast.LENGTH_SHORT).show();
        }
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 5);
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);

        if (result == RESULT_OK) {
            final Uri imageUri = data.getData();
            HelpActivityPermissionsDispatcher.setImageWithCheck(this, imageUri);

            SharedPreferences.Editor myPrefsEdit = myPrefs.edit();

            myPrefsEdit.putString("image", imageUri.toString());
            myPrefsEdit.apply();
        } else {
            Toast.makeText(HelpActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    @NeedsPermission(Manifest.permission.SEND_SMS)
    void shareHelp() {
        SmsManager smsManager = SmsManager.getDefault();

        String text = "Help! I'm about to make a terrible mistake. Please call me ASAP!";

        List<AccountabilityFriend> list = AccountabilityFriend.getAllHelpActive();
        for (AccountabilityFriend a : list) {
            smsManager.sendTextMessage(a.phone, null, text, null, null);
        }
    }
}
