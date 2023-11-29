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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin.AddNewNewsActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.NewsAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.News;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class NewsFragmentAdmin extends Fragment {
    CardView btnThemMoiTinTuc;
    RecyclerView rcvDanhSachTinTuc;
    List<News> list = new ArrayList<>();
    Connection_SQL connection;
    NewsAdapter adapter ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_admin,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnThemMoiTinTuc=view.findViewById(R.id.btnThemMoiTinTuc);
        rcvDanhSachTinTuc=view.findViewById(R.id.rcvDanhSachTinTucAdmin);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getAllListNews();
        adapter= new NewsAdapter(getContext(),list);
        rcvDanhSachTinTuc.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        btnThemMoiTinTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewNewsActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getAllListNews() {
        try{
            connection = new Connection_SQL();
            String sql = "SELECT * FROM TINTUC";
            PreparedStatement stm = connection.SQLconnection().prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                int idTinTuc = resultSet.getInt("IDTINTUC");
                String tieude = resultSet.getString("TIEUDE");
                String noiDung = resultSet.getString("NOIDUNG");
                String hinhAnh = resultSet.getString("HINHANH");
                String ngaydangtin = resultSet.getString("NGAYDANGTIN");
                int likeCount = resultSet.getInt("LIKECOUNT");
                int commentCount = resultSet.getInt("COMMENTCOUNT");
                int shareCount = resultSet.getInt("SHARECOUNT");
                News news = new News(idTinTuc,tieude,noiDung,hinhAnh,ngaydangtin,likeCount,commentCount,shareCount);
                list.add(news);
            }
            resultSet.close();
            stm.close();
            connection.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
