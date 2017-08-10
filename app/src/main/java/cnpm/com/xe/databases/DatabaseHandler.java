package cnpm.com.xe.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import cnpm.com.xe.models.Chuyen;

import static cnpm.com.xe.SettingsXe.TT_DAHUY;

/**
 * Created by dinht on 16/05/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DBchuyen";

    // Contacts table name
    private static final String TABLE_CHUYEN = "chuyen";


    // Contacts Table Columns names
    private static final String ID = "id";
    private static final String DIEM_DON = "diemdon";
    private static final String DIEM_DEN = "diemden";
    private static final String KHOANG_CACH = "khoangcach";
    private static final String GIA = "gia";
    private static final String GIO_KHOI_HANH = "giokhoihanh";
    private static final String NGAY_QUAY_LAI = "ngayquaylai";
    private static final String NGAY_DANG_KY = "ngaydangky";
    private static final String NGAY_KHOI_HANH = "ngaykhoihanh";
    private static final String KHU_HOI = "khuhoi";
    private static final String SO_LUONG = "soluong";
    private static final String TINH_TRANG = "tinhtrang";
    private static final String GHI_CHU = "ghichu";
    private static final String MA_KHUYEN_MAI = "makhuyenmai";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CHUYEN + "("
                + ID + " INTEGER PRIMARY KEY,"
                + DIEM_DON + " TEXT,"
                + DIEM_DEN + " TEXT, "
                + KHOANG_CACH + " DOUBLE, "
                + GIA + " DOUBLE, "
                + GIO_KHOI_HANH + " TEXT, "
                + NGAY_DANG_KY + " TEXT, "
                + NGAY_KHOI_HANH + " TEXT, "
                + NGAY_QUAY_LAI + " TEXT, "
                + KHU_HOI + " BOOL, "
                + SO_LUONG + " INTEGER, "
                + TINH_TRANG + " INTEGER, "
                + GHI_CHU + " TEXT, "
                + MA_KHUYEN_MAI + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHUYEN);
        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void add_Chuyen(Chuyen chuyen) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DIEM_DON, chuyen.getDiemdon());
        values.put(DIEM_DEN, chuyen.getDiemden());
        values.put(KHOANG_CACH, chuyen.getKhoangCach());
        values.put(GIA, chuyen.getGiaDuKien());
        values.put(SO_LUONG, chuyen.getSoluong());
        values.put(GIO_KHOI_HANH, chuyen.getGioKhoiHanh());
        values.put(NGAY_DANG_KY, chuyen.getNgayDangKy());
        values.put(NGAY_KHOI_HANH, chuyen.getNgayKhoiHanh());
        values.put(NGAY_QUAY_LAI, chuyen.getNgayQuayLai());
        values.put(KHU_HOI, chuyen.isKhuHoi());
        values.put(TINH_TRANG, chuyen.getTinhTrang());
        values.put(GHI_CHU, chuyen.getGhichu());
        values.put(MA_KHUYEN_MAI, chuyen.getMakhuyenmai());
        // Inserting Row
        db.insert(TABLE_CHUYEN, null, values);
        db.close(); // Closing database connection
    }

    // Updating single contact
    public int update_Chuyen(Chuyen chuyen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DIEM_DON, chuyen.getDiemdon());
        values.put(DIEM_DEN, chuyen.getDiemden());
        values.put(KHOANG_CACH, chuyen.getKhoangCach());
        values.put(GIA, chuyen.getGiaDuKien());
        values.put(SO_LUONG, chuyen.getSoluong());
        values.put(GIO_KHOI_HANH, chuyen.getGioKhoiHanh()== 0 ? 8: chuyen.getGioKhoiHanh());
        values.put(NGAY_DANG_KY, chuyen.getNgayDangKy());
        values.put(NGAY_KHOI_HANH, chuyen.getNgayKhoiHanh());
        values.put(NGAY_QUAY_LAI, chuyen.getNgayQuayLai());
        values.put(KHU_HOI, chuyen.isKhuHoi());
        values.put(TINH_TRANG, chuyen.getTinhTrang());
        values.put(GHI_CHU, chuyen.getGhichu());
        values.put(MA_KHUYEN_MAI, chuyen.getMakhuyenmai());

        // updating row
        return db.update(TABLE_CHUYEN, values, ID + " = ?",
                new String[]{String.valueOf(chuyen.getId())});
    }

    // Deleting single contacti8
    public int delete_Chuyen(Chuyen chuyen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TINH_TRANG, TT_DAHUY);
//        update_Chuyen(chuyen);
//        db.delete(TABLE_CHUYEN, ID + " = ?",
//                new String[]{String.valueOf(chuyen.getId())});
        // updating row
        return db.update(TABLE_CHUYEN, values, ID + " = ?",
                new String[]{String.valueOf(chuyen.getId())});
//        db.close();
    }

    public void deleteAll_Chuyen() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_CHUYEN);
        db.close();
    }

    // Getting single contact
    public Chuyen getChuyen(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CHUYEN, new String[]{ID, DIEM_DON, DIEM_DEN, KHOANG_CACH,
                        GIA,SO_LUONG, GIO_KHOI_HANH, NGAY_DANG_KY, NGAY_KHOI_HANH, NGAY_QUAY_LAI, KHU_HOI,
                        TINH_TRANG, GHI_CHU, MA_KHUYEN_MAI}, ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
//        String diemdon, String diemden, double khoangCach, double giaDuKien, int soLuong,
//        float gioKhoiHanh, String ngayDangKy, String ngayKhoiHanh, String ngayQuayLai,
//        boolean khuHoi, int tinhTrang,  String ghiChu, String khuyenMai
        Chuyen chuyen = new Chuyen(
//                cursor.getString(0),//ID
                cursor.getString(1),//DIEM DON
                cursor.getString(2),//DIEM DEN
                Double.parseDouble(cursor.getString(3)),//KHOANG CACH
                Double.parseDouble(cursor.getString(4)),//GIA
                Integer.parseInt(cursor.getString(5)),//SO LUONG
                Float.parseFloat(cursor.getString(6)), //GIO KHOI HANH
                cursor.getColumnName(7),//NGAY DANG KY
                cursor.getColumnName(8),//NGAY KHOI HANH
                cursor.getString(9),//NGAY QUAY LAI
                Boolean.parseBoolean(cursor.getString(10)),//KHU HOI
                Integer.parseInt(cursor.getString(11)),//TINH TRANG
                cursor.getString(12),//GHI CHU
                cursor.getString(13));//KM
        // return contact
        /*   public Chuyen(String id, String diemdon, String diemden,
        double khoangCach, double giaDuKien,
         float gioKhoiHanh, String ngayDangKy, String ngayKhoiHanh, String ngayQuayLai,
                  boolean khuHoi, int soluong, int tinhTrang, String ghichu, String makhuyenmai) */
        return chuyen;
    }

    // Getting All Contacts
    public ArrayList<Chuyen> getAllChuyen() {
        ArrayList<Chuyen> listChuyen = new ArrayList<Chuyen>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CHUYEN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Chuyen chuyen = new Chuyen();
                chuyen.setId(cursor.getString(0));
                chuyen.setDiemdon(cursor.getString(1));
                chuyen.setDiemden(cursor.getString(2));
                chuyen.setKhoangCach(Double.parseDouble(cursor.getString(3)));
                chuyen.setGiaDuKien(Double.parseDouble(cursor.getString(4)));
                chuyen.setGioKhoiHanh(Float.parseFloat(cursor.getString(5)));
                chuyen.setNgayDangKy(cursor.getString(6));
//                chuyen.setNgayDangKy(cursor.getColumnName(6));
//                chuyen.setNgayKhoiHanh(cursor.getColumnName(7));
                chuyen.setNgayKhoiHanh(cursor.getString(7));
                chuyen.setNgayQuayLai(cursor.getString(8));
                chuyen.setKhuHoi(Boolean.parseBoolean(cursor.getString(9)));
                chuyen.setSoluong(cursor.getString(10) == "" ? 0 : Integer.parseInt(cursor.getString(10)));
                chuyen.setTinhTrang(cursor.getString(11) == "" ? 1 : Integer.parseInt(cursor.getString(11)));
                chuyen.setGhichu(cursor.getString(12) == "" ? " " : cursor.getString(12));
                chuyen.setMakhuyenmai(cursor.getString(13) == "" ? " " : cursor.getString(13));
                // Adding contact to list
                listChuyen.add(chuyen);
            } while (cursor.moveToNext());
        }

        // return contact list
        return listChuyen;
    }


    // Getting contacts Count
    public int getEmployeesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CHUYEN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }


}
