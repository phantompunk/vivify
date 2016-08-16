package com.rva.mrb.vivify.Model.Data;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Alarm extends RealmObject {

    @PrimaryKey
    private String id;
    private String mAlarmName;
    private boolean mIsSet;
    private boolean mStandardTime;
    private String mWakeTime;
    private String mRepeat;
    private Date time;
    public Alarm() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmAlarmName() {
        return mAlarmName;
    }

    public void setmAlarmName(String mAlarmName) {
        this.mAlarmName = mAlarmName;
    }

    public boolean ismIsSet() {
        return mIsSet;
    }

    public void setmIsSet(boolean mIsSet) {
        this.mIsSet = mIsSet;
    }

    public boolean ismStandardTime() {
        return mStandardTime;
    }

    public void setmStandardTime(boolean mStandardTime) {
        this.mStandardTime = mStandardTime;
    }

    public String getmWakeTime() {
        return mWakeTime;
    }

    public void setmWakeTime(String mWakeTime) {
        this.mWakeTime = mWakeTime;
    }

    public String getmRepeat() {
        return mRepeat;
    }

    public void setmRepeat(String mRepeat) {
        this.mRepeat = mRepeat;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
