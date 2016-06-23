package com.rva.mrb.vivify;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by rigo on 6/23/16.
 */
public class AlarmAdapter extends ExpandableRecyclerAdapter<AlarmHolder, AlarmInfoHolder> {

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
}
