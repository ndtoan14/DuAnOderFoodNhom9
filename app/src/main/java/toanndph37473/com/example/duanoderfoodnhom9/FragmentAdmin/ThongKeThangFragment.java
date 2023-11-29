package toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ThongKeThangAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.ThongKeThang;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class ThongKeThangFragment extends Fragment {

    private ThongKeThangAdapter adapter;
    private List<ThongKeThang> list ;
    private Button btn_ThongKeThang ;
    RecyclerView rcl;
    Connection_SQL connection_sql;
    EditText ed_thang,ed_nam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongke_thang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_ThongKeThang = view.findViewById(R.id.btn_thong_ke_thang);
        rcl = view.findViewById(R.id.recycle_thong_ke_thang);
        ed_nam = view.findViewById(R.id.ed_nam_thong_ke);
        ed_thang = view.findViewById(R.id.ed_thang_thong_ke);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        btn_ThongKeThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int thang = Integer.parseInt(ed_thang.getText().toString().trim());
                int nam = Integer.parseInt(ed_nam.getText().toString().trim());
                list = new ArrayList<>();
                getAllListThongKeThang(thang,nam);
                adapter = new ThongKeThangAdapter(list,getContext());
                adapter.notifyDataSetChanged();
                rcl.setAdapter(adapter);
            }
        });


    }
    public List<ThongKeThang> getAllListThongKeThang(int thang,int nam){

        try {
            connection_sql = new Connection_SQL();
            String sql = "select SUM(THONGKETHANG.DABAN) as TONGDABAN, TEN,GIATIEN,HINHANH\n" +
                    "from THONGKETHANG JOIN  HAMBURGER on THONGKETHANG.IDHAMBURGER = HAMBURGER.IDHAMBURGER\n" +
                    "where MONTH(NGAYBAN) = ? and YEAR(NGAYBAN)=? \n" +
                    "GROUP BY THONGKETHANG.IDHAMBURGER,TEN,GIATIEN,HINHANH";
            PreparedStatement statement = connection_sql.SQLconnection().prepareStatement(sql);
            statement.setInt(1,thang);
            statement.setInt(2,nam);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String tenSP = resultSet.getString("TEN");
                double giaSP = resultSet.getDouble("GIATIEN");
                int    tongDaBan = resultSet.getInt("TONGDABAN");
                String hinhAnh  = resultSet.getString("HINHANH");

                ThongKeThang thongKeThang = new ThongKeThang(tongDaBan,tenSP,hinhAnh,giaSP);
                list.add(thongKeThang);
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
