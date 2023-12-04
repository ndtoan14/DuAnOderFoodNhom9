package toanndph37473.com.example.duanoderfoodnhom9.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class OTPPhoneActivity extends AppCompatActivity {
    private TextInputLayout edOTP;
    private Button btnXacThuc;
    private int idUser;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    Users users = new Users();
    private String hinhAnhU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpphone);
        edOTP = findViewById(R.id.edOTP);
        btnXacThuc = findViewById(R.id.btnXacThuc);
        getDulieu();
        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        // Nhận mã OTP đã được gửi từ Firebase
        mVerificationId = getIntent().getStringExtra("verificationId");
        btnXacThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = edOTP.getEditText().getText().toString().trim();

                // Kiểm tra mã OTP có hợp lệ không
                if (verificationCode.isEmpty()) {
                    Toast.makeText(OTPPhoneActivity.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo một đối tượng PhoneAuthCredential từ mã OTP và ID xác thực
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);

                // Xác thực số điện thoại của người dùng
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Xác thực thành công
                                    Toast.makeText(OTPPhoneActivity.this, "Verification successful", Toast.LENGTH_SHORT).show();
                                    signUpinDB();

                                    // dang ki len firebase
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS").child(String.valueOf(idUser));
                                    Users u = new Users(idUser, users.getTen(), users.getEmail(), users.getSoDienThoai(), users.getDiaChi(), users.getMatKhau(), 1, 0, hinhAnhU);
                                    ref.setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.e("SignUpWithFireBase", "Dang ki bang firebase thanh cong");
                                                insertAddressMacDinh();
                                                Intent intent = new Intent(OTPPhoneActivity.this,LoginActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(OTPPhoneActivity.this, "Đăng kí thành công, bây giờ hãy đăng nhập", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    });


                                } else {
                                    // Xác thực thất bại
                                    Toast.makeText(OTPPhoneActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private boolean signUpinDB() {
        try{
            Connection_SQL connection_sql = new Connection_SQL();
            String insertUsers = "INSERT INTO USERS (TEN,EMAIL,SODIENTHOAI,DIACHI,MATKHAU,VITIEN,QUYEN) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ptm = connection_sql.SQLconnection().prepareStatement(insertUsers);
            ptm.setString(1, users.getTen());
            ptm.setString(2, users.getEmail());
            ptm.setString(3, users.getSoDienThoai());
            ptm.setString(4, users.getDiaChi());
            ptm.setString(5, users.getMatKhau());
            ptm.setDouble(6, 0);
            ptm.setInt(7, 1);
            int row = ptm.executeUpdate();
            if(row>0){
                Log.e("SignUpWithDB", "Dang ki thanh cong tren DB");
                String layThongTinUser = "SELECT * FROM USERS WHERE SODIENTHOAI = ?";
                PreparedStatement stm = connection_sql.SQLconnection().prepareStatement(layThongTinUser);
                stm.setString(1,users.getSoDienThoai());
                ResultSet rs = stm.executeQuery();
                if(rs.next()){
                    idUser = rs.getInt("IDUSERS");
                    hinhAnhU = rs.getString("HINHANH");
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void getDulieu() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        users = (Users) bundle.getSerializable("signUp");
    }
    private void insertAddressMacDinh() {
        try{
            Connection_SQL connection_sql = new Connection_SQL();
            String sql= "INSERT INTO THONGTINNHANHANG (TEN,SODIENTHOAI,DIACHI,IDUSERS) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection_sql.SQLconnection().prepareStatement(sql);
            preparedStatement.setString(1, users.getTen());
            preparedStatement.setString(2, users.getSoDienThoai());
            preparedStatement.setString(3, users.getDiaChi());
            preparedStatement.setInt(4, users.getIdUser());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
