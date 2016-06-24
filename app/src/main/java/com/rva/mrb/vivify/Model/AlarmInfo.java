package com.rva.mrb.vivify.Model;

import io.realm.RealmObject;

/**
 * Created by rigo on 6/23/16.
 */
public class AlarmInfo extends RealmObject {

    private String mTime;
    private boolean mVibrate;

    public AlarmInfo(String time) { mTime = time; }

    public String getTime() { return mTime; }
}
