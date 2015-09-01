package com.slickmobile.simpledatausage.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper {

    // General database information
    public static String DATABASE_NAME = "usage.db";
    private static int DATABASE_VERSION = 1;
    public static String COL_ID = BaseColumns._ID;

    // Total usage table information
    public static String TOTAL_USAGE_TABLE = "total_usage";
    public static String COL_BYTES = "bytes";
    public static String[] TOTAL_USAGE_COLUMNS = { COL_ID, COL_BYTES };

    // App usage table information
    public static String APP_USAGE_TABLE = "app_usage";
    public static String COL_UID = "uid";
    public static String COL_PACKAGE = "package";
    public static String COL_LABEL = "label";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TOTAL_USAGE_TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_BYTES + " INTEGER"
                + ");");
        db.execSQL("CREATE TABLE " + APP_USAGE_TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_UID + " INTEGER,"
                + COL_PACKAGE + " TEXT,"
                + COL_LABEL + " TEXT,"
                + COL_BYTES + " INTEGER,"
                + "UNIQUE (" + COL_UID + ", " + COL_PACKAGE + ")"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TOTAL_USAGE_TABLE + ";");
        onCreate(db);
    }


}
