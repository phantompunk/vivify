package com.rva.mrb.vivify;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

/**
 * Created by Bao on 6/24/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AlarmApplication)getApplication()).getComponent();
    }

    protected void onDestroy(){
        closeRealm();
        super.onDestroy();
    }

    protected abstract void closeRealm();
}