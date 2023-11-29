package toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.AdminAdapter.TopAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Top;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class TopFragment extends Fragment {
    TopAdapter topAdapter;
    RecyclerView rcv_top;
    List<Top> list = new ArrayList<>();
    Connection_SQL connection_sql;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcv_top = view.findViewById(R.id.rcv_top);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);


        topAdapter = new TopAdapter(list_top(),getContext());
        rcv_top.setAdapter(topAdapter);
        topAdapter.notifyDataSetChanged();
    }


    public List<Top> list_top(){

        try {
            connection_sql =new Connection_SQL();
            String sql_top = "select  HOADON.IDUSERS,USERS.TEN,USERS.EMAIL,USERS.SODIENTHOAI,USERS.DIACHI,USERS.HINHANH,SUM(TONGTIEN) as ttien \n" +
                    "FROM HOADON INNER JOIN USERS \n" +
                    "ON USERS.IDUSERS = HOADON.IDUSERS \n" +
                    "GROUP BY HOADON.IDUSERS,USERS.SODIENTHOAI,USERS.TEN,USERS.HINHANH,USERS.EMAIL,USERS.DIACHI ORDER BY SUM(TONGTIEN) DESC";
            PreparedStatement statement = connection_sql.SQLconnection().prepareStatement(sql_top);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("IDUSERS");
                String ten = resultSet.getString("TEN");
                String email = resultSet.getString("EMAIL");
                String sdt = resultSet.getString("SODIENTHOAI");
                String diachi = resultSet.getString("DIACHI");
                String hinhanh = resultSet.getString("HINHANH");
                Double ttien = resultSet.getDouble("ttien");
                Top top = new Top(id,ten,email,sdt,diachi,hinhanh,ttien);
                list.add(top);
            }
            resultSet.close();
            statement.close();
            connection_sql.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return list;
    }

}
