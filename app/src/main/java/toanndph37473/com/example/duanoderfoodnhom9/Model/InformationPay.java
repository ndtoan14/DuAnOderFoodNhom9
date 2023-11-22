package toanndph37473.com.example.duanoderfoodnhom9.Model;

import java.io.Serializable;

public class InformationPay implements Serializable {
    private int idThongTin,idUsers;
    private String ten,soDienThoai,diaChi;

    public InformationPay() {
    }

    public InformationPay(int idThongTin, int idUsers, String ten, String soDienThoai, String diaChi) {
        this.idThongTin = idThongTin;
        this.idUsers = idUsers;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
    }

    public int getIdThongTin() {
        return idThongTin;
    }

    public void setIdThongTin(int idThongTin) {
        this.idThongTin = idThongTin;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
