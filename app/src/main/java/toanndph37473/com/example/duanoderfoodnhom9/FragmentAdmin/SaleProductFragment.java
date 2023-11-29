package toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin;

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
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin.AddNewSaleProduct;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ProductAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class SaleProductFragment extends Fragment {
    RecyclerView rcv;
    CardView btnAddNewSale;
    ProductAdapter adapter;
    List<Hamburger> list = new ArrayList<>();
    Connection_SQL connection_sql;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale_product,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv=view.findViewById(R.id.rcvDanhSachKhuyenMai);
        btnAddNewSale=view.findViewById(R.id.btnThemMoiKhuyenMai);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setSpanCount(3);
        getAllListSale();
        adapter= new ProductAdapter(list,getContext());
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        btnAddNewSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewSaleProduct.class);
                startActivity(intent);
            }
        });
    }

    private void getAllListSale() {
        try{
            connection_sql = new Connection_SQL();
            String sql = "SELECT * FROM HAMBURGER WHERE GIAKM > 0 AND SOLUONG > 0";
            PreparedStatement stm = connection_sql.SQLconnection().prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();
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
                list.add(hamburger);
            }
            resultSet.close();
            stm.close();
            connection_sql.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
