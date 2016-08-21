package com.rva.mrb.vivify.View.Detail;

import android.app.TimePickerDialog;
import android.os.Bundle;
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
    @BindView(R.id.sunday_check) CheckBox sundayCb;
    @BindView(R.id.monday_check) CheckBox mondayCb;
    @BindView(R.id.tuesday_check) CheckBox tuesdayCb;
    @BindView(R.id.wednesday_check) CheckBox wednesdayCb;
    @BindView(R.id.thursday_check) CheckBox thursdayCb;
    @BindView(R.id.friday_check) CheckBox fridayCb;
    @BindView(R.id.saturday_check) CheckBox saturdayCb;

    int repeatDays = 0;
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


//        Log.d("Bits", "days: " + days);
        int sun = 1, mon = 2, tue = 4, wed = 8, thu = 16, fri = 32, sat = 64;
        int days = Alarm.MONDAY | Alarm.SATURDAY | Alarm.TUESDAY;
        Log.d("Bits", "days MWF: " + days);
        Calendar cal = Calendar.getInstance();
        int currentDay = cal.get(Calendar.DAY_OF_WEEK);
        if((Alarm.SUNDAY & days) == Alarm.SUNDAY)
            Log.d("Bits", "Days contains today's day");
        else
            Log.d("Bits", "Days does not contain todays day");

//        sundayCb.setChecked(true);
//        mondayCb.setChecked(true);
//        wednesdayCb.setChecked(true);
//        fridayCb.setChecked(true);
//        saturdayCb.setChecked(true);
        onSetRepeat();
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
                mStandardTime.setChecked(alarm.is24hr());
                setRepeatCheckBoxes(alarm.getDecDaysOfWeek());
//                mEditRepeat.setText(alarm.getmcRepeat());
            }
        } else {
            Log.d("DetailTime", detailPresenter.getCurrentTime());
            mEditTime.setText(detailPresenter.getCurrentTime());
        }
    }

    private void setRepeatCheckBoxes(int daysOfWeek) {
        if ((daysOfWeek & Alarm.SUNDAY) == Alarm.SUNDAY)
            sundayCb.setChecked(true);
        if ((daysOfWeek & Alarm.MONDAY) == Alarm.MONDAY)
            mondayCb.setChecked(true);
        if ((daysOfWeek & Alarm.TUESDAY) == Alarm.TUESDAY)
            tuesdayCb.setChecked(true);
        if ((daysOfWeek & Alarm.WEDNESDAY) == Alarm.WEDNESDAY)
            wednesdayCb.setChecked(true);
        if ((daysOfWeek & Alarm.THURSDAY) == Alarm.THURSDAY)
            thursdayCb.setChecked(true);
        if ((daysOfWeek & Alarm.FRIDAY) == Alarm.FRIDAY)
            fridayCb.setChecked(true);
        if ((daysOfWeek & Alarm.SATURDAY) == Alarm.SATURDAY)
            saturdayCb.setChecked(true);
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
        onSetRepeat();
        detailPresenter.onAddClick(
                getApplicationContext(),
                editname.getText().toString(),
                mEditTime.getText().toString(),
                mIsSet.isChecked(),
                mStandardTime.isChecked(),
                Integer.toBinaryString(repeatDays)
                );
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
        onSetRepeat();
        Bundle bundle = getIntent().getExtras();
        if (bundle.getInt("Position") >= 0) {
            detailPresenter.onSaveAlarm(
                    getApplicationContext(),
                    bundle.getString("AlarmID"),
                    editname.getText().toString(),
                    mEditTime.getText().toString(),
                    mIsSet.isChecked(),
                    mStandardTime.isChecked(),
                    Integer.toBinaryString(repeatDays)
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

    public void onSetRepeat() {
        // using bit wise operations to keep track of days
        // binary represenation of an alarm repeating
        // on Sunday and Saturday is 1000001

        // Should all CB's be passed to presenter?
        if (sundayCb.isChecked())
            repeatDays = repeatDays | Alarm.SUNDAY;
        if (mondayCb.isChecked())
            repeatDays = repeatDays | Alarm.MONDAY;
        if (tuesdayCb.isChecked())
            repeatDays = repeatDays | Alarm.TUESDAY;
        if (wednesdayCb.isChecked())
            repeatDays = repeatDays | Alarm.WEDNESDAY;
        if (thursdayCb.isChecked())
            repeatDays = repeatDays | Alarm.THURSDAY;
        if (fridayCb.isChecked())
            repeatDays = repeatDays | Alarm.FRIDAY;
        if (saturdayCb.isChecked())
            repeatDays = repeatDays | Alarm.SATURDAY;

        Log.d(TAG, "Repeat Days: " + Integer.toBinaryString(repeatDays));
    }
}
