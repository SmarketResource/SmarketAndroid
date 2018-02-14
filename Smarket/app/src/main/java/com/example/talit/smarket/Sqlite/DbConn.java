package com.example.talit.smarket.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.talit.smarket.LogicalView.Busca;
import com.example.talit.smarket.LogicalView.Usuario;

import java.util.ArrayList;

/**
 * Created by talit on 12/02/2018.
 */

public class DbConn {

    private final SQLiteDatabase db;

    public DbConn(Context c) {
        DbCore dbcore = new DbCore(c);
        db = dbcore.getWritableDatabase();
    }
    public void insertConsumidor(String idConsumidor,String access_token,String tpAcesso) {
        ContentValues valor = new ContentValues();
        valor.put("id_cons", idConsumidor);
        valor.put("access_token", access_token);
        valor.put("tp_acesso", tpAcesso);
        db.insert("consumidor", null, valor);
    }
    public void insertSearchView(String busca){
        ContentValues valor = new ContentValues();
        valor.put("busca",busca);
        db.insert("searchview", null, valor);
    }
    public Usuario selectConsumidor() {

        Cursor cursor = db.query(true, "consumidor", null, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            return new Usuario(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2));
        }
        return null;
    }

    public ArrayList<Busca> selectHistorico(){
        ArrayList<Busca> listas = new ArrayList<Busca>();

        String[] colunas_db = new String[]{"id_hist","busca"};
        Cursor cursor = db.query(true, "searchview", null, null, null, null, null, null, null);

        if(cursor.moveToFirst()){

            do{
                listas.add(new Busca(cursor.getInt(0),
                        cursor.getString(1)));

            }while(cursor.moveToNext());

        }
        return listas;
    }

    public int totalBuscas(){

        Cursor cursor = db.rawQuery("SELECT COUNT (*) FROM searchview ",null);

        int count = 0;
        if(cursor.moveToFirst())
        {
            count = cursor.getInt(0);
        }
        return count;
    }

    public void deleteConsumidor() {
        db.execSQL("delete from consumidor");
    }

    public void deleteBusca(int id){
        db.delete("searchview","id_hist  ="+ id,null);
    }

    public void deleteHistorico() {
        db.execSQL("delete from searchview");
    }
}
