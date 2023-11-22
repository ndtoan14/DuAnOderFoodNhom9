package toanndph37473.com.example.duanoderfoodnhom9.Model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Hamburger implements Serializable {
    private int id;
    private String ten,moTaNgan,moTaChiTiet,hinhAnh;
    private int soLuong;
    private double giaTien;
    private int daBan;
    private double giaKM;



    public double getGiaKM() {
        return giaKM;
    }

    public void setGiaKM(double giaKM) {
        this.giaKM = giaKM;
    }

    public Hamburger() {
    }

    public Hamburger(String ten, String moTaNgan, String moTaChiTiet, int soLuong, double giaTien) {
        this.ten = ten;
        this.moTaNgan = moTaNgan;
        this.moTaChiTiet = moTaChiTiet;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
    }

    public Hamburger(int id, String ten, String moTaNgan, String moTaChiTiet, String hinhAnh, int soLuong, double giaTien) {
        this.id = id;
        this.ten = ten;
        this.moTaNgan = moTaNgan;
        this.moTaChiTiet = moTaChiTiet;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
    }

    public Hamburger(int id, String ten, String moTaNgan, String moTaChiTiet, String hinhAnh, int soLuong, double giaTien, int daBan, double giaKM) {
        this.id = id;
        this.ten = ten;
        this.moTaNgan = moTaNgan;
        this.moTaChiTiet = moTaChiTiet;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.daBan = daBan;
        this.giaKM = giaKM;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTaNgan() {
        return moTaNgan;
    }

    public void setMoTaNgan(String moTaNgan) {
        this.moTaNgan = moTaNgan;
    }

    public String getMoTaChiTiet() {
        return moTaChiTiet;
    }

    public void setMoTaChiTiet(String moTaChiTiet) {
        this.moTaChiTiet = moTaChiTiet;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public int getDaBan() {
        return daBan;
    }

    public void setDaBan(int daBan) {
        this.daBan = daBan;
    }
}
