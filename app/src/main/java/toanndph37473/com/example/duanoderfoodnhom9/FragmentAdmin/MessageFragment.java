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

import toanndph37473.com.example.duanoderfoodnhom9.AdminAdapter.ListMessUserAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class MessageFragment extends Fragment {
    RecyclerView rcv ;
    ListMessUserAdapter adapter;
    List<Users> list = new ArrayList<>();
    int idUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_mess_users,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcvListMessUsers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getListUser();
        adapter = new ListMessUserAdapter(list,getContext());
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getListUser() {
        try{
            Connection_SQL connection_sql = new Connection_SQL();
            String sql = "SELECT * FROM USERS WHERE QUYEN <> 0";
            PreparedStatement ptm = connection_sql.SQLconnection().prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                 idUser = rs.getInt("IDUSERS");
                String email = rs.getString("EMAIL");
                String ten = rs.getString("TEN");
                String sdt = rs.getString("SODIENTHOAI");
                String diaChi = rs.getString("DIACHI");
                String matKhau = rs.getString("MATKHAU");
                Double viTien = rs.getDouble("VITIEN");
                int quyen = rs.getInt("QUYEN");
                String avt = rs.getString("HINHANH");
                Users u = new Users(idUser,ten,email,sdt,diaChi,matKhau,quyen,viTien,avt);
                list.add(u);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    public void getListtn() {
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("conversations").child(idUser+"_admin").child("messages");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
}
