package com.rva.mrb.vivify.View.AddNewAlarm;

import com.rva.mrb.vivify.BasePresenter;
import com.rva.mrb.vivify.Model.Alarm;

public interface AlarmDetailPresenter extends BasePresenter<AlarmDetailView>{
    void onAddClick(String name, String time, boolean isSet, boolean isStandardTime, String repeat);
    Alarm getAlarm(int index);
    void onDeleteAlarm(int alarmid);
    void onSaveAlarm(int alarmid,String name,String time, boolean isSet, boolean isStandardTime, String repeat);
}