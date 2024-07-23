package com.ilham.sipol.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class PasienServices extends SQLiteOpenHelper {

    public PasienServices(Context context) {
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
        db.execSQL("DROP TABLE IF EXISTS "+Services.TABEL_PASIEN);
        onCreate(db);
    }

    public void insert(String id, String nama, String jk, String usia, String poli, String gol_darah){
        String QUERY="INSERT INTO " + Services.TABEL_PASIEN + " VALUES('" + id +
                "','" +nama+"','"+jk+"','"+usia+"','"+poli +"','"+ gol_darah +"')";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public void update(String id, String nama, String jk, String usia, String poli, String gol_darah, String old_id) {
        String QUERY="UPDATE " + Services.TABEL_PASIEN + " SET id_pasien='" + id +
                "',nama_pasien='"+
                nama+"',jenis_kelamin='"+jk+"',usia='"+usia+"',poli='"+ poli +"',gol_darah='"+ gol_darah +"' WHERE id_pasien='"+old_id + "'";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public void delete(String id) {
        String QUERY="DELETE FROM " + Services.TABEL_PASIEN +
                " WHERE id_pasien='"+id + "'";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(QUERY);
    }

    public ArrayList<HashMap<String,String>> getAll(){
        ArrayList<HashMap<String,String >> daftar=new ArrayList<>();
        String QUERY="SELECT * FROM " + Services.TABEL_PASIEN;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String,String> temp=new HashMap<>();
                temp.put("id_pasien",cursor.getString(0));
                temp.put("nama_pasien",cursor.getString(1));
                temp.put("jk",cursor.getString(2));
                temp.put("usia", cursor.getString(3));
                temp.put("poli",cursor.getString(4));
                temp.put("gol_darah",cursor.getString(5));
                daftar.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return daftar;
    }

    public ArrayList<HashMap<String,String>> getAllByNama(String nama) {
        ArrayList<HashMap<String,String >> daftar=new ArrayList<>();
        String QUERY="SELECT * FROM " + Services.TABEL_PASIEN +
                " WHERE nama_pasien like '%"+ nama + "%'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String,String> temp=new HashMap<>();

                temp.put("id_pasien",cursor.getString(0));
                temp.put("nama_pasien",cursor.getString(1));
                temp.put("jk",cursor.getString(2));
                temp.put("usia", cursor.getString(3));
                temp.put("poli",cursor.getString(4));
                temp.put("gol_darah",cursor.getString(5));
                daftar.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return daftar;
    }

    public boolean isExists(String kd){
        boolean cek=false;

        String QUERY="SELECT * FROM " +Services.TABEL_PASIEN +
                " WHERE id_pasien='" +kd +"'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(QUERY,null);

        if (cursor.getCount()>0){
            cek=true;
        }
        return cek;
    }
}
