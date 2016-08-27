package com.rva.mrb.vivify.Model.Data;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Alarm extends RealmObject {

    public static final String TAG = Alarm.class.getSimpleName();
    public static final String TIME_FORMAT = "hh:mm a";

    public static final int FLAG_NEXT_ALARM = 24;

    // used to set when to repeat an alarm
    // flipping bits to set days
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 4;
    public static final int WEDNESDAY = 8;
    public static final int THURSDAY = 16;
    public static final int FRIDAY = 32;
    public static final int SATURDAY = 64;

    @PrimaryKey
    private String id;
    private String alarmLabel;
    private boolean enabled;
    private int hour;
    private int minute;
    private int am_pm;
    private boolean is24hr;
    private String mWakeTime;
    private String daysOfWeek;
    private Date time;
    private Date createdAt;
    private String trackName;
    private String artist;
    private String trackId;
    private String trackImage;

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

    public boolean is24hr() {
        return is24hr;
    }

    public void set24hr(boolean is24hr) {
        this.is24hr = is24hr;
    }

    public String getmWakeTime() {
        return mWakeTime;
    }

    public void setmWakeTime(String mWakeTime) {
        this.mWakeTime = mWakeTime;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int isAm_pm() {
        return am_pm;
    }

    public void setAm_pm(int am_pm) {
        this.am_pm = am_pm;
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
            setHour(cal.get(Calendar.HOUR));
            calendar.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
            setMinute(cal.get(Calendar.MINUTE));
            calendar.set(Calendar.AM_PM, cal.get(Calendar.AM_PM));
            setAm_pm(cal.get(Calendar.AM_PM));
            Log.d("SetTime", "New time " + calendar.getTime());
            Calendar currentTime = Calendar.getInstance();
            if (calendar.before(currentTime))
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            time = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date updateTime() {
        Calendar calendar = Calendar.getInstance();
        if(time.before(Calendar.getInstance().getTime())) {
            Calendar update = Calendar.getInstance();
            int day = mapToAlarmDays(calendar.get(Calendar.DAY_OF_WEEK));

//            update.add(Calendar.DAY_OF_WEEK, getNextDayEnabled(day));
            // parse days of week and set on next day available
            // if days of week = 0 then set for next day
//            if (getDecDaysOfWeek()==0)
//                update.add(Calendar.DAY_OF_WEEK, 1);
//            else if ((getDecDaysOfWeek() & SUNDAY) == SUNDAY)
//                update.add(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//            else if ((getDecDaysOfWeek() & MONDAY) == MONDAY)
//                update.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            update.set(Calendar.HOUR_OF_DAY, hour);
            update.set(Calendar.MINUTE, minute);
            update.set(Calendar.AM_PM, am_pm);
            update.add(Calendar.DAY_OF_YEAR, getNextDayEnabled());
//            update.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            time = update.getTime();
        }
        return time;
    }

    public Long getTimeInMillis() {
        return time.getTime();
    }

    public int getNextDayEnabled() {
        Calendar next = Calendar.getInstance();

        int todaysDay = mapToAlarmDays(next.get(Calendar.DAY_OF_WEEK));
        int daysFromNow = 0;
        if (getDecDaysOfWeek()==0)
            daysFromNow = 1;
        else
            for (int days = 1; days <= 7; days++) {
                if ((getDecDaysOfWeek() & todaysDay) == todaysDay) {
                    daysFromNow = days;
                    break;
                }
                next.add(Calendar.DAY_OF_YEAR, 1);
                todaysDay = mapToAlarmDays(next.get(Calendar.DAY_OF_WEEK));

            }
        //                switch ((getDecDaysOfWeek() & todaysDay)) {
//                    case SUNDAY:
//                        day =1;
//                        break;
//                    default:
//                        break;
//                }
//        else if ((getDecDaysOfWeek() & SUNDAY) == SUNDAY)
//            update.add(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//        else if ((getDecDaysOfWeek() & MONDAY) == MONDAY)
//            update.add(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // map calendar days to alarm days
//        if (getDecDaysOfWeek() == 0)
//            return Calendar
//        if ((SUNDAY & daysOfWeek))
        Log.d(TAG, "Next alarm occurence is in " + daysFromNow + " days");
        return daysFromNow;
    }

    public int mapToAlarmDays(int calendarDay) {
        switch (calendarDay) {
            case 1:
                return SUNDAY;
            case 2:
                return MONDAY;
            case 3:
                return TUESDAY;
            case 4:
                return WEDNESDAY;
            case 5:
                return THURSDAY;
            case 6:
                return FRIDAY;
            case 7:
                return SATURDAY;
            default:
                return 0;
        }
    }

    public int mapToCalendarDays(int alarmDay) {
        switch (alarmDay) {
            case 1:
                return Calendar.SUNDAY;
            case 2:
                return Calendar.MONDAY;
            case 4:
                return Calendar.TUESDAY;
            case 8:
                return Calendar.WEDNESDAY;
            case 16:
                return Calendar.THURSDAY;
            case 32:
                return Calendar.FRIDAY;
            case 64:
                return Calendar.SATURDAY;
            default:
                return 0;
        }
    }

    public int getDecDaysOfWeek() {
        return Integer.parseInt(daysOfWeek, 2);
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackImage() {
        return trackImage;
    }

    public void setTrackImage(String trackImage) {
        this.trackImage = trackImage;
    }
}
