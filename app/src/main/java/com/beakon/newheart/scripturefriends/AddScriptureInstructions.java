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

package com.beakon.newheart.scripturefriends;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.beakon.newheart.R;

public class AddScriptureInstructions extends AppActivity {

    public static final String GOSPEL_LIBRARY_PACKAGE_NAME = "org.lds.ldssa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scripture_instructions);

        // Set up Gospel Library button
        Button b = (Button) findViewById(R.id.button_open_gospel_library);
        if (isGospelLibraryInstalled()) {
            b.setText(R.string.open_gospel_library);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // End this activity
                    // Launch Gospel Library app
                    Intent i = getPackageManager().getLaunchIntentForPackage(GOSPEL_LIBRARY_PACKAGE_NAME);
                    startActivity(i);
                }
            });
        } else {
            b.setText(R.string.install_gospel_library);
            // Open Google play to install the app.
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    installGospelLibrary();
                }
            });
        }

        // Set up Import SM button
        Button buttonSM = (Button) findViewById(R.id.button_import_sm);
        buttonSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScriptureMasteryHelper.showDialog(AddScriptureInstructions.this, new Runnable() {
                    @Override
                    public void run() {
                        finish(); // Finish the activity after the user imports SM scriptures
                    }
                });
            }
        });

        // Start tutorial animation
        ImageView demo = (ImageView) findViewById(R.id.how_to_add_view);
        AnimationDrawable ad = (AnimationDrawable) demo.getDrawable();
        ad.start();
    }

    /**
     * Launches the Play store (or a browser as fallback) to install the Gospel Library app.
     */
    private void installGospelLibrary() {
        finish();
        try {
            // Try to open using the Play app directly
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                    "market://details?id=" + GOSPEL_LIBRARY_PACKAGE_NAME)));
        } catch (ActivityNotFoundException e) {
            // Fallback: use Play store URL
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                    "https://play.google.com/store/apps/details?id="
                            + GOSPEL_LIBRARY_PACKAGE_NAME)));
        }
    }

    private boolean isGospelLibraryInstalled() {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(GOSPEL_LIBRARY_PACKAGE_NAME, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
