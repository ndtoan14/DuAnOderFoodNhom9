package toanndph37473.com.example.duanoderfoodnhom9.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ProductReviewsAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Notification;
import toanndph37473.com.example.duanoderfoodnhom9.Model.OrderHistory;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class ProductReviewsActivity extends AppCompatActivity {
    Notification notification = new Notification();
    RecyclerView recyclerView;
    ProductReviewsAdapter adapter;
    List<OrderHistory> list = new ArrayList<>();
    OrderHistory orderHistory = new OrderHistory();
    CardView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_reviews);
        btnBack = findViewById(R.id.btnBackinProDuctReviews);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        notification = (Notification) bundle.getSerializable("notification");
        recyclerView = findViewById(R.id.rcvChiTietDHDaDat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getAllOrderHistory();
        adapter = new ProductReviewsAdapter(list,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private List<OrderHistory> getAllOrderHistory() {
        try{
            Connection_SQL connection_sql = new Connection_SQL();
            String truyVan = "SElECT * FROM LICHSUDATHANG WHERE IDUSERS = ? AND IDTHONGBAO = ?";
            PreparedStatement ptm = connection_sql.SQLconnection().prepareStatement(truyVan);
            ptm.setInt(1,notification.getIdUsers());
            ptm.setInt(2,notification.getIdThongBao());
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                int idLichSu = rs.getInt("IDLICHSUDATHANG");
                int idUsers = rs.getInt("IDUSERS");
                int idThongBao = rs.getInt("IDTHONGBAO");
                int idHamburger = rs.getInt("IDHAMBURGER");
                int maDSSP = rs.getInt("MADANHSACHSANPHAM");
                String tenHamburger = rs.getString("TENHAMBURGER");
                int soluong = rs.getInt("SOLUONG");
                double giaTien = rs.getDouble("GIATIEN");
                String anhSanPham = rs.getString("ANHSANPHAM");
                orderHistory = new OrderHistory(idLichSu,idUsers,idThongBao,idHamburger,maDSSP,tenHamburger,anhSanPham,soluong,giaTien);
                list.add(orderHistory);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


}