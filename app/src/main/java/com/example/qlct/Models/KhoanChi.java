package com.example.qlct.Models;

public class KhoanChi {
    public int idChi;
    public String tenChi;
    public String loaiChi;
    public String thoiDiemChi;
    public int soTien;
    public int danhGia;
    public int deleteFlag;
    public int idLoaiChi;

    public KhoanChi(int idChi, String tenChi, String loaiChi, String thoiDiemChi, int soTien, int danhGia, int deleteFlag, int idLoaiChi) {
        this.idChi = idChi;
        this.tenChi = tenChi;
        this.loaiChi = loaiChi;
        this.thoiDiemChi = thoiDiemChi;
        this.soTien = soTien;
        this.danhGia = danhGia;
        this.deleteFlag = deleteFlag;
        this.idLoaiChi = idLoaiChi;
    }

    public int getIdChi() {
        return idChi;
    }

    public void setIdChi(int idChi) {
        this.idChi = idChi;
    }

    public String getTenChi() {
        return tenChi;
    }

    public void setTenChi(String tenChi) {
        this.tenChi = tenChi;
    }

    public String getLoaiChi() {
        return loaiChi;
    }

    public void setLoaiChi(String loaiChi) {
        this.loaiChi = loaiChi;
    }

    public String getThoiDiemChi() {
        return thoiDiemChi;
    }

    public void setThoiDiemChi(String thoiDiemChi) {
        this.thoiDiemChi = thoiDiemChi;
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

    public int getIdLoaiChi() {
        return idLoaiChi;
    }

    public void setIdLoaiChi(int idLoaiChi) {
        this.idLoaiChi = idLoaiChi;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public KhoanChi() {
    }
}
