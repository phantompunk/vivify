package com.rva.mrb.vivify.View.Search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.Model.SpotifyClient;
import com.rva.mrb.vivify.Model.SpotifyService;
import com.rva.mrb.vivify.Model.User;
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

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements SearchView,
        ConnectionStateCallback, PlayerNotificationCallback {

    @Inject
    SearchPresenter searchPresenter;

    @Inject
    SpotifyService spotifyService;

    // Spotify
    private static final String CLIENT_ID = "c07baf896d3a4b4b99c09fa61592eb1d";
    private static final int REQUEST_CODE = 5123;
    private static final String REDIRECT_URI = "vivify://callback";
    private Player mPlayer;
    private Config playerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchComponent searchComponent = DaggerSearchComponent.builder()
                .applicationModule(new ApplicationModule((AlarmApplication) getApplication()))
                .searchModule(new SearchModule(this))
                .applicationComponent(((AlarmApplication) getApplication()).getComponent())
                .build();
        searchComponent.inject(this);
        ButterKnife.bind(this);

        initSpotify();
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
            if (requestCode == REQUEST_CODE) {
                AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
                switch (response.getType()) {
                    case TOKEN:
                        Log.d("Spotify", "Response Token: " + response.getAccessToken());
                        playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                        break;
                    case ERROR:
                        break;
                    default:
                }
            }
        }
    }

    @OnClick(R.id.fab3)
    public void onSearchClick(){
        Log.d("MyApp", "Fab Click");
        spotifyService.getUser("rmoran92").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    Log.d("SpotifyService", "Successful: " + response.isSuccessful());
                    Log.d("SpotifyService", "Code:" + response.code());
                    Log.d("SpotifyService", "Message:" + response.message());
                    Log.d("SpotifyService", "Body:" + response.body());
                    Log.d("SpotifyService", "Username:" + user.getDisplayName());
                    Log.d("SpotifyService", "UserId:" + user.getId());
                }
                else {
                    Log.d("SpotifyService", "Successful: " + response.isSuccessful());
                    Log.d("SpotifyService", "Code:" + response.code());
                    Log.d("SpotifyService", "Message:" + response.message());
                    Log.d("SpotifyService", "Body:" + response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
//        alarmPresenter.onSearch();
//        mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
//            @Override
//            public void onInitialized(Player player) {
//                mPlayer.addConnectionStateCallback(SearchActivity.this);
//                mPlayer.addPlayerNotificationCallback(SearchActivity.this);
//                mPlayer.play("spotify:track:53XV0VrMvBcMBUWv5xSERU");
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
//            }
//        });
    }

    @Override
    protected void closeRealm() {

    }


    @Override
    public void onStart() {
        super.onStart();
        searchPresenter.setView(this);
    }

    // Spotify methods
    @Override
    public void onLoggedIn() {
        Log.d("Spotify", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("Spotify", "User logged out");

    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        Log.d("Spotify", "Login failed");

    }

    @Override
    public void onTemporaryError() {
        Log.d("Spotify", "Temporary error occurred");

    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d("Spotify", "Received connection message");

    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("Spotify", "Playback event received: " + eventType.name());

    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {
        Log.d("Spotify", "Playback error received: " + errorType.name());

    }
}
