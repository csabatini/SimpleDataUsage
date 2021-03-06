package com.slickmobile.simpledatausage.injection.module;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.slickmobile.simpledatausage.data.DatabaseHelper;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    public SQLiteOpenHelper provideDbHelper(Context context) {
        return new DatabaseHelper(context);
    }

    @Provides
    @Singleton
    public SqlBrite provideSqlBrite() {
        return SqlBrite.create();
    }

    @Provides
    @Singleton
    public BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper dbHelper) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(dbHelper);
        //db.setLoggingEnabled(true);
        return db;
    }
}