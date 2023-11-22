package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class Rate {
    private int idDanhGia, idUsers, idHamburger;
    private double rate;
    private String noiDung,ngayDanhGia;

    public Rate() {
    }

    public Rate(int idDanhGia, int idUsers, int idHamburger, double rate, String noiDung, String ngayDanhGia) {
        this.idDanhGia = idDanhGia;
        this.idUsers = idUsers;
        this.idHamburger = idHamburger;
        this.rate = rate;
        this.noiDung = noiDung;
        this.ngayDanhGia = ngayDanhGia;
    }

    public int getIdDanhGia() {
        return idDanhGia;
    }

    public void setIdDanhGia(int idDanhGia) {
        this.idDanhGia = idDanhGia;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public int getIdHamburger() {
        return idHamburger;
    }

    public void setIdHamburger(int idHamburger) {
        this.idHamburger = idHamburger;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayDanhGia() {
        return ngayDanhGia;
    }

    public void setNgayDanhGia(String ngayDanhGia) {
        this.ngayDanhGia = ngayDanhGia;
    }
}
