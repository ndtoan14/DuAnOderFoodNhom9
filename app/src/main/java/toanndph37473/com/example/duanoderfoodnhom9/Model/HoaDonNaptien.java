package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class HoaDonNaptien {
    private int idHoaDonNap,idUsers,trangThai;
    private String hinhAnh;
    private double soTienNap;

    public HoaDonNaptien() {
    }

    public HoaDonNaptien(int idHoaDonNap, int idUsers, int trangThai, String hinhAnh, double soTienNap) {
        this.idHoaDonNap = idHoaDonNap;
        this.idUsers = idUsers;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
        this.soTienNap = soTienNap;
    }

    public int getIdHoaDonNap() {
        return idHoaDonNap;
    }

    public void setIdHoaDonNap(int idHoaDonNap) {
        this.idHoaDonNap = idHoaDonNap;
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

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public double getSoTienNap() {
        return soTienNap;
    }

    public void setSoTienNap(double soTienNap) {
        this.soTienNap = soTienNap;
    }
}
