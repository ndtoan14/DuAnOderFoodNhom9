package toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin.AddNewProductActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ProductAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class ProductFragment extends Fragment {
    private ProductAdapter adapter;
    private List<Hamburger> list ;
    CardView btnThemMoiSanPham;
    Connection_SQL connection;
    private RecyclerView rcv;
    DAO dao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product,container,false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnThemMoiSanPham=view.findViewById(R.id.btnThemMoiSanPham);
        rcv=view.findViewById(R.id.rcvListProduct);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setSpanCount(3);
        list=new ArrayList<>();
        getAllListProduct();
        adapter=new ProductAdapter(list, getContext());
        adapter.notifyDataSetChanged();
        rcv.setAdapter(adapter);

        btnThemMoiSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddNewProductActivity.class);
                startActivity(intent);
            }
        });

    }


    public List<Hamburger> getAllListProduct() {
        list.clear();
        try {
            Connection_SQL connection = new Connection_SQL();
            String sql = "SELECT * FROM HAMBURGER";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("IDHAMBURGER");
                String ten = resultSet.getString("TEN");
                String moTaNgan = resultSet.getString("MOTANGAN");
                String moTaChiTiet = resultSet.getString("MOTACHITIET");
                double giaTien = resultSet.getDouble("GIATIEN");
                String anhsql = resultSet.getString("HINHANH");
                int soLuong = resultSet.getInt("SOLUONG");
                int daBan = resultSet.getInt("DABAN");
                double giaKM = resultSet.getDouble("GIAKM");
                Hamburger hamburger = new Hamburger(id,ten,moTaNgan,moTaChiTiet,anhsql,soLuong,giaTien,daBan,giaKM);
                list.add(hamburger);


            }
            resultSet.close();
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public void onResume() {
        super.onResume();
        getAllListProduct();
        adapter.notifyDataSetChanged();
        dao = new DAO();        //dao hết ngày hết km
        dao.UpdateKM();
        dao.CallKM();
        dao.DeleteKM();
    }

    @Override
    public void onStart() {
        super.onStart();
        dao = new DAO();        //dao hết ngày hết km
        dao.UpdateKM();
        dao.CallKM();
        dao.DeleteKM();
    }
}
