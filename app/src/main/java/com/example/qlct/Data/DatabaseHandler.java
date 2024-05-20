package com.example.qlct.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qlct.Models.KhoanChi;
import com.example.qlct.Models.KhoanThu;
import com.example.qlct.Models.LoaiChi;
import com.example.qlct.Models.LoaiThu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String CREATE_CLASS_TABLE_KHOANTHU = "CREATE TABLE IF NOT EXISTS KhoanThu(" +
                "idThu INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenThu NVARCHAR(200)," +
                "loaiThu NVARCHAR(200)," +
                "thoiDiemThu DATETIME," +
                "soTien INTEGER," +
                "danhGia INTEGER," +
                "deleteFlag INTEGER," +
                "idLoaiThu INTEGER," +
                "FOREIGN KEY(idLoaiThu) REFERENCES LoaiThu(id))";
        sqLiteDatabase.execSQL(CREATE_CLASS_TABLE_KHOANTHU);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_LOAITHU);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_LOAICHI);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_KHOANCHI);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_KHOANTHU);
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


    //Table Khoản Thu
    private static final String KEY_NAME_TABLE_KHOANTHU = "KhoanThu";
    private static final String KEY_TABLE_ID_KHOANTHU = "idThu";
    private static final String KEY_TABLE_NAME_KHOANTHU = "tenThu";
    private static final String KEY_TABLE_LOAITHU_KHOANTHU = "loaiThu";
    private static final String KEY_TABLE_THOIDIEM_KHOANTHU = "thoiDiemThu";
    private static final String KEY_TABLE_SOTIEN_KHOANTHU = "soTien";
    private static final String KEY_TABLE_DANHGIA_KHOANTHU = "danhGia";
    private static final String KEY_TABLE_DELETEFLAG_KHOANTHU = "deleteFlag";
    private static final String KEY_TABLE_ID_LOAITHU_KHOANTHU = "idLoaiThu";

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

    private static final String CREATE_CLASS_TABLE_KHOANTHU = "CREATE TABLE " + KEY_NAME_TABLE_KHOANTHU + "(" +
            KEY_TABLE_ID_KHOANTHU + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_TABLE_NAME_KHOANTHU + " TEXT," +
            KEY_TABLE_LOAITHU_KHOANTHU + " TEXT," +
            KEY_TABLE_THOIDIEM_KHOANTHU + " DATETIME," +
            KEY_TABLE_SOTIEN_KHOANTHU + " INTEGER," +
            KEY_TABLE_DANHGIA_KHOANTHU + " INTEGER," +
            KEY_TABLE_DELETEFLAG_KHOANTHU + " INTEGER," +
            KEY_TABLE_ID_LOAITHU_KHOANTHU + " INTEGER," +
            "FOREIGN KEY(" + KEY_TABLE_ID_LOAITHU_KHOANTHU + ") REFERENCES " + KEY_NAME_TABLE_LOAITHU + "(" + KEY_TABLE_ID_LOAITHU + ")" +
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

    public void addKhoanThu(KhoanThu khoanThu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_NAME_KHOANTHU, khoanThu.getTenThu());
        values.put(KEY_TABLE_ID_LOAITHU_KHOANTHU, khoanThu.getLoaiThu());
        values.put(KEY_TABLE_THOIDIEM_KHOANTHU, khoanThu.getThoiDiemThu());
        values.put(KEY_TABLE_SOTIEN_KHOANTHU, khoanThu.getSoTien());
        values.put(KEY_TABLE_DANHGIA_KHOANCHI, khoanThu.getDanhGia());
        values.put(KEY_TABLE_DELETEFLAG_KHOANTHU, khoanThu.getDeleteFlag());
        values.put(KEY_TABLE_ID_LOAITHU_KHOANTHU, khoanThu.getIdLoaiThu());

        db.insert(KEY_NAME_TABLE_KHOANTHU, null, values);
        db.close();
    }

    public Cursor GetDate(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    //Lấy dữ liệu chi của 1 ngày
    public Cursor GetDailyDataExpenses(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * " +
                "FROM KhoanChi " +
                "WHERE DATE(KhoanChi.thoiDiemChi) = '" + date + "' " +
                "ORDER BY KhoanChi.thoiDiemChi ASC";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //Tinh tổng chi của 1 ngày
    public int CalculateTotalExpenses(String date) {
        Cursor cursor = GetDailyDataExpenses(date);
        int totalExpenses = 0;

        if (cursor != null && cursor.moveToFirst()) {
            int soTienColumn = cursor.getColumnIndex("soTien");
            if (soTienColumn != -1) {
                do {
                    int soTien = cursor.getInt(soTienColumn);
                    if (soTien >= 0) {
                        totalExpenses += soTien;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return totalExpenses;
    }

    //Lấy dữ liệu thu của 1 ngày
    public Cursor GetDailyDataIncome(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * " +
                "FROM KhoanThu " +
                "INNER JOIN LoaiThu ON KhoanThu.idLoaiThu = LoaiThu.id " +
                "WHERE DATE(KhoanThu.thoiDiemThu) = '" + date + "' " +
                "ORDER BY KhoanThu.thoiDiemThu ASC";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //Tinh tổng thu của 1 ngày
    public int CalculateTotalIncome(String date) {
        Cursor cursor = GetDailyDataIncome(date);
        int totalIncome = 0;

        if (cursor != null && cursor.moveToFirst()) {
            int soTienColumn = cursor.getColumnIndex("soTien");
            if (soTienColumn != -1) {
                do {
                    int soTien = cursor.getInt(soTienColumn);
                    if (soTien >= 0) {
                        totalIncome += soTien;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return totalIncome;
    }

    public List<KhoanThu> getKhoanThuList(String date) {
        List<KhoanThu> khoanThuList = new ArrayList<>();
        Cursor cursor = GetDailyDataIncome(date);

        if (cursor.moveToFirst()) {
            do {
                KhoanThu khoanThu = new KhoanThu();

                int idThuIndex = cursor.getColumnIndex("idThu");
                if (idThuIndex != -1) {
                    khoanThu.setIdThu(cursor.getInt(idThuIndex));
                }

                int tenThuIndex = cursor.getColumnIndex("tenThu");
                if (tenThuIndex != -1) {
                    khoanThu.setTenThu(cursor.getString(tenThuIndex));
                }

                int loaiThuIndex = cursor.getColumnIndex("loaiThu");
                if (loaiThuIndex != -1) {
                    khoanThu.setLoaiThu(cursor.getString(loaiThuIndex));
                }

                int thoiDiemThuIndex = cursor.getColumnIndex("thoiDiemThu");
                if (thoiDiemThuIndex != -1) {
                    khoanThu.setThoiDiemThu(cursor.getString(thoiDiemThuIndex));
                }

                int soTienIndex = cursor.getColumnIndex("soTien");
                if (soTienIndex != -1) {
                    khoanThu.setSoTien(cursor.getInt(soTienIndex));
                }

                int danhGiaIndex = cursor.getColumnIndex("danhGia");
                if (danhGiaIndex != -1) {
                    khoanThu.setDanhGia(cursor.getInt(danhGiaIndex));
                }

                int deleteFlagIndex = cursor.getColumnIndex("deleteFlag");
                if (deleteFlagIndex != -1) {
                    khoanThu.setDeleteFlag(cursor.getInt(deleteFlagIndex));
                }

                int idLoaiThuIndex = cursor.getColumnIndex("idLoaiThu");
                if (idLoaiThuIndex != -1) {
                    khoanThu.setIdLoaiThu(cursor.getInt(idLoaiThuIndex));
                }

                khoanThuList.add(khoanThu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return khoanThuList;
    }

    public List<KhoanChi> getKhoanChiList(String date) {
        List<KhoanChi> khoanChiList = new ArrayList<>();
        Cursor cursor = GetDailyDataExpenses(date);

        if (cursor.moveToFirst()) {
            do {
                KhoanChi khoanChi = new KhoanChi();

                int idChiIndex = cursor.getColumnIndex("idChi");
                if (idChiIndex != -1) {
                    khoanChi.setIdChi(cursor.getInt(idChiIndex));
                }

                int tenChiIndex = cursor.getColumnIndex("tenChi");
                if (tenChiIndex != -1) {
                    khoanChi.setTenChi(cursor.getString(tenChiIndex));
                }

                int loaichiIndex = cursor.getColumnIndex("loaiChi");
                if (loaichiIndex != -1) {
                    khoanChi.setLoaiChi(cursor.getString(loaichiIndex));
                }

                int thoiDiemChiIndex = cursor.getColumnIndex("thoiDiemChi");
                if (thoiDiemChiIndex != -1) {
                    khoanChi.setThoiDiemChi(cursor.getString(thoiDiemChiIndex));
                }

                int soTienIndex = cursor.getColumnIndex("soTien");
                if (soTienIndex != -1) {
                    khoanChi.setSoTien(cursor.getInt(soTienIndex));
                }

                int danhGiaIndex = cursor.getColumnIndex("danhGia");
                if (danhGiaIndex != -1) {
                    khoanChi.setDanhGia(cursor.getInt(danhGiaIndex));
                }

                int deleteFlagIndex = cursor.getColumnIndex("deleteFlag");
                if (deleteFlagIndex != -1) {
                    khoanChi.setDeleteFlag(cursor.getInt(deleteFlagIndex));
                }

                int idLoaiChiIndex = cursor.getColumnIndex("idLoaiChi");
                if (idLoaiChiIndex != -1) {
                    khoanChi.setIdLoaiChi(cursor.getInt(idLoaiChiIndex));
                }

                khoanChiList.add(khoanChi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return khoanChiList;
    }

    public static String getKeyNameTableKhoanChi() {
        return KEY_NAME_TABLE_KHOANCHI;
    }

    public static String getKeyTableIdKhoanChi() {
        return KEY_TABLE_ID_KHOANCHI;
    }

    public static String getKeyTableNameKhoanChi() {
        return KEY_TABLE_NAME_KHOANCHI;
    }

    public static String getKeyTableLoaiChiKhoanChi() {
        return KEY_TABLE_LOAICHI_KHOANCHI;
    }

    public static String getKeyTableThoiDiemKhoanChi() {
        return KEY_TABLE_THOIDIEM_KHOANCHI;
    }

    public static String getKeyTableSoTienKhoanChi() {
        return KEY_TABLE_SOTIEN_KHOANCHI;
    }

    public static String getKeyTableDanhGiaKhoanChi() {
        return KEY_TABLE_DANHGIA_KHOANCHI;
    }

    public static String getKeyTableDeleteFlagKhoanChi() {
        return KEY_TABLE_DELETEFLAG_KHOANCHI;
    }

    public static String getKeyTableIdLoaiChiKhoanChi() {
        return KEY_TABLE_ID_LOAICHI_KHOANCHI;
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

    public ArrayList<LoaiThu> getAllLoaiThu() {
        ArrayList<LoaiThu> loaiThuList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM LoaiThu";

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor != null) {
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("tenLoaiThu");

                while (cursor.moveToNext()) {
                    LoaiThu loaiThu = new LoaiThu();

                    if (idIndex >= 0) {
                        loaiThu.setId(cursor.getInt(idIndex));
                    }
                    if (nameIndex >= 0) {
                        loaiThu.setNameLoaiThu(cursor.getString(nameIndex));
                    }

                    loaiThuList.add(loaiThu);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return loaiThuList;
    }
}


