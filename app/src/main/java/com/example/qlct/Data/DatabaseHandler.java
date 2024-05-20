package com.example.qlct.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qlct.Models.KhoanChi;
import com.example.qlct.Models.LoaiChi;
import com.example.qlct.Models.LoaiThu;

import java.util.ArrayList;

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



        String CREATE_CLASS_TABLE_KHOANCHI = "CREATE TABLE IF NOT EXISTS KhoanChi(" +
                "idChi INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenChi NVARCHAR(200)," +
                "loaiChi NVARCHAR(200)," +
                "thoiDiemChi DATETIME," +
                "soTien INTEGER," +
                "danhGia INTEGER," +
                "deleteFlag INTEGER," +
                "idLoaiChi INTEGER," +
                "FOREIGN KEY(idLoaiChi) REFERENCES LoaiChi(id))";
        sqLiteDatabase.execSQL(CREATE_CLASS_TABLE_KHOANCHI);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_LOAITHU);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_LOAICHI);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_KHOANCHI);
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

    //Table Khoản Chi
    private static final String KEY_NAME_TABLE_KHOANCHI = "KhoanChi";
    private static final String KEY_TABLE_ID_KHOANCHI = "idChi";
    private static final String KEY_TABLE_NAME_KHOANCHI = "tenChi";
    private static final String KEY_TABLE_LOAICHI_KHOANCHI = "loaiChi";
    private static final String KEY_TABLE_THOIDIEM_KHOANCHI = "thoiDiemChi";
    private static final String KEY_TABLE_SOTIEN_KHOANCHI = "soTien";
    private static final String KEY_TABLE_DANHGIA_KHOANCHI = "danhGia";
    private static final String KEY_TABLE_DELETEFLAG_KHOANCHI = "deleteFlag";
    private static final String KEY_TABLE_ID_LOAICHI_KHOANCHI = "idLoaiChi";


    private static final String CREATE_CLASS_TABLE_LOAITHU = "CREATE TABLE " + KEY_NAME_TABLE_LOAITHU + "(" + KEY_TABLE_ID_LOAITHU + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TABLE_NAME_LOAITHU + " TEXT," + ")";
    private static final String CREATE_CLASS_TABLE_LOAICHI = "CREATE TABLE " + KEY_NAME_TABLE_LOAICHI + "(" + KEY_TABLE_ID_LOAICHI + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TABLE_NAME_LOAICHI + " TEXT," + ")";
    private static final String CREATE_CLASS_TABLE_KHOANCHI = "CREATE TABLE " + KEY_NAME_TABLE_KHOANCHI + "(" +
            KEY_TABLE_ID_KHOANCHI + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_TABLE_NAME_KHOANCHI + " TEXT," +
            KEY_TABLE_LOAICHI_KHOANCHI + " TEXT," +
            KEY_TABLE_THOIDIEM_KHOANCHI + " DATETIME," +
            KEY_TABLE_SOTIEN_KHOANCHI + " INTEGER," +
            KEY_TABLE_DANHGIA_KHOANCHI + " INTEGER," +
            KEY_TABLE_DELETEFLAG_KHOANCHI + " INTEGER," +
            KEY_TABLE_ID_LOAICHI_KHOANCHI + " INTEGER," +
            "FOREIGN KEY(" + KEY_TABLE_ID_LOAICHI_KHOANCHI + ") REFERENCES " + KEY_NAME_TABLE_LOAICHI + "(" + KEY_TABLE_ID_LOAICHI + ")" +
            ")";



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

    public void addKhoanChi(KhoanChi khoanChi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_NAME_KHOANCHI, khoanChi.getTenChi());
        values.put(KEY_TABLE_LOAICHI_KHOANCHI, khoanChi.getLoaiChi());
        values.put(KEY_TABLE_THOIDIEM_KHOANCHI, khoanChi.getThoiDiemChi());
        values.put(KEY_TABLE_SOTIEN_KHOANCHI, khoanChi.getSoTien());
        values.put(KEY_TABLE_DANHGIA_KHOANCHI, khoanChi.getDanhGia());
        values.put(KEY_TABLE_DELETEFLAG_KHOANCHI, khoanChi.getDeleteFlag());
        values.put(KEY_TABLE_ID_LOAICHI_KHOANCHI, khoanChi.getIdLoaiChi());

        db.insert(KEY_NAME_TABLE_KHOANCHI, null, values);
        db.close();
    }

    public Cursor GetDate(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public ArrayList<LoaiChi> getAllLoaiChi() {
        ArrayList<LoaiChi> loaiChiList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM LoaiChi";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor != null) {
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("tenLoaiChi");

                while (cursor.moveToNext()) {
                    LoaiChi loaiChi = new LoaiChi();

                    if (idIndex >= 0) {
                        loaiChi.setId(cursor.getInt(idIndex));
                    }
                    if (nameIndex >= 0) {
                        loaiChi.setNameLoaiChi(cursor.getString(nameIndex));
                    }

                    loaiChiList.add(loaiChi);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return loaiChiList;
    }
}
