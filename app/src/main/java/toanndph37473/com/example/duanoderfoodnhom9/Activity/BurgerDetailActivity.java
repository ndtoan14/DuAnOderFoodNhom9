package toanndph37473.com.example.duanoderfoodnhom9.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ListRateAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.CartManager;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Rate;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class BurgerDetailActivity extends AppCompatActivity {
    CardView btnback,btnCard;
    ImageView imgBurger;
    TextView tvTenSanPham, tvMoTaNgan,tvMoTaChiTiet,tvGiaTien, tvTang, tvGiam, tvSoLuong;
    Button btnAddToCart;
    int idHamburger,soLuong ;
    String ten,moTaNgan,moTaChiTiet,hinhAnh;
    double giaTien,giaKM;
    List<CartManager> listCart = new ArrayList<>();
    Connection_SQL connection;
    RecyclerView rcv ;
    ListRateAdapter adapter;
    List<Rate> listRate = new ArrayList<>();
    Rate item = new Rate();
    double avg;
    ImageView rateDetail1,rateDetail2,rateDetail3,rateDetail4,rateDetail5,imgShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger_detail);
        anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Hamburger burger = (Hamburger) bundle.getSerializable("burger");
        tvTenSanPham.setText(burger.getTen());
        tvMoTaNgan.setText(burger.getMoTaNgan());
        tvMoTaChiTiet.setText(burger.getMoTaChiTiet());
        ten=burger.getTen();
        moTaNgan=burger.getMoTaNgan();
        idHamburger=burger.getId();
        soLuong=burger.getSoLuong();
        moTaChiTiet=burger.getMoTaChiTiet();
        hinhAnh=burger.getHinhAnh();
        giaTien=burger.getGiaTien();
        giaKM=burger.getGiaKM();
        if(giaKM<=0){
            tvGiaTien.setText(""+burger.getGiaTien());
        }else{
            tvGiaTien.setText(String.valueOf(burger.getGiaKM()));
        }
        // Chuỗi đường dẫn base64 dạng string
        String base64String = burger.getHinhAnh();
        // Chuyển đổi đường dẫn base64 thành Bitmap
        byte[] decodedString = Base64.decode(base64String.substring(base64String.indexOf(",") + 1), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        // Hiển thị Bitmap trong ImageView
        imgBurger.setImageBitmap(bitmap);
        // xu li adapter
        getListRate();
        adapter = new ListRateAdapter(listRate,getApplicationContext());
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // kiem tra luong rate
        getAvgRate();
        if(avg<1.5&& avg>0){
            rateDetail1.setImageResource(R.drawable.ic_saovang);
            rateDetail2.setImageResource(R.drawable.ic_saotrang);
            rateDetail3.setImageResource(R.drawable.ic_saotrang);
            rateDetail4.setImageResource(R.drawable.ic_saotrang);
            rateDetail5.setImageResource(R.drawable.ic_saotrang);
        }else if(avg<=2.5){
            rateDetail1.setImageResource(R.drawable.ic_saovang);
            rateDetail2.setImageResource(R.drawable.ic_saovang);
            rateDetail3.setImageResource(R.drawable.ic_saotrang);
            rateDetail4.setImageResource(R.drawable.ic_saotrang);
            rateDetail5.setImageResource(R.drawable.ic_saotrang);
        }else if(avg<=3.5){
            rateDetail1.setImageResource(R.drawable.ic_saovang);
            rateDetail2.setImageResource(R.drawable.ic_saovang);
            rateDetail3.setImageResource(R.drawable.ic_saovang);
            rateDetail4.setImageResource(R.drawable.ic_saotrang);
            rateDetail5.setImageResource(R.drawable.ic_saotrang);
        }else if(avg<=4.5){
            rateDetail1.setImageResource(R.drawable.ic_saovang);
            rateDetail2.setImageResource(R.drawable.ic_saovang);
            rateDetail3.setImageResource(R.drawable.ic_saovang);
            rateDetail4.setImageResource(R.drawable.ic_saovang);
            rateDetail5.setImageResource(R.drawable.ic_saotrang);
        }else{
            rateDetail1.setImageResource(R.drawable.ic_saovang);
            rateDetail2.setImageResource(R.drawable.ic_saovang);
            rateDetail3.setImageResource(R.drawable.ic_saovang);
            rateDetail4.setImageResource(R.drawable.ic_saovang);
            rateDetail5.setImageResource(R.drawable.ic_saovang);
        }

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = burger.getTen();
                String productDescription = burger.getMoTaNgan();
                String productDescriptionLong = burger.getMoTaChiTiet();
                Double price ;
                if (burger.getGiaKM()>0){
                    price = burger.getGiaKM();
                }else {
                    price = burger.getGiaTien();
                }
                String url_img = "https://www.tasteofhome.com/wp-content/uploads/2018/01/exps28800_UG143377D12_18_1b_RMS.jpg";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, productName);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Tên sản phẩm:" +productName + "\n" + productDescription +"\n"+ productDescriptionLong +"\n" + "Giá :" + price+"$" + "\n" + url_img);
                Intent intent1 = Intent.createChooser(shareIntent,null);
                try {
                    startActivity(intent1);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Thông báo nếu không cài đặt Zalo trên điện thoại.
                    Toast.makeText(BurgerDetailActivity.this, "Ứng dụng zalo chưa được cài đặt trên máy bạn!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // xu li logic tang giam so luong
        tvTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = Integer.parseInt(tvSoLuong.getText().toString());
                sl = sl +1;
                tvSoLuong.setText(""+sl);

            }
        });
        tvGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(tvSoLuong.getText().toString())>1){
                    int sl = Integer.parseInt(tvSoLuong.getText().toString());
                    sl = sl - 1;
                    tvSoLuong.setText(""+sl);
                }else{
                    return;
                }
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users user = UserSession.getCurrentUser(BurgerDetailActivity.this);
                int userId = user.getIdUser();
                try {
                    Connection_SQL connection_sql = new Connection_SQL();

                    // Kiểm tra sản phẩm đã có trong giỏ hàng hay chưa
                    String queryCheck = "SELECT * FROM DANHSACHSANPHAM WHERE IDUSERS=? AND IDHAMBURGER=?";
                    PreparedStatement pstmtCheck = connection_sql.SQLconnection().prepareStatement(queryCheck);
                    pstmtCheck.setInt(1, userId);
                    pstmtCheck.setInt(2, idHamburger);
                    ResultSet rs = pstmtCheck.executeQuery();

                    if (rs.next()) {
                        // Sản phẩm đã có trong giỏ hàng, tăng số lượng sản phẩm lên
                        int sl = rs.getInt("SOLUONG");
                        int newSl = sl + Integer.parseInt(tvSoLuong.getText().toString().trim());
                        String queryUpdate = "UPDATE DANHSACHSANPHAM SET SOLUONG=? WHERE IDUSERS=? AND IDHAMBURGER=?";
                        PreparedStatement pstmtUpdate = connection_sql.SQLconnection().prepareStatement(queryUpdate);
                        pstmtUpdate.setInt(1, newSl);
                        pstmtUpdate.setInt(2, userId);
                        pstmtUpdate.setInt(3, idHamburger);
                        pstmtUpdate.executeUpdate();
                        Toast.makeText(BurgerDetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        //kiem tra gia khuyen mai cua san pham
                        String km = "SELECT * FROM HAMBURGER WHERE IDHAMBURGER = ? ";
                        PreparedStatement preparedStatement = connection_sql.SQLconnection().prepareStatement(km);
                        preparedStatement.setInt(1,idHamburger);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if(resultSet.next()){
                            double giasauKM = resultSet.getDouble("GIAKM");
                            String queryInsert = "INSERT INTO DANHSACHSANPHAM (IDUSERS, IDHAMBURGER, MADANHSACHSANPHAM, TENHAMBURGER, SOLUONG, GIATIEN, ANHSANPHAM) VALUES (?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement pstmtInsert = connection_sql.SQLconnection().prepareStatement(queryInsert);
                            pstmtInsert.setInt(1, userId);
                            pstmtInsert.setInt(2, idHamburger);
                            pstmtInsert.setInt(3, CartActivity.maDssp);
                            pstmtInsert.setString(4, ten);
                            pstmtInsert.setInt(5, Integer.parseInt(tvSoLuong.getText().toString()));
                            if(giasauKM>0){
                                pstmtInsert.setDouble(6, giasauKM);
                            }else{
                                pstmtInsert.setDouble(6, giaTien);
                            }
                            pstmtInsert.setString(7, hinhAnh);
                            pstmtInsert.executeUpdate();
                            Toast.makeText(BurgerDetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//                            String soluongtronggio = (String) MainActivity.tvSoLuongGioHang.getText();
//                            int soluong = Integer.parseInt(soluongtronggio)+1;
//                            BurgerActivity.tvSoLuongTrongGioDetail.setText(String.valueOf(soluong));
                        }



                    }

                    rs.close();
                    pstmtCheck.close();
                    connection_sql.SQLconnection().close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }



        });
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCart();
            }
        });
    }
    private void getAvgRate(){
        try {
            connection = new Connection_SQL();
            String getAvgRate = "SELECT AVG(RATE) as AVGRATE FROM DANHGIA WHERE IDHAMBURGER = ? ";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(getAvgRate);
            ptm.setInt(1,idHamburger);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                avg = rs.getDouble("AVGRATE");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private List<Rate> getListRate() {
        try{
            connection = new Connection_SQL();
            String getRate = "SELECT * FROM DANHGIA WHERE IDHAMBURGER = ? ";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(getRate);
            ptm.setInt(1,idHamburger);
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                int idDanhGia = rs.getInt("IDDANHGIA");
                int idUsers = rs.getInt("IDUSERS");
                int idHamburger = rs.getInt("IDHAMBURGER");
                double rate = rs.getDouble("RATE");
                String noiDung = rs.getString("NOIDUNG");
                String ngayDanhGia = rs.getString("NGAYDANHGIA");
                item = new Rate(idDanhGia,idUsers,idHamburger,rate,noiDung,ngayDanhGia);
                listRate.add(item);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return listRate;
    }


    private void showCart() {
        Intent intent = new Intent(BurgerDetailActivity.this,CartActivity.class);
        startActivity(intent);
        finish();
    }

    private void anhxa() {
        btnback=findViewById(R.id.btnBurgerDetailBack);
        btnCard=findViewById(R.id.btnCartBurgerDetail);
        imgBurger=findViewById(R.id.anhSanPhamBurgerDetail);
        tvTenSanPham=findViewById(R.id.tvTenSanPhamBurgerDetail);
        tvMoTaNgan=findViewById(R.id.tvMotaNganBurgerDetail);
        tvMoTaChiTiet=findViewById(R.id.tvMoTaChiTietBurgerDetail);
        tvGiaTien=findViewById(R.id.tvGiaTienBurgerDetail);
        btnAddToCart= findViewById(R.id.btnThemVaoGioHangBurgerDetail);
        tvTang=findViewById(R.id.tvTang);
        tvGiam=findViewById(R.id.tvGiam);
        tvSoLuong=findViewById(R.id.tvSoLuongBurgerDetail);
        rcv = findViewById(R.id.rcvDanhGiaInBurgerDetail);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rateDetail1 = findViewById(R.id.rateDetail1);
        rateDetail2 = findViewById(R.id.rateDetail2);
        rateDetail3 = findViewById(R.id.rateDetail3);
        rateDetail4 = findViewById(R.id.rateDetail4);
        rateDetail5 = findViewById(R.id.rateDetail5);
        imgShare = findViewById(R.id.img_share);
    }

}