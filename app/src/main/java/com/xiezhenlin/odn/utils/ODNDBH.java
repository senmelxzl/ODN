package com.xiezhenlin.odn.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xiezhenlin on 2016/4/1.
 */
public class OdnDBH extends SQLiteOpenHelper {

    private  static final String TAG="OdnDBH";
    // Database Info
    private static final String DATABASE_NAME = "odndb";
    private static final int DATABASE_VERSION = 1;
    // Table Names
    public static final String TABLE_ODNCOMMENT = "odn";

    // ODN Table Columns
    public static final String KEY_ODN_ID = "id";
    public static final String KEY_ODN_COMMENT_ID = "odn_commentId";
    public static final String KEY_ODN_COMMENT = "odn_comment";
    public static final String KEY_ODN_COMMENT_DATE = "odn_commentdate";
    public OdnDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ODN_TABLE = "CREATE TABLE " + TABLE_ODNCOMMENT +
                "(" +
                KEY_ODN_ID + " INTEGER PRIMARY KEY," +
                KEY_ODN_COMMENT_ID + " INTEGER KEY," +
                KEY_ODN_COMMENT + " TEXT," +
                KEY_ODN_COMMENT_DATE + " TEXT" +
                ")";
        db.execSQL(CREATE_ODN_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ODNCOMMENT);
            onCreate(db);
        }
    }
}
