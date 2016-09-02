package com.rva.mrb.vivify.View.Detail;

import android.app.AlarmManager;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.rva.mrb.vivify.Model.Data.Alarm;
import com.rva.mrb.vivify.Model.Service.AlarmScheduler;
import com.rva.mrb.vivify.Model.Service.RealmService;
import com.spotify.sdk.android.player.ConnectionStateCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailPresenterImpl implements DetailPresenter, RealmService.OnTransactionCallback {

    public static final String TAG = DetailPresenterImpl.class.getSimpleName();
    private final String TIME_FORMAT = "hh:mm a";
    private final RealmService mRealmService;
    private DetailView mDetailView = new DetailView.EmptyDetailView();


    public DetailPresenterImpl(RealmService realmService){ mRealmService = realmService; }

    public Alarm getAlarm(String index) {
        return mRealmService.getAlarm(index);
    }

    @Override
    public void onDeleteAlarm(String alarmid) {
        mRealmService.deleteAlarm(alarmid);
    }

    @Override
    public void onSaveAlarm(Context context,String alarmid, String name, String time, boolean isSet, boolean isStandardTime, String repeat, String trackName, String artist, String trackId, String trackImage) {
        mRealmService.saveAlarm(alarmid, name, time, isSet, isStandardTime, repeat, trackName, artist, trackId, trackImage);
//        AlarmScheduler.enableAlarmById(context, alarmid);
    }

    @Override
    public void onRealmSuccess() {

    }

    @Override
    public void onRealmError(Exception e) {

    }

    @Override
    public void onAddClick(Context context, String name, String time, boolean isSet,
                           boolean isStandardTime, String repeat, String trackName, String artist, String trackId, String trackImage) {
        mRealmService.addAlarm(name, time, isSet, isStandardTime, repeat, trackName, artist, trackId, trackImage);
        if (isSet) {
            String newestAlarmId;
            try {
                newestAlarmId = mRealmService.getNewestAlarmId();
                Log.d("New", "Alarm id is: " + newestAlarmId);
            } catch (Exception e) {
                Log.e(TAG, "Alarm not found, trying againg. " + e.getMessage());
                newestAlarmId = mRealmService.getNewestAlarmId();
            }
            if (newestAlarmId != null) {
                Log.d("realm", "Alarm id: " + newestAlarmId); // getAlarm.last()
//                AlarmScheduler.enableAlarmById(context, newestAlarmId);
            }
        }
    }

    public String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.US);
        String time = simpleDateFormat.format(cal.getTime());
        Log.d(TAG, "Current time: " + cal.getTime());
        return (time.indexOf("0")==0) ? time.substring(1): time;
    }
    public String getTime(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
//        Log.d("Calendar", "Current time " + cal.getTime());
        cal.set(Calendar.HOUR_OF_DAY, hour);
//        Log.d("Calendar", "Hour set " + cal.getTime());
        cal.set(Calendar.MINUTE, minute);
//        Log.d("Calendar", "Minute set " + cal.getTime());
//        Log.d("Alarm", "Literal Time " + cal.getTime());
        Calendar currentTime = Calendar.getInstance();
        if (cal.before(currentTime))
            cal.add(Calendar.DAY_OF_YEAR,1);

//        String am_pm = (cal.get(Calendar.AM_PM)==Calendar.AM) ? "AM" : "PM";
//        String hrString = String.valueOf(hour);
//        hrString = (hour > 12) ? String.valueOf(hour-12) : hrString;
//        String minString = String.valueOf(minute);
//        minString = (minute < 10) ? "0" + minString : minString;
//        return hrString + ":" + minString + " " + am_pm;

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(TIME_FORMAT, Locale.US);
        String time = simpleDateFormat.format(cal.getTime());
        Log.d(TAG, "Wake Time: " + cal.getTime());
        return (time.indexOf("0")==0) ? time.substring(1) : time;
    }

    @Override
    public int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    @Override
    public int getCurrentMinute() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    @Override
    public String getNewestAlarm() {
        return mRealmService.getNewestAlarmId();
    }

    @Override
    public void setView(DetailView view) {
        mDetailView = view;
    }

    @Override
    public void clearView() {
        mDetailView = new DetailView.EmptyDetailView();
    }

    @Override
    public void closeRealm() {
        mRealmService.closeRealm();
    }
}