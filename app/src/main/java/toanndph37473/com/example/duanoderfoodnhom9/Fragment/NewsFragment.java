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

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.NewsAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Interface.Service;
import toanndph37473.com.example.duanoderfoodnhom9.R;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.News;


public class NewsFragment extends Fragment {
    RecyclerView rcvDanhSachTinTuc;
    List<News> list = new ArrayList<>();
    Connection_SQL connection;
    NewsAdapter adapter ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvDanhSachTinTuc=view.findViewById(R.id.rcvDanhsachTInTucUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getAllListNews();
        adapter= new NewsAdapter(getContext(), list, new Service() {
            @Override
            public void addp(int i) {

            }

            @Override
            public void subtraction(int i) {

            }

            @Override
            public void deleteincart(int i) {

            }

            @Override
            public void onLikeClick(int i) { // xu li like
                // Lấy item tại vị trí position
                News item = list.get(i);
                // Đổi trạng thái Like của item
                item.setIsliked(!item.isIsliked());
                // Cập nhật item trong danh sách
                list.set(i, item);

                // Cập nhật adapter
                adapter.notifyItemChanged(i);

            }
        });
        rcvDanhSachTinTuc.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
