package com.rva.mrb.vivify.Model.AlarmHelper;


import com.rva.mrb.vivify.Model.RealmService;

public class AlarmHelper {
    private RealmService realmService;

    public AlarmHelper(RealmService realmService) {
        this.realmService = realmService;
    }

    public String getMessage() {
        return realmService.getMessage();
    }
}
