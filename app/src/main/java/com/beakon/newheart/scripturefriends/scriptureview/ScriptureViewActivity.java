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

package com.beakon.newheart.scripturefriends.scriptureview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.beakon.newheart.R;
import com.beakon.newheart.scripturefriends.AppActivity;
import com.beakon.newheart.scripturefriends.ExportActivity;
import com.beakon.newheart.scripturefriends.NewMainActivity;
import com.beakon.newheart.scripturefriends.Scripture;
import com.beakon.newheart.scripturefriends.memorize.MemorizeActivity;
import com.beakon.newheart.scripturefriends.memorize.MemorizeTestActivity;

public class ScriptureViewActivity extends AppActivity {
    public static final int NUM_OF_TABS = 2;
    public static final int IDX_SCRIPTURE_TAB = 0;
    public static final int IDX_NOTES_TAB = 1;

    public class ScriptureViewAdapter extends FragmentPagerAdapter {
        final NotesViewFragment notesFrag;
        final ScriptureTextFragment scriptureFrag;

        public ScriptureViewAdapter(FragmentManager fm) {
            super(fm);
            notesFrag = NotesViewFragment.createNew(scripture);
            scriptureFrag = ScriptureTextFragment.createNew(scripture);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case IDX_SCRIPTURE_TAB:
                    return scriptureFrag;
                case IDX_NOTES_TAB:
                    return notesFrag;
                default:
                    Log.e("ScriptureViewAdapter", "Unsupported page index: " + position);
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case IDX_SCRIPTURE_TAB:
                    return getResources().getString(R.string.tab_title_scripture);
                case IDX_NOTES_TAB:
                    return getResources().getString(R.string.tab_title_notes);
                default:
                    Log.e("ScriptureViewAdapter", "Unsupported page index: " + position);
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_OF_TABS;
        }
    }

    // The result code that this activity ends with iff the scripture has been deleted.
    public static final int RESULT_SCRIPTURE_DELETED = 2;
    // The scripture being shown in this activity
    Scripture scripture;

    private ViewPager pager;
    private ScriptureViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scripture_view);

        // Set up the App Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the Scripture to display
        Intent intent = getIntent();
        if (intent.hasExtra(ScriptureIntent.EXTRA_SCRIPTURE)) {
            scripture = intent.getParcelableExtra(ScriptureIntent.EXTRA_SCRIPTURE);
        }
        if (scripture == null) {
            // If no Scripture was put into the intent, abort the Activity and report an error.
            Log.e("ScriptureViewActivity", "ScriptureViewActivity was launched without a Scripture!");
            finish();
            return;
        }

        // Display the scripture reference in the Activty title
        String reference = scripture.getReference();
        ActionBar ab = getSupportActionBar();
        //ab.setTitle(reference);


        // Set up the page fragments (scripture view and notes)
        adapter = new ScriptureViewAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Set up tabs
        TabLayout tl = (TabLayout) findViewById(R.id.tabs);
        tl.setTabsFromPagerAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl));
        tl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Nothing doing!
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Nothing doing!
            }
        });

        // Set the result to RESULT_OK by default.
        setResult(RESULT_OK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scripture_view, menu);

        MenuItem markCompleted = menu.findItem(R.id.action_mark_completed);
        if (scripture.isCompleted()) {
            markCompleted.setTitle(R.string.menu_mark_in_progress);
        } else {
            markCompleted.setTitle(R.string.menu_mark_complete);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_delete) {// Delete this scripture
            scripture.deleteWithConfirmation(this, new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
            return true;
        } else if (i == R.id.action_add_note) {
            adapter.notesFrag.launchAddNoteActivity();
            return true;
        } else if (i == R.id.action_memorize) {// Open the Memorize view
            startActivity(new ScriptureIntent(this, MemorizeActivity.class, scripture));
            return true;
        } else if (i == R.id.action_start_test) {// Launch MemorizeTestActivity
            startActivity(new ScriptureIntent(this, MemorizeTestActivity.class, scripture));
            return true;
        } else if (i == R.id.action_mark_completed) {// Mark the scripture as completed/incompleted - i.e. toggle the category
            if (!scripture.isCompleted()) {
                scripture.changeCategory(this, NewMainActivity.Category.COMPLETED);
                item.setTitle(R.string.menu_mark_in_progress);
            } else {
                scripture.changeCategory(this, NewMainActivity.Category.IN_PROGRESS);
                item.setTitle(R.string.menu_mark_complete);
            }
            finish();
            return true;
        } else if (i == R.id.action_export) {
            startActivity(new ScriptureIntent(this, ExportActivity.class, scripture));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
