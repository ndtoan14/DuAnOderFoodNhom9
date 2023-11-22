package toanndph37473.com.example.duanoderfoodnhom9.Model;

import java.io.Serializable;

public class Top implements Serializable {
    int iduser;
    String ten;
    String email;
    String sodienthoai;
    String diachi;
    String hinhanh;
    Double ttien;

    public Top() {
    }

    public Top(int iduser, String ten, String email, String sodienthoai, String diachi, String hinhanh, Double ttien) {
        this.iduser = iduser;
        this.ten = ten;
        this.email = email;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
        this.hinhanh = hinhanh;
        this.ttien = ttien;
    }

    public int getIduser() {
        return iduser;
    }

    public String getTen() {
        return ten;
    }

    public String getEmail() {
        return email;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public Double getTtien() {
        return ttien;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public void setTtien(Double ttien) {
        this.ttien = ttien;
    }
}
