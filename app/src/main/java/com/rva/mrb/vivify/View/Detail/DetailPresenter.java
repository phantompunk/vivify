package com.rva.mrb.vivify.View.Detail;

import com.rva.mrb.vivify.BasePresenter;
import com.rva.mrb.vivify.Model.Data.Alarm;

public interface DetailPresenter extends BasePresenter<DetailView>{
    void onAddClick(String name, String time, boolean isSet, boolean isStandardTime, String repeat);
    Alarm getAlarm(int index);
    void onDeleteAlarm(int alarmid);
    void onSaveAlarm(int alarmid,String name,String time, boolean isSet, boolean isStandardTime, String repeat);
}