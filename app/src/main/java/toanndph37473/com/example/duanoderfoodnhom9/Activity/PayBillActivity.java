package toanndph37473.com.example.duanoderfoodnhom9.Activity;



import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.ZaloPay.Api.CreateOrder;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.PayAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.InformationPay;
import toanndph37473.com.example.duanoderfoodnhom9.Model.ListProductCart;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PayBillActivity extends AppCompatActivity {
    RecyclerView rcvPay;
    PayAdapter adapter;
    static List<ListProductCart> list =new ArrayList<>();
    TextView tvSoDuViTien,tvTongTienPay,tvTongTienHang,tvShip,tvThongTinKhac;
    Button btnMuaBangViTien,btnMuaBangZaloPay;
    ConstraintLayout backgroundCart;
    TextInputLayout edName,edPhone,edAddress,edVoucher;
    double ttHang;
    static int tongTienThanhToan ;
    public static double ship;
    double viTien;
    Connection_SQL connection;
    //zalo pay
    String lblZpTransToken, txtToken;
    String tvtongthanhtoanzalopay;
    public static boolean check = true;
    InformationPay informationPay;
    CardView btnback;
    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);
        tvSoDuViTien=findViewById(R.id.tvSoDuViTienPay);
        btnMuaBangViTien=findViewById(R.id.btnPayNowPay);
        btnMuaBangZaloPay=findViewById(R.id.btnThanhToanZaloPay);
        tvTongTienHang=findViewById(R.id.tvTongTienhang);
        tvTongTienPay=findViewById(R.id.tvTongTienPay);
        edName=findViewById(R.id.edNamePay);
        edPhone=findViewById(R.id.edPhonePay);
        edAddress=findViewById(R.id.edAddressPay);
        edVoucher=findViewById(R.id.edVoucherPay);
        tvShip=findViewById(R.id.tvShip);
        rcvPay = findViewById(R.id.rcvPay);
        backgroundCart=findViewById(R.id.backgroundCart);
        tvThongTinKhac = findViewById(R.id.tvChonThongTinKhac);
        btnback = findViewById(R.id.btnBackPayBill);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayBillActivity.this,CartActivity.class);
                startActivity(intent);
                finish();
            }
        });


        tvThongTinKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayBillActivity.this, SelectInfomation.class);
                startActivity(intent);
                finish();
            }
        });
//zalo
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // ZaloPay SDK Init
        ZaloPaySDK.init(553, Environment.SANDBOX);// tich hop moi truong


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getAllListPay();
        adapter = new PayAdapter(list,this);
        rcvPay.setAdapter(adapter);
        Users user = UserSession.getCurrentUser(PayBillActivity.this);
        idUser = user.getIdUser();
//        viTien = user.getViTien();
        //xu li lay vi tien tren firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS").child(String.valueOf(user.getIdUser())).child("viTien");        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viTien = snapshot.getValue(double.class);
                tvSoDuViTien.setText("$"+ viTien);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

// mua bang vi
        btnMuaBangViTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muaBangViTien();

            }
        });
        // mua bang zalo pay

        if(edAddress.getEditText().getText().toString().trim().equalsIgnoreCase("Thái Bình")
                ||edAddress.getEditText().getText().toString().trim().equalsIgnoreCase("Thai Binh")
                ||edAddress.getEditText().getText().toString().trim().equalsIgnoreCase("ThaiBinh")
        ){
            ship = 1;
            tvShip.setText("$"+ship);
        }else{
            ship = 2;
            tvShip.setText("$"+ship);
        }
