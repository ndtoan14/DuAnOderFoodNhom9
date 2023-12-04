package toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class AddNewProductActivity extends AppCompatActivity {
    private TextInputLayout edTenSP, edGiaTien, edSoLuong, edMoTaNgan, edMoTaChiTiet;
    private Button btnSelectImage, btnAddNew;
    private ImageView imgSanPham;
    private final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    private String encodedImage;
    String tenSP,giaTien,soLuong,moTaNgan,moTaChiTiet;
    DAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        dao = new DAO();
        anhxa();

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()){
                    return;
                }
                edTenSP.setError(null);
                edGiaTien.setError(null);
                edSoLuong.setError(null);
                edMoTaNgan.setError(null);
                edMoTaChiTiet.setError(null);
                addNew();

                onBackPressed();
                finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgSanPham.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void addNew() {

        tenSP = edTenSP.getEditText().getText().toString().trim();
        giaTien = edGiaTien.getEditText().getText().toString().trim();
        soLuong = edSoLuong.getEditText().getText().toString().trim();
        moTaNgan = edMoTaNgan.getEditText().getText().toString().trim();
        moTaChiTiet = edMoTaChiTiet.getEditText().getText().toString().trim();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        dao.insertHamburger(tenSP,moTaNgan,moTaChiTiet,giaTien,soLuong,encodedImage,getApplicationContext());


    }

    private boolean validate(){
        String tenSP = edTenSP.getEditText().getText().toString().trim();
        String giaTien = edGiaTien.getEditText().getText().toString().trim();
        String soLuong = edSoLuong.getEditText().getText().toString().trim();
        String moTaNgan = edMoTaNgan.getEditText().getText().toString().trim();
        String moTaChiTiet = edMoTaChiTiet.getEditText().getText().toString().trim();


        if (tenSP.isEmpty()) {
            edTenSP.setError("Không được để trống tên sản phẩm");
            edGiaTien.setError(null);
            edSoLuong.setError(null);
            edMoTaNgan.setError(null);
            edMoTaChiTiet.setError(null);
            return false;
        }else if (tenSP.length() >= 11){
            edTenSP.setError("Tên sản phẩm không được quá 11 kí tự");
            edGiaTien.setError(null);
            edSoLuong.setError(null);
            edMoTaNgan.setError(null);
            edMoTaChiTiet.setError(null);
        }
        else if (giaTien.isEmpty()){
            edTenSP.setError(null);
            edGiaTien.setError("Không được để trống giá tiền");
            edSoLuong.setError(null);
            edMoTaNgan.setError(null);
            edMoTaChiTiet.setError(null);
            return false;
        }
        else if (Double.parseDouble(giaTien) <= 0) {
            edTenSP.setError(null);
            edGiaTien.setError("Giá tiền phải lớn hơn 0");
            edSoLuong.setError(null);
            edMoTaNgan.setError(null);
            edMoTaChiTiet.setError(null);
            return false;
        }else if (soLuong.isEmpty()){
            edTenSP.setError(null);
            edGiaTien.setError(null);
            edSoLuong.setError("Số lượng không được để trống");
            edMoTaNgan.setError(null);
            edMoTaChiTiet.setError(null);
            return false;
        }
        else if (Integer.parseInt(soLuong) <= 0) {
            edTenSP.setError(null);
            edGiaTien.setError(null);
            edSoLuong.setError("Số lượng phải lớn hơn 0");
            edMoTaNgan.setError(null);
            edMoTaChiTiet.setError(null);
            return false;
        }else if(moTaNgan.isEmpty()){
            edTenSP.setError(null);
            edGiaTien.setError(null);
            edSoLuong.setError(null);
            edMoTaNgan.setError("Mô tả ngắn không được để trống");
            edMoTaChiTiet.setError(null);
            return false;
        }else if (moTaChiTiet.isEmpty()){
            edTenSP.setError(null);
            edGiaTien.setError(null);
            edSoLuong.setError(null);
            edMoTaNgan.setError(null);
            edMoTaChiTiet.setError("Mô tả chi tiết không được để trống");
            return false;
        }else if (bitmap == null) {
            Toast.makeText(getApplicationContext(), "Vui lòng chọn ảnh sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void anhxa() {
        edTenSP=findViewById(R.id.edTenSanPham);
        edGiaTien=findViewById(R.id.edGiaTien);
        edSoLuong=findViewById(R.id.edSoLuong);
        edMoTaNgan=findViewById(R.id.edMoTaNgan);
        edMoTaChiTiet=findViewById(R.id.edMoTaChiTiet);
        btnSelectImage=findViewById(R.id.btnSelectImage);
        btnAddNew=findViewById(R.id.btnAddNew);
        imgSanPham=findViewById(R.id.imgSanPham);
    }


}