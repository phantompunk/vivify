package com.rva.mrb.vivify.View.Alert;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.R;

public class AlertActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);



    }

    @Override
    protected void closeRealm() {

    }

}
