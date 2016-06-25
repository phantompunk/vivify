package com.rva.mrb.vivify.View.Alarm;

import com.rva.mrb.vivify.Model.RealmService;

/**
 * Created by Bao on 6/24/16.
 */
public class AlarmPresenterImpl{

    private final RealmService mRealmService;

    public AlarmPresenterImpl(RealmService realmService){
        mRealmService = realmService;
    }

    public String getRSMessage(){
        return mRealmService.getMessage();
    }

    public String getMessage(){
        return "SUCESSFULL!!!";
    }

}
