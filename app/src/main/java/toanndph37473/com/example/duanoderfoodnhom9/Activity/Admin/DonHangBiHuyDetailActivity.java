package toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.AdminAdapter.DonHangBiHuyDetailAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.DanhSachSanPhamBiHuy;
import toanndph37473.com.example.duanoderfoodnhom9.Model.ThongkeDonHangBiHuy;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class DonHangBiHuyDetailActivity extends AppCompatActivity {

    TextView tvHoTen, tvDiaChi,tvSoDienThoai, tvThoiGianDat,tvIdDonhang;
    RecyclerView rcv;
    Connection_SQL connection ;
    String ngayHuy,ten,diachi,soDienthoai;
    List<DanhSachSanPhamBiHuy> list =new ArrayList<>();
    DonHangBiHuyDetailAdapter adapter;
    CardView btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_bi_huy_detail);
        anhxa();
        getDuLieu();

        tvHoTen.setText(ten);
        tvThoiGianDat.setText(ngayHuy);
        tvSoDienThoai.setText(soDienthoai);
        tvDiaChi.setText(diachi);

        getAllDSSP_cua_MotDonHang();
        adapter =new DonHangBiHuyDetailAdapter(list,this);
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public List<DanhSachSanPhamBiHuy> getAllDSSP_cua_MotDonHang() {
        connection = new Connection_SQL();
        String sql = "select IDDANHSACHDONHANGBIHUY,IDUSERS,DANHSACHDONHANGBIHUY.IDHAMBURGER,TENHAMBURGER,MADANHSACHSANPHAM,DANHSACHDONHANGBIHUY.SOLUONG,DANHSACHDONHANGBIHUY.GIATIEN,ANHSANPHAM " +
                "from DANHSACHDONHANGBIHUY " +
                "inner join HAMBURGER on DANHSACHDONHANGBIHUY.IDHAMBURGER=HAMBURGER.IDHAMBURGER " +
                "WHERE NGAYHUY=?" ;
        try {
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setString(1,ngayHuy);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int IdDSSPBH = resultSet.getInt("IDDANHSACHDONHANGBIHUY");
                int IdUsers = resultSet.getInt("IDUSERS");
                int IdHamBurger =resultSet.getInt("IDHAMBURGER");
                int MaSSP = resultSet.getInt("MADANHSACHSANPHAM");
                int soLuong = resultSet.getInt("SOLUONG");
                double DonGia = resultSet.getDouble("GIATIEN");
                String AnhSp = resultSet.getString("ANHSANPHAM");
                String TenSP = resultSet.getString("TENHAMBURGER");

                DanhSachSanPhamBiHuy danhSachSanPhamBiHuy = new DanhSachSanPhamBiHuy(IdDSSPBH,IdUsers,IdHamBurger,TenSP,MaSSP,soLuong,DonGia,AnhSp);
                list.add(danhSachSanPhamBiHuy);
            }
            resultSet.close();
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void getDuLieu() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ThongkeDonHangBiHuy thongkeDonHangBiHuy = (ThongkeDonHangBiHuy) bundle.getSerializable("itemDonHangBiHuy");
        ngayHuy = thongkeDonHangBiHuy.getNgayUserHuyDonHang();
        ten = thongkeDonHangBiHuy.getTenUserHuyDonHang();
        diachi = thongkeDonHangBiHuy.getDiaChiUserHuyDonHang();
        soDienthoai = thongkeDonHangBiHuy.getSdtUserHuyDonHang();

    }
    private void anhxa() {
        tvThoiGianDat = findViewById(R.id.tvThoiGianBiHuy);
        tvHoTen = findViewById(R.id.tvTenNguoiDatBiHuy);
        tvDiaChi = findViewById(R.id.tvDiaChiBiHuy);
        tvSoDienThoai = findViewById(R.id.tvSoDienThoaiBiHuy);
        tvIdDonhang = findViewById(R.id.tvIdDonHang);
        rcv = findViewById(R.id.rcvThongTinDonHangBiHuy);
        btnback = findViewById(R.id.btnDonHangBiHuyDetailBack);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
    }
}