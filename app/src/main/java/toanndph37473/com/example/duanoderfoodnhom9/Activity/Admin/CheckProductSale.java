package toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.AdminAdapter.CheckProductAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class CheckProductSale extends AppCompatActivity {
    RecyclerView rcv;
    List<Hamburger> list = new ArrayList<>();
    public static List<Hamburger> listcheck = new ArrayList<>();
    CheckProductAdapter adapter;
    Button btnback;
    DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_product_sale);
        btnback=findViewById(R.id.btnbackk);
        rcv=findViewById(R.id.rcvCheckProduct);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        gridLayoutManager.setSpanCount(3);
        dao = new DAO();
        list = dao.getListHamBurgerTheoSoLuong();
        adapter = new CheckProductAdapter(list,this);
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listcheck = adapter.getCheckedProducts();
                Intent intent = new Intent(CheckProductSale.this,AddNewSaleProduct.class);
                startActivity(intent);
            }
        });

    }


}