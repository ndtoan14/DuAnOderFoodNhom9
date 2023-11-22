package toanndph37473.com.example.duanoderfoodnhom9.Model;

import java.io.Serializable;

public class ThongkeDonHangBiHuy implements Serializable {
    private String tenUserHuyDonHang,sdtUserHuyDonHang,diaChiUserHuyDonHang,ngayUserHuyDonHang,liDoUserHuyDonHang,trangThaiUserHuyDonHang;
    private double donGiaUserHuyDonHang;

    public ThongkeDonHangBiHuy() {
    }

    public ThongkeDonHangBiHuy(String tenUserHuyDonHang, String sdtUserHuyDonHang, String diaChiUserHuyDonHang, String ngayUserHuyDonHang, double donGiaUserHuyDonHang, String liDoUserHuyDonHang) {
        this.tenUserHuyDonHang = tenUserHuyDonHang;
        this.sdtUserHuyDonHang = sdtUserHuyDonHang;
        this.diaChiUserHuyDonHang = diaChiUserHuyDonHang;
        this.ngayUserHuyDonHang = ngayUserHuyDonHang;
        this.donGiaUserHuyDonHang = donGiaUserHuyDonHang;
        this.liDoUserHuyDonHang = liDoUserHuyDonHang;
    }

    public ThongkeDonHangBiHuy(String tenUserHuyDonHang, String sdtUserHuyDonHang, String diaChiUserHuyDonHang, String ngayUserHuyDonHang, String trangThaiUserHuyDonHang, double donGiaUserHuyDonHang) {
        this.tenUserHuyDonHang = tenUserHuyDonHang;
        this.sdtUserHuyDonHang = sdtUserHuyDonHang;
        this.diaChiUserHuyDonHang = diaChiUserHuyDonHang;
        this.ngayUserHuyDonHang = ngayUserHuyDonHang;
        this.trangThaiUserHuyDonHang = trangThaiUserHuyDonHang;
        this.donGiaUserHuyDonHang = donGiaUserHuyDonHang;

    }

    public String getTenUserHuyDonHang() {
        return tenUserHuyDonHang;
    }

    public void setTenUserHuyDonHang(String tenUserHuyDonHang) {
        this.tenUserHuyDonHang = tenUserHuyDonHang;
    }

    public String getSdtUserHuyDonHang() {
        return sdtUserHuyDonHang;
    }

    public void setSdtUserHuyDonHang(String sdtUserHuyDonHang) {
        this.sdtUserHuyDonHang = sdtUserHuyDonHang;
    }

    public String getDiaChiUserHuyDonHang() {
        return diaChiUserHuyDonHang;
    }

    public void setDiaChiUserHuyDonHang(String diaChiUserHuyDonHang) {
        this.diaChiUserHuyDonHang = diaChiUserHuyDonHang;
    }

    public String getNgayUserHuyDonHang() {
        return ngayUserHuyDonHang;
    }

    public void setNgayUserHuyDonHang(String ngayUserHuyDonHang) {
        this.ngayUserHuyDonHang = ngayUserHuyDonHang;
    }

    public String getTrangThaiUserHuyDonHang() {
        return trangThaiUserHuyDonHang;
    }

    public void setTrangThaiUserHuyDonHang(String trangThaiUserHuyDonHang) {
        this.trangThaiUserHuyDonHang = trangThaiUserHuyDonHang;
    }

    public double getDonGiaUserHuyDonHang() {
        return donGiaUserHuyDonHang;
    }

    public void setDonGiaUserHuyDonHang(double donGiaUserHuyDonHang) {
        this.donGiaUserHuyDonHang = donGiaUserHuyDonHang;
    }

    public String getLiDoUserHuyDonHang() {
        return liDoUserHuyDonHang;
    }

    public void setLiDoUserHuyDonHang(String liDoUserHuyDonHang) {
        this.liDoUserHuyDonHang = liDoUserHuyDonHang;
    }
}
