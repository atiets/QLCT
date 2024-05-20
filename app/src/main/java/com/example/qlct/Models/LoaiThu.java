package com.example.qlct.Models;

import java.io.Serializable;

public class LoaiThu implements Serializable {
    private int id;
    private String NameLoaiThu;

    public LoaiThu(int id, String nameLoaiThu) {
        this.id = id;
        NameLoaiThu = nameLoaiThu;
    }

    public LoaiThu() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameLoaiThu() {
        return NameLoaiThu;
    }

    public void setNameLoaiThu(String nameLoaiThu) {
        NameLoaiThu = nameLoaiThu;
    }
}
