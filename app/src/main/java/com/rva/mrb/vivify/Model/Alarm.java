package com.rva.mrb.vivify.Model;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by rigo on 6/23/16.
 */
public class Alarm extends RealmObject implements ParentListItem{

    private int id;
    private String mName;
    private boolean mAlarmSet;
    private boolean mStandardTime;
    private String mTime;
    private String mRepeat;
    private List<AlarmInfo> mAlarmInfo;

    public Alarm(String name, List<AlarmInfo> info) {
        mName = name;
        mAlarmInfo = info;
    }

    public String getName() { return mName; }

    public List<?> getChildItemList() { return mAlarmInfo; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public boolean ismAlarmSet() {
        return mAlarmSet;
    }

    public void setmAlarmSet(boolean mAlarmSet) {
        this.mAlarmSet = mAlarmSet;
    }

    public boolean ismStandardTime() {
        return mStandardTime;
    }

    public void setmStandardTime(boolean mStandardTime) {
        this.mStandardTime = mStandardTime;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmRepeat() {
        return mRepeat;
    }

    public void setmRepeat(String mRepeat) {
        this.mRepeat = mRepeat;
    }

    @Override
    public boolean isInitiallyExpanded() { return false; }
}