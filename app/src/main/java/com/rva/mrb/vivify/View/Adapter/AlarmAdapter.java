package com.rva.mrb.vivify.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rva.mrb.vivify.Model.Alarm;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.AddNewAlarm.NewAlarmActivity;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class AlarmAdapter extends RealmBasedRecyclerViewAdapter<Alarm, AlarmAdapter.ViewHolder> {

    public AlarmAdapter(Context context, RealmResults<Alarm> realmResults,
            boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.alarm_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindRealmViewHolder(AlarmAdapter.ViewHolder viewHolder, int position) {
        final Alarm alarm = realmResults.get(position);
        viewHolder.timeTv.setText(alarm.getmWakeTime());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CardView", "Click Success");
//                Context context = view.getContext();
//                Intent intent = new Intent(context, NewAlarmActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RealmViewHolder {

        @BindView(R.id.alarm_tv) TextView timeTv;
        @BindView(R.id.card_alarms) CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

