package com.rva.mrb.vivify.Model.Service;

import android.util.Log;

import com.rva.mrb.vivify.Model.Data.Alarm;

import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmService {

    public static final String TAG = RealmService.class.getSimpleName();

    private final Realm mRealm;

    public RealmService(final Realm realm) {
        mRealm = realm;
    }

    public RealmResults<Alarm> getAllAlarms() {
        return mRealm.where(Alarm.class).findAll();
    }

    public Alarm getAlarm(final String alarmId) {
        Log.d(TAG, "Alarm id: " + mRealm.where(Alarm.class)
                .equalTo("id", alarmId).findFirst().getId());
        return mRealm.where(Alarm.class).equalTo("id", alarmId).findFirst();
    }

    public static Alarm getAlarmById(final String alarmId) {
        final Realm realm = Realm.getDefaultInstance();
        Log.d(TAG, "Alarm id: " + realm.where(Alarm.class)
                .equalTo("id", alarmId).findFirst().getId());
        Log.d(TAG, "Alarm time: " + realm.where(Alarm.class)
                .equalTo("id", alarmId).findFirst().getTime());
        return realm.where(Alarm.class).equalTo("id", alarmId).findFirst();
    }

    public String getNewestAlarmId() {
        return mRealm.where(Alarm.class).findAllSorted("createdAt").first().getId();
    }
    public Alarm getNewestAlarm() {
        return mRealm.where(Alarm.class).findAllSorted("createdAt").first();
    }

    public static Alarm getNextPendingAlarm() {
        final Realm realm = Realm.getDefaultInstance();
        Log.d(TAG, "Number of Alarm objects " +
                realm.where(Alarm.class).findAll().size());
        Log.d(TAG, "Number of enabled Alarm objects " +
                realm.where(Alarm.class).equalTo("enabled", true).findAll().size());
        Log.d(TAG, "Pending alarm date: " +
                realm.where(Alarm.class).equalTo("enabled", true)
                    .findAllSorted("time").first().getTime());
        return realm.where(Alarm.class).equalTo("enabled", true)
                .findAllSorted("time").last();
    }

    public static void enableAlarm(final String alarmId) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Alarm alarm = realm.where(Alarm.class).equalTo("id", alarmId).findFirst();
                if (alarm.isEnabled())
                    alarm.setEnabled(false);
                else
                    alarm.setEnabled(true);
//                alarm.setEnabled(!alarm.isEnabled());
                Log.d(TAG, "Alarm Enabled: " + alarm.isEnabled());
            }
        });
    }

    public static void updateAlarms() {
        Log.d(TAG, "Updating Alarms");
        final Realm realm = Realm.getDefaultInstance();
        // find all enabled alarms whose Times are old
        RealmResults<Alarm> enabledAlarms = realm.where(Alarm.class).equalTo("enabled", true)
                .lessThan("time", Calendar.getInstance().getTime()).findAll();
        Log.d(TAG, "First Alarm Time is: " + enabledAlarms.size());
        for(final Alarm update : enabledAlarms) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    update.updateTime();
                    Log.d(TAG, "Update Alarm Time is: " + update.getTime());
                }
            });
        }
    }
    // Return the the newest alarm by pulling the highest alarm id
    public String getNextAlarm() {
        if (mRealm.where(Alarm.class).equalTo("enabled",true).findAll().size() > 0)
            return mRealm.where(Alarm.class).equalTo("enabled", true).findAll()
                .sort("time").last().getTime()+"";
        else
            return "No Alarm set";
    }

    public void saveAlarm(final String alarmId, final String name, final String time, final boolean isSet, final boolean isStandardTime, final String repeat) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Alarm editAlarm = realm.where(Alarm.class).equalTo("id", alarmId).findFirst();
                editAlarm.setAlarmLabel(name);
                editAlarm.setId(alarmId);
                editAlarm.setmWakeTime(time);
                editAlarm.setTime(time);
                editAlarm.setEnabled(isSet);
                editAlarm.set24hr(isStandardTime);
                editAlarm.setDaysOfWeek(repeat);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Save Alarm Success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("EditAlarm", "failed: " + error.getMessage());
            }
        });
    }

    public void deleteAlarm(final String alarmId) {
        final Realm realm = Realm.getDefaultInstance();
//        Log.d("Realm", realm.toString());
        final RealmResults<Alarm> results = realm.where(Alarm.class).equalTo("id", alarmId).findAll();
//        Log.d("realm", results.get(0).getmWakeTime());
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Alarm.class).equalTo("id", alarmId).findAll().deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("Successful", "Alarm deleted");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("Error", error.getMessage());
            }
        });
    }

    public void addAlarmAsync(final String name, final String time,
                              final boolean isSet, final boolean isStandardTime,
                              final String repeat) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(final Realm realm) {
                Alarm alarm = realm.createObject(Alarm.class);
                alarm.setId(UUID.randomUUID().toString());
                alarm.setAlarmLabel(name);
                alarm.setmWakeTime(time);
                alarm.setTime(time);
                alarm.setEnabled(isSet);
                alarm.set24hr(isStandardTime);
                alarm.setDaysOfWeek(repeat);
                alarm.setCreatedAt(Calendar.getInstance().getTime());
            }
        }, new Realm.Transaction.OnSuccess() {

            @Override
            public void onSuccess() {
                Log.d("successful", "Successful transaction!");
            }
        }, new Realm.Transaction.OnError(){

            @Override
            public void onError(Throwable error) {
                Log.d("error", error.getMessage());
            }
        });
    }

    public String getMessage(){
        return "From realmService!!";
    }

    public void closeRealm() {
        mRealm.close();
    }

    public interface OnTransactionCallback {
        void onRealmSuccess();
        void onRealmError(final Exception e);
    }
}
