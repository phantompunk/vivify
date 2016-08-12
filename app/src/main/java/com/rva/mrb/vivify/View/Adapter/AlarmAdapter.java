package com.rva.mrb.vivify.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.rva.mrb.vivify.Model.Data.Alarm;
import com.rva.mrb.vivify.R;
import com.rva.mrb.vivify.View.Detail.DetailActivity;
import com.rva.mrb.vivify.View.Alarm.AlarmsPresenter;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

public class AlarmAdapter extends RealmBasedRecyclerViewAdapter<Alarm, AlarmAdapter.ViewHolder> {

    @Inject
    AlarmsPresenter alarmsPresenter;

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
    public void onBindRealmViewHolder(AlarmAdapter.ViewHolder viewHolder, final int position) {
        final Alarm alarm = realmResults.get(position);
        viewHolder.timeTv.setText(alarm.getmWakeTime());
        viewHolder.nameTv.setText(alarm.getmAlarmName());
        viewHolder.isSet.setChecked(alarm.ismIsSet());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CardViewOnClick", position+"");
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("NewAlarm", false);
                intent.putExtra("Position", position);
                view.getContext().startActivity(intent);
            }
        });
//        notifyItemChanged(position);
    }

    public class ViewHolder extends RealmViewHolder {

        @BindView(R.id.alarm_tv) TextView timeTv;
        @BindView(R.id.card_alarms) CardView cardView;
        @BindView(R.id.alarm_nametv) TextView nameTv;
        @BindView(R.id.alarm_is_set) Switch isSet;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("CardViewOnClick", (getAdapterPosition()+1)+"");
//                    Intent intent = new Intent(view.getContext(), AlarmDDetailActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("NewAlarm", false);
//                    intent.putExtra("Position", getAdapterPosition()+1);
//                    view.getContext().startActivity(intent);
//                }
//            });
        }
    }
}

