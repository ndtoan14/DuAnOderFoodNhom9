package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class promotion {
    int ID_khuyenmai;
    int ID_hamburger;
    String TenKM;
    String ngayDB;
    String ngayKT;
    Float PTKM;

    public promotion() {
    }

    public promotion(int ID_khuyenmai, int ID_hamburger, String tenKM, String ngayDB, String ngayKT, Float PTKM) {
        this.ID_khuyenmai = ID_khuyenmai;
        this.ID_hamburger = ID_hamburger;
        TenKM = tenKM;
        this.ngayDB = ngayDB;
        this.ngayKT = ngayKT;
        this.PTKM = PTKM;
    }

    public int getID_khuyenmai() {
        return ID_khuyenmai;
    }

    public int getID_hamburger() {
        return ID_hamburger;
    }

    public String getTenKM() {
        return TenKM;
    }

    public String getNgayDB() {
        return ngayDB;
    }

    public String getNgayKT() {
        return ngayKT;
    }

    public Float getPTKM() {
        return PTKM;
    }

    public void setID_khuyenmai(int ID_khuyenmai) {
        this.ID_khuyenmai = ID_khuyenmai;
    }

    public void setID_hamburger(int ID_hamburger) {
        this.ID_hamburger = ID_hamburger;
    }

    public void setTenKM(String tenKM) {
        TenKM = tenKM;
    }

    public void setNgayDB(String ngayDB) {
        this.ngayDB = ngayDB;
    }

    public void setNgayKT(String ngayKT) {
        this.ngayKT = ngayKT;
    }

    public void setPTKM(Float PTKM) {
        this.PTKM = PTKM;
    }
}
