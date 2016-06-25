package com.rva.mrb.vivify.View.Alarm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.rva.mrb.vivify.Model.Alarm;
import com.rva.mrb.vivify.Model.AlarmInfo;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Adapter.AlarmAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class AlarmsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    private AlarmAdapter mAlarmAdapter;
    AlarmsPresenter mAlarmsPresenter;

    private List<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        AlarmInfo bird = new AlarmInfo("6:30");
        AlarmInfo owl = new AlarmInfo("10:30");
        RealmList<AlarmInfo> a = new RealmList<>(bird);
        RealmList<AlarmInfo> b = new RealmList<>(owl);
        Alarm early = new Alarm("EarlyBird", a);
        Alarm midnight = new Alarm("MidnightOwl", b);
        alarms = Arrays.asList(early, midnight);

        initializeAlarmsList(alarms);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void initializeAlarmsList(final List<Alarm> alarms) {
        mAlarmAdapter = new AlarmAdapter(this, alarms);
        mAlarmAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {

            @Override
            public void onListItemExpanded(int position) {
                Alarm expandedAlarm = alarms.get(position);
            }

            @Override
            public void onListItemCollapsed(int position) {
                Alarm collaspedAlarm = alarms.get(position);
            }
        });

        mRecyclerView.setAdapter(mAlarmAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
