package com.example.myapplication.model;

public class KhoanThuChi {

    private int maKhoan;
    private String tenKhoan;
    private String loaiKhoan;

    public KhoanThuChi() {
    }

    public KhoanThuChi(int maKhoan) {
        this.maKhoan = maKhoan;
    }

    public KhoanThuChi(String tenKhoan) {
        this.tenKhoan = tenKhoan;
    }

    public KhoanThuChi(String tenKhoan, String loaiKhoan) {
        this.tenKhoan = tenKhoan;
        this.loaiKhoan = loaiKhoan;
    }

    public KhoanThuChi(int maKhoan, String tenKhoan){
        this.maKhoan = maKhoan;
        this.tenKhoan = tenKhoan;
    }

    public KhoanThuChi(int maKhoan, String tenKhoan, String loaiKhoan) {
        this.maKhoan = maKhoan;
        this.tenKhoan = tenKhoan;
        this.loaiKhoan = loaiKhoan;
    }

    public int getMaKhoan() {
        return maKhoan;
    }

    public void setMaKhoan(int maKhoan) {
        this.maKhoan = maKhoan;
    }

    public String getTenKhoan() {
        return tenKhoan;
    }

    public void setTenKhoan(String tenKhoan) {
        this.tenKhoan = tenKhoan;
    }

    public String getLoaiKhoan() {
        return loaiKhoan;
    }

    public void setLoaiKhoan(String loaiKhoan) {
        this.loaiKhoan = loaiKhoan;
    }

    @Override
    public String toString() {
        return "KhoanThuChi{" +
                "maKhoan=" + maKhoan +
                ", tenKhoan='" + tenKhoan + '\'' +
                ", loaiKhoan='" + loaiKhoan + '\'' +
                '}';
    }

}
