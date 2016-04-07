package com.xiezhenlin.odn.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xiezhenlin.odn.domain.NoteDomain;
import com.xiezhenlin.odn.utils.OdnDBH;

import java.util.ArrayList;

/**
 * Created by xiezhenlin on 2016/4/7.
 */
public class ODNDao {
    private final  static String TAG="ODNDao";
    private Context mContext;
    private OdnDBH mOdnDBH;
    private SQLiteDatabase db;
    public ODNDao(Context context){
        mContext=context;
        mOdnDBH=new OdnDBH(mContext);
    }
    // Insert an ODN into the database
    public boolean addODN(NoteDomain noteDomain) {
        Log.i(TAG, "addODN start");
        // Create and/or open the database for writing
        db = mOdnDBH.getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            //long userId = addOrUpdateUser(noteDomain.user);
            Log.i(TAG, "ODN comment:" + "Id is " + String.valueOf(noteDomain.getOdn_id()) + ";" + "comment is " + noteDomain.getOdn_comment() + ";" + "comment date is " + noteDomain.getOdn_date());
            ContentValues values = new ContentValues();
            values.put(OdnDBH.KEY_ODN_COMMENT_ID, noteDomain.getOdn_id());
            values.put(OdnDBH.KEY_ODN_COMMENT, noteDomain.getOdn_comment());
            values.put(OdnDBH.KEY_ODN_COMMENT_DATE, noteDomain.getOdn_date());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(OdnDBH.TABLE_ODNCOMMENT, null, values);
            db.setTransactionSuccessful();
            Log.i(TAG, "addODN done!");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add ODN to database!");
            return false;
        } finally {
            db.endTransaction();
            db.close();
            return true;
        }
    }
    //get ODN data
    public ArrayList<NoteDomain> getODNs(){
        db=mOdnDBH.getReadableDatabase();
        ArrayList<NoteDomain> noteDomains=new ArrayList<NoteDomain>();
        Cursor c = db.rawQuery("SELECT * FROM "+OdnDBH.TABLE_ODNCOMMENT, null);
        while (c.moveToNext()) {
            NoteDomain noteDomain = new NoteDomain();
            noteDomain.setOdn_id(c.getInt(c.getColumnIndex(OdnDBH.KEY_ODN_COMMENT_ID)));
            noteDomain.setOdn_comment(c.getString(c.getColumnIndex(OdnDBH.KEY_ODN_COMMENT)));
            noteDomain.setOdn_date(c.getString(c.getColumnIndex(OdnDBH.KEY_ODN_COMMENT_DATE)));
            noteDomains.add(noteDomain);
        }
        c.close();
        db.close();
        return noteDomains;
    }
}
