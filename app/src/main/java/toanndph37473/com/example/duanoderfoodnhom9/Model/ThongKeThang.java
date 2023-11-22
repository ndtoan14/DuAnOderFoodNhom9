package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class ThongKeThang {
    private  int IdThongKeThang,IdUsers,IdHamburger,TongDaBan;
    private String TenHamburger,HinhAnhHamburger;
    private double DonGia;


    public ThongKeThang() {
    }

    public ThongKeThang(int tongDaBan, String tenHamburger, double donGia) {
        TongDaBan = tongDaBan;
        TenHamburger = tenHamburger;
        DonGia = donGia;
    }

    public ThongKeThang(int tongDaBan, String tenHamburger, String hinhAnhHamburger, double donGia) {
        TongDaBan = tongDaBan;
        TenHamburger = tenHamburger;
        HinhAnhHamburger = hinhAnhHamburger;
        DonGia = donGia;
    }

    public ThongKeThang(int idThongKeThang, int idUsers, int idHamburger, int tongDaBan, String tenHamburger, double donGia) {
        IdThongKeThang = idThongKeThang;
        IdUsers = idUsers;
        IdHamburger = idHamburger;
        TongDaBan = tongDaBan;
        TenHamburger = tenHamburger;
        DonGia = donGia;
    }

    public int getIdThongKeThang() {
        return IdThongKeThang;
    }

    public void setIdThongKeThang(int idThongKeThang) {
        IdThongKeThang = idThongKeThang;
    }

    public int getIdUsers() {
        return IdUsers;
    }

    public void setIdUsers(int idUsers) {
        IdUsers = idUsers;
    }

    public int getIdHamburger() {
        return IdHamburger;
    }

    public void setIdHamburger(int idHamburger) {
        IdHamburger = idHamburger;
    }

    public int getTongDaBan() {
        return TongDaBan;
    }

    public void setTongDaBan(int tongDaBan) {
        TongDaBan = tongDaBan;
    }

    public String getTenHamburger() {
        return TenHamburger;
    }

    public void setTenHamburger(String tenHamburger) {
        TenHamburger = tenHamburger;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double donGia) {
        DonGia = donGia;
    }

    public String getHinhAnhHamburger() {
        return HinhAnhHamburger;
    }

    public void setHinhAnhHamburger(String hinhAnhHamburger) {
        HinhAnhHamburger = hinhAnhHamburger;
    }
}
