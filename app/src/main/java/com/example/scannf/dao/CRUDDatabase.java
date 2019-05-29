package com.example.scannf.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CRUDDatabase {
    private SQLiteDatabase db;
    private StartDatabase bd;

    public CRUDDatabase(Context context) {
        bd = new StartDatabase(context);
        ContentValues valores;
        db = bd.getWritableDatabase();

    }

    public String insertDataUser(String titulo, String autor, String editora) {
        ContentValues valores;
        long resultado;

        valores = new ContentValues();
        valores.put(StartDatabase.UID_USER, titulo);
        valores.put(StartDatabase.NAME_USER, autor);
        valores.put(StartDatabase.CAR_USER, editora);
        valores.put(StartDatabase.PASS_USER, editora);

        resultado = db.insert(StartDatabase.TABLE_USER, null, valores);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }
}
