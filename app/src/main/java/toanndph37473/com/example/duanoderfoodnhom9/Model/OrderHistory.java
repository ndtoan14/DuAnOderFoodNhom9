package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class OrderHistory {
    private int idLichSuDatHang,idUsers,idThongBao,idHamBurger,maDanhSachSanPham;
    private String tenHamburger, anhSanPham;
    private int soLuong;
    private double giaTien;

    public OrderHistory() {
    }

    public OrderHistory(int idLichSuDatHang, int idUsers, int idThongBao, int idHamBurger, int maDanhSachSanPham, String tenHamburger, String anhSanPham, int soLuong, double giaTien) {
        this.idLichSuDatHang = idLichSuDatHang;
        this.idUsers = idUsers;
        this.idThongBao = idThongBao;
        this.idHamBurger = idHamBurger;
        this.maDanhSachSanPham = maDanhSachSanPham;
        this.tenHamburger = tenHamburger;
        this.anhSanPham = anhSanPham;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
    }

    public int getIdLichSuDatHang() {
        return idLichSuDatHang;
    }

    public void setIdLichSuDatHang(int idLichSuDatHang) {
        this.idLichSuDatHang = idLichSuDatHang;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public int getIdThongBao() {
        return idThongBao;
    }

    public void setIdThongBao(int idThongBao) {
        this.idThongBao = idThongBao;
    }

    public int getIdHamBurger() {
        return idHamBurger;
    }

    public void setIdHamBurger(int idHamBurger) {
        this.idHamBurger = idHamBurger;
    }

    public int getMaDanhSachSanPham() {
        return maDanhSachSanPham;
    }

    public void setMaDanhSachSanPham(int maDanhSachSanPham) {
        this.maDanhSachSanPham = maDanhSachSanPham;
    }

    public String getTenHamburger() {
        return tenHamburger;
    }

    public void setTenHamburger(String tenHamburger) {
        this.tenHamburger = tenHamburger;
    }

    public String getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(String anhSanPham) {
        this.anhSanPham = anhSanPham;
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
}
