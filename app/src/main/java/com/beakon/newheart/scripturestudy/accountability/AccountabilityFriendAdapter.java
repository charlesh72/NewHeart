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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.service.ActOfService;
import com.beakon.newheart.activities.service.DaysActsOfService;

import org.w3c.dom.Text;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Charles on 11/16/2017.
 */

public class AccountabilityFriendAdapter extends ArrayAdapter<AccountabilityFriend> {

    public static final int layoutResourceId = R.layout.accountability_friend_list_item;
    List<AccountabilityFriend> list;

    public AccountabilityFriendAdapter(@NonNull Context context, List<AccountabilityFriend> list) {
        super(context, 0, list);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId, null);
            viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);

            viewHolder.rowSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int pos = (Integer) buttonView.getTag();
                list.get(pos).setActive(isChecked);
            });
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.rowSwitch.setTag(position);

        viewHolder.rowSwitch.setChecked(list.get(position).active);
        viewHolder.rowName.setText(list.get(position).name);
        viewHolder.rowPhone.setText(list.get(position).phone);

        return row;
    }

    static class ViewHolder {

        TextView rowName = null;
        TextView rowPhone = null;
        Switch rowSwitch = null;

        ViewHolder(View row) {
            this.rowName = (TextView) row.findViewById(R.id.accFriendItemTVName);
            this.rowPhone = (TextView) row.findViewById(R.id.accFriendItemTVPhone);
            this.rowSwitch = (Switch) row.findViewById(R.id.accFriendItemSactive);
        }
    }
}