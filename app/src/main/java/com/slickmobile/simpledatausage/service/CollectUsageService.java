package com.slickmobile.simpledatausage.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.TrafficStats;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.slickmobile.simpledatausage.R;
import com.slickmobile.simpledatausage.data.DatabaseHelper;
import com.slickmobile.simpledatausage.ui.MainActivity;
import com.slickmobile.simpledatausage.util.ByteFormatter;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class CollectUsageService extends BaseService {

    private final int EMPTY = 0;
    private final int NOT_EMPTY = 1;
    private Observable<Integer> dbObservable = Observable.empty();

    @Inject
    BriteDatabase db;
    @Inject
    NotificationManager nm;
    @Inject
    ByteFormatter bf;

    private Notification.Builder nb;

    public void onCreate() {
        super.onCreate();
        getAppComponent().inject(this);
        dbObservable = db.createQuery(DatabaseHelper.TOTAL_USAGE_TABLE,
                "SELECT CASE WHEN EXISTS (SELECT 1 FROM " + DatabaseHelper.TOTAL_USAGE_TABLE + " LIMIT 1) THEN 1 ELSE 0 END")
                .map(new Func1<SqlBrite.Query, Integer>() {
                    @Override
                    public Integer call(SqlBrite.Query query) {
                        Cursor cursor = query.run();
                        cursor.moveToFirst();
                        return cursor.getInt(0);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        //buildNotification();
        //startForeground(1, nb.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dbObservable.subscribe(new DbSubscriber());
        Timber.d("subscribed: %s", DateTime.now());
        List<ApplicationInfo> apps = updateInstalledApplications();
        updatePerAppUsage(apps);
        return super.onStartCommand(intent, flags, startId);
    }

    private List<ApplicationInfo> updateInstalledApplications() {
        PackageManager pm = this.getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        ContentValues[] values = new ContentValues[apps.size()];
        for (int i = 0; i < apps.size(); i++) {
            values[i] = new ContentValues();
            values[i].put(DatabaseHelper.COL_UID, apps.get(i).uid);
            values[i].put(DatabaseHelper.COL_PACKAGE, apps.get(i).packageName);
            values[i].put(DatabaseHelper.COL_LABEL, apps.get(i).loadLabel(pm).toString());
        }
        insertInBackground(DatabaseHelper.APP_USAGE_TABLE, values);
        return apps;
    }

    private void updatePerAppUsage(List<ApplicationInfo> apps) {
        ContentValues[] values = new ContentValues[apps.size()];
        for (int i = 0; i < apps.size(); i++) {
            values[i] = new ContentValues();
            int uid = apps.get(i).uid;
            values[i].put(DatabaseHelper.COL_UID, uid);
            values[i].put(DatabaseHelper.COL_BYTES, TrafficStats.getUidRxBytes(uid));
        }
        updateInBackground(DatabaseHelper.APP_USAGE_TABLE, values);
    }

    private void buildNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        nb = new Notification.Builder(this)
                .setContentTitle("Simple Data Usage")
                .setContentText("0 bytes used")
                .setSmallIcon(R.drawable.ic_transfer)
                .setOngoing(true)
                .setContentIntent(pendingIntent);
    }

    private class DbSubscriber extends Subscriber<Integer> {
        @Override
        public void onCompleted() {
            Timber.d("onComplete");
        }

        @Override
        public void onError(Throwable e) {
            Timber.d(e, "onError");
        }

        @Override
        public void onNext(Integer queryResult) {
            unsubscribe();
            long bytes = TrafficStats.getTotalRxBytes();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COL_BYTES, bytes);
            switch (queryResult) {
                case EMPTY:
                    insertInBackground(DatabaseHelper.TOTAL_USAGE_TABLE, values);
                    Timber.d("Inserting bytes: %d", bytes);
                    break;
                case NOT_EMPTY:
                    updateInBackground(DatabaseHelper.TOTAL_USAGE_TABLE, values, null, null);
                    Timber.d("Updating bytes: %d", bytes);
                    break;
            }
            //nb.setContentText(bf.format(bytes) + " used")
            //        .setWhen(System.currentTimeMillis());
            //nm.notify(1, nb.build());
        }
    }

    private void insertInBackground(final String table, final ContentValues values) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                db.insert(table, values);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void insertInBackground(final String table, final ContentValues[] values){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                db.beginTransaction();
                try {
                    for (ContentValues val : values) {
                        db.insert(table, val);
                    }
                    db.setTransactionSuccessful();
                } catch (SQLiteConstraintException ex) {
                    Timber.d("SQLiteConstraintException for app_usage table");
                } finally {
                    db.endTransaction();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void updateInBackground(final String table, final ContentValues values,
                                    final String whereClause, final String whereArgs) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                db.insert(table, values);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void updateInBackground(final String table, final ContentValues[] values){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                db.beginTransaction();
                try {
                    for (ContentValues val : values) {
                        db.update(table, val, "uid=?", val.getAsString(DatabaseHelper.COL_UID));
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

}
