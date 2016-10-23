package com.rva.mrb.vivify.View.Detail;

import android.content.Context;

import com.rva.mrb.vivify.BasePresenter;
import com.rva.mrb.vivify.Model.Data.Alarm;

import java.util.Date;

public interface DetailPresenter extends BasePresenter<DetailView>{
    void onAddClick(Context context, String name, String time, boolean isSet, boolean isStandardTime, String repeat, String trackName, String artist, String trackId, String trackImage);
    void onAddClick(Alarm alarm);
    Alarm getAlarm(String index);
    void onDeleteAlarm(String alarmid);
    void onDeleteAlarm(Alarm alarm);
    void onSaveAlarm(Context applicationContext, String alarmid, String name, String time, boolean isSet, boolean isStandardTime, String repeat, String trackName, String artist, String trackId, String trackImage);
    void onSaveAlarm(Alarm alarm);

    String getCurrentTime();
    String getTime(int hour, int minute);
    Date getDate(int hour, int minute);
    int getCurrentHour();
    int getHour(Alarm alarm);
    int getCurrentMinute();
    int getMinute(Alarm alarm);

    String getNewestAlarm();

//    int getAMPM(int hour);
}