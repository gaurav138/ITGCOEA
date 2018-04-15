package com.example.w10.itgcoea.Admin;

/**
 * Created by W10 on 15/03/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by nadeem on 24-Jan-18.
 */
public class SQLiteDbHandler {
    private static final String DATABASE_NAME = "notificationdb";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourhelper;
    private final Context ourcontext;
    private SQLiteDatabase ourdatabase;

    public SQLiteDbHandler(Context c) {
        ourcontext = c;
    }

    public void createEventEntry(DBNotification event) {

     //   ourdatabase.delete("notify","message = '" + event.message + "'",null);

        ContentValues cv = new ContentValues();
        cv.put("title",event.title);
        cv.put("message",event.message);
        cv.put("date",event.date);
        cv.put("status", "0");

        ourdatabase.insert("notify",null,cv);


    }

    public DBNotification[] getContactData() {
        Cursor c = ourdatabase.rawQuery("select * from `notify` ", null);
        int title = c.getColumnIndex("title");
        int message = c.getColumnIndex("message");
        int date = c.getColumnIndex("date");
        int status = c.getColumnIndex("status");
        int rowcount = 0;
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
            rowcount++;
        }
        if(rowcount == 0) {
            return  null;
        }
        DBNotification results[] = new DBNotification[rowcount];
        int i=0;
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
            results[i] = new DBNotification();
            results[i].title = c.getString(title);
            results[i].message = c.getString(message);
            results[i].status = c.getString(status);
            results[i].date = c.getString(date);
            i++;
        }
        return results;
    }

    public SQLiteDbHandler open() throws SQLException{
        ourhelper = new DbHelper(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
        return this;
    }
    public void close(){
        ourhelper.close();
    }

    private static class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS notify(title varchar(50) NOT NULL,message varchar(200) NOT NULL PRIMARY KEY,date varchar(20) NOT NULL,status varchar(10))");
              }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS notify");

            onCreate(db);
        }
    }
}