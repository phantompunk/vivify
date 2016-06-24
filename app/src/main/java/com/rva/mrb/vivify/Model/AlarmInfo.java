package com.rva.mrb.vivify.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rigo on 6/23/16.
 */
public class AlarmInfo extends RealmObject {

    @PrimaryKey
    private int id;
    private String mTime;
    private boolean mVibrate;

    public AlarmInfo() {

    }

    public AlarmInfo(String time) { mTime = time; }

    public String getTime() { return mTime; }
}
