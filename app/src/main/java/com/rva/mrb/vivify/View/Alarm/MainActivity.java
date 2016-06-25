package com.rva.mrb.vivify.View.Alarm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.Model.Alarm;
import com.rva.mrb.vivify.Model.AlarmInfo;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Adapter.AlarmAdapter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity implements AlarmsView, AlarmAdapter.OnAlarmClickListener{
    @Override
    public void onBookClick(int id) {

    }

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    private AlarmAdapter mAdapter;
    private AlarmAdapter.OnAlarmClickListener mAlarmListner;

    private List<Alarm> alarms;

    @Inject
    AlarmsPresenter alarmPresenter;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        AlarmInfo bird = new AlarmInfo(alarmPresenter.getMessage());
        AlarmInfo owl = new AlarmInfo(alarmPresenter.getRSMessage());
        RealmList<AlarmInfo> a = new RealmList<>(bird);
        RealmList<AlarmInfo> b = new RealmList<>(owl);
        Alarm early = new Alarm("EarlyBird", a);
        Alarm midnight = new Alarm("MidnightOwl", b);
        alarms = Arrays.asList(early, midnight);
        initializeAlarmsList(alarms);

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAdapter.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.onRestoreInstanceState(savedInstanceState);
    }

    public void initializeAlarmsList(final List<Alarm> alarms) {
        mAdapter = new AlarmAdapter(this, alarms);
        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {

            @Override
            public void onListItemExpanded(int position) {
                Alarm expandedAlarm = alarms.get(position);
            }

            @Override
            public void onListItemCollapsed(int position) {
                Alarm collaspedAlarm = alarms.get(position);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onStart() {
        super.onStart();
        alarmPresenter.setView(this);
    }

    public void showAlarms(final RealmResults<Alarm> alarms) {
        mAdapter.setAlarms(alarms);
    }

    public void onStop() {
        super.onStop();
        alarmPresenter.clearView();
    }

}
