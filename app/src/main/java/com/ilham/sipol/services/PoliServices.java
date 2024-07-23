package com.ilham.sipol.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class PoliServices extends SQLiteOpenHelper {

    public PoliServices(Context context) {
        super(context,Services.NAMA_DATABASE,null,Services.VERSI_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String BUAT_TABEL="CREATE TABLE " + Services.TABEL_POLI +
                "(id_poli TEXT PRIMARY KEY NOT NULL," +
                "nama_poli TEXT NOT NULL)";
        db.execSQL(BUAT_TABEL);

        final String BUAT_TABEL2="CREATE TABLE " + Services.TABEL_PASIEN +
                "(id_pasien TEXT PRIMARY KEY NOT NULL," +
                "nama_pasien TEXT NOT NULL,jenis_kelamin TEXT NOT NULL,usia TEXT NOT NULL,poli TEXT NOT NULL,gol_darah TEXT NOT NULL)";
        db.execSQL(BUAT_TABEL2);

        final String BUAT_TABEL3="CREATE TABLE " + Services.TABEL_USER+
                "(uid TEXT PRIMARY KEY NOT NULL," +
                "nama TEXT NOT NULL,password TEXT NOT NULL)";
        db.execSQL(BUAT_TABEL3);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Services.TABEL_POLI);
        onCreate(db);
    }

    public void insert(String id,String nama){
        String QUERY="INSERT INTO " + Services.TABEL_POLI + " VALUES('" + id +
                "','" +nama+"')";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(QUERY);
    }


    public void update(String id, String nama, String old_id) {
        String QUERY="UPDATE " + Services.TABEL_POLI + " SET id_poli='" + id +
                "',nama_poli='"+
                nama+"' WHERE id_poli='"+old_id + "'";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public void delete(String id) {
        String QUERY="DELETE FROM " + Services.TABEL_POLI +
                " WHERE id_poli='"+id + "'";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public ArrayList<HashMap<String,String>> getAll(){
        ArrayList<HashMap<String,String >> daftar=new ArrayList<>();
        String QUERY="SELECT * FROM " + Services.TABEL_POLI;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String,String> temp=new HashMap<>();
                temp.put("id_poli",cursor.getString(0));
                temp.put("nama_poli",cursor.getString(1));
                daftar.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return daftar;
    }


    public ArrayList<HashMap<String,String>> getAllByNama(String nama) {
        ArrayList<HashMap<String,String >> daftar=new ArrayList<>();
        String QUERY="SELECT * FROM " + Services.TABEL_POLI +
                " WHERE nama_poli like '%"+ nama + "%'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String,String> temp=new HashMap<>();
                temp.put("id_poli",cursor.getString(0));
                temp.put("nama_poli",cursor.getString(1));
                daftar.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return daftar;
    }
    public ArrayList<String> getDataPoli() {
        ArrayList<String> NamaPoli = new ArrayList<>();
        String QUERY="SELECT nama_poli FROM " + Services.TABEL_POLI;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if (cursor.moveToFirst()){
            do{
                NamaPoli.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return NamaPoli;
    }

    public boolean isExists(String kd){
        boolean cek=false;

        String QUERY="SELECT * FROM " +Services.TABEL_POLI +
                " WHERE id_poli='" +kd +"'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if (cursor.getCount()>0){
            cek=true;
        }
        return cek;
    }

}
