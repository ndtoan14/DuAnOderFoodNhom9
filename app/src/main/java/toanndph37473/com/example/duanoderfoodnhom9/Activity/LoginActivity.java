package toanndph37473.com.example.duanoderfoodnhom9.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import toanndph37473.com.example.duanoderfoodnhom9.AdminActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.MainActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.NetworkChangeListener;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class LoginActivity extends AppCompatActivity {
    private CardView btnLogin;
    private TextView btnSignUp,tvQuenMK;
    private TextInputLayout edPhone,edPassword;
    Connection_SQL connection;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private FirebaseAuth mAuth;
    String finalPhone;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kiểm tra xem User đã đăng nhập trước đó hay chưa
        Users currentUser = UserSession.getCurrentUser(this);
        if (currentUser != null) {
            if(UserSession.getCurrentUser(this).getQuyen()==0){
                // Nếu đã đăng nhập rồi thì chuyển hướng đến Activity chính
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
                finish();
                return;
            }else{
                // Nếu đã đăng nhập rồi thì chuyển hướng đến Activity chính
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }
        // Nếu chưa đăng nhập thì hiển thị giao diện đăng nhập
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.cardLogin);
        edPhone = findViewById(R.id.layUserName);
        edPassword = findViewById(R.id.layPassword);
        btnSignUp = findViewById(R.id.btnSignUpinLogin);
        tvQuenMK = findViewById(R.id.tvQuenMK);
        mAuth = FirebaseAuth.getInstance();

        tvQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = edPhone.getEditText().getText().toString().trim();
                if (phone.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui long nhap so dien thoai truoc khi quen mk", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra nếu đầu số là "0"
                if (phone.startsWith("0")) {
                    // Thay đổi đầu số thành "+84"
                    phone = "+84" + phone.substring(1);
                }
                finalPhone = phone;

                if (kiemTraSoDienThoaiTonTai()) {
                    Toast.makeText(LoginActivity.this, "So dien thoai khong ton tai", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    // Xác thực thành công
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    // Xác thực thất bại
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    // Mã OTP đã được gửi đến số điện thoại của người dùng
                                    Toast.makeText(LoginActivity.this, "Verification code sent quenmk", Toast.LENGTH_SHORT).show();

                                    // Chuyển sang Activity để nhập mã OTP
                                    Intent intent = new Intent(LoginActivity.this, OTPQuenMK.class);
                                    intent.putExtra("id", verificationId); // Truyền verificationId dưới dạng Extra
                                    intent.putExtra("phone", edPhone.getEditText().getText().toString().trim());
                                    startActivity(intent);

                                }
                            };


                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(finalPhone)       // Số điện thoại của người dùng
                                    .setTimeout(120L, TimeUnit.SECONDS) // Thời gian hết hạn của mã OTP
                                    .setActivity(LoginActivity.this)
                                    .setCallbacks(mCallbacks)// Activity để xử lý sự kiện OTP
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();


            }
        });
    }

    private boolean kiemTraSoDienThoaiTonTai() {
        try{
            connection  = new Connection_SQL();
            String sql = "SELECT * FROM USERS";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()){
                String sdt = rs.getString("SODIENTHOAI");
                if(finalPhone.equals(sdt) || edPhone.getEditText().getText().toString().trim().equals(sdt)){
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private void login()  {
        try {
        connection = new Connection_SQL();
        if(connection!=null){
            String username = Objects.requireNonNull(edPhone.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(edPassword.getEditText()).getText().toString();
            if(username.isEmpty()){
                edPhone.setError("So dien thoai khong duoc de trong");
            }else{
                edPhone.setError(null);
            }
            if(username.length()>11){
                edPhone.setError("So dien thoai khong vuot qua 11 ki tu");
            }else {
                edPhone.setError(null);
            }
            if(password.isEmpty()){
                edPassword.setError("Mat khau khong duoc de trong");
            }else{
                edPassword.setError(null);
            }
            String sql = "SELECT COUNT(*) as count,QUYEN FROM USERS WHERE SODIENTHOAI = ? AND MATKHAU = ? GROUP BY QUYEN";
            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            String sql2 ="SELECT * FROM USERS WHERE SODIENTHOAI = ? AND MATKHAU = ?";
            PreparedStatement statement2 = connection.SQLconnection().prepareStatement(sql2);
            statement2.setString(1, username);
            statement2.setString(2, password);
            ResultSet result2 = statement2.executeQuery();
            if(result2.next()){
                int idUser = result2.getInt("IDUSERS");
                int quyen = result2.getInt("QUYEN");
                String name = result2.getString("TEN");
                String email = result2.getString("EMAIL");
                String diaChi=result2.getString("DIACHI");
                double viTien = result2.getDouble("VITIEN");
                String hinhAnh = result2.getString("HINHANH");
                Users user;
                if(hinhAnh==null) {
                    user = new Users(idUser, name, email, username, diaChi, password, quyen, viTien);
                }else{
                    user = new Users(idUser, name, email, username, diaChi, password, quyen, viTien,hinhAnh);
                }
                UserSession.saveUser(LoginActivity.this,user);
            }
            if (result.next()) {
                int count = result.getInt("count");
                int quyen = result.getInt("QUYEN");
                if (count == 1) {
                    if (quyen == 0) {

                        // progress
                        View progressView = getLayoutInflater().inflate(R.layout.item_progress, null);
                        ViewGroup rootLayout = findViewById(android.R.id.content);
                        rootLayout.addView(progressView);



                        Toast.makeText(this, "Admin", Toast.LENGTH_SHORT).show();
                        // Chuyển đến trang chủ

                        Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
                        startActivity(intent);
                    } else {
                        // Chuyển đến trang phụ
                        View progressView = getLayoutInflater().inflate(R.layout.item_progress, null);
                        ViewGroup rootLayout = findViewById(android.R.id.content);
                        rootLayout.addView(progressView);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(this, "User", Toast.LENGTH_SHORT).show();
                    }
//                    finishAffinity();
                    Toast.makeText(this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    // Đăng nhập thất bại
                    Toast.makeText(this, "Tai khoan hoac mat khau sai", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Tai khoan hoac mat khau sai", Toast.LENGTH_SHORT).show();
            }
            result.close();
            statement.close();
            statement2.close();
            connection.SQLconnection().close();

        }else{
            Toast.makeText(this, "Ket noi den co so du lieu khong thanh cong", Toast.LENGTH_SHORT).show();
        }
    }catch (Exception e){
            e.printStackTrace();
        }
    }



//    @Override
//    protected void onStart() {
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(networkChangeListener,filter);
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        unregisterReceiver(networkChangeListener);
//        super.onStop();
//    }


}