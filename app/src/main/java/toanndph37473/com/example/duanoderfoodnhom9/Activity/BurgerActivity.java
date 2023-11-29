package toanndph37473.com.example.duanoderfoodnhom9.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ProductAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class BurgerActivity extends AppCompatActivity {
    RecyclerView rcvBurger;
    ProductAdapter adapter;
    List<Hamburger> list =new ArrayList<>();
    CardView cardCartBurger;
    Connection_SQL connection;
    public static TextView tvSoLuongTrongGioDetail;
    DAO dao ;
    int soluongtronggio= 0;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger);
        rcvBurger = findViewById(R.id.rcvBurger);
        cardCartBurger=findViewById(R.id.cardCartBurger);
        tvSoLuongTrongGioDetail = findViewById(R.id.tvSoLuongTrongGioDetail);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        gridLayoutManager.setSpanCount(3);
        dao = new DAO();
        list = dao.getHamburgerList();
        adapter = new ProductAdapter(list,this);
        rcvBurger.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        cardCartBurger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BurgerActivity.this,CartActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void soLuongTrongGio(){
        try{
            Connection_SQL connection_sql = new Connection_SQL();
            String count = "SELECT COUNT(IDDANHSACHSANPHAM) AS SOLUONGINCART FROM DANHSACHSANPHAM";
            PreparedStatement ptm = connection_sql.SQLconnection().prepareStatement(count);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                soluongtronggio = rs.getInt("SOLUONGINCART");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        soLuongTrongGio();
        tvSoLuongTrongGioDetail.setText(""+soluongtronggio);
        super.onStart();
    }

    @Override
    protected void onResume() {
        soLuongTrongGio();
        tvSoLuongTrongGioDetail.setText(""+soluongtronggio);
        super.onResume();
    }
}