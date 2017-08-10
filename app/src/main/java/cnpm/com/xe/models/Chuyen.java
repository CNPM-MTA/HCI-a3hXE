package cnpm.com.xe.models;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cnpm.com.xe.SettingsXe;

import static cnpm.com.xe.SettingsXe.CHUYEN_MA;
import static cnpm.com.xe.SettingsXe.TT_DADI;
import static cnpm.com.xe.SettingsXe.TT_DAHUY;
import static cnpm.com.xe.SettingsXe.TT_DANGCHAY;
import static cnpm.com.xe.SettingsXe.TT_DANGDOI;

/**
 * Created by dinht on 13/05/2017.
 */

public class Chuyen implements Serializable {
    private String id;
    private String diemdon;
    private String diemden;
    private double khoangCach;
    private double giaDuKien;
    private float gioKhoiHanh;
    private String ngayDangKy;
    private String ngayKhoiHanh;
    private String ngayQuayLai;//nếu là khứ hồi
    private boolean khuHoi;
    private int soluong;
    private int tinhTrang;//
    private String ghichu;
    private String makhuyenmai;


    public Chuyen(Chuyen chuyen) {
        new Chuyen(chuyen.getId(), chuyen.getDiemdon(), chuyen.getDiemden(), chuyen.getKhoangCach(), chuyen.getGiaDuKien(),
                chuyen.getGioKhoiHanh(), chuyen.getNgayDangKy(), chuyen.getNgayKhoiHanh(), chuyen.getNgayQuayLai(),
                chuyen.isKhuHoi(), chuyen.getSoluong(), chuyen.getTinhTrang(), chuyen.getGhichu(), chuyen.getMakhuyenmai());
    }

    public Chuyen() {
        this.id = (CHUYEN_MA) + (SettingsXe.CHUYEN_COUNT++);
    }

    public Chuyen(String diemdon, String diemden, double khoangcach) {
        this.id = (CHUYEN_MA) + (SettingsXe.CHUYEN_COUNT++);
        setDiemdon(diemdon);
        setDiemden(diemden);
        setKhoangCach(khoangcach);
        setGiaDuKien(0);
        setNgayKhoiHanh("1/1/2017");
    }

    public Chuyen(String diemdon, String diemden, double khoangCach, double giaDuKien,
                  float gioKhoiHanh, String ngayDangKy, String ngayKhoiHanh, String ngayQuayLai, boolean khuHoi, int soluong) {
        this.id = (CHUYEN_MA) + (SettingsXe.CHUYEN_COUNT++);
        this.diemdon = diemdon;
        this.diemden = diemden;
        this.khoangCach = khoangCach;
        this.giaDuKien = giaDuKien;
        this.gioKhoiHanh = gioKhoiHanh;
        setNgayDangKy(ngayDangKy);
        setNgayKhoiHanh(ngayKhoiHanh);
        setNgayQuayLai(ngayQuayLai);
        this.khuHoi = khuHoi;
        if (!khuHoi) this.setNgayQuayLai(null);
        this.soluong = soluong;
        setTinhTrang(TT_DANGDOI);
        setGiaDuKien(giaDuKien);
    }

    public Chuyen(String diemdon, String diemden, double khoangCach, double giaDuKien,
                  float gioKhoiHanh, String ngayDangKy, String ngayKhoiHanh, String ngayQuayLai,
                  boolean khuHoi, int soluong, int tinhTrang, String ghichu, String makhuyenmai) {
        this.id = (CHUYEN_MA) + (SettingsXe.CHUYEN_COUNT++);
        this.diemdon = diemdon;
        this.diemden = diemden;
        this.gioKhoiHanh = gioKhoiHanh;
        setKhoangCach(khoangCach);
        setGiaDuKien(giaDuKien);

        setNgayDangKy(ngayDangKy);
        setNgayKhoiHanh(ngayKhoiHanh);
        setNgayQuayLai(ngayQuayLai);
        this.khuHoi = khuHoi;
        setSoluong(soluong);
        setTinhTrang(tinhTrang);
        this.ghichu = ghichu;
        this.makhuyenmai = makhuyenmai;
        setGiaDuKien(giaDuKien);
    }

