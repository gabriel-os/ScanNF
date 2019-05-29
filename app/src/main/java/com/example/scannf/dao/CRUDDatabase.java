package com.example.scannf.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CRUDDatabase {
    private SQLiteDatabase db;
    private StartDatabase bd;

    public CRUDDatabase(Context context) {
        bd = new StartDatabase(context);
        ContentValues valores;
        db = bd.getWritableDatabase();

    }

    public void insertDataUser(String uid, String name) {
        ContentValues valores;
        long resultado;

        valores = new ContentValues();
        valores.put(StartDatabase.UID_USER, uid);
        valores.put(StartDatabase.NAME_USER, name);


        resultado = db.insert(StartDatabase.TABLE_USER, null, valores);
        db.close();

        if (resultado == -1)
            Log.v("TESTE", "N");
        else
            Log.v("TESTE", "F");

    }

    public String getName() {
        Cursor cursor;
        String[] campos = {bd.NAME_USER};
        db = bd.getReadableDatabase();

        //cursor =db.query(bd.TABLE_USER, campos, null, null, null, null, null, null);

        cursor = db.rawQuery("SELECT name FROM users", null);

        cursor.moveToFirst();


        String name = cursor.getString(cursor.getColumnIndex("name"));

        StringBuilder conversor = new StringBuilder();
        conversor.append(name);
        db.close();
        Log.v("TESTE2", conversor.toString());
        return conversor.toString();

    }


}
