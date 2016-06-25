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

public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    private AlarmAdapter mAdapter;

    @Inject
    AlarmPresenterImpl alarmPresenter;

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
        AlarmInfo owl = new AlarmInfo("10:30");
        RealmList<AlarmInfo> a = new RealmList<>(bird);
        RealmList<AlarmInfo> b = new RealmList<>(owl);
        Alarm early = new Alarm("EarlyBird", a);
        Alarm midnight = new Alarm("MidnightOwl", b);
        final List<Alarm> alarms = Arrays.asList(early, midnight);

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

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAdapter.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.onRestoreInstanceState(savedInstanceState);
    }
}
