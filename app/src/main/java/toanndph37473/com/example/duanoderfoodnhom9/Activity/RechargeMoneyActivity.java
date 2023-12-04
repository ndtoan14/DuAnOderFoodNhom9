package toanndph37473.com.example.duanoderfoodnhom9.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.RechargeMoney;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class RechargeMoneyActivity extends AppCompatActivity {
    Button btnXacNhanNap;
    ImageView imgNaptien;
    TextInputLayout edSoTienNap;
    private final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    private String encodedImage;
    int idHoaDonNapTien ;
    List<RechargeMoney> listyc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_money);
        imgNaptien = findViewById(R.id.imgAnhNapTien);
        btnXacNhanNap = findViewById(R.id.btnXacNhanNap);
        edSoTienNap=findViewById(R.id.edSoTienMuonNap);

        imgNaptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        btnXacNhanNap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sotien = edSoTienNap.getEditText().getText().toString().trim();
                if(sotien.isEmpty()){
                    edSoTienNap.setError("Vui Long nhap so tien muon nap");
                    return;
                }else{
                    edSoTienNap.setError(null);
                }
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                idHoaDonNapTien = sharedPreferences.getInt("count",0) + 1; // Tăng giá trị khóa lên 1
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("count", idHoaDonNapTien);
                editor.apply();
                if(bitmap == null) {
                    Toast.makeText(getApplicationContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                    return;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                try {
                    Connection_SQL connection = new Connection_SQL();
                    Users user = UserSession.getCurrentUser(RechargeMoneyActivity.this);
                    int idUsers = user.getIdUser();
                    String insertHDNapTien = "INSERT INTO HOADONNAPTIEN (IDHOADONNAPTIEN,HINHANH,IDUSERS,SOTIENNAP) VALUES (?, ?, ?,?)";
                    PreparedStatement statement = connection.SQLconnection().prepareStatement(insertHDNapTien);
                    statement.setInt(1,idHoaDonNapTien);
                    statement.setString(2,encodedImage);
                    statement.setInt(3,idUsers);
                    statement.setDouble(4,Double.parseDouble(sotien));
                    int rowInserted = statement.executeUpdate();
                    if (rowInserted>0){
                        String sqlHOADONNAPTIEN = "SELECT * FROM HOADONNAPTIEN WHERE IDHOADONNAPTIEN = ?";
                        PreparedStatement statement1 = connection.SQLconnection().prepareStatement(sqlHOADONNAPTIEN);
                        statement1.setInt(1,idHoaDonNapTien);
                        ResultSet rs = statement1.executeQuery();
                        if(rs.next()) {
                            int idHDN = rs.getInt("IDHOADONNAPTIEN");
                            String hinhAnh = rs.getString("HINHANH");
                            int tt = rs.getInt("TRANGTHAI");
                            int idU = rs.getInt("IDUSERS");
                            RechargeMoney rechargeMoney = new RechargeMoney(idHDN,tt,idU,hinhAnh,Double.parseDouble(sotien));
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("HOADONNAPTIEN");
                            ref.child(String.valueOf(idHDN)).setValue(rechargeMoney).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RechargeMoneyActivity.this, "Yeu cau da duoc gui len he thong vui long cho chut nhe ", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                    }
                                }
                            });

                        }

                    }
                    statement.close();
                    connection.SQLconnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "them that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgNaptien.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}