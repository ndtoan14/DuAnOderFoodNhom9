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

import toanndph37473.com.example.duanoderfoodnhom9.R;

public class OTPQuenMK extends AppCompatActivity {
    TextInputLayout edOTPQUENMK;
    Button btnXacThucQuenMK;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpquen_mk);
        edOTPQUENMK = findViewById(R.id.edOTPQUENMK);
        btnXacThucQuenMK = findViewById(R.id.btnXacThucQuenMK);

        // Khởi tạo FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        // Nhận mã OTP đã được gửi từ Firebase
        mVerificationId = getIntent().getStringExtra("id");
        phone = getIntent().getStringExtra("phone");
        btnXacThucQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = edOTPQUENMK.getEditText().getText().toString().trim();

                // Kiểm tra mã OTP có hợp lệ không
                if (verificationCode.isEmpty()) {
                    Toast.makeText(OTPQuenMK.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(OTPQuenMK.this, "Verification successful", Toast.LENGTH_SHORT).show();
                                    Log.e("Loi", "xac thuc thanh cong: ");
                                    Intent intent = new Intent(OTPQuenMK.this,FormDoiPassActivity.class);
                                    intent.putExtra("phoneUpdatePass",phone);
                                    startActivity(intent);

                                } else {
                                    // Xác thực thất bại
                                    Toast.makeText(OTPQuenMK.this, "Verification failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}