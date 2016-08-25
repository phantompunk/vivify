package com.rva.mrb.vivify.View.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.FractionRes;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.Model.Data.Alarm;
import com.rva.mrb.vivify.Model.Service.RealmService;
import com.rva.mrb.vivify.Model.Service.WakeReceiver;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Adapter.AlarmAdapter;
import com.rva.mrb.vivify.View.Detail.DetailActivity;
import com.rva.mrb.vivify.View.Search.SearchActivity;

import org.w3c.dom.Text;

import java.util.BitSet;
import java.util.Calendar;
import java.util.Observable;
import java.util.UUID;
import java.util.logging.Handler;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class AlarmActivity extends BaseActivity implements AlarmsView {

    public static final String TAG = AlarmActivity.class.getSimpleName();
    public static final int DetailRequest = 45;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_notification) TextView alarmNotification;
    @BindView(R.id.recyclerview) RealmRecyclerView mRecyclerView;
    @Inject AlarmsPresenter alarmPresenter;

    private AlarmAdapter mAdapter;
    private AlarmAdapter.OnAlarmToggleListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlarmComponent alarmComponent = DaggerAlarmComponent.builder()
                        .applicationModule(new ApplicationModule((AlarmApplication)getApplication()))
                        .alarmModule(new AlarmModule(this))
                        .applicationComponent(((AlarmApplication) getApplication()).getComponent())
                        .build();
        alarmComponent.inject(this);
        ButterKnife.bind(this);
        // set our own toolbar and disable the default app name title
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // find the next scheduled alarm
        updateAlarmNotification();

        // custom listener listening alarm toggles
        listener = new AlarmAdapter.OnAlarmToggleListener() {
            @Override
            public void onAlarmToggle() {
                updateAlarmNotification();
            }
        };
        // create a new container to list all alarms
        // and set to auto update from realm results
        mAdapter = new AlarmAdapter(getApplicationContext(),
                alarmPresenter.getAllAlarms(), listener, true, true);
        mRecyclerView.setAdapter(mAdapter);

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        alarmPresenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d(TAG, "Next wake time is " + alarmPresenter.getNextAlarmTime());
        mAdapter.notifyDataSetChanged();
    }

    public void showAlarms(final RealmResults<Alarm> alarms) {
    }

    @Override
    public void showAddNewAlarmView() {
//        startActivity(new Intent(this, DetailActivity.class));
        startActivityForResult(new Intent(this, DetailActivity.class), DetailRequest);
    }

    @OnClick(R.id.new_alarm_fab)
    public void onAddNewAlarmClick(){
        alarmPresenter.onAddNewAlarm();
    }

    public void updateAlarmNotification() {
        alarmNotification.setText(alarmPresenter.getNextAlarmTime());
    }

    public void closeRealm(){
        alarmPresenter.closeRealm();
    }

    public void onStop() {
        super.onStop();
        alarmPresenter.clearView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == DetailRequest) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Result Successful");
                if (data.getBooleanExtra("enabled", true)) {
                    Log.d(TAG, "New alarm is toggled");
                    updateAlarmNotification();
                }
            }
        }
    }
}