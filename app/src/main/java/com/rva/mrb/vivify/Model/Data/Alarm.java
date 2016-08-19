package com.rva.mrb.vivify.Model.Data;

import android.content.Intent;
import android.util.Log;

import com.rva.mrb.vivify.Model.Service.WakeReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Alarm extends RealmObject {

    public static final String TAG = Alarm.class.getSimpleName();
    public static final String TIME_FORMAT = "hh:mm a";

    @PrimaryKey
    private String id;
    private String alarmLabel;
    private boolean enabled;
    private boolean mStandardTime;
    private String mWakeTime;
    private String mRepeat;
    private Date time;
    private Date createdAt;

    public Alarm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlarmLabel() {
        return alarmLabel;
    }

    public void setAlarmLabel(String alarmLabel) {
        this.alarmLabel = alarmLabel;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getTime() {
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
//        try {
//            Log.d(TAG, "Alarm wake time: " + mWakeTime);
//            cal.setTime(sdf.parse(mWakeTime));
//            Log.d(TAG, "Parsed time: " + cal.getTime());
//            Log.d(TAG, "Time set by setTime(): " + time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return time;
    }

    public void setTime(String wakeTime) {
        Log.d("SetTime", "Method Start");
        Calendar cal = Calendar.getInstance();
        Log.d("SetTime", "Current Time:" + cal.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        try {
            time = sdf.parse(wakeTime);
            cal.setTime(time);
            Log.d("SetTime", "Hour: " + cal.get(Calendar.HOUR));
            Log.d("SetTime", "Minute: " + cal.get(Calendar.MINUTE));
            Log.d("SetTime", "AMPM: " + cal.get(Calendar.AM_PM));
            Log.d("SetTime", "Set time to " + cal.getTime());

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR));
            calendar.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
            calendar.set(Calendar.AM_PM, cal.get(Calendar.AM_PM));
            Log.d("SetTime", "New time " + calendar.getTime());
            Calendar currentTime = Calendar.getInstance();
            if (calendar.before(currentTime))
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            time = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

//    public Intent getAlarmIntent() {
//        return new Intent(WakeReceiver.class);
//    }

}
