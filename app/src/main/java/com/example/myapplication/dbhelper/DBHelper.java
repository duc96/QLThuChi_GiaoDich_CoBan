package com.example.myapplication.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.dao.BangGiaoDichDAO;
import com.example.myapplication.dao.KhoanThuChiDAO;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "QuanlyThuChi";
    public static final int DB_VERSION = 4;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KhoanThuChiDAO.CREATE_TABLE);
        db.execSQL(BangGiaoDichDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(KhoanThuChiDAO.DROP_TABLE);
        db.execSQL(BangGiaoDichDAO.DROP_TABLE);
        db.execSQL(KhoanThuChiDAO.CREATE_TABLE);
        db.execSQL(BangGiaoDichDAO.CREATE_TABLE);
    }
}
