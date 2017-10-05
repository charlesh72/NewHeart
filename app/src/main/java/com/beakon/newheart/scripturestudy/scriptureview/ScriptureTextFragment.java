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

package com.beakon.newheart.scripturestudy.scriptureview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beakon.newheart.R;
import com.beakon.newheart.scripturestudy.Scripture;

/**
 * Created by Dan on 10/24/2015.
 */
public class ScriptureTextFragment extends Fragment {
    private static final String ARGSKEY_SCRIPTURE = "ScriptureTextFrag.scripture";
    private Scripture scripture;

    public static ScriptureTextFragment createNew(Scripture scripture) {
        ScriptureTextFragment f = new ScriptureTextFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGSKEY_SCRIPTURE, scripture);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_scripture_text, container, false);
        // just in case I change the layout in the future:
        TextView tv = (TextView) view.findViewById(R.id.scripture_text);

        Bundle args = getArguments();
        scripture = args.getParcelable(ARGSKEY_SCRIPTURE);
        tv.setText(scripture.getBody());

        return view;
    }
}
