package toanndph37473.com.example.duanoderfoodnhom9.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.CartAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.Interface.Service;
import toanndph37473.com.example.duanoderfoodnhom9.Model.ListProductCart;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class CartActivity extends AppCompatActivity{
    RecyclerView rcvCart;
    CartAdapter adapter;
    List<ListProductCart> listProductCart =new ArrayList<>();
    public static TextView tvTongTien;
    Button btnBuyNow;
    CardView btnBacktoBurgerDetail;
    int a= 0;
    public static int maDssp=0;
    Connection_SQL connection;
    ConstraintLayout layoutCart;
    int idhamburger;
    double tt;
    DAO dao;
    String check = "true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tvTongTien=findViewById(R.id.tvTongTienCart);
        btnBuyNow=findViewById(R.id.btnBuyNowCart);
        btnBacktoBurgerDetail=findViewById(R.id.btnBackInCart);
        layoutCart = findViewById(R.id.backgroundCart);
        rcvCart = findViewById(R.id.rcvCart);
        dao = new DAO();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        kiemTraGioHang();
        getAllList();
        tongTien();
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.parseDouble(tvTongTien.getText().toString().trim())<=0){
                    Toast.makeText(CartActivity.this, "Khong co san pham nao trong gio hang", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    for(ListProductCart item : listProductCart){
                        if(item.getSoLuong()>dao.getSoLuongSanPham(item.getIdHamburger())){
                            check="false";
                            break;
                        }else{
                            check="true";
                        }
                    }
                    if(check.equalsIgnoreCase("false")){
                        Toast.makeText(CartActivity.this, "Trong gio hang co san pham hien khong con du hang de dap ung nhu cau", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        Intent intent = new Intent(CartActivity.this, PayBillActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });
        btnBacktoBurgerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getAllList() {
        getAllListCart();

        adapter = new CartAdapter(listProductCart, this, new Service() {
            @Override
            public void addp(int i) {
                updateSoLuongCong(i);
                tongTien();
            }

            @Override
            public void subtraction(int i) {
                updateSoLuongTru(i);

                tongTien();

            }

            @Override
            public void deleteincart(int i) {
                deletecart(i);
            }

            @Override
            public void onLikeClick(int i) {

            }
        });
        rcvCart.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void deletecart(int position) {
        ListProductCart cart = listProductCart.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Ban co chac chan muon xoa san pham nay khoi gio hang ?");
        builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    connection = new Connection_SQL();
                    String deleteCart = "DELETE FROM DANHSACHSANPHAM WHERE IDHAMBURGER = ? ";
                    PreparedStatement ptm = connection.SQLconnection().prepareStatement(deleteCart);
                    ptm.setInt(1,cart.getIdHamburger());
                    int row = ptm.executeUpdate();
                    if(row>0){
                        listProductCart.clear();
                        getAllList();
                        tongTien();
                        kiemTraGioHang();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void updateSoLuongTru(int index) {
        try {
            connection = new Connection_SQL();
            Users users = UserSession.getCurrentUser(CartActivity.this);
            int userID = users.getIdUser();
            String sqll = "UPDATE DANHSACHSANPHAM " +
                    "SET SOLUONG = SOLUONG - 1 " +
                    "WHERE IDHAMBURGER = ? AND IDUSERS = ?;";
            PreparedStatement sttm = connection.SQLconnection().prepareStatement(sqll);
            sttm.setInt(1, index);
            sttm.setInt(2, userID);
            int rowsUpdated = sttm.executeUpdate();
            double total = 0;
            if (rowsUpdated > 0) {

                for (ListProductCart item : listProductCart) {
                    total += item.getGiaTien() * item.getSoLuong();
                }
                tvTongTien.setText("" + total);
            } else {
                Toast.makeText(this, "Cập nhật số lượng sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
            for(ListProductCart item : listProductCart){
                if(item.getSoLuong()>dao.getSoLuongSanPham(item.getIdHamburger())){
                    check="false";
                    break;
                }else{
                    check="true";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSoLuongCong(int index) {
        try {
            connection = new Connection_SQL();
            Users users = UserSession.getCurrentUser(CartActivity.this);
            int userID = users.getIdUser();
            String sqll = "UPDATE DANHSACHSANPHAM " +
                    "SET SOLUONG = SOLUONG + 1 " +
                    "WHERE IDHAMBURGER = ? AND IDUSERS = ?;";
            PreparedStatement sttm = connection.SQLconnection().prepareStatement(sqll);
            sttm.setInt(1, index);
            sttm.setInt(2, userID);
            sttm.executeUpdate();
            for(ListProductCart item : listProductCart){
                if(item.getSoLuong()>dao.getSoLuongSanPham(item.getIdHamburger())){
                    check="false";
                    break;
                }else{
                    check="true";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ListProductCart> getAllListCart() {

        try {
            connection = new Connection_SQL();
            Users user = UserSession.getCurrentUser(CartActivity.this);
            int userId = user.getIdUser();
            String sql = "SELECT * FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("IDDANHSACHSANPHAM");
                int idusers = resultSet.getInt("IDUSERS");
                idhamburger = resultSet.getInt("IDHAMBURGER");
                String truyvan = "SELECT GIAKM FROM HAMBURGER WHERE IDHAMBURGER = ?";
                double giaTien = resultSet.getDouble("GIATIEN");
                PreparedStatement preparedStatement = connection.SQLconnection().prepareStatement(truyvan);
                preparedStatement.setInt(1,idhamburger);
                ResultSet resultSet1 = preparedStatement.executeQuery();
                double giaKM = 0;
                if(resultSet1.next()){
                    giaKM = resultSet1.getDouble("GIAKM");
                    if(giaKM>0){
                        String updategia = "UPDATE DANHSACHSANPHAM " +
                                "SET GIATIEN = ? " +
                                "WHERE IDHAMBURGER = ?";
                        PreparedStatement preparedStatement1 = connection.SQLconnection().prepareStatement(updategia);
                        preparedStatement1.setDouble(1,giaKM);
                        preparedStatement1.setInt(2,idhamburger);
                        preparedStatement1.executeUpdate();
                    }else{
                        String updategia2 = "UPDATE DANHSACHSANPHAM " +
                                "SET GIATIEN = ? " +
                                "WHERE IDHAMBURGER = ?";
                        PreparedStatement preparedStatement2 = connection.SQLconnection().prepareStatement(updategia2);
                        preparedStatement2.setDouble(1,giaTien);
                        preparedStatement2.setInt(2,idhamburger);
                        preparedStatement2.executeUpdate();
                    }
                }
                String tenhamburger = resultSet.getString("TENHAMBURGER");

                int soLuong = resultSet.getInt("SOLUONG");
                String hinhAnh = resultSet.getString("ANHSANPHAM");
                String maDanhSachSanPham = String.valueOf(resultSet.getInt("MADANHSACHSANPHAM"));
                if(maDanhSachSanPham==null){
                    maDanhSachSanPham = String.valueOf(a);
                    maDssp = Integer.parseInt(maDanhSachSanPham);
                }
                ListProductCart cart;
                if(giaKM<=0) {
                    cart = new ListProductCart(id, maDssp, idusers, idhamburger, tenhamburger, hinhAnh, soLuong, giaTien);
                    String updategia = "UPDATE DANHSACHSANPHAM " +
                            "SET GIATIEN = ? " +
                            "WHERE IDHAMBURGER = ?";
                    PreparedStatement ptm = connection.SQLconnection().prepareStatement(updategia);
                    ptm.setDouble(1,giaTien);
                    ptm.setInt(2,idhamburger);
                    ptm.executeUpdate();
                }else{
                    cart = new ListProductCart(id, maDssp, idusers, idhamburger, tenhamburger, hinhAnh, soLuong, giaKM);
                    String updategia = "UPDATE DANHSACHSANPHAM " +
                            "SET GIATIEN = ? " +
                            "WHERE IDHAMBURGER = ?";
                    PreparedStatement ptm = connection.SQLconnection().prepareStatement(updategia);
                    ptm.setDouble(1,giaKM);
                    ptm.setInt(2,idhamburger);
                    ptm.executeUpdate();
                }
                listProductCart.add(cart);

            }
            resultSet.close();
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listProductCart;
    }
    @SuppressLint("ResourceAsColor")
    public void kiemTraGioHang(){
        try {
            connection = new Connection_SQL();
            String kiemtra = "SELECT COUNT(IDDANHSACHSANPHAM) AS COUNTS FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(kiemtra);
            ptm.setInt(1,UserSession.getCurrentUser(CartActivity.this).getIdUser());
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                if(rs.getInt("COUNTS")>0){
                    rcvCart.setBackground(null);
                }else {
                    rcvCart.setBackgroundResource(R.drawable.no_product_in_cart);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void tongTien(){
        try {
            connection = new Connection_SQL();
            Users user = UserSession.getCurrentUser(CartActivity.this);
            int userId = user.getIdUser();
            String sum = "SELECT SUM(GIATIEN * SOLUONG) as TONG FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
            PreparedStatement statement1 = connection.SQLconnection().prepareStatement(sum);
            statement1.setInt(1, userId);
            ResultSet resultSet0 = statement1.executeQuery();
            if(resultSet0.next()){
                tt = resultSet0.getDouble("TONG");
            }
            tvTongTien.setText(""+tt);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
