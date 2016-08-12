package com.rva.mrb.vivify.View.Wake;

import android.os.Bundle;
import android.widget.Button;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.Model.Service.AlarmScheduler;
import com.rva.mrb.vivify.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WakeActivity extends BaseActivity {

    @BindView(R.id.dismiss_alarm) Button dismissBt;
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
        ButterKnife.bind(this);


    }

//    @Override
//    protected void onResume() {}

    @Override
    protected void closeRealm() {

    }

    @OnClick(R.id.dismiss_alarm)
    public void onDismiss() {
        AlarmScheduler.cancelNextAlarm(getApplicationContext());
    }

}
