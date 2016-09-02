package com.rva.mrb.vivify.View.Detail;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.Model.Data.Alarm;
import com.rva.mrb.vivify.Model.Data.SimpleTrack;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Adapter.AlarmAdapter;
import com.rva.mrb.vivify.View.Search.SearchActivity;


import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailView {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.edit_name) EditText editname;
    @BindView(R.id.edit_time) EditText mEditTime;
//    @BindView(R.id.edit_repeat) EditText mEditRepeat;
    @BindView(R.id.track_tv) TextView mTrackTv;
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
    private String trackName;
    private String artistName;
    private String trackId;
    private String trackImage;
    final private int requestCode = 1;

    // used to create a binary representation of days an alarm is enabled
    private int repeatDays = 0;
//    private int alarmHour = 0;
//    private int alarmMinute = 0;
//    private int alarmAM_PM = 0;

    @Inject DetailPresenter detailPresenter;
    private AlarmAdapter.OnAlarmToggleListener alarmToggleListener;


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
//        onSetRepeat();
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
            if (!bundle.getString("AlarmID").isEmpty()) {
                Alarm alarm = detailPresenter.getAlarm(bundle.getString("AlarmID"));
//                Log.d("EditAlarm", alarm.getmWakeTime());
                mEditTime.setText(alarm.getmWakeTime());
                mIsSet.setChecked(alarm.isEnabled());
                mStandardTime.setChecked(alarm.is24hr());
                setRepeatCheckBoxes(alarm.getDecDaysOfWeek());
//                mEditRepeat.setText(alarm.getmcRepeat());
                trackName = alarm.getTrackName();
                artistName = alarm.getArtistName();
                trackId = alarm.getTrackId();
                trackImage = alarm.getTrackImage();
                Log.d("trackImageDA", "track image url: " + trackImage);
                setTrackTv();
            }
        } else {
//            Log.d("DetailTime", detailPresenter.getCurrentTime());
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
                Integer.toBinaryString(repeatDays),
                trackName,
                artistName,
                trackId,
                trackImage
                );
        Intent returnIntent = new Intent();
        returnIntent.putExtra("enabled", true);
        Log.d(TAG, "Extra " + returnIntent.getBooleanExtra("enabled", true));
        setResult(Activity.RESULT_OK, returnIntent);
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
        if (!bundle.getString("AlarmID").isEmpty()) {
            detailPresenter.onSaveAlarm(
                    getApplicationContext(),
                    bundle.getString("AlarmID"),
                    editname.getText().toString(),
                    mEditTime.getText().toString(),
                    mIsSet.isChecked(),
                    mStandardTime.isChecked(),
                    Integer.toBinaryString(repeatDays),
                    trackName,
                    artistName,
                    trackId,
                    trackImage
            );
        }
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("enabled", true);
//        Log.d(TAG, "Extra " + returnIntent.getBooleanExtra("enabled", mIsSet.isChecked()));
//        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @OnClick(R.id.edit_time)
    public void onPickTime() {
//        Log.d("EditTime", "Click Success");
//        final Calendar cal = Calendar.getInstance();
        TimePickerDialog timePickerDialog =
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                mEditTime.setText(detailPresenter.getTime(hour, minute));
//                alarmHour = hour;
//                alarmHour = minute;
//                alarmAM_PM = detailPresenter.getAMPM(hour);
            }
                    // Set Alarm time as default if it exists
        }, detailPresenter.getCurrentHour(), detailPresenter.getCurrentMinute(), false);
        timePickerDialog.show();
    }

    public void onSetRepeat() {

        repeatDays = 0;
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

    @OnClick(R.id.spotify_search)
    public void onSearchClick() {

        Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                SimpleTrack.Item track = (SimpleTrack.Item) extras.get("track");
                Log.d("onActivityResult", track.getName());
                trackName = track.getName();
                artistName = track.getArtists().get(0).getName();
                trackId = track.getId();
                trackImage = track.getAlbum().getImages().get(1).getUrl();
                setTrackTv();
            }
        }
    }

    public void setTrackTv() {
        mTrackTv.setText(trackName + " by " + artistName);
    }

}
