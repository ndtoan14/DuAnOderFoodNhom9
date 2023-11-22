package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class DanhSachSanPhamBiHuy {
    private int IdDSSPBH,IdUsers,IdHamBurger,MaSSP,soLuong;
    private double DonGia;
    private String AnhSp,TenSp;

    public DanhSachSanPhamBiHuy() {
    }

    public DanhSachSanPhamBiHuy(int idDSSPBH, int idUsers, int idHamBurger,String TenSp, int maSSP, int soLuong, double donGia, String anhSp) {
        this.IdDSSPBH = idDSSPBH;
        this.IdUsers = idUsers;
        this.IdHamBurger = idHamBurger;
        this.TenSp = TenSp;
        this.MaSSP = maSSP;
        this.soLuong = soLuong;
        this.DonGia = donGia;
        this.AnhSp = anhSp;
    }

    public int getIdDSSPBH() {
        return IdDSSPBH;
    }

    public void setIdDSSPBH(int idDSSPBH) {
        IdDSSPBH = idDSSPBH;
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
