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
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.RequestMoneyAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.HoaDonNaptien;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class RequestMoneyFragment extends Fragment {
    RecyclerView rcv;
    RequestMoneyAdapter adapter;
    List<HoaDonNaptien> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_money,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv=view.findViewById(R.id.rcvYeuCauNapTien);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getListYeuCauNap();
        adapter=new RequestMoneyAdapter(list,getContext());
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void getListYeuCauNap() {
        try {
            Connection_SQL connection_sql = new Connection_SQL();
            String sql = "SELECT * FROM HOADONNAPTIEN WHERE TRANGTHAI = 1";
            PreparedStatement pt = connection_sql.SQLconnection().prepareStatement(sql);
            ResultSet rs = pt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("IDHOADONNAPTIEN");
                String hinhanh = rs.getString("HINHANH");
                int trangThai = rs.getInt("TRANGTHAI");
                int iduser = rs.getInt("IDUSERS");
                double soTienNap = rs.getDouble("SOTIENNAP");
                HoaDonNaptien item = new HoaDonNaptien(id,iduser,trangThai,hinhanh,soTienNap);
                list.add(item);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

