package com.example.qlct.Models;

public class KhoanThu {
    public int idThu;
    public String tenThu;
    public String loaiThu;
    public String thoiDiemThu;
    public int danhGia;
    public int deleteFlag;
    public int idLoaiThu;

    public KhoanThu(int idThu, String tenThu, String loaiThu, String thoiDiemThu, int danhGia, int deleteFlag, int idLoaiThu) {
        this.idThu = idThu;
        this.tenThu = tenThu;
        this.loaiThu = loaiThu;
        this.thoiDiemThu = thoiDiemThu;
        this.danhGia = danhGia;
        this.deleteFlag = deleteFlag;
        this.idLoaiThu = idLoaiThu;
    }

    public int getIdThu() {
        return idThu;
    }

    public void setIdThu(int idThu) {
        this.idThu = idThu;
    }

    public String getTenThu() {
        return tenThu;
    }

    public void setTenThu(String tenThu) {
        this.tenThu = tenThu;
    }

    public String getLoaiThu() {
        return loaiThu;
    }

    public void setLoaiThu(String loaiThu) {
        this.loaiThu = loaiThu;
    }

    public String getThoiDiemThu() {
        return thoiDiemThu;
    }

    public void setThoiDiemThu(String thoiDiemThu) {
        this.thoiDiemThu = thoiDiemThu;
    }

    public int getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(int danhGia) {
        this.danhGia = danhGia;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getIdLoaiThu() {
        return idLoaiThu;
    }

    public void setIdLoaiThu(int idLoaiThu) {
        this.idLoaiThu = idLoaiThu;
    }
}
