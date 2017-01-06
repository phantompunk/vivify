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
import com.rva.mrb.vivify.Model.Data.Album;
import com.rva.mrb.vivify.Model.Data.Artist;
import com.rva.mrb.vivify.Model.Data.MediaType;
import com.rva.mrb.vivify.Model.Data.Playlist;
import com.rva.mrb.vivify.Model.Data.Track;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Adapter.AlarmAdapter;
import com.rva.mrb.vivify.View.Search.SearchActivity;


import org.parceler.Parcels;

import java.util.Calendar;
import java.util.Date;

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
    private int mediaType;
    private Alarm alarm = new Alarm();
    final private int requestCode = 1;

    // used to create a binary representation of days an alarm is enabled
    private int repeatDays = 0;

    @Inject DetailPresenter detailPresenter;
    private AlarmAdapter.OnAlarmToggleListener alarmToggleListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        //inject dagger and butterknife dependencies
        DetailComponent detailComponent = DaggerDetailComponent.builder()
                .applicationModule(new ApplicationModule((AlarmApplication) getApplication()))
                .detailModule(new DetailModule())
                .applicationComponent(((AlarmApplication) getApplication()).getComponent())
                .build();
        detailComponent.inject(this);
        ButterKnife.bind(this);

        Log.d("test", "ID: " + alarm.getId());
        //check if this is a new alarm
        isNewAlarm();
    }

    /**
     * This method programmatically shows add, save, and delete buttons
     */
    private void isNewAlarm() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            setVisibility();
            if (bundle.getBoolean("NewAlarm", true) == false) {
                alarm = Parcels.unwrap(getIntent().getParcelableExtra("Alarm"));
//                Log.d("EditAlarm", alarm.getmWakeTime());
                mEditTime.setText(alarm.getmWakeTime());
                mIsSet.setChecked(alarm.isEnabled());
                mStandardTime.setChecked(alarm.is24hr());
                setRepeatCheckBoxes(alarm.getDecDaysOfWeek());
//                mEditRepeat.setText(alarm.getmcRepeat());
                trackName = alarm.getTrackName();
                artistName = bundle.getString("AlarmArtist");
                trackId = alarm.getTrackId();
                trackImage = alarm.getTrackImage();
                mediaType = alarm.getMediaType();
                Log.d("trackImageDA", "track image url: " + trackImage);
                setTrackTv();
            }
            // No longer being used
//            if (!bundle.getString("AlarmID").isEmpty()) {
//                Alarm alarm = Parcels.unwrap(getIntent().getParcelableExtra("Alarm"));
////                Log.d("EditAlarm", alarm.getmWakeTime());
//                mEditTime.setText(alarm.getmWakeTime());
//                mIsSet.setChecked(alarm.isEnabled());
//                mStandardTime.setChecked(alarm.is24hr());
//                setRepeatCheckBoxes(alarm.getDecDaysOfWeek());
////                mEditRepeat.setText(alarm.getmcRepeat());
//                trackName = alarm.getTrackName();
//                artistName = alarm.getArtistName();
//                trackId = alarm.getTrackId();
//                trackImage = alarm.getTrackImage();
//                Log.d("trackImageDA", "track image url: " + trackImage);
//                setTrackTv();
//            }
        } else {
            // TODO fill with default settings for new alarm
            Log.d("DetailTime", detailPresenter.getCurrentTime());
            mEditTime.setText(detailPresenter.getCurrentTime());
            alarm.setTime(Calendar.getInstance().getTime());
        }
    }

    private void setVisibility() {
        addbt.setVisibility(View.GONE);
        savebt.setVisibility(View.VISIBLE);
        deletebt.setVisibility(View.VISIBLE);
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

    /**
     * This method passes the alarm object to detailPresenter to add the alarm to realm
     */
    @OnClick(R.id.button_add)
    public void onAddClick() {
        Log.d("label",editname.getText().toString());
        setAlarm();
        detailPresenter.onAddClick(alarm, getApplicationContext());

        Intent returnIntent = new Intent();
        returnIntent.putExtra("enabled", true);
        Log.d(TAG, "Extra " + returnIntent.getBooleanExtra("enabled", true));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    /**
     * This method passes the current alarm object to detailPresenter to be deleted
     */
    @OnClick(R.id.button_delete)
    public void onDeleteAlarm() {
        detailPresenter.onDeleteAlarm(alarm);
        finish();
    }

    /**
     * This method passes the current alarm object to detailPresenter to be saved to realm
     */
    @OnClick(R.id.button_save)
    public void onSaveAlarm() {
        setAlarm();
        detailPresenter.onSaveAlarm(alarm, getApplicationContext());
        finish();
    }

    @OnClick(R.id.edit_time)
    public void onPickTime() {
        TimePickerDialog timePickerDialog =
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                mEditTime.setText(detailPresenter.getTime(hour, minute));
                Log.d("timepicker", "hour: " + hour);
                alarm.setmWakeTime(mEditTime.getText().toString());
                Date date = detailPresenter.getDate(alarm, hour, minute);
                alarm.setTime(date);

            }
                    // Set Alarm time as default if it exists
        }, detailPresenter.getHour(alarm), detailPresenter.getMinute(alarm), false);
        timePickerDialog.show();
    }

    /**
     * This method sets the repeating days
     */
    public void onSetRepeat() {

        repeatDays = 0;
        alarm.setDaysOfWeek("0");
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

        alarm.setDaysOfWeek(Integer.toBinaryString(repeatDays));
        Log.d(TAG, "Repeat Days: " + Integer.toBinaryString(repeatDays));
    }

    /**
     * This method starts(for result) a new SearchActivity to search for spotify music
     */
    @OnClick(R.id.spotify_search)
    public void onSearchClick() {

        Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, requestCode);
    }

    public void setAlarm() {
        onSetRepeat();
        alarm.setAlarmLabel(editname.getText().toString());
        alarm.setEnabled(mIsSet.isChecked());
        alarm.setmWakeTime(mEditTime.getText().toString());
        Log.d("Set", "Time: " + alarm.getTime());
        alarm.setDaysOfWeek(Integer.toBinaryString(repeatDays));
        alarm.setTrackId(trackId);
        alarm.setTrackImage(trackImage);
        alarm.setTrackName(trackName);
        alarm.setArtist(artistName);
        alarm.setMediaType(mediaType);
    }

    // Not being used
