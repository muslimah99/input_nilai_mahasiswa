package com.universitaskuningan.inputnilaimahasiswa;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context) {
        super(context, "DatabaseNilai.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE tabel_nilai (nim INTEGER PRIMARY KEY, nama VARCHAR, " +
                "kelas VARCHAR, nilai_tugas REAL, nilai_proses REAL, nilai_uts REAL, " +
                "nilai_uas REAL, nilai_akhir REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tabel_nilai");
        onCreate(db);
    }

    public boolean insertData(String nim, String nama, String kelas, Double nilai_tugas, Double
            nilai_proses, Double nilai_uts, Double nilai_uas, Double nilai_akhir) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nim", nim);
        contentValues.put("nama", nama);
        contentValues.put("kelas", kelas);
        contentValues.put("nilai_tugas", nilai_tugas);
        contentValues.put("nilai_proses", nilai_proses);
        contentValues.put("nilai_uts", nilai_uts);
        contentValues.put("nilai_uas", nilai_uas);
        contentValues.put("nilai_akhir", nilai_akhir);
        db.insert("tabel_nilai", null, contentValues);
        return true;
    }

    public Cursor getData(String nim) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tabel_nilai WHERE nim = " +nim+ "", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, "tabel_nilai");
    }

    public boolean updateData(String nim, String nama, String kelas, Double nilai_tugas, Double
            nilai_proses, Double nilai_uts, Double nilai_uas, Double nilai_akhir) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nim", nim);
        contentValues.put("nama", nama);
        contentValues.put("kelas", kelas);
        contentValues.put("nilai_tugas", nilai_tugas);
        contentValues.put("nilai_proses", nilai_proses);
        contentValues.put("nilai_uts", nilai_uts);
        contentValues.put("nilai_uas", nilai_uas);
        contentValues.put("nilai_akhir", nilai_akhir);
        db.update("tabel_nilai", contentValues, "nim = ? ", new String[]{nim});
        return true;
    }

    public void deleteData(String nim) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tabel_nilai", "nim = ? ", new String[]{nim});
    }

    public ArrayList<String> getAllData() {
        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery( "SELECT * FROM tabel_nilai", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            String nim = res.getString(res.getColumnIndex("nim"));
            String nama = res.getString(res.getColumnIndex("nama"));
            String kelas = res.getString(res.getColumnIndex("kelas"));
            String nilai_tugas = res.getString(res.getColumnIndex("nilai_tugas"));
            String nilai_proses = res.getString(res.getColumnIndex("nilai_proses"));
            String nilai_uts = res.getString(res.getColumnIndex("nilai_uts"));
            String nilai_uas = res.getString(res.getColumnIndex("nilai_uas"));
            String nilai_akhir = res.getString(res.getColumnIndex("nilai_akhir"));
            //array_list.add(nim+", "+nama+", "+kelas+", "+nilai_tugas+", "+nilai_proses+", "+nilai_uts+", "+nilai_uas+", "+nilai_akhir);
            array_list.add(nim+"            "+nilai_akhir);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getSomeData(String kelas) {
        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor res = db.rawQuery( "SELECT * FROM tabel_nilai " +
                "WHERE kelas = '"+kelas+"'", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            String nim = res.getString(res.getColumnIndex("nim"));
            String nilai_akhir = res.getString(res.getColumnIndex("nilai_akhir"));
            array_list.add(nim+"            "+nilai_akhir);
            res.moveToNext();
        }
        return array_list;
    }
}