//        tinhShip();
        tongTienThanhToan();
        btnMuaBangZaloPay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                muaBangZaloPay();
            }
        });
    }

    private void muaBangZaloPay() {
        //tao don
        CreateOrder orderApi = new CreateOrder();

        try {
            JSONObject data = orderApi.createOrder(tvtongthanhtoanzalopay);
            String code = data.getString("returncode");
            if (code.equals("1")) {
                lblZpTransToken= "zptranstoken";
                txtToken=(data.getString("zptranstoken"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //thanh toan

        String token = txtToken;
        ZaloPaySDK.getInstance().payOrder(PayBillActivity.this, token, "demozpdk://app", new PayOrderListener() {
            @Override
            public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

//                        check = false;
//                        try{
//                            Connection_SQL connection = new Connection_SQL();
//                            Users user = UserSession.getCurrentUser(PayBillActivity.this);
//                            int userId = user.getIdUser();
//                            String sql = "SELECT MADANHSACHSANPHAM FROM DANHSACHSANPHAM WHERE IDUSERS = ?" ;
//                            PreparedStatement stm = connection.SQLconnection().prepareStatement(sql);
//                            stm.setInt(1, userId);
//                            ResultSet rs = stm.executeQuery();
//                            int maDanhSachSanPham = CartActivity.maDssp;
//                            if(rs.next()){
//                                maDanhSachSanPham = rs.getInt("MADANHSACHSANPHAM");
//                            }
//                            Calendar calendar = Calendar.getInstance();
//                            int year = calendar.get(Calendar.YEAR);
//                            int month = calendar.get(Calendar.MONTH);
//                            int day = calendar.get(Calendar.DAY_OF_MONTH);
//                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                            int minute = calendar.get(Calendar.MINUTE);
//                            int second = calendar.get(Calendar.SECOND);
//// Hiển thị thời điểm hiện tại
//                            String currentTime = "Ngày " + day + "/" + (month + 1) + "/" + year + " " + hour + ":" + minute + ":" + second;
//
//                            String sql2 = "INSERT INTO THANHTOAN (IDUSERS,MADANHSACHSANPHAM,TONGTIEN,TRANGTHAI,NGAYDAT) VALUES (?, ?, ?, ?, ?)";
//                            PreparedStatement stm2 = connection.SQLconnection().prepareStatement(sql2);
//                            stm2.setInt(1,idUser);
//                            stm2.setInt(2,maDanhSachSanPham);
//                            stm2.setDouble(3,tongTienThanhToan);
//                            stm2.setInt(4,1);
//                            stm2.setString(5,currentTime);
//                            int row = stm2.executeUpdate();
//                            if(row >0 ){
//                                CartActivity.maDssp = CartActivity.maDssp +1;
//                            }
//                            for(ListProductCart cart : list){
//                                // thêm dssp mới
//                                String insertDSDH = "INSERT INTO DANHSACHDONHANG (IDUSERS, IDHAMBURGER, MADANHSACHSANPHAM, TENHAMBURGER, SOLUONG, GIATIEN, ANHSANPHAM, PHUONGTHUCTHANHTOAN) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//                                PreparedStatement pstmtInsert = connection.SQLconnection().prepareStatement(insertDSDH);
//                                pstmtInsert.setInt(1, cart.getIdUsers());
//                                pstmtInsert.setInt(2, cart.getIdHamburger());
//                                pstmtInsert.setInt(3, cart.getMaDanhSachSanPham());
//                                pstmtInsert.setString(4, cart.getTenHamburger());
//                                pstmtInsert.setInt(5, cart.getSoLuong());
//                                pstmtInsert.setDouble(6, cart.getGiaTien());
//                                pstmtInsert.setString(7, cart.getAnhSanPham());
//                                pstmtInsert.setInt(8,1);
//                                int rowDSDH = pstmtInsert.executeUpdate();
//                                if (rowDSDH > 0) {
//                                    //xoa danh sach san pham
//                                    Connection_SQL c1 = new Connection_SQL();
//                                    String sql1 = "DELETE FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
//                                    PreparedStatement statement1 = c1.SQLconnection().prepareStatement(sql1);
//                                    statement1.setInt(1, cart.getIdUsers());
//                                    statement1.executeUpdate();
////                            if(x>0){
////                                Intent intent = new Intent(PayBillActivity.this,MainActivity.class);
////                                startActivity(intent);
////                                finish();
////                            }
//                                }
//
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                    }

                });
            }

            @Override
            public void onPaymentCanceled(String zpTransToken, String appTransID) {
                new android.app.AlertDialog.Builder(PayBillActivity.this)
                        // neu thoat thanh toan
                        .setTitle("User Cancel Payment")
                        .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }

            @Override
            public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                new android.app.AlertDialog.Builder(PayBillActivity.this)
                        //thanh toan that bai
                        .setTitle("Payment Fail")
                        .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }
        });

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    private void tongTienThanhToan() {
        String voucher = edVoucher.getEditText().getText().toString().trim();
        double giamTien=0;
        if(voucher.equalsIgnoreCase("PH29152")){
            giamTien = 2;
        }
        if(voucher.equalsIgnoreCase("PH29289")){
            giamTien=1;
        }
        if(voucher.equalsIgnoreCase("PH29221")){
            giamTien=1;
        }
        if(voucher.equalsIgnoreCase("PH29203")){
            giamTien=1;
        }
        if(voucher.equalsIgnoreCase("PH29191")){
            giamTien=1;
        }
        tongTienThanhToan = (int) (ttHang + ship - giamTien);
        tvTongTienPay.setText(String.valueOf(tongTienThanhToan));
        tvtongthanhtoanzalopay = tvTongTienPay.getText().toString().trim();

    }


    private void muaBangViTien() {
        if(viTien - tongTienThanhToan >=0){
            thanhToanThanhCong();

        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage("So du khong du, ban co muon nap them tien vao vi ?");
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(PayBillActivity.this, RechargeMoneyActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();
        }
    }

    private void thanhToanThanhCong() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PayBillActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setCancelable(false);
        builder.setMessage("Bạn có chắc chắn muốn mua ngay ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    connection = new Connection_SQL();
                    Users user = UserSession.getCurrentUser(PayBillActivity.this);
                    int userId = user.getIdUser();
                    String sql = "SELECT MADANHSACHSANPHAM FROM DANHSACHSANPHAM WHERE IDUSERS = ?" ;
                    PreparedStatement stm = connection.SQLconnection().prepareStatement(sql);
                    stm.setInt(1, userId);
                    ResultSet rs = stm.executeQuery();
                    int maDanhSachSanPham = CartActivity.maDssp;
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);
// Hiển thị thời điểm hiện tại
                    String currentTime = "Ngày " + day + "/" + (month + 1) + "/" + year + " " + hour + ":" + minute + ":" + second;

                    if(rs.next()){
                        maDanhSachSanPham = rs.getInt("MADANHSACHSANPHAM");
                    }
                    String sql2 = "INSERT INTO THANHTOAN (IDUSERS,MADANHSACHSANPHAM,TONGTIEN,TRANGTHAI,NGAYDAT) VALUES (? , ?, ?, ?, ?)";
                    PreparedStatement stm2 = connection.SQLconnection().prepareStatement(sql2);
                    stm2.setInt(1,userId);
                    stm2.setInt(2,maDanhSachSanPham);
                    stm2.setDouble(3,tongTienThanhToan);
                    stm2.setInt(4,1);
                    stm2.setString(5,currentTime);
                    int row = stm2.executeUpdate();
                    if(row >0 ){
                        CartActivity.maDssp = CartActivity.maDssp +1;
                        for(ListProductCart cart : list){
                            // thêm dssp mới
                            String insertDSDH = "INSERT INTO DANHSACHDONHANG (IDUSERS, IDHAMBURGER, MADANHSACHSANPHAM, TENHAMBURGER, SOLUONG, GIATIEN, ANHSANPHAM) VALUES (?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement pstmtInsert = connection.SQLconnection().prepareStatement(insertDSDH);
                            pstmtInsert.setInt(1, cart.getIdUsers());
                            pstmtInsert.setInt(2, cart.getIdHamburger());
                            pstmtInsert.setInt(3, cart.getMaDanhSachSanPham());
                            pstmtInsert.setString(4, cart.getTenHamburger());
                            pstmtInsert.setInt(5, cart.getSoLuong());
                            pstmtInsert.setDouble(6, cart.getGiaTien());
                            pstmtInsert.setString(7, cart.getAnhSanPham());
                            int rowDSDH = pstmtInsert.executeUpdate();
                            if (rowDSDH > 0) {
                                Log.e("insertDHDH", "insert DANH SACH DON HANG THANH CONG");
                            }
                            //xoa danh sach san pham
                            Connection_SQL c1 = new Connection_SQL();
                            String sql1 = "DELETE FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
                            PreparedStatement statement1 = c1.SQLconnection().prepareStatement(sql1);
                            statement1.setInt(1, cart.getIdUsers());
                            statement1.executeUpdate();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(PayBillActivity.this, PaySuccess.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    public void thanhToanZaloThanhCong(){
        try{
            Connection_SQL connection = new Connection_SQL();
            int maDanhSachSanPham = CartActivity.maDssp;

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
// Hiển thị thời điểm hiện tại
            String currentTime = "Ngày " + day + "/" + (month + 1) + "/" + year + " " + hour + ":" + minute + ":" + second;

            String sql2 = "INSERT INTO THANHTOAN (IDUSERS,MADANHSACHSANPHAM,TONGTIEN,TRANGTHAI,NGAYDAT) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm2 = connection.SQLconnection().prepareStatement(sql2);
            stm2.setInt(1,idUser);
            stm2.setInt(2,maDanhSachSanPham);
            stm2.setDouble(3,tongTienThanhToan);
            stm2.setInt(4,1);
            stm2.setString(5,currentTime);
            int row = stm2.executeUpdate();
            if(row >0 ){
                CartActivity.maDssp = CartActivity.maDssp +1;
            }
            for(ListProductCart cart : list){
                // thêm dssp mới
                String insertDSDH = "INSERT INTO DANHSACHDONHANG (IDUSERS, IDHAMBURGER, MADANHSACHSANPHAM, TENHAMBURGER, SOLUONG, GIATIEN, ANHSANPHAM, PHUONGTHUCTHANHTOAN) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmtInsert = connection.SQLconnection().prepareStatement(insertDSDH);
                pstmtInsert.setInt(1, cart.getIdUsers());
                pstmtInsert.setInt(2, cart.getIdHamburger());
                pstmtInsert.setInt(3, cart.getMaDanhSachSanPham());
                pstmtInsert.setString(4, cart.getTenHamburger());
                pstmtInsert.setInt(5, cart.getSoLuong());
                pstmtInsert.setDouble(6, cart.getGiaTien());
                pstmtInsert.setString(7, cart.getAnhSanPham());
                pstmtInsert.setInt(8,1);
                int rowDSDH = pstmtInsert.executeUpdate();
                if (rowDSDH > 0) {
                    //xoa danh sach san pham
                    Connection_SQL c1 = new Connection_SQL();
                    String sql1 = "DELETE FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
                    PreparedStatement statement1 = c1.SQLconnection().prepareStatement(sql1);
                    statement1.setInt(1, cart.getIdUsers());
                    statement1.executeUpdate();
//                            if(x>0){
//                                Intent intent = new Intent(PayBillActivity.this,MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    public List<ListProductCart> getAllListPay() {

        try {
            connection = new Connection_SQL();
            Users user = UserSession.getCurrentUser(PayBillActivity.this);
            int userId = user.getIdUser();
            String sql = "SELECT * FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("IDDANHSACHSANPHAM");
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
            String sql2 = "SELECT * FROM USERS WHERE IDUSERS = ?";
            PreparedStatement stm = connection.SQLconnection().prepareStatement(sql2);
            stm.setInt(1,userId);
            ResultSet resultSet1 = stm.executeQuery();
            while(resultSet1.next()){
                String ten = resultSet1.getString("TEN");
                String diaChi= resultSet1.getString("DIACHI");
                String sdt = resultSet1.getString("SODIENTHOAI");
                Objects.requireNonNull(edName.getEditText()).setText(ten);
                Objects.requireNonNull(edAddress.getEditText()).setText(diaChi);
                Objects.requireNonNull(edPhone.getEditText()).setText(sdt);
            }
            String sql3 = "SELECT SUM(GIATIEN * SOLUONG) as TONG FROM DANHSACHSANPHAM WHERE IDUSERS = ?";
            PreparedStatement stm2 = connection.SQLconnection().prepareStatement(sql3);
            stm2.setInt(1, userId);
            ResultSet resultSet2 = stm2.executeQuery();
            if(resultSet2.next()){
                ttHang = resultSet2.getDouble("TONG");
                tvTongTienHang.setText("$"+ttHang);
            }
            resultSet2.close();
            stm2.close();
            resultSet1.close();
            stm.close();
            resultSet.close();
            statement.close();
            connection.SQLconnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    protected void onResume() {
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            informationPay = (InformationPay) bundle.getSerializable("information");
            if (informationPay != null) {
                edName.getEditText().setText(informationPay.getTen());
                edAddress.getEditText().setText(informationPay.getDiaChi());
                edPhone.getEditText().setText(informationPay.getSoDienThoai());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onResume();
    }

}
