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

package com.beakon.newheart.scripturestudy.list;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.BaseActivity;
import com.beakon.newheart.scripturestudy.AddScriptureInstructions;
import com.beakon.newheart.scripturestudy.Scripture;
import com.beakon.newheart.scripturestudy.ScriptureMasteryHelper;

import java.io.File;

public class ScriptureListActivity extends BaseActivity {

    public enum Category {
        IN_PROGRESS,
        COMPLETED
    }

    private final static String TAG = "SLISTACTIVITY";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private MainPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 8/4/2017 Set up dagger dependency injection for menus at least

        setContentView(R.layout.activity_new_main);

        // Set up the App Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Migrate scriptures from old file system
        File ipDir = Scripture.getDir(this, Category.IN_PROGRESS);
        File parentDir = ipDir.getParentFile();
        File oldDir = new File(parentDir, "app_present");
        if (oldDir.exists()) {
            File[] oldFiles = oldDir.listFiles();
            for (File f : oldFiles) {
                Log.d(TAG, "old = " + f.getAbsolutePath());
                File newPath = new File(ipDir, f.getName());
                Log.d(TAG, "new = " + newPath.getAbsolutePath());
                f.renameTo(newPath);
            }
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        int margin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        mViewPager.setPageMargin(margin);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        // Set up "Add" floating action button.
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Launch activity with instructions for adding scriptures
//                Intent i = new Intent(ScriptureListActivity.this.getApplicationContext(), AddScriptureInstructions.class);
//                startActivity(i);
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();

//        // One in fifty times, show a SnackBar prompting the user to view the about page
//        int rand = new Random().nextInt(50);
//        if (rand == 33) {
//            Snackbar s = Snackbar.make(findViewById(R.id.fab),
//                    R.string.popup_like_this_app, Snackbar.LENGTH_LONG);
//            s.setAction(R.string.popup_contribute, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(ScriptureListActivity.this, AboutActivity.class));
//                }
//            });
//            s.show();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_insert_sm) {
            ScriptureMasteryHelper.showDialog(this, new Runnable() {
                @Override
                public void run() {
                    refreshScriptureLists();
                }
            });
            return true;
        } else if (id == R.id.actionAdd) {
            // Launch activity with instructions for adding scriptures
            Intent i = new Intent(ScriptureListActivity.this.getApplicationContext(), AddScriptureInstructions.class);
            i.putExtra(Intent.EXTRA_TITLE, "Entry");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshScriptureLists() {
        mSectionsPagerAdapter.refreshLists();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class MainPagerAdapter extends FragmentPagerAdapter {

        private ScriptureListFragment[] frags = new ScriptureListFragment[2];

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private Category getCategoryForPosition(int position) {
            if (position == 0) {
                return Category.IN_PROGRESS;
            } else if (position == 1) {
                return Category.COMPLETED;
            } else {
                Log.e("MainPagerAdapter", "getCategoryForPosition() called with invalid position");
                return null;
            }
        }

        @Override
        public Fragment getItem(int position) {
            if (frags[position] == null) {
                frags[position] = ScriptureListFragment.newInstance(getCategoryForPosition(position));
            }
            return frags[position];
        }

        @Override
        public int getCount() {
            // Show 2 total pages - "In Progress" and "Completed"
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Category cat = getCategoryForPosition(position);
            switch (cat) {
                case IN_PROGRESS:
                    return "In Progress";
                case COMPLETED:
                    return "Completed";
                default:
                    // Invalid position
                    return null;
            }
        }

        public void refreshLists() {
            for (ScriptureListFragment f : frags) {
                f.refreshScriptureList();
            }
        }
    }

}