    public Chuyen(String id, String diemdon, String diemden, double khoangCach, double giaDuKien,
                  float gioKhoiHanh, String ngayDangKy, String ngayKhoiHanh, String ngayQuayLai,
                  boolean khuHoi, int soluong, int tinhTrang, String ghichu, String makhuyenmai) {
        this.id = (CHUYEN_MA) + (SettingsXe.CHUYEN_COUNT++);
        this.diemdon = diemdon;
        this.diemden = diemden;
        this.gioKhoiHanh = gioKhoiHanh;
        setKhoangCach(khoangCach);
        setGiaDuKien(giaDuKien);

        setNgayDangKy(ngayDangKy);
        setNgayKhoiHanh(ngayKhoiHanh);
        setNgayQuayLai(ngayQuayLai);
        this.khuHoi = khuHoi;
        setSoluong(soluong);
        setTinhTrang(tinhTrang);
        this.ghichu = ghichu;
        this.makhuyenmai = makhuyenmai;
        setGiaDuKien(giaDuKien);
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMakhuyenmai() {
        return makhuyenmai;
    }

    public void setMakhuyenmai(String makhuyenmai) {
        this.makhuyenmai = makhuyenmai;
    }

    public Chuyen(String diemdon, String diemden, double khoangCach, double giaDuKien,
                  float gioKhoiHanh, String ngayDangKy, String ngayKhoiHanh,
                  boolean khuHoi, int soLuong, int tinhTrang) {

        this.id = (CHUYEN_MA) + (SettingsXe.CHUYEN_COUNT++);
        this.diemdon = diemdon;
        this.diemden = diemden;
        this.khoangCach = khoangCach;
        this.gioKhoiHanh = gioKhoiHanh;

        setNgayDangKy(ngayDangKy);
        setNgayKhoiHanh(ngayKhoiHanh);
        setNgayQuayLai(ngayQuayLai);
        this.khuHoi = khuHoi;
        setSoluong(soLuong);
        setTinhTrang(tinhTrang);
        setGiaDuKien(giaDuKien);
    }

    /*//cursor.getString(0),//ID
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
                    Integer.parseInt(cursor.getString(12)),//GHI CHU
                    cursor.getString(13), cursor.getString(14));//KM*/
    public Chuyen(String diemdon, String diemden, double khoangCach, double giaDuKien, int soLuong,
                  float gioKhoiHanh, String ngayDangKy, String ngayKhoiHanh, String ngayQuayLai,
                  boolean khuHoi, int tinhTrang, String ghiChu, String khuyenMai) {
        this.gioKhoiHanh = gioKhoiHanh;
        this.id = (CHUYEN_MA) + (SettingsXe.CHUYEN_COUNT++);
        this.diemdon = diemdon;
        this.diemden = diemden;
        this.khoangCach = khoangCach;
        this.soluong = soLuong;
        if (!isKhuHoi()) this.ngayQuayLai = null;
        this.gioKhoiHanh = gioKhoiHanh;

        setNgayDangKy(ngayDangKy);
        setNgayKhoiHanh(ngayKhoiHanh);
        setNgayQuayLai(ngayQuayLai);
        this.khuHoi = khuHoi;
        setGiaDuKien(giaDuKien);
        setTinhTrang(tinhTrang);
        setGhichu(ghiChu);
        setMakhuyenmai(khuyenMai);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiemdon() {
        return diemdon;
    }

    public void setDiemdon(String diemdon) {

        this.diemdon = diemdon;
    }

    public String getDiemden() {
        return diemden;
    }

    public void setDiemden(String diemden) {
        this.diemden = diemden;
    }

    public double getKhoangCach() {
        return khoangCach;
    }

    public void setKhoangCach(double khoangCach) {
        if (khoangCach <= 0) this.khoangCach = 15;
        else
            this.khoangCach = khoangCach;
    }

    public double getGiaDuKien() {
        return giaDuKien;
    }

    public void setGiaDuKien(double giaDuKien) {
        if (giaDuKien <= 0) this.giaDuKien = getKhoangCach() * getSoluong() * 1000;
        else this.giaDuKien = giaDuKien;
    }

    public String getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(String ngayDangKy) {
        if (ngayDangKy == "")
            this.ngayDangKy = "1/12017";
        this.ngayDangKy = ngayDangKy;
    }

    public String getNgayKhoiHanh() {
//        if(ngayKhoiHanh.contains("ngaykhoihanh"))
//            ngayKhoiHanh = "5/24/2017";
        return ngayKhoiHanh;
    }

    public void setNgayKhoiHanh(String ngayKhoiHanh) {
        if (ngayKhoiHanh == "" || ngayKhoiHanh.contains("ngaykhoihanh"))
            this.ngayKhoiHanh = "5/24/2017";
        else this.ngayKhoiHanh = ngayKhoiHanh;
    }

    public String getNgayQuayLai() {
        return ngayQuayLai;
    }

    public void setNgayQuayLai(String ngayQuayLai) {
        this.ngayQuayLai = ngayQuayLai;
    }

    public boolean isKhuHoi() {
        return khuHoi;
    }

    public void setKhuHoi(boolean khuHoi) {
        this.khuHoi = khuHoi;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soLuong) {
        Log.w("so luong", "" + soLuong);
        if (soLuong > 0 && soLuong < 50)
            this.soluong = soLuong;

        else this.soluong = 1;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        if (tinhTrang == TT_DANGCHAY) this.tinhTrang = TT_DANGCHAY;
        else if (tinhTrang == TT_DAHUY) this.tinhTrang = TT_DAHUY;
        else if (tinhTrang == TT_DADI) this.tinhTrang = TT_DADI;
        else if (tinhTrang == TT_DANGDOI) this.tinhTrang = TT_DANGDOI;
    }

    public float getGioKhoiHanh() {
        return gioKhoiHanh;
    }

    public void setGioKhoiHanh(float gioKhoiHanh) {
        this.gioKhoiHanh = gioKhoiHanh;
    }

    public void setGioKhoiHanh1(String gioKhoiHanh) {
        if (gioKhoiHanh.contains("AM"))
            this.gioKhoiHanh = Integer.parseInt(gioKhoiHanh.replace("AM", "").toString());
        else this.gioKhoiHanh = Integer.parseInt(gioKhoiHanh.replace("PM", "").toString());
    }
}
