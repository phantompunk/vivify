package com.rva.mrb.vivify.Model.RealmHelper;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.rva.mrb.vivify.Model.Service.RealmService;

public class RealmHelper{

    private RealmService realmService;
    public RealmHelper(RealmService realmService) {
        this.realmService = realmService;
    }
}
