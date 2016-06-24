package com.rva.mrb.vivify.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.rva.mrb.vivify.Model.Alarm;
import com.rva.mrb.vivify.Model.AlarmInfo;
import com.rva.mrb.vivify.R;

import java.util.List;

/**
 * Created by rigo on 6/23/16.
 */
public class AlarmAdapter extends ExpandableRecyclerAdapter<AlarmAdapter.AlarmHolder, AlarmAdapter.AlarmInfoHolder> {

    private LayoutInflater mInflator;

    public AlarmAdapter(Context context, @NonNull List<? extends ParentListItem> parentListItem) {
        super(parentListItem);
        mInflator = LayoutInflater.from(context);
    }

    public AlarmHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View alarmView = mInflator.inflate(R.layout.alarm_view, parentViewGroup, false);
        return new AlarmHolder(alarmView);
    }

    public AlarmInfoHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View alarmInfo = mInflator.inflate(R.layout.alarm_info_view, childViewGroup, false);
        return new AlarmInfoHolder(alarmInfo);
    }
    public void onBindParentViewHolder(AlarmHolder alarmHolder, int postion, ParentListItem parentListItem) {
        Alarm alarm = (Alarm) parentListItem;
        alarmHolder.bind(alarm);
    }

    public void onBindChildViewHolder(AlarmInfoHolder alarmInfoHolder, int position, Object childListItem) {
        AlarmInfo alarmInfo = (AlarmInfo) childListItem;
        alarmInfoHolder.bind(alarmInfo);
    }

    public class AlarmHolder extends ParentViewHolder {

        private TextView mAlarmTV;

        public AlarmHolder(View view) {
            super(view);
            mAlarmTV = (TextView) view.findViewById(R.id.alarm_tv);
        }

        public void bind(Alarm alarm) { mAlarmTV.setText(alarm.getName()); }

    }

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
}
