package com.rva.mrb.vivify;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

/**
 * Created by rigo on 6/23/16.
 */
public class AlarmHolder extends ParentViewHolder {

    private TextView mAlarmTV;

    public AlarmHolder(View view) {
        super(view);
        mAlarmTV = (TextView) view.findViewById(R.id.alarm_tv);
    }

    public void bind(Alarm alarm) { mAlarmTV.setText(alarm.getName()); }

}
