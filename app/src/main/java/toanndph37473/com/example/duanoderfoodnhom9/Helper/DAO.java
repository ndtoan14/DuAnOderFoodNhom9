package toanndph37473.com.example.duanoderfoodnhom9.Helper;

import android.content.Context;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Model.CommentNews;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.Model.promotion;
public class DAO {
    Connection_SQL connection;
    List<Hamburger> hamburgerList = new ArrayList<>();
    // cac ham truy van
    public List<Hamburger> getHamburgerList() {

        try {
            connection = new Connection_SQL();
            String sql = "SELECT * FROM HAMBURGER";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("IDHAMBURGER");
                String ten = resultSet.getString("TEN");
                String moTaNgan = resultSet.getString("MOTANGAN");
                String moTaChiTiet = resultSet.getString("MOTACHITIET");
                double giaTien = resultSet.getDouble("GIATIEN");
                int soLuong = resultSet.getInt("SOLUONG");
                String hinhAnh = resultSet.getString("HINHANH");
                int daBan = resultSet.getInt("DABAN");
                double giaKM = resultSet.getDouble("GIAKM");
                Hamburger hamburger = new Hamburger(id,ten,moTaNgan,moTaChiTiet,hinhAnh,soLuong,giaTien,daBan,giaKM);
                hamburgerList.add(hamburger);
            }
            resultSet.close();
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hamburgerList;
    }

