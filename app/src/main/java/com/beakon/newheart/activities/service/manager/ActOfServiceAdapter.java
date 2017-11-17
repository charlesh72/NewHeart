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

package com.beakon.newheart.activities.service.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.beakon.newheart.R;
import com.beakon.newheart.activities.service.ActOfService;

import java.util.List;

/**
 * Created by Charles on 11/16/2017.
 */

public class ActOfServiceAdapter extends ArrayAdapter<ActOfService> {

    public static final int layoutResourceId = R.layout.act_of_service_list_item;
    List<ActOfService> list;

    public ActOfServiceAdapter(@NonNull Context context, List<ActOfService> list) {
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

            viewHolder.rowCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int pos = (Integer) buttonView.getTag();
                // TODO: 11/17/2017 Possibly do something on check, or remove and create a on confirm method
            });
        } else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.rowCheckBox.setTag(position);

        viewHolder.rowCheckBox.setText(list.get(position).text);

        return row;
    }

    static class ViewHolder {
        CheckBox rowCheckBox = null;

        ViewHolder(View row) {
            this.rowCheckBox = (CheckBox) row.findViewById(R.id.serviceListItemCBItem);
        }
    }
}