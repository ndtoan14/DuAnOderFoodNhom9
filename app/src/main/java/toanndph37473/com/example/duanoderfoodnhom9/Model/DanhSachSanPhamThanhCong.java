package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class DanhSachSanPhamThanhCong {
    private int IdDSSPTC,IdUsers,IdHamBurger,MaSSP,soLuong;
    private double DonGia;
    private String AnhSp,TenSp;

    public DanhSachSanPhamThanhCong() {
    }

    public DanhSachSanPhamThanhCong(int idDSSPTC, int idUsers, int idHamBurger, int maSSP, int soLuong, double donGia, String anhSp, String tenSp) {
        IdDSSPTC = idDSSPTC;
        IdUsers = idUsers;
        IdHamBurger = idHamBurger;
        MaSSP = maSSP;
        this.soLuong = soLuong;
        DonGia = donGia;
        AnhSp = anhSp;
        TenSp = tenSp;
    }

    public int getIdDSSPTC() {
        return IdDSSPTC;
    }

    public void setIdDSSPTC(int idDSSPTC) {
        IdDSSPTC = idDSSPTC;
    }

    public int getIdUsers() {
        return IdUsers;
    }

    public void setIdUsers(int idUsers) {
        IdUsers = idUsers;
    }

    public int getIdHamBurger() {
        return IdHamBurger;
    }

    public void setIdHamBurger(int idHamBurger) {
        IdHamBurger = idHamBurger;
    }

    public int getMaSSP() {
        return MaSSP;
    }

    public void setMaSSP(int maSSP) {
        MaSSP = maSSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double donGia) {
        DonGia = donGia;
    }

    public String getAnhSp() {
        return AnhSp;
    }

    public void setAnhSp(String anhSp) {
        AnhSp = anhSp;
    }

    public String getTenSp() {
        return TenSp;
    }

    public void setTenSp(String tenSp) {
        TenSp = tenSp;
    }
}
