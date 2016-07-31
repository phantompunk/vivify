package com.rva.mrb.vivify.View.AddNewAlarm;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.Model.Alarm;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Adapter.AlarmAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bao on 6/25/16.
 */
public class AlarmDetailActivity extends BaseActivity implements AlarmDetailView {

    @BindView(R.id.layout_container)
    LinearLayout mLayoutContainer;
    @BindView(R.id.edit_name)
    EditText editname;
    @BindView(R.id.edit_time)
    EditText mEditTime;
    @BindView(R.id.edit_repeat)
    EditText mEditRepeat;
    @BindView(R.id.isSet)
    CheckBox mIsSet;
    @BindView(R.id.standard_time)
    CheckBox mStandardTime;
    @BindView(R.id.button_add)
    Button addbt;
    @BindView(R.id.button_delete)
    Button deletebt;
    @BindView(R.id.button_save)
    Button savebt;

    @Inject
    AlarmDetailPresenter alarmDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        AlarmDetailComponent alarmDetailComponent = DaggerAlarmDetailComponent.builder()
                .applicationModule(new ApplicationModule((AlarmApplication) getApplication()))
                .alarmDetailModule(new AlarmDetailModule())
                .applicationComponent(((AlarmApplication) getApplication()).getComponent())
                .build();
        alarmDetailComponent.inject(this);
        ButterKnife.bind(this);
        isNewAlarm();

    }

    private void isNewAlarm() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getBoolean("NewAlarm", true) == false) {
                addbt.setVisibility(View.GONE);
                savebt.setVisibility(View.VISIBLE);
                deletebt.setVisibility(View.VISIBLE);
            }
            Log.d("Position", bundle.getInt("Position") + "");
            if (bundle.getInt("Position", -1) >= 0) {
                Alarm alarm = alarmDetailPresenter.getAlarm(bundle.getInt("Position")+1 );
                Log.d("EditAlarm", alarm.getmWakeTime());
                mEditTime.setText(alarm.getmWakeTime());
                mIsSet.setChecked(alarm.ismIsSet());
                mStandardTime.setChecked(alarm.ismStandardTime());
                mEditRepeat.setText(alarm.getmRepeat());
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        alarmDetailPresenter.setView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        alarmDetailPresenter.clearView();
    }

    @Override
    protected void closeRealm() {
        alarmDetailPresenter.closeRealm();
    }

    @OnClick(R.id.button_add)
    public void onAddClick() {
        alarmDetailPresenter.onAddClick(
                editname.getText().toString(),
                mEditTime.getText().toString(),
                mIsSet.isChecked(),
                mStandardTime.isChecked(),
                mEditRepeat.getText().toString()
                );
    }

    @OnClick(R.id.button_delete)
    public void onDeleteAlarm() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.getInt("Position") >= 0)
        alarmDetailPresenter.onDeleteAlarm(bundle.getInt("Position")+1);
    }

    @OnClick(R.id.button_save)
    public void onSaveAlarm() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.getInt("Position") >= 0) {
            alarmDetailPresenter.onSaveAlarm(bundle.getInt("Position") + 1,
                    editname.getText().toString(),
                    mEditTime.getText().toString(),
                    mIsSet.isChecked(),
                    mStandardTime.isChecked(),
                    mEditRepeat.getText().toString()
            );
        }
    }

    /**
     * TODO presenter should handle more of this
     */
    @OnClick(R.id.edit_time)
    public void onPickTime() {
        Log.d("EditTime", "Click Success");
        int hour = 8;
        int minute = 00;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String hrStr = String.valueOf(hour);
                String minStr = (hour < 10) ? "0" + String.valueOf(minute) : String.valueOf(minute);
                mEditTime.setText(hrStr + ":" + minStr);
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }
}
