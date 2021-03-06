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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.beakon.newheart.R;
import com.beakon.newheart.scripturestudy.AddScriptureInstructions;
import com.beakon.newheart.scripturestudy.ExportActivity;
import com.beakon.newheart.scripturestudy.Scripture;
import com.beakon.newheart.scripturestudy.memorize.MemorizeActivity;
import com.beakon.newheart.scripturestudy.scriptureview.AddNoteActivity;
import com.beakon.newheart.scripturestudy.scriptureview.ScriptureIntent;
import com.beakon.newheart.scripturestudy.scriptureview.ScriptureViewActivity;

import java.util.LinkedList;

public class ScriptureListFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_CATEGORY = "category";
    public static final int REQUEST_VIEW_SCRIPTURE = 1;

    private LinkedList<Scripture> mScriptureList;
    private View mContentView;
    private ListView mListView;
    private ScriptureListActivity.Category mCategory;
    private int mContextMenuPos = -1;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ScriptureListFragment newInstance(ScriptureListActivity.Category category) {
        ScriptureListFragment fragment = new ScriptureListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public ScriptureListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategory = (ScriptureListActivity.Category) getArguments().getSerializable(ARG_CATEGORY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.content_main_onelist, container, false);
        mListView = (ListView) mContentView.findViewById(R.id.scripture_list_view);

        refreshScriptureList();
        return mContentView;
    }

    // Fills or refreshes the list of Scriptures
    public void refreshScriptureList() {
        mScriptureList = Scripture.loadScriptures(getContext(), mCategory);

        if (!mScriptureList.isEmpty()) {
            mListView.setAdapter(new ArrayAdapter<Scripture>(getContext(), R.layout.list_item, R.id.item_text, mScriptureList));
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Scripture scripture = mScriptureList.get(position);
                    startActivityForResult(new ScriptureIntent(getContext(), ScriptureViewActivity.class, scripture), REQUEST_VIEW_SCRIPTURE);
                }
            });
            registerForContextMenu(mListView);
        } else if (mCategory == ScriptureListActivity.Category.IN_PROGRESS){
            // Set up the "Tap to Add a Scripture" filler list entry.
            mListView.setAdapter(new ArrayAdapter<String>(
                    getContext(),
                    R.layout.list_item,
                    R.id.item_text,
                    new String[]{getResources().getString(R.string.tap_to_add_scripture)}));
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Launch the AddScriptureInstructions activity
                    Intent i = new Intent(getContext(), AddScriptureInstructions.class);
                    startActivity(i);
                }
            });
            unregisterForContextMenu(mListView);
        } else {
            // Set up a "Memorize a scripture to put it here." filler entry.
            mListView.setAdapter(new ArrayAdapter<String>(
                    getContext(),
                    R.layout.list_item,
                    R.id.item_text,
                    new String[]{getResources().getString(R.string.complete_a_scripture)}));
            mListView.setOnItemClickListener(null);
            unregisterForContextMenu(mListView);
        }

        Log.d("ScriptureListFragment", mScriptureList.size() + " scriptures in category " + mCategory.name());
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshScriptureList();
    }

    // This is called to create the context menu for the list menu items.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_scripture, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d("ScriptureListFragment", "onCreateContextMenu " + mCategory.name());

        // Get index of item that was long-clicked
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        mContextMenuPos = info.position;
        Scripture s = mScriptureList.get(mContextMenuPos);
        Log.d("ScriptureListFragment", "scripture = " + s.getReference());
        MenuItem markCompleted = menu.findItem(R.id.action_mark_completed);
        if (s.isCompleted()) {
            markCompleted.setTitle(R.string.menu_mark_in_progress);
        } else {
            markCompleted.setTitle(R.string.menu_mark_complete);
        }

    }

    // Called when the user taps an item in a scripture context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d("ScriptureListFragment", "onContextItemSelected " + mCategory.name() + ", " + mContextMenuPos);

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position; // Get index of item that was long-clicked
        if (mContextMenuPos != pos) {
            return false;
        }
        mContextMenuPos = -1;
        Scripture s = mScriptureList.get(pos);
        Log.d("ScriptureListFragment", "scripture = " + s.getReference());

        int i = item.getItemId();
        if (i == R.id.action_delete) {// Delete this scripture
            s.deleteWithConfirmation(getContext(), new Runnable() {
                @Override
                public void run() {
                    // Refresh mScripturesList
                    refreshScriptureList();
                }
            });
            return true;
        } else if (i == R.id.action_add_note) {
            startActivity(new ScriptureIntent(getContext(), AddNoteActivity.class, s));
            return true;
        } else if (i == R.id.action_view) {
            startActivityForResult(new ScriptureIntent(getContext(), ScriptureViewActivity.class, s), REQUEST_VIEW_SCRIPTURE);
            return true;
        } else if (i == R.id.action_memorize) {
            startActivity(new ScriptureIntent(getContext(), MemorizeActivity.class, s));
            return true;
        } else if (i == R.id.action_mark_completed) {// Mark the scripture as completed/incompleted - i.e. toggle the category
            if (!s.isCompleted()) {
                s.changeCategory(getContext(), ScriptureListActivity.Category.COMPLETED);
                item.setTitle(R.string.menu_mark_in_progress);
            } else {
                s.changeCategory(getContext(), ScriptureListActivity.Category.IN_PROGRESS);
                item.setTitle(R.string.menu_mark_complete);
            }
            ((ScriptureListActivity) getActivity()).refreshScriptureLists();
            return true;
        } else if (i == R.id.action_export) {
            startActivity(new ScriptureIntent(getContext(), ExportActivity.class, s));
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
}
