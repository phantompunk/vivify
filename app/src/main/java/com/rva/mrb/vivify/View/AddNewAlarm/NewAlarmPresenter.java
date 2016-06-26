package com.rva.mrb.vivify.View.AddNewAlarm;

import com.rva.mrb.vivify.BasePresenter;

/**
 * Created by Bao on 6/25/16.
 */
public interface NewAlarmPresenter extends BasePresenter<NewAlarmView>{
    void onAddClick(String time, boolean isSet, boolean isStandardTime, String repeat);
}
