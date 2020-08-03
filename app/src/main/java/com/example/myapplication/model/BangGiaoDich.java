package com.example.myapplication.model;

public class BangGiaoDich {

    private int maGiaoDich;
    private String ngayGiaoDich;
    private float soTienGiaoDich;
    private int maKhoan;
    private String moTa;

    public BangGiaoDich() {
    }

    public BangGiaoDich(float soTienGiaoDich, int maKhoan) {
        this.soTienGiaoDich = soTienGiaoDich;
        this.maKhoan = maKhoan;
    }

    public BangGiaoDich(String ngayGiaoDich, float soTienGiaoDich, int maKhoan, String moTa) {
        this.ngayGiaoDich = ngayGiaoDich;
        this.soTienGiaoDich = soTienGiaoDich;
        this.maKhoan = maKhoan;
        this.moTa = moTa;
    }

    public BangGiaoDich(int maGiaoDich, String ngayGiaoDich, float soTienGiaoDich, int maKhoan, String moTa) {
        this.maGiaoDich = maGiaoDich;
        this.ngayGiaoDich = ngayGiaoDich;
        this.soTienGiaoDich = soTienGiaoDich;
        this.maKhoan = maKhoan;
        this.moTa = moTa;
    }

    public int getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(int maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public String getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(String ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public float getSoTienGiaoDich() {
        return soTienGiaoDich;
    }

    public void setSoTienGiaoDich(float soTienGiaoDich) {
        this.soTienGiaoDich = soTienGiaoDich;
    }

    public int getMaKhoan() {
        return maKhoan;
    }

    public void setMaKhoan(int maKhoan) {
        this.maKhoan = maKhoan;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "BangGiaoDich{" +
                "maGiaoDich=" + maGiaoDich +
                ", ngayGiaoDich='" + ngayGiaoDich + '\'' +
                ", soTienGiaoDich=" + soTienGiaoDich +
                ", maKhoan=" + maKhoan +
                ", moTa='" + moTa + '\'' +
                '}';
    }

}
