package com.rva.mrb.vivify.Model.Data;

import android.util.Log;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.AlarmRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.internal.Keep;

@Parcel (implementations = {AlarmRealmProxy.class},
    value = Parcel.Serialization.BEAN,
    analyze = {Alarm.class})
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
    //    private int hour;
//    private int minute;
//    private int am_pm;
    private boolean is24hr;
    private String mWakeTime;
    private String daysOfWeek;
    private Date time;
    private Date createdAt;
    private String trackName;
    private String artist;
    private String trackId;
    private String trackImage;
    private int mediaType;

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
        if (isEnabled()) {
            updateTime();
        }
    }

    private void clearTime() {
        setTime(new Date());
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

//    public int getHour() {
//        return hour;
//    }
//
//    public void setHour(int hour) {
//        this.hour = hour;
//    }
//
//    public int getMinute() {
//        return minute;
//    }
//
//    public void setMinute(int minute) {
//        this.minute = minute;
//    }
//
//    public int isAm_pm() {
//        return am_pm;
//    }
//
//    public void setAm_pm(int am_pm) {
//        this.am_pm = am_pm;
//    }

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
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(time);
//        return cal;
    }

    public Calendar getCal() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal;
    }

//    public String getTimeAsString() {
//
//    }

//    public void setTime(String wakeTime) {
//        Calendar timeHolder = Calendar.getInstance();
//        Calendar timeUpdate = Calendar.getInstance();
////        Log.d(TAG, "Current Time:" + cal.getTime());
//        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
//        try {
//            time = sdf.parse(wakeTime);
//            timeHolder.setTime(time);
////            Log.d("SetTime", "Hour: " + timeHolder.get(Calendar.HOUR));
////            Log.d("SetTime", "Minute: " + cal.get(Calendar.MINUTE));
////            Log.d("SetTime", "AMPM: " + cal.get(Calendar.AM_PM));
////            Log.d("SetTime", "Set time to " + cal.getTime());
//
//            // update the time from the parsed time string
//            timeUpdate.set(Calendar.HOUR, timeHolder.get(Calendar.HOUR));
//            timeUpdate.set(Calendar.MINUTE, timeHolder.get(Calendar.MINUTE));
//            timeUpdate.set(Calendar.AM_PM, timeHolder.get(Calendar.AM_PM));
//            timeUpdate.set(Calendar.SECOND, 0);
//
//            // while were at it save the values
//            setHour(timeHolder.get(Calendar.HOUR));
//            setMinute(timeHolder.get(Calendar.MINUTE));
//            setAm_pm(timeHolder.get(Calendar.AM_PM));
//
//            Log.d(TAG, "New time " + timeUpdate.getTime());
//            time = timeUpdate.getTime();
//            // update the time if necessary
//
//////            Calendar currentTime = Calendar.getInstance();
////            if (timeUpdate.before(Calendar.getInstance()))
////                timeUpdate.add(Calendar.DAY_OF_YEAR, 1);
//
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    public Date updateTime() {
        // update alarm time if necessary
//        if(getDaysOfWeek().equals("0")) {
//            setEnabled(false);
//        }
        if (getCal().before(Calendar.getInstance())) {
            // holds new date
            Calendar update = Calendar.getInstance();
            update.set(Calendar.HOUR, getCal().get(Calendar.HOUR_OF_DAY));
            update.set(Calendar.MINUTE, getCal().get(Calendar.MINUTE));
            update.set(Calendar.AM_PM, getCal().get(Calendar.AM_PM));
            update.set(Calendar.SECOND, 0);

            // checks to find the next available day
            update.add(Calendar.DAY_OF_YEAR, getNextDayEnabled());
            Log.d(TAG, "Alarm time: " + update.getTime());
            time = update.getTime();
        }
        return time;
    }

    public Long getTimeInMillis() {
        return getCal().getTimeInMillis();
    }

    public int getNextDayEnabled() {
        // this method only gets called if the alarm is old
        // meaning we can roll the date ahead one day
        // right off the bat and then begin checking
        Calendar next = Calendar.getInstance();

        //next.add(Calendar.DAY_OF_YEAR, 1);

        // convert the Calendar day to a value we can use
        int todaysDay = mapToAlarmDays(next.get(Calendar.DAY_OF_WEEK));
        Log.d("Day", "Binary day: " + todaysDay);

        // roll the calendar this many days forward
        int daysFromNow = 0;

        Log.d("alarm.java", "repeat today: " + ((getDecDaysOfWeek() & todaysDay)==1));
        Log.d("alarm.java", "getDecDaysOfWeek: " + getDecDaysOfWeek());
        Log.d("alarm.java", "getDecDaysOfWeek & today: " + (getDecDaysOfWeek() & todaysDay));


        // we are using bitwise operations to store multiple values in a
        // single column since Realm does not support an array of primitive
        // data types. We are storing a string of up to 7 binary digits.We
        // can think of this as a row of switches each with an on/off button
        // corresponding to each day.
        if (getDecDaysOfWeek() == 0) {
            Calendar cal = Calendar.getInstance();
            boolean before = time.before(cal.getTime());
            Log.d("date", "before current date: " + before);
            if (time.before(cal.getTime())) {
                daysFromNow = 1;
            } else {
                daysFromNow = 0;
            }
            return daysFromNow;
        }
        else if ((getDecDaysOfWeek() & todaysDay) == todaysDay) {
            Calendar calendar = Calendar.getInstance();
            Log.d("alarm.java", "time: " + time);
            Log.d("alarm.java", "current time: " + calendar.getTime());
            Log.d("alarm.java", "before: " + !time.before(calendar.getTime()));


            if (!time.before(calendar.getTime())) {
                Log.d("alarm object", "before: " + !time.before(calendar.getTime()));
                daysFromNow = 0;
                return daysFromNow;
            }

        }
            next.add(Calendar.DAY_OF_YEAR, 1);
            Calendar current = Calendar.getInstance();
            for (int days = 1; days <= 7; days++) {
                todaysDay = mapToAlarmDays(next.get(Calendar.DAY_OF_WEEK));
                Log.d(TAG, "Next days is " + todaysDay);
                daysFromNow = days;

                // In this case we are checking to see if today is contained
                // in our binary repeat days value. This is done by utilizing the
                // & operation, remember that is will only flip the bit of the
                // digit corresponding to this day. If thats true then it must equal
                // the binary value of today
                if ((getDecDaysOfWeek() & todaysDay) == todaysDay) {
                    //if (!time.before(current.getTime())) {
                    Log.d(TAG, "Fire when");
                    daysFromNow = days;
                    break;
                    //}

                }
                // If today is not contained within the binary string then
                // roll the date foreword one day
                next.add(Calendar.DAY_OF_YEAR, 1);
            }


        Log.d(TAG, "Next alarm occurence is in " + daysFromNow + " days");
        return daysFromNow;
    }

    /**
     * Match any Calendar.Day_Of_Week value to the corresponding int value in
     * Alarm so we can decipher our days enabled binary string
     * @param calendarDay any Calendar.Day_Of_Week (ex. Sunday = 1, Saturday = 7)
     * @return a binary value of that day
     */
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

//    public int mapToCalendarDays(int alarmDay) {
//        switch (alarmDay) {
//            case 1:
//                return Calendar.SUNDAY;
//            case 2:
//                return Calendar.MONDAY;
//            case 4:
//                return Calendar.TUESDAY;
//            case 8:
//                return Calendar.WEDNESDAY;
//            case 16:
//                return Calendar.THURSDAY;
//            case 32:
//                return Calendar.FRIDAY;
//            case 64:
//                return Calendar.SATURDAY;
//            default:
//                return 0;
//        }
//    }

    public int getDecDaysOfWeek() {
        return (daysOfWeek != null) ? Integer.parseInt(daysOfWeek, 2) : 0;
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

    public int getHour() {
        return getCal().get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return getCal().get(Calendar.MINUTE);
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }
}
