package com.rva.mrb.vivify;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * Created by rigo on 6/23/16.
 */
public class AlarmInfoHolder extends ChildViewHolder {

    private TextView mAlarmInfoTV;

    public AlarmInfoHolder(View view) {
        super(view);
        mAlarmInfoTV = (TextView) view.findViewById(R.id.alarm_info_tv);
    }

    public void bind(AlarmInfo info) {
        mAlarmInfoTV.setText(info.getTime());
    }
}
