package com.rva.mrb.vivify.View.Detail;

import android.content.Context;

import com.rva.mrb.vivify.BasePresenter;
import com.rva.mrb.vivify.Model.Data.Alarm;

public interface DetailPresenter extends BasePresenter<DetailView>{
    void onAddClick(Context context, String name, String time, boolean isSet, boolean isStandardTime, String repeat);
    Alarm getAlarm(String index);
    void onDeleteAlarm(String alarmid);
    void onSaveAlarm(String alarmid,String name,String time, boolean isSet, boolean isStandardTime, String repeat);
}