    public List<Hamburger> getListHamBurgerTheoSoLuong(){
        List<Hamburger> list = new ArrayList<>();
        try{
            connection = new Connection_SQL();
            String sql = "SELECT * FROM HAMBURGER WHERE SOLUONG > 0";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                int id= rs.getInt("IDHAMBURGER");
                String ten = rs.getString("TEN");
                String motangan = rs.getString("MOTANGAN");
                String motachitiet = rs.getString("MOTACHITIET");
                double giaTien = rs.getDouble("GIATIEN");
                String ha = rs.getString("HINHANH");
                int soluong= rs.getInt("SOLUONG");
                int daban = rs.getInt("DABAN");
                double giaKM = rs.getDouble("GIAKM");
                Hamburger hamburger = new Hamburger(id,ten,motangan,motachitiet,ha,soluong,giaTien,daban,giaKM);
                list.add(hamburger);
            }
            rs.close();
            ptm.close();
            connection.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
    public int getSoLuongSanPham(int idHamburger){
        int soluong = 0;
        try{
            connection = new Connection_SQL();
            String sql = "SELECT SOLUONG FROM HAMBURGER WHERE IDHAMBURGER = ?";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(sql);
            ptm.setInt(1,idHamburger);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                soluong = rs.getInt("SOLUONG");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return soluong;
    }

    public List<CommentNews> getAllComment (int idTintuc){
        List<CommentNews> list = new ArrayList<>();
        try{
            connection = new Connection_SQL();
            String sql = "SELECT * FROM COMMENT WHERE IDTINTUC = ? ";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(sql);
            ptm.setInt(1,idTintuc);
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                int id= rs.getInt("COMMENTID");
                String noidung = rs.getString("NOIDUNG");
                String ten = rs.getString("TENUSER");
                int idTInTuc = rs.getInt("IDTINTUC");
                String ngaycmt = rs.getString("NGAYCMT");
                String hinhanh = rs.getString("HINHANH");
                int idUsers= rs.getInt("IDUSERS");
                CommentNews item = new CommentNews(id,idUsers,idTInTuc,noidung,ngaycmt,ten,hinhanh);
                list.add(item);
            }
            rs.close();
            ptm.close();
            connection.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    // cac ham them moi
    public boolean insertHamburger(String tenSP, String moTaNgan, String moTaChiTiet, String giaTien, String soLuong, String encodedImage, Context context){
        int rowInserted;
        try {
            connection = new Connection_SQL();
            String sql = "INSERT INTO HAMBURGER (TEN, MOTANGAN,MOTACHITIET,GIATIEN,SOLUONG,HINHANH) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setString(1, tenSP);
            statement.setString(2, moTaNgan);
            statement.setString(3, moTaChiTiet);
            statement.setDouble(4, Double.parseDouble(giaTien));
            statement.setInt(5, Integer.parseInt(soLuong));
            statement.setString(6, encodedImage);
            rowInserted = statement.executeUpdate();
            if (rowInserted>0){
                return true;
            }
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context, "them that bai", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    public int insertKhuyenMai(int idHamBurger,String tenkm,String ngayBD,String ngayKT,String phantram){
        int row = 0;
        try {
            connection  = new Connection_SQL();
            String insertKM = "INSERT INTO KHUYENMAI (IDHAMBURGER,TENKHUYENMAI,NGAYBATDAU,NGAYKETTHUC,PHANTRAMKHUYENMAI) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = connection.SQLconnection().prepareStatement(insertKM);
            stm.setInt(1, idHamBurger);
            stm.setString(2, tenkm);
            stm.setString(3, ngayBD);
            stm.setString(4, ngayKT);
            stm.setString(5, phantram);
            row = stm.executeUpdate();
            stm.close();
            connection.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(row>0){
            return 1;
        }else{
            return -1;
        }
    }
    public int insertTinTuc(String tieuDe,String noiDung,String hinhAnh, String ngayDangTin){
        int row = 0;
        try {
            connection  = new Connection_SQL();
            String insertNews = "INSERT INTO TINTUC (TIEUDE,NOIDUNG,HINHANH,NGAYDANGTIN) VALUES ( ?, ?, ?, ?)";
            PreparedStatement stm = connection.SQLconnection().prepareStatement(insertNews);
            stm.setString(1, tieuDe);
            stm.setString(2, noiDung);
            stm.setString(3, hinhAnh);
            stm.setString(4,ngayDangTin);
            row = stm.executeUpdate();
            stm.close();
            connection.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(row>0){
            return 1;
        }else{
            return -1;
        }
    }
    public int insertThongTinNhanHang(String ten, String sodienthoai, String diachi, int idUsers){
        int row = 0;
        try{
            connection = new Connection_SQL();
            String insert = "INSERT INTO THONGTINNHANHANG (TEN,SODIENTHOAI,DIACHI,IDUSERS) VALUES (?, ?, ?, ?)";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(insert);
            ptm.setString(1,ten);
            ptm.setString(2,sodienthoai);
            ptm.setString(3,diachi);
            ptm.setInt(4,idUsers);
            row = ptm.executeUpdate();
            ptm.close();
            connection.SQLconnection().close();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(row>0){
            return 1;
        }else{
            return -1;
        }
    }



    //update
    public int capNhatGiaKMHamBurgerTheoId(String phantram, double giaTien, int idHamBurger) {
        int row = 0;
        try{
            connection = new Connection_SQL();
            String updateHamburger = "UPDATE HAMBURGER " +
                    "SET GIAKM = ? " +
                    "WHERE IDHAMBURGER = ?";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(updateHamburger);
            double dbphanTram = Double.parseDouble(phantram);
            double giaSale = Double.parseDouble(String.valueOf(giaTien - (giaTien*dbphanTram)/100));
            ptm.setDouble(1,giaSale);
            ptm.setInt(2,idHamBurger);
            row = ptm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(row > 0){
            return 1;
        }else{
            return -1;
        }
    }
    public int updateHamburgerTheoId(String namePr,String mo_ta_ngan,String mo_ta_chi_tiet,String gia, String soLuong,String hinhAnh, int IdHamberger){
        int row = 0;
        try {
            connection = new Connection_SQL();
            String sql = "Update HAMBURGER set TEN=?,MOTANGAN=?,MOTACHITIET=?,GIATIEN=?,SOLUONG=?,HINHANH=? where IDHAMBURGER=?";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setString(1,namePr);
            statement.setString(2,mo_ta_ngan);
            statement.setString(3,mo_ta_chi_tiet);
            statement.setDouble(4, Double.parseDouble(gia));
            statement.setInt(5, Integer.parseInt(soLuong));
            statement.setString(6,hinhAnh);
            statement.setInt(7,IdHamberger);
            row = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(row>0){
            return 1;
        }else{
            return -1;
        }
    }

    public void CallKM() {
        connection = new Connection_SQL();
        String sql_callkm = "select * from khuyenmai";
        try {
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql_callkm);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int _idKM = resultSet.getInt("IDKHUYENMAI");
                int ID_hamburger = resultSet.getInt("IDHAMBURGER");
                String TenKM = resultSet.getString("TENKHUYENMAI");
                String ngayDB = resultSet.getString("NGAYBATDAU");
                String ngayKT = resultSet.getString("NGAYKETTHUC");
                Float PTKM =   resultSet.getFloat("PHANTRAMKHUYENMAI");
                promotion promotion = new promotion(_idKM,ID_hamburger,TenKM,ngayDB,ngayKT,PTKM);
                if (promotion.getPTKM()==0){
                    connection = new Connection_SQL();
                    String update_GiaKM = "update HAMBURGER set GIAKM=0 where IDHAMBURGER= ? ";
                    PreparedStatement statement1 = connection.SQLconnection().prepareStatement(update_GiaKM);
                    statement1.setInt(1,promotion.getID_hamburger());
                    statement1.executeUpdate();
                    statement1.close();
                    connection.SQLconnection().close();
                }
            }
            statement.close();
            resultSet.close();
            connection.SQLconnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateKM() {
        connection = new Connection_SQL();
        String sql_update_KM = "update KHUYENMAI SET PHANTRAMKHUYENMAI = 0 WHERE getdate() >= NGAYKETTHUC";
        try {
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql_update_KM);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteKM(){
        connection = new Connection_SQL();
        String sql_select_KM = "delete from khuyenmai where phantramkhuyenmai =0";
        try {
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql_select_KM);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
