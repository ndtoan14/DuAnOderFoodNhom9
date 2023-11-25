package toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.AdminAdapter.DonHangBiHuyDetailAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.AdminAdapter.DonHangThanhCongDetailAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.DanhSachSanPhamBiHuy;
import toanndph37473.com.example.duanoderfoodnhom9.Model.DanhSachSanPhamThanhCong;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Invoice;
import toanndph37473.com.example.duanoderfoodnhom9.Model.ThongkeDonHangBiHuy;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class DonHangThanhCongDetailActivity extends AppCompatActivity {

    TextView tvHoTen, tvDiaChi,tvSoDienThoai, tvThoiGianDat,tvIdDonhang;
    RecyclerView rcv;
    Connection_SQL connection ;
    String ngayXacNhan,ten,diachi,soDienthoai;
    int idUsers;
    List<DanhSachSanPhamThanhCong> list =new ArrayList<>();
    DonHangThanhCongDetailAdapter adapter;
    CardView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_thanh_cong_detail);
        anhxa();
        getDuLieu();

        // lấy thông tin số điện thoại và địa chỉ
        connection = new Connection_SQL();
        String sql1 = "SELECT * FROM USERS WHERE IDUSERS = ?";
        try {
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql1);
            statement.setInt(1,idUsers);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                soDienthoai = resultSet.getString("SODIENTHOAI");
                diachi = resultSet.getString("DIACHI");
            }
            resultSet.close();
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvHoTen.setText(ten);
        tvThoiGianDat.setText(ngayXacNhan);
        tvSoDienThoai.setText(soDienthoai);
        tvDiaChi.setText(diachi);

        getAllDSSP_cua_MotDonHang();
        adapter =new DonHangThanhCongDetailAdapter(list,this);
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDuLieu() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Invoice invoice = (Invoice) bundle.getSerializable("itemDonHangThanhCong");
        idUsers = bundle.getInt("idUsers");
        ngayXacNhan = invoice.getNgayXuat();
        ten = invoice.getTenUsers();


    }
    public List<DanhSachSanPhamThanhCong> getAllDSSP_cua_MotDonHang() {
        connection = new Connection_SQL();
        String sql = "select IDDANHSACHDONHANGTHANHCONG,IDUSERS,DANHSACHDONHANGTHANHCONG.IDHAMBURGER,TENHAMBURGER,MADANHSACHSANPHAM,DANHSACHDONHANGTHANHCONG.SOLUONG,DANHSACHDONHANGTHANHCONG.GIATIEN,ANHSANPHAM " +
                "from DANHSACHDONHANGTHANHCONG " +
                "inner join HAMBURGER on DANHSACHDONHANGTHANHCONG.IDHAMBURGER=HAMBURGER.IDHAMBURGER " +
                "WHERE NGAYXACNHAN=?" ;
        try {
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setString(1,ngayXacNhan);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int IdDSSPTC = resultSet.getInt("IDDANHSACHDONHANGTHANHCONG");
                int IdUsers = resultSet.getInt("IDUSERS");
                int IdHamBurger =resultSet.getInt("IDHAMBURGER");
                int MaSSP = resultSet.getInt("MADANHSACHSANPHAM");
                int soLuong = resultSet.getInt("SOLUONG");
                double DonGia = resultSet.getDouble("GIATIEN");
                String AnhSp = resultSet.getString("ANHSANPHAM");
                String TenSP = resultSet.getString("TENHAMBURGER");

                DanhSachSanPhamThanhCong danhSachSanPhamThanhCong = new DanhSachSanPhamThanhCong(IdDSSPTC,IdUsers,IdHamBurger,MaSSP,soLuong,DonGia,AnhSp,TenSP);
                list.add(danhSachSanPhamThanhCong);
            }
            resultSet.close();
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    private void anhxa() {
        tvThoiGianDat = findViewById(R.id.tvThoiGianThanhCong);
        tvHoTen = findViewById(R.id.tvTenNguoiDatThanhCong);
        tvDiaChi = findViewById(R.id.tvDiaChiThanhCong);
        tvSoDienThoai = findViewById(R.id.tvSoDienThoaiThanhCong);
        tvIdDonhang = findViewById(R.id.tvIdDonHang);
        rcv = findViewById(R.id.rcvThongTinDonHangThanhCong);
        btnBack = findViewById(R.id.btnDonHangThanhCongDetailBack);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
    }
}