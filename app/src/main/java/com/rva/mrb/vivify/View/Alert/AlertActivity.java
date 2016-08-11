package com.rva.mrb.vivify.View.Alert;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Alarm.AlarmModule;
import com.rva.mrb.vivify.View.Search.DaggerSearchComponent;

import javax.inject.Inject;

public class AlertActivity extends BaseActivity {

    @Inject AlertPresenter alertPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        AlertComponent alertComponent = DaggerAlertComponent.builder()
                .applicationModule(new ApplicationModule((AlarmApplication) getApplication()))
                .alertModule(new AlertModule(this))
                .applicationComponent(((AlarmApplication) getApplication()).getComponent())
                .build();
        alertComponent.inject(this);


    }

//    @Override
//    protected void onResume() {}

    @Override
    protected void closeRealm() {

    }

}
