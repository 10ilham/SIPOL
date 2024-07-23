package com.ilham.sipol.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class UserServices extends SQLiteOpenHelper {

    public UserServices(Context context) {
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
        db.execSQL("DROP TABLE IF EXISTS "+Services.TABEL_USER);
        onCreate(db);
    }

    public void register(String uid, String password, String nama){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + Services.TABEL_USER + " (uid, password, nama) " +
                "VALUES ('" + uid + "', '" + password + "', '" + nama + "')";
        db.execSQL(query);

    }

    public boolean login(String uid, String password) {
        boolean loggedIn = false;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            String query = "SELECT * FROM " + Services.TABEL_USER +
                    " WHERE uid = '" + uid + "' AND password = '" + password + "'";
            cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0){
                loggedIn = true;
                System.out.println("Login berhasil.");
            } else {
                System.out.println("Login gagal. UID atau password salah.");
            }
        } catch (SQLiteException e){
            System.err.println("Error saat login: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return loggedIn;
    }

    public boolean isExist(String uid){
        boolean exist = false;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            String query = "SELECT * FROM " + Services.TABEL_USER +
                    " WHERE uid = '" + uid + "'";
            cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                exist = true;
            }
        } catch (SQLiteException e){
            System.err.println("Error saat memeriksa keberadaan pengguna: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return exist;
    }

    public String getFullName(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT nama FROM " + Services.TABEL_USER + " WHERE uid = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        String fullName = null;
        if (cursor.moveToFirst()) {
            fullName = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
        }
        cursor.close();
        db.close();
        return fullName;
    }
}
