package com.rva.mrb.vivify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


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