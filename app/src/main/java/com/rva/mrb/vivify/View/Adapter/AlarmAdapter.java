package com.rva.mrb.vivify.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.rva.mrb.vivify.Model.Alarm;
import com.rva.mrb.vivify.Model.AlarmInfo;
import com.rva.mrb.vivify.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by rigo on 6/23/16.
 */
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
        viewHolder.timeTv.setText(alarm.getmTime());
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
//ExpandableRecyclerAdapter<AlarmAdapter.AlarmHolder, AlarmAdapter.AlarmInfoHolder>
//implements RealmChangeListener {
//
//    private RealmResults<Alarm> mAlarms;
//    private LayoutInflater mInflator;
//
//    public AlarmAdapter(Context context, @NonNull List<? extends ParentListItem> parentListItem) {
//        super(parentListItem);
//        mInflator = LayoutInflater.from(context);
//    }
//
//    public AlarmHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
//        View alarmView = mInflator.inflate(R.layout.alarm_view, parentViewGroup, false);
//        return new AlarmHolder(alarmView);
//    }
//
//    public AlarmInfoHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
//        View alarmInfo = mInflator.inflate(R.layout.alarm_info_view, childViewGroup, false);
//        return new AlarmInfoHolder(alarmInfo);
//    }
//    public void onBindParentViewHolder(AlarmHolder alarmHolder, int postion, ParentListItem parentListItem) {
//        Alarm alarm = (Alarm) parentListItem;
//        alarmHolder.bind(alarm);
//    }
//
//    public void onBindChildViewHolder(AlarmInfoHolder alarmInfoHolder, int position, Object childListItem) {
//        AlarmInfo alarmInfo = (AlarmInfo) childListItem;
//        alarmInfoHolder.bind(alarmInfo);
//    }
//
//    public void setAlarms(final RealmResults<Alarm> alarms) {
//        mAlarms = alarms;
//        mAlarms.addChangeListener(this);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public void onChange(Object element) {
//        notifyDataSetChanged();
//    }
//
//    public class AlarmHolder extends ParentViewHolder {
//
//        @BindView(R.id.alarm_tv) TextView mAlarmTV;
//
//        public AlarmHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//
//        public void bind(Alarm alarm) { mAlarmTV.setText(alarm.getmTime()); }
//
//    }
//
//    public class AlarmInfoHolder extends ChildViewHolder {
//
//        @BindView(R.id.alarm_info_tv) TextView mAlarmInfoTV;
//
//        public AlarmInfoHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//
//        public void bind(AlarmInfo info) {
//            mAlarmInfoTV.setText(info.getTime());
//        }
//    }
//
//    public interface OnAlarmClickListener {
//        void onBookClick(int id);
//    }
