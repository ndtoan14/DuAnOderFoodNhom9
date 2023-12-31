package toanndph37473.com.example.duanoderfoodnhom9.Model;

import java.io.Serializable;

public class Invoice implements Serializable {
    private int idHoaDon, idThanhtoan,idUsers,trangThai;
    private String tenUsers,ngayXuat,diaChi,soDienThoai;
    private double tongTien;

    public Invoice() {
    }

    public Invoice(int idHoaDon, int idThanhtoan, int idUsers, int trangThai, String tenUsers, String ngayXuat, double tongTien) {
        this.idHoaDon = idHoaDon;
        this.idThanhtoan = idThanhtoan;
        this.idUsers = idUsers;
        this.trangThai = trangThai;
        this.tenUsers = tenUsers;
        this.ngayXuat = ngayXuat;
        this.tongTien = tongTien;
    }

    public Invoice(int idHoaDon, int idThanhtoan, int idUsers, int trangThai, String tenUsers, String ngayXuat, String diaChi, String soDienThoai, double tongTien) {
        this.idHoaDon = idHoaDon;
        this.idThanhtoan = idThanhtoan;
        this.idUsers = idUsers;
        this.trangThai = trangThai;
        this.tenUsers = tenUsers;
        this.ngayXuat = ngayXuat;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.tongTien = tongTien;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public int getIdThanhtoan() {
        return idThanhtoan;
    }

    public void setIdThanhtoan(int idThanhtoan) {
        this.idThanhtoan = idThanhtoan;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenUsers() {
        return tenUsers;
    }

    public void setTenUsers(String tenUsers) {
        this.tenUsers = tenUsers;
    }

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
