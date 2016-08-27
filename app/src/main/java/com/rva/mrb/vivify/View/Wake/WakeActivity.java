package com.rva.mrb.vivify.View.Wake;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.Model.Service.AlarmScheduler;
import com.rva.mrb.vivify.R;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WakeActivity extends BaseActivity implements ConnectionStateCallback,
        PlayerNotificationCallback {

    @BindView(R.id.dismiss_tv) TextView dismissTv;
    @BindView(R.id.snooze_tv) TextView snoozeTv;
    @BindView(R.id.myseek) SeekBar seekBar;
    @BindView(R.id.trackImageView) ImageView trackIV;
    @Inject
    WakePresenter wakePresenter;

    // Spotify
    private static final String CLIENT_ID = "c07baf896d3a4b4b99c09fa61592eb1d";
    private static final int REQUEST_CODE = 5123;
    private static final String REDIRECT_URI = "vivify://callback";
    private Player mPlayer;
    private Config playerConfig;
    private ApplicationModule applicationModule = new ApplicationModule((AlarmApplication) getApplication());
    private String trackId;
    private String trackImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Dagger and Butterknife dependenncy injecetions
        setContentView(R.layout.activity_wake);
        WakeComponent wakeComponent = DaggerWakeComponent.builder()
                .applicationModule(applicationModule)
                .wakeModule(new WakeModule(this))
                .applicationComponent(((AlarmApplication) getApplication()).getComponent())
                .build();
        wakeComponent.inject(this);
        ButterKnife.bind(this);

        //Retrieve access token from spotify
        initSpotify();

        //Get trackId and image URL from Bundle
        Bundle extras = getIntent().getExtras();
        trackId = (String) extras.get("trackId");
        trackImage = (String) extras.get("trackImage");

        //Use Glide to load image URL
        Glide.with(this)
                .load(trackImage)
                .into(trackIV);
        trackIV.setScaleType(ImageView.ScaleType.FIT_XY);
        Log.d("trackImage", "Traack Image Url: " + trackImage);

        //Set the seekbar that dissmisses/snoozes alarm
        setSeekBar();

        //Allow activity to wake up device
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

//    @Override
//    protected void onResume() {}

    @Override
    protected void closeRealm() {

    }

    /*
    This method is called when the user dismisses the alarm. It cancels the alarm and pauses the
    player.
     */
    public void onDismiss() {
        AlarmScheduler.cancelNextAlarm(getApplicationContext());
        mPlayer.pause();
        finish();
    }

    /*
    This method is called when the user snoozes the alarm. It pauses the player and reschedules the
    alarm.
     */
    public void onSnooze() {
        mPlayer.pause();
        AlarmScheduler.snoozeNextAlarm(getApplicationContext(), trackId, trackImage);
        finish();
    }

    /*
    This method sets the seekbar listener and allows user to snooze or dismiss the alarm.
     */
    public void setSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(seekBar.getProgress() > 85) {
                    dismissTv.setTextSize(30);
                    dismissTv.setTypeface(null, Typeface.BOLD);
                }
                else if(seekBar.getProgress() < 15) {
                    snoozeTv.setTextSize(30);
                    snoozeTv.setTypeface(null, Typeface.BOLD);
                }
                else{
                    dismissTv.setTextSize(20);
                    snoozeTv.setTextSize(20);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress() > 85) {
                    onDismiss();
                }
                else if(seekBar.getProgress() < 15) {
                    onSnooze();
                }
                else {
                    seekBar.setProgress(50);
                }
            }
        });
    }

    private void initSpotify() {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                case TOKEN:
                    Log.d("Spotify", "Response Token: " + response.getAccessToken());
                    applicationModule.setAccessToken(response.getAccessToken());
                    //Initialize Spotify player
                    playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                    mPlayer = Spotify.getPlayer(playerConfig, this,
                            new Player.InitializationObserver() {
                        @Override
                        public void onInitialized(Player player) {
                            mPlayer.addConnectionStateCallback(WakeActivity.this);
                            mPlayer.addPlayerNotificationCallback(WakeActivity.this);
                            mPlayer.play("spotify:track:" + trackId);
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                        }
                    });
                    break;
                case ERROR:
                    break;
                default:
            }
        }

    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Throwable throwable) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {

    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {

    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}
