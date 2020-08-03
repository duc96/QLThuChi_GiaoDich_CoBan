package com.example.myapplication.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.KhoanThuChi;

import java.util.ArrayList;
import java.util.List;

public class KhoanThuChiDAO {

    private SQLiteDatabase db;
    public static final String TABLE_NAME = "khoan_thu_chi";
    public static final String MA_KHOAN = "ma_khoan";
    public static final String TEN_KHOAN = "ten_khoan";
    public static final String LOAI_KHOAN = "loai_khoan";
    public static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + " ( " +
            MA_KHOAN + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            TEN_KHOAN + " VARCHAR NOT NULL, " +
            LOAI_KHOAN + " VARCHAR NOT NULL" +
            " );";

    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE_NAME;

    public KhoanThuChiDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long createKhoanThuChi(KhoanThuChi khoanThuChi){
        ContentValues values = new ContentValues();
        values.put(TEN_KHOAN,khoanThuChi.getTenKhoan());
        values.put(LOAI_KHOAN,khoanThuChi.getLoaiKhoan());
        return db.insert(TABLE_NAME,null, values);
    }

    public KhoanThuChi readKhoanThuChi(int makhoan){
        String[] columns = {MA_KHOAN,TEN_KHOAN,LOAI_KHOAN};
        String selection = MA_KHOAN + " = ?";
        String[] selectionArgs = {makhoan+""};
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);

        if(cursor.moveToNext()){
            return new KhoanThuChi(cursor.getInt(cursor.getColumnIndex(MA_KHOAN)),
                    cursor.getString(cursor.getColumnIndex(TEN_KHOAN)),
                    cursor.getString(cursor.getColumnIndex(LOAI_KHOAN)));
        }
        return null;
    }

    public List readAll(){
        List thuchi = new ArrayList();
        String[] columns = {MA_KHOAN,TEN_KHOAN,LOAI_KHOAN};
        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext()){
            thuchi.add(new KhoanThuChi(cursor.getInt(cursor.getColumnIndex(MA_KHOAN)),
                    cursor.getString(cursor.getColumnIndex(TEN_KHOAN)),
                    cursor.getString(cursor.getColumnIndex(LOAI_KHOAN))));
        }
        return thuchi;
    }

    public List readLoai(String loai){
        List dsloai = new ArrayList();
        String[] columns = {MA_KHOAN,TEN_KHOAN,LOAI_KHOAN};
        String selction = LOAI_KHOAN + " = ?";
        String[] selectionArgs = {loai+""};

        Cursor cursor = db.query(TABLE_NAME,columns,selction,selectionArgs,null,null,null);

        while(cursor.moveToNext()){
            dsloai.add(new KhoanThuChi(cursor.getInt(cursor.getColumnIndex(MA_KHOAN)),
                    cursor.getString(cursor.getColumnIndex(TEN_KHOAN))
            ));
        }
        return dsloai;
    }

    public List readMaKhoan(){
        List listMaKhoan = new ArrayList();
        String[] columns = {MA_KHOAN};

        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);

        while (cursor.moveToNext()){
            int a = cursor.getInt(cursor.getColumnIndex(MA_KHOAN));
            listMaKhoan.add(a);
        }
        return listMaKhoan;
    }

    public int update(KhoanThuChi khoanThuChi){
        ContentValues values = new ContentValues();
        String whereClause = MA_KHOAN + " = ?";
        String[] whereArgs = {khoanThuChi.getMaKhoan()+""};

        values.put(TEN_KHOAN,khoanThuChi.getTenKhoan());

        return db.update(TABLE_NAME,values,whereClause,whereArgs);
    }

    public int delete(int makhoan){
        String whereClause = MA_KHOAN + " = ?";
        String[] whereArgs = {makhoan+""};
        return db.delete(TABLE_NAME,whereClause,whereArgs);
    }

}
