package com.rva.mrb.vivify.View.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.Model.Data.Tokens;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.Spotify.NodeService;
import com.rva.mrb.vivify.Spotify.SpotifyService;
import com.rva.mrb.vivify.View.Alarm.AlarmActivity;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bao on 9/28/16.
 */
public class LoginActivity extends BaseActivity {

    @Inject
    NodeService nodeService;
    @Inject
    SpotifyService spotifyService;
    @BindView(R.id.login_button)
    Button loginButton;
    private static final String CLIENT_ID = "c07baf896d3a4b4b99c09fa61592eb1d";
    private static final String REDIRECT_URI = "vivify://callback";
    private static final int REQUEST_CODE = 5123;
    private ApplicationModule applicationModule = new ApplicationModule((AlarmApplication) getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoginStatus();
        setContentView(R.layout.activity_login);
        LoginComponent loginComponent = DaggerLoginComponent.builder()
                .applicationModule(applicationModule)
                .applicationComponent(((AlarmApplication) getApplication()).getComponent())
                .build();
        loginComponent.inject(this);
        ButterKnife.bind(this);

    }

    public void checkLoginStatus(){
        Log.d("Login", "Checking login status");
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String refreshToken = sharedPreferences.getString("refresh_token", null);
        if(refreshToken == null || refreshToken.equals("-1")) {
        }
        else{
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick() {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.CODE, REDIRECT_URI);

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
                case CODE:

                    Log.d("Spotify", "Response Code: " + response.getCode());
                    // applicationModule.setAccessToken(response.getAccessToken());
                    nodeService.getTokens(response.getCode()).enqueue(new Callback<Tokens>() {
                        @Override
                        public void onResponse(Call<Tokens> call, Response<Tokens> response) {
                            Log.d("Node", "Response Message: " + response.message());
                            Tokens results = response.body();
                            Log.d("Node", "Response Body: " + response.body().toString());
                            Log.d("Node", "Code: " + response.code());
                            Log.d("Node", "AccessToken: " + results.getAccessToken());
                            Log.d("Node", "Refresh Token: " + results.getRefreshToken());

                            SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("access_token", results.getAccessToken());
                            editor.putString("refresh_token", results.getRefreshToken());
                            editor.commit();

                            applicationModule.setAccessToken(results.getAccessToken());
                            applicationModule.setRefreshToken(results.getRefreshToken());
                            checkLoginStatus();

                        }

                        @Override
                        public void onFailure(Call<Tokens> call, Throwable t) {
                            Log.d("Node", "Call failed: " + t.getMessage());
                            t.printStackTrace();
                        }
                    });
                    Log.d("Node", "Made call to node");
                    break;
                case ERROR:
                    break;
                default:
            }
        }
    }

    @Override
    protected void closeRealm() {

    }
}
