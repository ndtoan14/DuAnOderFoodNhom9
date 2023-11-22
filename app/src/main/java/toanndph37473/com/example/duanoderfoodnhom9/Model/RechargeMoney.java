package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class RechargeMoney {
    private int idHoaDonNapTien, trangThai, idUsers;
    private String hinhAnh;
    private double soTienNap;

    public RechargeMoney() {
    }

    public int getIdHoaDonNapTien() {
        return idHoaDonNapTien;
    }

    public RechargeMoney(int idHoaDonNapTien, int trangThai, int idUsers, String hinhAnh, double soTienNap) {
        this.idHoaDonNapTien = idHoaDonNapTien;
        this.trangThai = trangThai;
        this.idUsers = idUsers;
        this.hinhAnh = hinhAnh;
        this.soTienNap = soTienNap;
    }

    public double getSoTienNap() {
        return soTienNap;
    }

    public void setSoTienNap(double soTienNap) {
        this.soTienNap = soTienNap;
    }

    public void setIdHoaDonNapTien(int idHoaDonNapTien) {
        this.idHoaDonNapTien = idHoaDonNapTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
