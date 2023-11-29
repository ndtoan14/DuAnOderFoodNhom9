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

import toanndph37473.com.example.duanoderfoodnhom9.AdminAdapter.DonHangBiHuyAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.ThongkeDonHangBiHuy;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class ThongKeDonHangBiHuyFragment extends Fragment {

    private DonHangBiHuyAdapter adapter;
    List<ThongkeDonHangBiHuy> list = new ArrayList<>();
    Connection_SQL connection_sql;
    RecyclerView rcl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_don_hang_bi_huy,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rcl = view.findViewById(R.id.rcvDonHangBiHuy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        adapter = new DonHangBiHuyAdapter(getContext(),getAllDonHangBiHuy());
        rcl.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public List<ThongkeDonHangBiHuy> getAllDonHangBiHuy(){

        try {
            connection_sql = new Connection_SQL();
            String sql ="select TENUSERS,SODIENTHOAI,DIACHI,NGAYXUAT,REPLACE(SUBSTRING(NOIDUNG, 36, 2000), N'Số tiền đơn này sẽ được hoàn lại vào ví tiền', '') as LIDO,THONGBAO.TONGTIEN from THONGBAO  " +
                    "inner join HOADON  on HOADON.TONGTIEN=THONGBAO.TONGTIEN and HOADON.IDUSERS=THONGBAO.IDUSERS inner join USERS on HOADON.IDUSERS = USERS.IDUSERS " +
                    "where HOADON.TRANGTHAI = ? and THONGBAO.TRANGTHAI = ? ORDER BY NGAYXUAT ASC";
            PreparedStatement statement = connection_sql.SQLconnection().prepareStatement(sql);
            statement.setInt(1,3);
            statement.setInt(2,2);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String ten = resultSet.getString("TENUSERS");
                String sodienthoai = resultSet.getString("SODIENTHOAI");
                String diachi = resultSet.getString("DIACHI");
                String ngayxuat = resultSet.getString("NGAYXUAT");
                double dongia = Double.parseDouble(resultSet.getString("TONGTIEN"));
                String lido = resultSet.getString("LIDO");

                ThongkeDonHangBiHuy thongkeDonHangBiHuy = new ThongkeDonHangBiHuy(ten,sodienthoai,diachi,ngayxuat,dongia,lido);
                list.add(thongkeDonHangBiHuy);
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
