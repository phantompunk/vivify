package com.rva.mrb.vivify.View.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rva.mrb.vivify.Model.Alarm;
import com.rva.mrb.vivify.R;


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
    }

    public class ViewHolder extends RealmViewHolder {

        @BindView(R.id.alarm_tv)
        TextView timeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

