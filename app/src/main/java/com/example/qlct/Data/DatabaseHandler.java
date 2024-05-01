package com.example.qlct.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qlct.Models.LoaiChi;
import com.example.qlct.Models.LoaiThu;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QLCT";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CLASS_TABLE_LOAITHU = "CREATE TABLE IF NOT EXISTS LoaiThu(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoaiThu NVARCHAR(200))";
        sqLiteDatabase.execSQL(CREATE_CLASS_TABLE_LOAITHU);
        String CREATE_CLASS_TABLE_LOAICHI = "CREATE TABLE IF NOT EXISTS LoaiChi(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoaiChi NVARCHAR(200))";
        sqLiteDatabase.execSQL(CREATE_CLASS_TABLE_LOAICHI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_LOAITHU);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_LOAICHI);
        onCreate(sqLiteDatabase);
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return  database.rawQuery(sql, null);
    }

    // Table Loai Thu
    private static final String KEY_NAME_TABLE_LOAITHU = "LoaiThu";
    private static final String KEY_TABLE_ID_LOAITHU = "id";
    private static final String KEY_TABLE_NAME_LOAITHU = "tenLoaiThu";

    // Table Loai Chi ---

    private static final String KEY_NAME_TABLE_LOAICHI = "LoaiChi";
    private static final String KEY_TABLE_ID_LOAICHI = "id";
    private static final String KEY_TABLE_NAME_LOAICHI = "tenLoaiChi";

    private static final String CREATE_CLASS_TABLE_LOAITHU = "CREATE TABLE " + KEY_NAME_TABLE_LOAITHU + "(" + KEY_TABLE_ID_LOAITHU + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TABLE_NAME_LOAITHU + " TEXT," + ")";
    private static final String CREATE_CLASS_TABLE_LOAICHI = "CREATE TABLE " + KEY_NAME_TABLE_LOAICHI + "(" + KEY_TABLE_ID_LOAICHI + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TABLE_NAME_LOAICHI + " TEXT," + ")";

    public void addLoaiThu(LoaiThu loaiThu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_NAME_LOAITHU, loaiThu.getNameLoaiThu());

        db.insert(KEY_NAME_TABLE_LOAITHU, null, values);
        db.close();
    }
    public void addLoaiChi(LoaiChi loaiChi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_NAME_LOAICHI, loaiChi.getNameLoaiChi());

        db.insert(KEY_NAME_TABLE_LOAICHI, null, values);
        db.close();
    }
    public Cursor GetDate(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}