//    public void updateAlarm(Alarm updateAlarm) {
//        updateAlarm.setAlarmLabel(editname.getText().toString());
//        updateAlarm.setEnabled(mIsSet.isChecked());
//        updateAlarm.setmWakeTime(mEditTime.getText().toString());
//        Log.d("Set", "Time: " + mEditTime.getText().toString());
//        updateAlarm.setTime(time.getTime());
//        updateAlarm.setDaysOfWeek(Integer.toBinaryString(repeatDays));
//        updateAlarm.setTrackId(trackId);
//        updateAlarm.setTrackImage(trackImage);
//        updateAlarm.setTrackName(trackName);
//        updateAlarm.setArtist(artistName);
//    }

    /**
     * This method called when the user selects a song in SearchActivity. This method sets music info
     * to the alarm object
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                MediaType type = Parcels.unwrap(data.getParcelableExtra("track"));
                mediaType = type.getMediaType();
                switch (type.getMediaType()) {
                    case MediaType.TRACK_TYPE:
                        Track track = type.getTrack();
                        Log.d("onActivityResult", track.getName());
                        trackName = track.getName();
                        artistName = track.getArtists().get(0).getName();
                        trackId = track.getId();
                        trackImage = track.getAlbum().getImages().get(1).getUrl();
                        break;
                    case MediaType.ALBUM_TYPE:
                        Album album = type.getAlbum();
                        trackName = album.getName();
                        artistName = album.getArtists().get(0).getName();
                        trackId = album.getId();
                        trackImage = album.getImages().get(0).getUrl();
                        break;
                    case MediaType.ARTIST_TYPE:
                        Artist artist = type.getArtist();
                        trackName = artist.getName();
                        artistName = artist.getName();
                        trackId = artist.getId();
                        trackImage = artist.getImages().get(0).getUrl();
                        break;
                    case MediaType.PLAYLIST_TYPE:
                        Playlist playlist = type.getPlaylist();
                        trackName = playlist.getName();
                        artistName = playlist.getOwner().getId();
                        trackId = playlist.getId();
                        trackImage = playlist.getImages().get(0).getUrl();
                        break;
                }
//                Track track = Parcels.unwrap(data.getParcelableExtra("track"));
//                Log.d("onActivityResult", track.getName());
//                trackName = track.getName();
//                artistName = track.getArtists().get(0).getName();
//                trackId = track.getId();
//                trackImage = track.getAlbum().getImages().get(1).getUrl();
                setTrackTv();
            }
        }
    }

    /**
     * This method displays the current spotify music assigned to the alarm object
     */
    public void setTrackTv() {
        mTrackTv.setText(trackName + " by " + artistName);
    }

}
