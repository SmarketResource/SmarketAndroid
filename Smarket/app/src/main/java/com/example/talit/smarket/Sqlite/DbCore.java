package com.example.talit.smarket.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by talit on 28/01/2018.
 */

public class DbCore extends SQLiteOpenHelper {

    private static final String DB_NAME = "SMARKET";
    private static final int DB_VERSION = 1;

    public DbCore(Context c){
        super(c,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table consumidor(id_cons text,status integer,tp_acesso integer)");
        db.execSQL("create table searchview(id_hist integer primary key autoincrement, busca text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table consumidor;");
        db.execSQL("drop table searchview");
        onCreate(db);
    }
}
