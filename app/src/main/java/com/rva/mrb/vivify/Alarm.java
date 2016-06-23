package com.rva.mrb.vivify;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by rigo on 6/23/16.
 */
public class Alarm implements ParentListItem{

    private String mName;
    private List<AlarmInfo> mAlarmInfo;

    public Alarm(String name, List<AlarmInfo> info) {
        mName = name;
        mAlarmInfo = info;
    }

    public String getName() { return mName; }

    public List<?> getChildItemList() { return mAlarmInfo; }

    @Override
    public boolean isInitiallyExpanded() { return false; }
}
