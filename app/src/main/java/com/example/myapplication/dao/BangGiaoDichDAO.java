package com.example.myapplication.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.BangGiaoDich;

import java.util.ArrayList;
import java.util.List;

public class BangGiaoDichDAO {

    private SQLiteDatabase db;
    private static final String TABLE_NAME = "bang_giao_dich";
    public static final String MA_GD = "ma_gd";
    public static final String NGAY_GD = "ngay_gd";
    public static final String SO_TIEN_GD = "so_tien_gd";
    public static final String MA_KHOAN = "ma_khoan";
    public static final String MO_TA = "mo_ta";
    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + " ( " +
            MA_GD + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            NGAY_GD + " VARCHAR NOT NULL, " +
            SO_TIEN_GD + " REAL NOT NULL, " +
            MA_KHOAN + " INTEGER ," +
            MO_TA + " VARCHAR ," +
            " FOREIGN KEY (" + MA_KHOAN + ") REFERENCES khoan_thu_chi (" + MA_KHOAN + ") " +
            " );";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public BangGiaoDichDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long createBangGD(BangGiaoDich bangGiaoDich){
        ContentValues values = new ContentValues();
        values.put(NGAY_GD,bangGiaoDich.getNgayGiaoDich());
        values.put(SO_TIEN_GD,bangGiaoDich.getSoTienGiaoDich());
        values.put(MA_KHOAN,bangGiaoDich.getMaKhoan());
        values.put(MO_TA,bangGiaoDich.getMoTa());
        return db.insert(TABLE_NAME,null,values);
    }

    public BangGiaoDich readBangGiaoDich(int maGD){
        String[] columns = {MA_GD,NGAY_GD,SO_TIEN_GD,MA_KHOAN,MO_TA};
        String selection = MA_GD + " = ?";
        String[] selectionArgs = {maGD+""};

        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);

        if(cursor.moveToNext()){
            return new BangGiaoDich(cursor.getInt(cursor.getColumnIndex(MA_GD)),
                    cursor.getString(cursor.getColumnIndex(NGAY_GD)),
                    cursor.getFloat(cursor.getColumnIndex(SO_TIEN_GD)),
                    cursor.getInt(cursor.getColumnIndex(MA_KHOAN)),
                    cursor.getString(cursor.getColumnIndex(MO_TA)));
        }
        return null;
    }

    public List readAll(){
        List listbangthuchi = new ArrayList();
        String[] columns = {MA_GD,NGAY_GD,SO_TIEN_GD,MA_KHOAN,MO_TA};
        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext()){
            listbangthuchi.add(new BangGiaoDich(cursor.getInt(cursor.getColumnIndex(MA_GD)),
                    cursor.getString(cursor.getColumnIndex(NGAY_GD)),
                    cursor.getFloat(cursor.getColumnIndex(SO_TIEN_GD)),
                    cursor.getInt(cursor.getColumnIndex(MA_KHOAN)),
                    cursor.getString(cursor.getColumnIndex(MO_TA))));
        }
        return listbangthuchi;
    }

    public int update(BangGiaoDich bangGiaoDich){
        ContentValues values = new ContentValues();
        String whereClause = MA_GD + " = ?";
        String[] whereArgs = {bangGiaoDich.getMaGiaoDich()+""};

        values.put(NGAY_GD,bangGiaoDich.getNgayGiaoDich());
        values.put(SO_TIEN_GD,bangGiaoDich.getSoTienGiaoDich());
        values.put(MA_KHOAN,bangGiaoDich.getMaKhoan());
        values.put(MO_TA,bangGiaoDich.getMoTa());

        return db.update(TABLE_NAME,values,whereClause,whereArgs);
    }

    public int delete(int maGD){
        String whereClause = MA_GD + " = ?";
        String[] whereArgs = {maGD+""};
        return db.delete(TABLE_NAME,whereClause,whereArgs);
    }

}
