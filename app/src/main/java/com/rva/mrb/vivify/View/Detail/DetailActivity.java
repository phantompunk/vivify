package com.rva.mrb.vivify.View.Detail;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.Model.Data.Alarm;
import com.rva.mrb.vivify.Model.Service.AlarmScheduler;
import com.rva.mrb.vivify.R;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailView {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.edit_name) EditText editname;
    @BindView(R.id.edit_time) EditText mEditTime;
    @BindView(R.id.edit_repeat) EditText mEditRepeat;
    @BindView(R.id.isSet) CheckBox mIsSet;
    @BindView(R.id.standard_time) CheckBox mStandardTime;
    @BindView(R.id.button_add) Button addbt;
    @BindView(R.id.button_delete) Button deletebt;
    @BindView(R.id.button_save) Button savebt;

    @Inject DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        DetailComponent detailComponent = DaggerDetailComponent.builder()
                .applicationModule(new ApplicationModule((AlarmApplication) getApplication()))
                .detailModule(new DetailModule())
                .applicationComponent(((AlarmApplication) getApplication()).getComponent())
                .build();
        detailComponent.inject(this);
        ButterKnife.bind(this);
        isNewAlarm();
    }

    // Programmatically show add,save, and delete buttons
    private void isNewAlarm() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getBoolean("NewAlarm", true) == false) {
                addbt.setVisibility(View.GONE);
                savebt.setVisibility(View.VISIBLE);
                deletebt.setVisibility(View.VISIBLE);
            }
            Log.d("Position", bundle.getInt("Position") + "");
            if (!bundle.getString("AlarmID").isEmpty()) {
                Alarm alarm = detailPresenter.getAlarm(bundle.getString("AlarmID"));
                Log.d("EditAlarm", alarm.getmWakeTime());
                mEditTime.setText(alarm.getmWakeTime());
                mIsSet.setChecked(alarm.isEnabled());
                mStandardTime.setChecked(alarm.ismStandardTime());
                mEditRepeat.setText(alarm.getmRepeat());
            }
        } else {
            Log.d("DetailTime", detailPresenter.getCurrentTime());
            mEditTime.setText(detailPresenter.getCurrentTime());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        detailPresenter.setView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        detailPresenter.clearView();
    }

    @Override
    protected void closeRealm() {
        detailPresenter.closeRealm();
    }

    @OnClick(R.id.button_add)
    public void onAddClick() {
        detailPresenter.onAddClick(
                getApplicationContext(),
                editname.getText().toString(),
                mEditTime.getText().toString(),
                mIsSet.isChecked(),
                mStandardTime.isChecked(),
                mEditRepeat.getText().toString()
                );
//        AlarmScheduler.setNextAlarm(getApplicationContext(),0);
        finish();
    }

    @OnClick(R.id.button_delete)
    public void onDeleteAlarm() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.getInt("Position") >= 0)
        detailPresenter.onDeleteAlarm(bundle.getString("AlarmID"));
        finish();
    }

    @OnClick(R.id.button_save)
    public void onSaveAlarm() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.getInt("Position") >= 0) {
            detailPresenter.onSaveAlarm(bundle.getString("AlarmID"),
                    editname.getText().toString(),
                    mEditTime.getText().toString(),
                    mIsSet.isChecked(),
                    mStandardTime.isChecked(),
                    mEditRepeat.getText().toString()
            );
        }
        finish();
    }

    /**
     * TODO show current time or that alarms set time
     */
    @OnClick(R.id.edit_time)
    public void onPickTime() {
        Log.d("EditTime", "Click Success");
//        final Calendar cal = Calendar.getInstance();
        TimePickerDialog timePickerDialog =
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                mEditTime.setText(detailPresenter.getTime(hour, minute));
            }
                    // Set Alarm time as default if it exists
        }, detailPresenter.getCurrentHour(), detailPresenter.getCurrentMinute(), false);
        timePickerDialog.show();
    }
}
