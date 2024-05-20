package com.example.qlct.Models;

import java.io.Serializable;

public class LoaiChi implements Serializable {
    private int id;
    private String NameLoaiChi;

    public LoaiChi(int id, String nameLoaiChi) {
        this.id = id;
        NameLoaiChi = nameLoaiChi;
    }

    public LoaiChi() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameLoaiChi() {
        return NameLoaiChi;
    }

    public void setNameLoaiChi(String nameLoaiChi) {
        NameLoaiChi = nameLoaiChi;
    }
}
