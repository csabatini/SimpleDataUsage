package com.slickmobile.simpledatausage.mvp.interactor;

import android.database.Cursor;

import com.slickmobile.simpledatausage.data.DatabaseHelper;
import com.slickmobile.simpledatausage.model.InstalledApp;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class AppListInteractorImpl implements AppListInteractor {

    private BriteDatabase db;

    @Inject
    public AppListInteractorImpl(BriteDatabase db) {
        this.db = db;
    }

    public Observable<List<InstalledApp>> getInstalledApps() {
        return db.createQuery(DatabaseHelper.APP_USAGE_TABLE, "SELECT * FROM app_usage")
          .map(APP_MAP);
    }

    private static final Func1<SqlBrite.Query, List<InstalledApp>> APP_MAP =
        new Func1<SqlBrite.Query, List<InstalledApp>>() {
            @Override
            public List<InstalledApp> call(SqlBrite.Query query) {
                Cursor cursor = query.run();
                List<InstalledApp> apps = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    int uid = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_UID));
                    long bytes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_BYTES));
                    String pkg = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PACKAGE));
                    String lbl = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LABEL));
                    apps.add(new InstalledApp(uid, bytes, pkg, lbl));
                }
                return apps;
            }
    };

}
