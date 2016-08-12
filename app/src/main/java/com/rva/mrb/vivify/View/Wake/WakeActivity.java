package com.rva.mrb.vivify.View.Wake;

import android.os.Bundle;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.R;

import javax.inject.Inject;

public class WakeActivity extends BaseActivity {

    @Inject
    WakePresenter wakePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        WakeComponent wakeComponent = DaggerWakeComponent.builder()
                .applicationModule(new ApplicationModule((AlarmApplication) getApplication()))
                .wakeModule(new WakeModule(this))
                .applicationComponent(((AlarmApplication) getApplication()).getComponent())
                .build();
        wakeComponent.inject(this);


    }

//    @Override
//    protected void onResume() {}

    @Override
    protected void closeRealm() {

    }

}
