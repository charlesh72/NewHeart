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

package com.beakon.newheart.activities.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.beakon.newheart.R;

import java.util.ArrayList;

/**
 * Created by Charles on 11/2/2017.
 */

public class ActOfServiceAdapter extends
        RecyclerView.Adapter<ActOfServiceAdapter.ViewHolder> {

    private ArrayList<ActOfServiceDay> lightDays;
    public Context context;

    public ActOfServiceAdapter(Context context, ArrayList<ActOfServiceDay> lightDays) {
        this.context = context;
        this.lightDays = lightDays;
    }

    @Override
    public ActOfServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View serviceActView = inflater.inflate(R.layout.item_light_day_2016, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(serviceActView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ActOfServiceAdapter.ViewHolder holder, int position) {
        ActOfServiceDay serviceDay = lightDays.get(position);

        holder.day.setText(String.valueOf(serviceDay.getDay()));
        holder.title.setText(serviceDay.getTitle());

        holder.act1.setText(serviceDay.getAct(1));
        holder.act1.setChecked(serviceDay.getCBox(1));
        holder.act1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO: 11/9/2017 Update if a box is checked
        });

        holder.act2.setText(serviceDay.getAct(2));
        holder.act2.setChecked(serviceDay.getCBox(2));
        holder.act2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO: 11/9/2017 Update if a box is checked
        });

        holder.act3.setText(serviceDay.getAct(3));
        holder.act3.setChecked(serviceDay.getCBox(3));
        holder.act3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO: 11/9/2017 Update if a box is checked
        });
    }

    @Override
    public int getItemCount() {
        return lightDays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView day;

        public TextView title;

        public CheckBox act1;

        public CheckBox act2;

        public CheckBox act3;

        public ViewHolder(View itemView) {
            super(itemView);

            day = (TextView) itemView.findViewById(R.id.lightDayTVDay);
            title = (TextView) itemView.findViewById(R.id.lightDayTVTitle);
            act1 = (CheckBox) itemView.findViewById(R.id.lightDayCBact1);
            act2 = (CheckBox) itemView.findViewById(R.id.lightDayCBact2);
            act3 = (CheckBox) itemView.findViewById(R.id.lightDayCBact3);

        }
    }
}
