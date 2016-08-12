package com.rva.mrb.vivify.View.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.os.Bundle;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.Model.Data.Alarm;
import com.rva.mrb.vivify.Model.Service.WakeReceiver;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Adapter.AlarmAdapter;
import com.rva.mrb.vivify.View.Detail.DetailActivity;
import com.rva.mrb.vivify.View.Search.SearchActivity;

import java.util.Calendar;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

public class AlarmActivity extends BaseActivity implements AlarmsView {

    @BindView(R.id.recyclerview) RealmRecyclerView mRecyclerView;
    private AlarmAdapter mAdapter;

    @Inject AlarmsPresenter alarmPresenter;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    public static Intent newIntent(Context context) {
        return new Intent(context, AlarmActivity.class);
    }

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

        mAdapter = new AlarmAdapter(getApplicationContext(), alarmPresenter.getAllAlarms(),true, true);
        mRecyclerView.setAdapter(mAdapter);

        alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AlarmActivity.this, WakeReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 5);
        cal.set(Calendar.MINUTE, 34);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()
                + 60*100, 1000*60*2, alarmIntent);
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
        mAdapter.notifyDataSetChanged();
    }

    public void showAlarms(final RealmResults<Alarm> alarms) {
    }

    @Override
    public void showAddNewAlarmView() {
        startActivity(new Intent(this, DetailActivity.class));
    }

    @Override
    public void showSearchView() {
        startActivity(new Intent(this, SearchActivity.class));
    }

    @OnClick(R.id.new_alarm_fab)
    public void onAddNewAlarmClick(){
//        Date time = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR, 6);
//        cal.set(Calendar.MINUTE, 45);
//        Log.d("Date", time.getTime()+"");
//        Log.d("Cal", cal.getTimeInMillis()+"");
//        Log.d("Cal", "Set for 6:45 " + cal.getTime());
//        Log.d("MyApp", "Fab Click");
        alarmPresenter.onAddNewAlarm();
    }

    public void closeRealm(){
        alarmPresenter.closeRealm();
    }

    public void onStop() {
        super.onStop();
        alarmPresenter.clearView();
    }
}