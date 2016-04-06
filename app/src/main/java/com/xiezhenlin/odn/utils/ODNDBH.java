package com.xiezhenlin.odn.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xiezhenlin.odn.domain.NoteDomain;

/**
 * Created by xiezhenlin on 2016/4/1.
 */
public class ODNDBH extends SQLiteOpenHelper {

    private  static final String TAG="ODNDBH";
    // Database Info
    private static final String DATABASE_NAME = "odndb";
    private static final int DATABASE_VERSION = 1;
    // Table Names
    private static final String TABLE_ODNCOMMENT = "odn";

    // ODN Table Columns
    private static final String KEY_ODN_ID = "id";
    private static final String KEY_ODN_COMMENT_ID = "odn_commentId";
    private static final String KEY_ODN_COMMENT = "odn_comment";
    private static final String KEY_ODN_COMMENT_DATE = "odn_commentdate";
    public ODNDBH(Context context) {
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ODNCOMMENT);
            onCreate(db);
        }
    }
    // Insert an ODN into the database
    public void addODN(NoteDomain noteDomain) {
        Log.i(TAG,"addODN start");
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            //long userId = addOrUpdateUser(noteDomain.user);
            Log.i(TAG,"ODN comment:"+"Id is "+String.valueOf(noteDomain.getOdn_id())+";"+"comment is "+noteDomain.getOdn_comment()+";"+"comment date is "+noteDomain.getOdn_date());
            ContentValues values = new ContentValues();
            values.put(KEY_ODN_COMMENT_ID, noteDomain.getOdn_id());
            values.put(KEY_ODN_COMMENT, noteDomain.getOdn_comment());
            values.put(KEY_ODN_COMMENT_DATE, noteDomain.getOdn_date());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_ODNCOMMENT, null, values);
            db.setTransactionSuccessful();
            Log.i(TAG,"addODN done!");
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database!");
        } finally {
            db.endTransaction();
        }
    }
}
