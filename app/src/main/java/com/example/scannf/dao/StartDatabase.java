package com.example.scannf.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StartDatabase extends SQLiteOpenHelper {
    static final String USER_DB = "scannf.db";
    static final String TABLE_USER = "users";
    static final String UID_USER = "uid";
    static final String NAME_USER = "name";
    static final String CAR_USER = "car";
    static final String PASS_USER = "pass";
    static final int VERSION = 1;

    static final String TABLE_NF = "nf";
    static final String NUMERO_NF = "numNota";
    static final String DT_RECORD = "dtRecord";
    static final String HR_RECORD = "hrRecord";
    static final String EDIT_NF = "hrRecord";

    public StartDatabase(Context context) {
        super(context, USER_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create a user table
        String sql = "CREATE TABLE " + TABLE_USER + "("
                + UID_USER + " text primary key,"
                + NAME_USER + " text,"
                + CAR_USER + " text,"
                + PASS_USER + " text"
                + ")";
        db.execSQL(sql);

        //Create a nf table
        sql = "CREATE TABLE " + TABLE_NF + "("
                + NUMERO_NF + " text primary key,"
                + DT_RECORD + " text,"
                + HR_RECORD + " text,"
                + EDIT_NF + " text"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NF);
        onCreate(db);
    }
}
