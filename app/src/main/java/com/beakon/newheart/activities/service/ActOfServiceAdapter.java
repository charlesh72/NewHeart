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
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ACT2016 = 0;
    private final int ACT2017 = 1;

    private ArrayList<ActOfServiceDay> lightDays;
    public Context context;

    public ActOfServiceAdapter(Context context, ArrayList<ActOfServiceDay> lightDays) {
        this.context = context;
        this.lightDays = lightDays;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        RecyclerView.ViewHolder viewHolder;

        // Inflate the custom layout
        switch (viewType) {
            case ACT2016:
                View actView16 = inflater.inflate(R.layout.item_light_day_2016, parent, false);
                viewHolder = new ViewHolder2016(actView16);
                break;
            case ACT2017:
                View actView17 = inflater.inflate(R.layout.item_light_day_2017, parent, false);
                viewHolder = new ViewHolder2017(actView17);
                break;
            default:
                View actViewDefault = inflater.inflate(R.layout.item_light_day_2017, parent, false);
                viewHolder = new ViewHolderLightDay(actViewDefault);
                break;
        }

        // Return a new holder instance
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ACT2016:
                ViewHolder2016 vh2016 = (ViewHolder2016) holder;
                configureViewHolder2016(vh2016, position);
                break;
            case ACT2017:
                ViewHolder2017 vh2017 = (ViewHolder2017) holder;
                configureViewHolder2017(vh2017, position);
                break;
            default:
                break;
        }


    }

    private void configureViewHolder2016(ViewHolder2016 holder, int position) {
        configueViewHolderLightDay(holder, position);
    }

    private void configureViewHolder2017(ViewHolder2017 holder, int position) {
        configueViewHolderLightDay(holder, position);

        ActOfServiceDay2017 serviceDay = (ActOfServiceDay2017) lightDays.get(position);
        holder.ref.setText(serviceDay.getRef());
    }

    private void configueViewHolderLightDay(ViewHolderLightDay holder, int position) {
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

    @Override
    public int getItemViewType(int position) {
        if (lightDays.get(position) instanceof ActOfServiceDay2016) {
            return ACT2016;
        } else if (lightDays.get(position) instanceof ActOfServiceDay2017) {
            return ACT2017;
        }
        return -1;
    }

    public class ViewHolderLightDay extends RecyclerView.ViewHolder {

        public TextView day;

        public TextView title;

        public CheckBox act1;

        public CheckBox act2;

        public CheckBox act3;

        public ViewHolderLightDay(View itemView) {
            super(itemView);

            day = (TextView) itemView.findViewById(R.id.lightDayTVDay);
            title = (TextView) itemView.findViewById(R.id.lightDayTVTitle);
            act1 = (CheckBox) itemView.findViewById(R.id.lightDayCBact1);
            act2 = (CheckBox) itemView.findViewById(R.id.lightDayCBact2);
            act3 = (CheckBox) itemView.findViewById(R.id.lightDayCBact3);

        }
    }

    public class ViewHolder2016 extends ViewHolderLightDay {

        public ViewHolder2016(View itemView) {
            super(itemView);
        }
    }

    public class ViewHolder2017 extends ViewHolderLightDay {

        public TextView ref;


        public ViewHolder2017(View itemView) {
            super(itemView);

            ref = (TextView) itemView.findViewById(R.id.lightDayTVRef);
        }
    }
}
