package toanndph37473.com.example.duanoderfoodnhom9.Activity;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.MainActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout edPhone, edPass, edName, edEmail,edAddress;
    CardView btnSignUp;
    private FirebaseAuth mAuth;
    String finalPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        anhxa();
        mAuth = FirebaseAuth.getInstance();
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
                        Toast.makeText(getApplicationContext(), "Verification code sent", Toast.LENGTH_SHORT).show();

                        // Chuyển sang Activity để nhập mã OTP
                        Intent intent = new Intent(SignUpActivity.this,OTPPhoneActivity.class);
                        Users users = new Users(edName.getEditText().getText().toString().trim()
                                ,edEmail.getEditText().getText().toString().trim()
                                ,edPhone.getEditText().getText().toString().trim()
                                ,edAddress.getEditText().getText().toString().trim()
                                ,edPass.getEditText().getText().toString().trim());
                        Bundle bundle = new Bundle();
                        intent.putExtra("verificationId", verificationId); // Truyền verificationId dưới dạng Extra
                        bundle.putSerializable("signUp",users);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                };
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edPhone.getEditText().getText().toString().trim();
                validate();
                if (phoneNumber.startsWith("0")) {
                    // Thay đổi đầu số thành "+84"
                    phoneNumber = "+84" + phoneNumber.substring(1);
                }
                finalPhone = phoneNumber;
                if (kiemTraSoDienThoaiTonTai()) {
                    Toast.makeText(SignUpActivity.this, "So dien thoai dang ki da ton tai\n" +
                            "vui long dang ki bang so khac!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Yêu cầu Firebase gửi mã OTP đến số điện thoại
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(finalPhone)       // Số điện thoại của người dùng
                                    .setTimeout(120L, TimeUnit.SECONDS) // Thời gian hết hạn của mã OTP
                                    .setActivity(SignUpActivity.this)
                                    .setCallbacks(mCallbacks)// Activity để xử lý sự kiện OTP
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                    // Xử lý sự kiện khi mã OTP được gửi đến số điện thoại

                }
            }
        });
    }

    private void validate() {
        if(edPhone.getEditText().getText().toString().trim().isEmpty()){
            edPhone.setError("So dien thoai khong duoc de trong");
            return;
        }
        if (!edPhone.getEditText().getText().toString().trim().startsWith("0")) {
            edPhone.setError("So dien thoai phai bat dau bang so 0 ");
            return;
        }
        if(edPhone.getEditText().getText().toString().trim().length()>11){
            edPhone.setError("So dien thoai khong vuot qua 11 ki tu");
            return;
        }else {
            edPhone.setError(null);
        }
        if(edPhone.getEditText().getText().toString().trim().length()<10) {
            edPhone.setError("So dien thoai khong nho hon 10 ki tu");
            return;
        }
        if(edPass.getEditText().getText().toString().trim().isEmpty()){
            edPass.setError("Mat khau khong duoc de trong");
            return;
        }else{
            edPass.setError(null);
        }
        if(edPass.getEditText().getText().toString().trim().isEmpty()){
            edPass.setError("Mat khau khong duoc de trong");
            return;
        }else{
            edPass.setError(null);
        }
        if(edName.getEditText().getText().toString().trim().isEmpty()){
            edName.setError("Name khong duoc de trong");
            return;
        }else{
            edName.setError(null);
        }
        if (!isValidEmail("example@email.com")) {
            // Địa chỉ email khong hợp lệ
            edEmail.setError("Dia chi email khong hop le");
            return;
        } else {
            // Địa chỉ email hợp lệ
            edEmail.setError(null);
        }

        if(edAddress.getEditText().getText().toString().trim().isEmpty()){
            edAddress.setError("Dia chi khong duoc de trong");
            return;
        }else{
            edAddress.setError(null);
        }
    }

    private boolean kiemTraSoDienThoaiTonTai() {
        try{
            Connection_SQL connection  = new Connection_SQL();
            String sql = "SELECT * FROM USERS";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()){
                String sdt = rs.getString("SODIENTHOAI");
                if(edPhone.getEditText().getText().toString().trim().equals(sdt)){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void anhxa() {
        edPhone = findViewById(R.id.layPhoneNumber);
        edPass = findViewById(R.id.layPasswords);
        edName = findViewById(R.id.layName);
        edEmail = findViewById(R.id.layEmail);
        edAddress = findViewById(R.id.layAddress);
        btnSignUp = findViewById(R.id.cardSignUp);
    }
}
