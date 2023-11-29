package toanndph37473.com.example.duanoderfoodnhom9.Fragment;

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
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.BienDongSoDuAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.HoaDonNaptien;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class RechargeNoticeFragment extends Fragment {
    RecyclerView rcv;
    BienDongSoDuAdapter adapter ;
    List<HoaDonNaptien> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recharge_notice,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcvBienDongSoDu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getListHoaDon();
        adapter = new BienDongSoDuAdapter(list,getContext());
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void getListHoaDon() {
        try {
            Connection_SQL connection_sql = new Connection_SQL();
            String sql = "SELECT * FROM HOADONNAPTIEN WHERE IDUSERS = ?";
            PreparedStatement statement = connection_sql.SQLconnection().prepareStatement(sql);
            statement.setInt(1,UserSession.getCurrentUser(getContext()).getIdUser());
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("IDHOADONNAPTIEN");
                String hinhanh = rs.getString("HINHANH");
                int trangThai = rs.getInt("TRANGTHAI");
                int iduser = rs.getInt("IDUSERS");
                double soTienNap = rs.getDouble("SOTIENNAP");
                HoaDonNaptien hoaDonNaptien = new HoaDonNaptien(id,iduser,trangThai,hinhanh,soTienNap);
                list.add(hoaDonNaptien);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
