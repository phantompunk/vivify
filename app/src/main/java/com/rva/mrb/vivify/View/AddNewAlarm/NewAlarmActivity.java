package com.rva.mrb.vivify.View.AddNewAlarm;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.rva.mrb.vivify.AlarmApplication;
import com.rva.mrb.vivify.ApplicationModule;
import com.rva.mrb.vivify.BaseActivity;
import com.rva.mrb.vivify.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bao on 6/25/16.
 */
public class NewAlarmActivity extends BaseActivity implements NewAlarmView {

    @BindView(R.id.layout_container)
    LinearLayout mLayoutContainer;
    @BindView(R.id.edit_time)
    EditText mEditTime;
    @BindView(R.id.edit_repeat)
    EditText mEditRepeat;
    @BindView(R.id.isSet)
    CheckBox mIsSet;
    @BindView(R.id.standard_time)
    CheckBox mStandardTime;

    @Inject NewAlarmPresenter newAlarmPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        NewAlarmComponent newAlarmComponent = DaggerNewAlarmComponent.builder()
                .applicationModule(new ApplicationModule((AlarmApplication) getApplication()))
                .newAlarmModule(new NewAlarmModule())
                .applicationComponent(((AlarmApplication) getApplication()).getComponent())
                .build();
        newAlarmComponent.inject(this);
        ButterKnife.bind(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        newAlarmPresenter.setView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        newAlarmPresenter.clearView();
    }

    @Override
    protected void closeRealm() {newAlarmPresenter.closeRealm();
    }

    @OnClick(R.id.button_add)
    public void onAddClick() {
        newAlarmPresenter.onAddClick(
                mEditTime.getText().toString(),
                mIsSet.isChecked(),
                mStandardTime.isChecked(),
                mEditRepeat.getText().toString()
                );
    }


};
