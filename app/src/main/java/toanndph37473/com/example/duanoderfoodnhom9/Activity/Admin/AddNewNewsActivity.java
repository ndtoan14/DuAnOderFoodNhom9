package toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ProductAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class AddNewNewsActivity extends AppCompatActivity {
    Button btnDangTinTuc, btnChonAnhReviews;
    ImageView imgAnhReviews;
    RecyclerView rcv;
    ProductAdapter adapter;
    TextInputLayout edTieuDe, edNoiDung;
    DAO dao;
    private final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_news);
        anhxa();
        dao = new DAO();
        btnChonAnhReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        btnDangTinTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieuDe = edTieuDe.getEditText().getText().toString().trim();
                String noiDung = edNoiDung.getEditText().getText().toString().trim();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                // Hiển thị thời điểm hiện tại
                String currentTime = "Ngày " + day + "/" + (month + 1) + "/" + year + " " + hour + ":" + minute + ":" + second;


                if (dao.insertTinTuc(tieuDe, noiDung, encodedImage, currentTime) > 0) {
                    Toast.makeText(AddNewNewsActivity.this, "Thêm tin tức thành công", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(AddNewNewsActivity.this, "Thêm tin tức thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgAnhReviews.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validate() {
        String tieuDe = edTieuDe.getEditText().getText().toString().trim();
        String noiDung = edNoiDung.getEditText().getText().toString().trim();

        if (tieuDe.isEmpty()) {
            edTieuDe.setError("Không được để trống tiêu đề");
            edNoiDung.setError(null);
            return false;
        } else if (noiDung.isEmpty()) {
            edTieuDe.setError(null);
            edNoiDung.setError("Không được để trống nội dung");
            return false;
        }else if (bitmap == null) {
            Toast.makeText(getApplicationContext(), "Vui lòng chọn ảnh sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void anhxa() {
        btnDangTinTuc = findViewById(R.id.btnDangTinTuc);
        edTieuDe = findViewById(R.id.edTieuDeNews);
        edNoiDung = findViewById(R.id.edNoiDungNews);
        btnChonAnhReviews = findViewById(R.id.btnChonAnhReviews);
        imgAnhReviews = findViewById(R.id.imgReviews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

    }
}
