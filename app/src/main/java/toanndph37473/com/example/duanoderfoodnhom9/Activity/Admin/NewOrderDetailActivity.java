
package toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.PayAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.ListProductCart;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Notification;
import toanndph37473.com.example.duanoderfoodnhom9.Model.PayBill;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;


public class NewOrderDetailActivity extends AppCompatActivity {
    int idThanhToan, idUsers, madanhsachsanpham,trangthai;
    double tongTien;
    String ngaydat;
    String tenUser, diaChi,soDienThoai;
    Connection_SQL connection ;
    List<ListProductCart> list = new ArrayList<>();
    TextView tvHoTen, tvDiaChi,tvSoDienThoai, tvThoiGianDat,tvTongTienHang,tvIdDonhang;
    Button btnXacNhanDH,btnHuyDonHang;
    RecyclerView rcv;
    PayAdapter adapter;
    Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_detail);
        anhxa();
        getDuLieu();
        getAllListPay();
        adapter = new PayAdapter(list,this);
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getUsers();
        tvHoTen.setText("Họ và Tên : "+users.getTen());
        tvDiaChi.setText("Địa Chỉ : "+users.getDiaChi());
        tvSoDienThoai.setText("Số Điện Thoại :"+users.getSoDienThoai());
        tvThoiGianDat.setText("Thời Điểm Đặt : "+ngaydat);
        tvTongTienHang.setText("Tổng Tiền Thanh Toán : "+tongTien);
        tvIdDonhang.setText("Đơn hàng số : "+idThanhToan);
        //xu li su kien nut
        btnXacNhanDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xacNhanDH();

            }
        });
        btnHuyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huyDonHang();


            }
        });
    }

    private void huyDonHang() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Bạn có chắc chắn muốn huỷ đơn ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog dialog1 = new Dialog(NewOrderDetailActivity.this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.dialog_lido);
                Window window = dialog1.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams winLayoutParams = window.getAttributes();
                winLayoutParams.gravity = Gravity.CENTER;
                window.setAttributes(winLayoutParams);
                TextInputLayout edLiDo;
                Button btnXacNhanHuyDon;
                edLiDo = dialog1.findViewById(R.id.edLiDoHuy);
                btnXacNhanHuyDon = dialog1.findViewById(R.id.btnXacNhanHuyDon);
                btnXacNhanHuyDon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String lido = edLiDo.getEditText().getText().toString().trim();
                        if (lido == null) {
                            edLiDo.setError("Vui lòng điền lí do huỷ cho khách hàng");
                        } else {
                            edLiDo.setError(null);
                            try {
                                connection = new Connection_SQL();
                                String sql2 = "SELECT TEN FROM USERS WHERE IDUSERS = ?";
                                PreparedStatement stm = connection.SQLconnection().prepareStatement(sql2);
                                stm.setInt(1, idUsers);
                                ResultSet rs = stm.executeQuery();
                                String tenUsers = "";
                                if (rs.next()) {
                                    tenUsers = rs.getString("TEN");
                                }

                                // lấy ngày hiện tại
                                Calendar calendar = Calendar.getInstance();
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                int minute = calendar.get(Calendar.MINUTE);
                                int second = calendar.get(Calendar.SECOND);
                                String currentTime ;
                                if ((month+1)<10){
                                    currentTime =  year + "-" +"0"+(month + 1) + "-" + day + " " + hour + ":" + minute + ":" + second;
                                }else {
                                    currentTime =  year + "-" + (month + 1) + "-" + day + " " + hour + ":" + minute + ":" + second;
                                }

                                String sqll = "INSERT INTO HOADON (IDTHANHTOAN,IDUSERS,TENUSERS,TONGTIEN,TRANGTHAI,NGAYXUAT) VALUES (?, ?, ?, ?, ?, ?)";
                                PreparedStatement sttm = connection.SQLconnection().prepareStatement(sqll);
                                sttm.setInt(1, idThanhToan);
                                sttm.setInt(2, idUsers);
                                sttm.setString(3, tenUsers);
                                sttm.setDouble(4, tongTien);
                                sttm.setInt(5, trangthai + 2);
                                int test = trangthai + 2;
                                Log.e("TAG", "test = " + test);
                                sttm.setString(6,currentTime);
                                int rowsUpdated = sttm.executeUpdate();
                                if (rowsUpdated > 0) {
                                    // xu li xoa du lieu trong thanh toan
                                    Toast.makeText(getApplicationContext(), "Xac nhan huy thanh cong", Toast.LENGTH_SHORT).show();
                                    // thuc hien xoa khoi list torng database
                                    Connection_SQL connection1 = new Connection_SQL();
                                    String sqlDelete = "DELETE FROM THANHTOAN WHERE IDTHANHTOAN = ?";
                                    PreparedStatement p = connection1.SQLconnection().prepareStatement(sqlDelete);
                                    p.setInt(1, idThanhToan);
                                    int rsDelete = p.executeUpdate();
                                    if (rsDelete > 0) {
                                        Toast.makeText(getApplicationContext(), "Xoa trong SQL thanh cong", Toast.LENGTH_SHORT).show();
                                    }

                                    // Xử lý select*from DANHSACHDONHANG để lấy dữ liệu chuyển sang DANHSACHDONHANGBIHUY

                                    Connection_SQL c3 = new Connection_SQL();
                                    String sql3 = "SELECT * FROM DANHSACHDONHANG WHERE IDUSERS = ? AND MADANHSACHSANPHAM = ?";
                                    PreparedStatement st3 = c3.SQLconnection().prepareStatement(sql3);
                                    st3.setInt(1,idUsers);
                                    st3.setInt(2,madanhsachsanpham);
                                    ResultSet resultSet = st3.executeQuery();
                                    while (resultSet.next()){
                                        int idUsres = resultSet.getInt("IDUSERS");
                                        int idHamburger = resultSet.getInt("IDHAMBURGER");
                                        int maDSSP = resultSet.getInt("MADANHSACHSANPHAM");
                                        int soLuong = resultSet.getInt("SOLUONG");
                                        double giaTien = resultSet.getDouble("GIATIEN");
                                        String tenhamburger = resultSet.getString("TENHAMBURGER");
                                        String anhHamburger = resultSet.getString("ANHSANPHAM");
                                        Connection_SQL c4 = new Connection_SQL();

                                        String sql4 = "INSERT INTO DANHSACHDONHANGBIHUY(IDUSERS, IDHAMBURGER, MADANHSACHSANPHAM, TENHAMBURGER, SOLUONG, GIATIEN, ANHSANPHAM,NGAYHUY) VALUES (?,?,?,?,?,?,?,?)";
                                        PreparedStatement st4 = c4.SQLconnection().prepareStatement(sql4);
                                        st4.setInt(1,idUsres);
                                        st4.setInt(2,idHamburger);
                                        st4.setInt(3,maDSSP);
                                        st4.setString(4,tenhamburger);
                                        st4.setInt(5,soLuong);
                                        st4.setDouble(6,giaTien);
                                        st4.setString(7,anhHamburger);
                                        st4.setString(8,currentTime);
                                        int insert4 = st4.executeUpdate();
                                        if (insert4>0){
                                            Toast.makeText(NewOrderDetailActivity.this, "Thêm thành công DSDHBH", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    // xử lý xóa danh sách sản phẩm sau khi hủy đơn thành công
                                    Connection_SQL c1 = new Connection_SQL();
                                    String sql1 = "DELETE FROM DANHSACHDONHANG WHERE IDUSERS = ? AND MADANHSACHSANPHAM = ?";
                                    PreparedStatement statement1 = c1.SQLconnection().prepareStatement(sql1);
                                    statement1.setInt(1,idUsers);
                                    statement1.setInt(2,madanhsachsanpham);
                                    int deleteDSSP = statement1.executeUpdate();
                                    if (deleteDSSP>0){
                                        Toast.makeText(getApplicationContext(), "Xóa DSDH thành công", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Xóa DSDH không thành công", Toast.LENGTH_SHORT).show();
                                    }

                                    //xu li thong bao
                                    Connection_SQL connect = new Connection_SQL();
                                    String sqlThongBao = "INSERT INTO THONGBAO (IDTHONGBAO,IDUSERS,TRANGTHAI,NOIDUNG,TONGTIEN) VALUES (?, ?, ?, ?, ?)";
                                    PreparedStatement pst = connect.SQLconnection().prepareStatement(sqlThongBao);
                                    pst.setInt(1, idThanhToan);
                                    pst.setInt(2, idUsers);
                                    pst.setInt(3, 2);
                                    pst.setString(4, "Đơn hàng của bạn đã bị huỷ, lí do : " + lido + "\nSố tiền đơn này sẽ được hoàn lại vào ví tiền");
                                    pst.setDouble(5, tongTien);
                                    int rsThongBao1 = pst.executeUpdate();
                                    if (rsThongBao1 > 0) {
                                        Connection_SQL connectionn = new Connection_SQL();
                                        String sqlSelect = "SELECT * FROM THONGBAO WHERE IDTHONGBAO = ?";
                                        PreparedStatement ptm = connectionn.SQLconnection().prepareStatement(sqlSelect);
                                        ptm.setInt(1, idThanhToan);
//                                            ptm.setInt(2, payBill.getIdUsers());
                                        ResultSet rsSelect = ptm.executeQuery();
                                        if (rsSelect.next()) {
                                            int idThongBao = rsSelect.getInt("IDTHONGBAO");
                                            int idU = rsSelect.getInt("IDUSERS");
                                            String noidungg = rsSelect.getString("NOIDUNG");
                                            double tongt = rsSelect.getDouble("TONGTIEN");
                                            String stringIdthongbao = String.valueOf(idThongBao);
                                            DatabaseReference rf = FirebaseDatabase.getInstance().getReference("ThongBao").child(String.valueOf(idU));
                                            if (stringIdthongbao != null) {
                                                Notification notification = new Notification(idThongBao, idU, 2, noidungg, tongt, ngaydat);
                                                rf.child(stringIdthongbao).setValue(notification);
                                            } else {
                                                Log.e("Loi thong bao", "id thong bao khong ton tai");
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Xac nhan that bai", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void xacNhanDH() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Bạn có chắc chắn muốn xác nhận đơn này ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenUsers = "";
                try {
                    connection = new Connection_SQL();
                    String sql2 = "SELECT TEN FROM USERS WHERE IDUSERS = ?";
                    PreparedStatement stm = connection.SQLconnection().prepareStatement(sql2);
                    stm.setInt(1, idUsers);
                    ResultSet rs = stm.executeQuery();
                    if (rs.next()) {
                        tenUsers = rs.getString("TEN");
                    }

                    // lấy ngày hiện tại
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);
                    String currentTime ;
                    if ((month+1)<10){
                        currentTime =  year + "-" +"0"+(month + 1) + "-" + day + " " + hour + ":" + minute + ":" + second;
                    }else {
                        currentTime =  year + "-" + (month + 1) + "-" + day + " " + hour + ":" + minute + ":" + second;
                    }

                    String sqll = "INSERT INTO HOADON (IDTHANHTOAN,IDUSERS,TENUSERS,TONGTIEN,TRANGTHAI,NGAYXUAT) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement sttm = connection.SQLconnection().prepareStatement(sqll);
                    sttm.setInt(1, idThanhToan);
                    sttm.setInt(2, idUsers);
                    sttm.setString(3, tenUsers);
                    sttm.setDouble(4,tongTien);
                    sttm.setInt(5, trangthai + 1);
                    sttm.setString(6, currentTime);
                    int rowsUpdated = sttm.executeUpdate();
                    if (rowsUpdated > 0) {
                        // xu li xoa du lieu trong thanh toan
                        Toast.makeText(getApplicationContext(), "Xac nhan thanh cong", Toast.LENGTH_SHORT).show();
                        //Kiem tra neu thanh toan bang Zalo pay roi thi khong tru tien trong vi nua
                        String phuongThucTT = "SELECT * FROM DANHSACHDONHANG WHERE IDUSERS = ? ";
                        PreparedStatement preparedStatement = connection.SQLconnection().prepareStatement(phuongThucTT);
                        preparedStatement.setInt(1,idUsers);
                        ResultSet rsxm = preparedStatement.executeQuery();
                        if(rsxm.next()){
                            int pttt = rsxm.getInt("PHUONGTHUCTHANHTOAN");
                            if(pttt!=1 ){
                                // update vi tien tren firebase
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS").child(String.valueOf(idUsers)).child("viTien");
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        double vif = snapshot.getValue(double.class);
                                        double visau = vif - tongTien;
                                        ref.setValue(visau);
                                        try{
                                            connection = new Connection_SQL();
                                            String udViTien = "UPDATE USERS " +
                                                    "SET VITIEN = ? " +
                                                    "WHERE IDUSERS = ?";
                                            PreparedStatement ptm = connection.SQLconnection().prepareStatement(udViTien);
                                            ptm.setDouble(1,visau);
                                            ptm.setInt(2,idUsers);
                                            ptm.executeUpdate();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }


                        // sau khi thanh toan thanh cong, update ban ra + solunog con lai cua sp
                        Connection_SQL c = new Connection_SQL();
                        String sql = "SELECT * FROM DANHSACHDONHANG WHERE IDUSERS = ?";
                        PreparedStatement statement = c.SQLconnection().prepareStatement(sql);
                        statement.setInt(1,idUsers);
                        ResultSet rls = statement.executeQuery();
                        while (rls.next()) {
                            int idhamburger = rls.getInt("IDHAMBURGER");
                            int soLuong = rls.getInt("SOLUONG");
                            String capnhatbanra = "UPDATE HAMBURGER " +
                                    "SET DABAN=DABAN+?, SOLUONG=SOLUONG-?" +
                                    "WHERE IDHAMBURGER = ?";
                            PreparedStatement pst = c.SQLconnection().prepareStatement(capnhatbanra);
                            pst.setInt(1,soLuong);
                            pst.setInt(2,soLuong);
                            pst.setInt(3,idhamburger);
                            int rss = pst.executeUpdate();

                            if(rss>0){
                                Log.e("UpdateBanRa", "Update so luong ban ra thanh cong");
                                Connection_SQL connection_sql = new Connection_SQL();
                                String sql1 = "INSERT INTO THONGKETHANG(IDUSERS,IDHAMBURGER,DABAN,NGAYBAN) values(?,?,?,?)";
                                PreparedStatement statement1 = connection_sql.SQLconnection().prepareStatement(sql1);
                                statement1.setInt(1,idUsers);
                                statement1.setInt(2,idhamburger);
                                statement1.setInt(3,soLuong);
                                statement1.setString(4,currentTime);

                                int insert_thongKeThang = statement1.executeUpdate();
                                if (insert_thongKeThang>0){
                                    Log.e("ThongKethang", "them thong ke thang thanh cong");
                                }
                            }
                        }

                        // Xử lý select*from DANHSACHDONHANG để lấy dữ liệu chuyển sang DANHSACHDONHANGTHANHCONG

                        Connection_SQL c3 = new Connection_SQL();
                        String sql3 = "SELECT * FROM DANHSACHDONHANG WHERE IDUSERS = ? AND MADANHSACHSANPHAM = ?";
                        PreparedStatement st3 = c3.SQLconnection().prepareStatement(sql3);
                        st3.setInt(1,idUsers);
                        st3.setInt(2,madanhsachsanpham);
                        ResultSet resultSet = st3.executeQuery();
                        while (resultSet.next()){
                            int idUsres = resultSet.getInt("IDUSERS");
                            int idHamburger = resultSet.getInt("IDHAMBURGER");
                            int maDSSP = resultSet.getInt("MADANHSACHSANPHAM");
                            int soLuong = resultSet.getInt("SOLUONG");
                            double giaTien = resultSet.getDouble("GIATIEN");
                            String tenhamburger = resultSet.getString("TENHAMBURGER");
                            String anhHamburger = resultSet.getString("ANHSANPHAM");
                            Connection_SQL c4 = new Connection_SQL();

                            String sql4 = "INSERT INTO DANHSACHDONHANGTHANHCONG(IDUSERS, IDHAMBURGER, MADANHSACHSANPHAM, TENHAMBURGER, SOLUONG, GIATIEN, ANHSANPHAM,NGAYXACNHAN) VALUES (?,?,?,?,?,?,?,?)";
                            PreparedStatement st4 = c4.SQLconnection().prepareStatement(sql4);
                            st4.setInt(1,idUsres);
                            st4.setInt(2,idHamburger);
                            st4.setInt(3,maDSSP);
                            st4.setString(4,tenhamburger);
                            st4.setInt(5,soLuong);
                            st4.setDouble(6,giaTien);
                            st4.setString(7,anhHamburger);
                            st4.setString(8,currentTime);
                            int insert4 = st4.executeUpdate();
                            if (insert4>0){
                                Toast.makeText(NewOrderDetailActivity.this, "Thêm thành công DSDHTC", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // xử lý xóa danh sách Don Hang sau khi xác nhận thành công
                        Connection_SQL c1 = new Connection_SQL();
                        String sql1 = "DELETE FROM DANHSACHDONHANG WHERE IDUSERS = ? AND MADANHSACHSANPHAM = ?";
                        PreparedStatement statement1 = c1.SQLconnection().prepareStatement(sql1);
                        statement1.setInt(1,idUsers);
                        statement1.setInt(2,madanhsachsanpham);
                        int deleteDSSP = statement1.executeUpdate();
                        if (deleteDSSP>0){
                            Toast.makeText(getApplicationContext(), "Xóa DSDH thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Xóa DSDH không thành công", Toast.LENGTH_SHORT).show();
                        }

                        // thuc hien xoa khoi list torng database
                        Connection_SQL connection1 = new Connection_SQL();
                        String sqlDelete = "DELETE FROM THANHTOAN WHERE IDTHANHTOAN = ?";
                        PreparedStatement p = connection1.SQLconnection().prepareStatement(sqlDelete);
                        p.setInt(1, idThanhToan);
                        int rsDelete = p.executeUpdate();
                        if (rsDelete > 0) {
                            Toast.makeText(getApplicationContext(), "Xoa trong SQL thanh cong", Toast.LENGTH_SHORT).show();
                        }
                        // xu li thong bao
                        Connection_SQL connect = new Connection_SQL();
                        String sqlThongBao = "INSERT INTO THONGBAO (IDTHONGBAO,IDUSERS,TRANGTHAI,NOIDUNG,TONGTIEN) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement pst = connect.SQLconnection().prepareStatement(sqlThongBao);
                        pst.setInt(1, idThanhToan);
                        pst.setInt(2, idUsers);
                        pst.setInt(3, 1);
                        pst.setString(4, "Đơn hàng của bạn đã được xác nhận!\nVui lòng đợt 10-15 phút chế biến và sau đó shipper sẽ giao đến cho bạn");
                        pst.setDouble(5, tongTien);
                        int rsThongBao1 = pst.executeUpdate();
                        if (rsThongBao1 > 0) {
                            Connection_SQL connectionn = new Connection_SQL();
                            String sqlSelect = "SELECT * FROM THONGBAO WHERE IDTHONGBAO = ?";
                            PreparedStatement ptm = connectionn.SQLconnection().prepareStatement(sqlSelect);
                            ptm.setInt(1, idThanhToan);
//                                    ptm.setInt(2,payBill.getIdUsers());
                            ResultSet rsSelect = ptm.executeQuery();
                            if (rsSelect.next()) {
                                int idThongBao = rsSelect.getInt("IDTHONGBAO");
                                int idU = rsSelect.getInt("IDUSERS");
                                String noidungg = rsSelect.getString("NOIDUNG");
                                double tongt = rsSelect.getDouble("TONGTIEN");
                                String stringIdthongbao = String.valueOf(idThongBao);
                                DatabaseReference rf = FirebaseDatabase.getInstance().getReference("ThongBao").child(String.valueOf(idU));
                                if (stringIdthongbao != null) {
                                    Notification notification = new Notification(idThongBao, idU, 1, noidungg, tongt, ngaydat);
                                    rf.child(stringIdthongbao).setValue(notification);
                                } else {
                                    Log.e("Loi thong bao", "id thong bao khong ton tai");
                                }
                                // INSERT LICHSUDATHANG
                                for(ListProductCart cart : list){
                                    String insertLSDH = "INSERT INTO LICHSUDATHANG (IDUSERS, IDHAMBURGER, MADANHSACHSANPHAM, TENHAMBURGER, SOLUONG, GIATIEN, ANHSANPHAM,IDTHONGBAO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                                    PreparedStatement pstmtInsert = connection.SQLconnection().prepareStatement(insertLSDH);
                                    pstmtInsert.setInt(1, cart.getIdUsers());
                                    pstmtInsert.setInt(2, cart.getIdHamburger());
                                    pstmtInsert.setInt(3, cart.getMaDanhSachSanPham());
                                    pstmtInsert.setString(4, cart.getTenHamburger());
                                    pstmtInsert.setInt(5, cart.getSoLuong());
                                    pstmtInsert.setDouble(6, cart.getGiaTien());
                                    pstmtInsert.setString(7, cart.getAnhSanPham());
                                    pstmtInsert.setInt(8,idThongBao);
                                    int rowDSDH = pstmtInsert.executeUpdate();
                                    if (rowDSDH > 0) {
                                        Log.e("insertDHDH", "insert LICH SU DAT HANG THANH CONG");
                                    }
                                }

                            }
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Xac nhan that bai", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }


    public void getUsers() {
        try{
            connection = new Connection_SQL();
            String getUser = "SELECT * FROM USERS WHERE IDUSERS = ?";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(getUser);
            ptm.setInt(1,idUsers);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                tenUser = rs.getString("TEN");
                diaChi = rs.getString("DIACHI");
                soDienThoai = rs.getString("SODIENTHOAI");
                users = new Users(tenUser,soDienThoai,diaChi);
            }
            rs.close();
            ptm.close();
            connection.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<ListProductCart> getAllListPay() {

        try {
            connection = new Connection_SQL();
            String sql = "SELECT * FROM DANHSACHDONHANG WHERE IDUSERS = ? AND MADANHSACHSANPHAM = ?";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setInt(1, idUsers);
            statement.setInt(2,madanhsachsanpham);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("IDDANHSACHDONHANG");
                int idusers = resultSet.getInt("IDUSERS");
                int idhamburger = resultSet.getInt("IDHAMBURGER");
                String truyvan = "SELECT GIAKM FROM HAMBURGER WHERE IDHAMBURGER = ?";
                PreparedStatement preparedStatement = connection.SQLconnection().prepareStatement(truyvan);
                preparedStatement.setInt(1,idhamburger);
                ResultSet resultSet1 = preparedStatement.executeQuery();
                double giaKM = 0;
                if(resultSet1.next()){
                    giaKM = resultSet1.getDouble("GIAKM");
                }
                int maDanhSachSanPham = resultSet.getInt("MADANHSACHSANPHAM");
                String tenhamburger = resultSet.getString("TENHAMBURGER");
                double giaTien = resultSet.getDouble("GIATIEN");
                int soLuong = resultSet.getInt("SOLUONG");
                String hinhAnh = resultSet.getString("ANHSANPHAM");
                ListProductCart cart;
                if(giaKM<=0) {
                    cart = new ListProductCart(id, maDanhSachSanPham, idusers, idhamburger, tenhamburger, hinhAnh, soLuong, giaTien);
                }else{
                    cart = new ListProductCart(id, maDanhSachSanPham, idusers, idhamburger, tenhamburger, hinhAnh, soLuong, giaKM);
                }
                list.add(cart);
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
        PayBill payBill = (PayBill) bundle.getSerializable("itemHoaDon");
        idThanhToan = payBill.getIdThanhToan();
        idUsers = payBill.getIdUsers();
        madanhsachsanpham=payBill.getMaDanhSachSanPham();
        trangthai = payBill.getTrangThai();
        tongTien = payBill.getTongTien();
        ngaydat = payBill.getNgayDat();


    }
    private void anhxa() {
        tvThoiGianDat = findViewById(R.id.tvThoiGianDat);
        tvHoTen = findViewById(R.id.tvTenNguoiDat);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvSoDienThoai = findViewById(R.id.tvSoDienThoai);
        tvTongTienHang = findViewById(R.id.tvTongTienDH);
        tvIdDonhang = findViewById(R.id.tvIdDonHang);
        btnXacNhanDH =findViewById(R.id.btnXacNhanDH);
        btnHuyDonHang = findViewById(R.id.btnHuyDH);
        rcv = findViewById(R.id.rcvThongTinDonHang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
    }

}




