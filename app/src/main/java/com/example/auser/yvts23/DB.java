package com.example.auser.yvts23;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by auser on 2017/12/7.
 */

public class DB {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_TABLE = "twzipcode";
    private static final String DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "(_id INTEGER PRIMARY KEY, note TEXT , created TEXT,email TEXT);";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        Context mCtx;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mCtx = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    private Context mCtx = null;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DB(Context ctx) {
        this.mCtx = ctx;
    }

    public DB open() throws SQLException {
        dbHelper = new DatabaseHelper(mCtx);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NOTE = "note";
    public static final String KEY_CREATED = "created";
    public static final String KEY_EMAIL = "email";

    String[] strCols = new String[]{
            KEY_ROWID, KEY_NOTE, KEY_CREATED,KEY_EMAIL
    };

    public Cursor getAll() {
        //return db.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        return db.query(DATABASE_TABLE, strCols, null, null, null, null, null);
    }


//edtName,edtTel,edtEmail
    public long create(String noteName) {
        Date now = new Date();
        ContentValues args = new ContentValues();
        args.put(KEY_NOTE, noteName);
        args.put(KEY_CREATED, now.getTime());
        args.put(KEY_EMAIL, "ABC");
        return db.insert(DATABASE_TABLE, null, args);
    }

    public long createUserData(String name,String tel,String email) {
        Date now = new Date();
        ContentValues args = new ContentValues();
        args.put(KEY_NOTE, name);
        args.put(KEY_CREATED, tel);
        args.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE, null, args);
    }

    public Cursor editSelect(long rowId) {
        return db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " where _id=" + rowId, null);

//        return db.query(DATABASE_TABLE, strCols, null, null, null, null, null);

//        if (rowId > 0)
//            return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
//        else
//            return db.delete(DATABASE_TABLE, null, null) > 0;
    }


    public void editExcute(String sql) {
        db.execSQL(sql);
//        if (rowId > 0)
//
//            return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
//        else
//            return db.delete(DATABASE_TABLE, null, null) > 0;
    }


    public boolean delete(long rowId) {
        if (rowId > 0)
            return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
        else
            return db.delete(DATABASE_TABLE, null, null) > 0;
    }

    public boolean delete() {
        return delete(-1);
    }

    public boolean update(long rowId, String note) {
        ContentValues args = new ContentValues();
        args.put(KEY_NOTE, note);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
