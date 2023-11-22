package toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class UpdateProduct extends AppCompatActivity {
    Connection_SQL connection;
    private final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    private String encodedImage;

    DAO dao;
    CardView cardViewBack;
    ImageView img_pr;
    String hinhAnh;
    Button btn_capnhat, btn_xoa;
    TextInputLayout ed_name, ed_mo_ta_ngan, ed_mo_ta_chi_tiet, ed_gia, ed_soluong;
    Hamburger hamburger;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product);
        dao = new DAO();
        anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        hamburger = (Hamburger) bundle.getSerializable("hamburger");
        // dán ảnh vào dialog
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        opts.inMutable = true;
        byte[] decodedString = Base64.decode(hamburger.getHinhAnh(), Base64.DEFAULT);
        Bitmap myBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
        myBitMap.setHasAlpha(true);
        img_pr.setImageBitmap(myBitMap);
        img_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        showThongTinCu();
        btn_capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namePr = ed_name.getEditText().getText().toString().trim();
                String mo_ta_ngan = ed_mo_ta_ngan.getEditText().getText().toString().trim();
                String mo_ta_chi_tiet = ed_mo_ta_chi_tiet.getEditText().getText().toString().trim();
                String gia = ed_gia.getEditText().getText().toString().trim();
                String soLuong = ed_soluong.getEditText().getText().toString().trim();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
                if (bitmap == null || encodedImage == null) {
                    hinhAnh = hamburger.getHinhAnh();
                } else {
                    hinhAnh = encodedImage;
                }

                if (!validateUpdate()) {
                    return;
                } else {
                    int IdHamberger = hamburger.getId();
                    dao.updateHamburgerTheoId(namePr, mo_ta_ngan, mo_ta_chi_tiet, gia, soLuong, hinhAnh, IdHamberger);
                    if (dao.updateHamburgerTheoId(namePr, mo_ta_ngan, mo_ta_chi_tiet, gia, soLuong, hinhAnh, IdHamberger) > 0) {
                        ed_name.setError(null);
                        ed_mo_ta_ngan.setError(null);
                        ed_mo_ta_chi_tiet.setError(null);
                        ed_gia.setError(null);
                        ed_soluong.setError(null);
                        onBackPressed();
                        Toast.makeText(UpdateProduct.this, "Update thanh cong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateProduct.this, "Update that bai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProduct.this);
                builder.setMessage("Bạn có muốn xóa sản phẩm này không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int idHumberger = hamburger.getId();
                        connection = new Connection_SQL();
                        String sql = "DELETE From HAMBURGER where IDHAMBURGER=?";
                        try {
                            PreparedStatement statement = connection.SQLconnection().prepareStatement(sql);
                            statement.setInt(1, idHumberger);
                            int rowDELETE = statement.executeUpdate();
                            if (rowDELETE > 0) {
                                Toast.makeText(UpdateProduct.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UpdateProduct.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                            }
                            statement.close();
                            connection.SQLconnection().close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            Toast.makeText(UpdateProduct.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });


        cardViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void showThongTinCu() {
        //gán lại các trường dữ liệu của sản phẩm vào tv
        ed_name.getEditText().setText(hamburger.getTen());
        ed_mo_ta_ngan.getEditText().setText(hamburger.getMoTaNgan());
        ed_mo_ta_chi_tiet.getEditText().setText(hamburger.getMoTaChiTiet());
        ed_gia.getEditText().setText(hamburger.getGiaTien() + "");
        ed_soluong.getEditText().setText(hamburger.getSoLuong() + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img_pr.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean validateUpdate() {
        String namePr = ed_name.getEditText().getText().toString().trim();
        String mo_ta_ngan = ed_mo_ta_ngan.getEditText().getText().toString().trim();
        String mo_ta_chi_tiet = ed_mo_ta_chi_tiet.getEditText().getText().toString().trim();
        String gia = ed_gia.getEditText().getText().toString().trim();
        String soLuong = ed_soluong.getEditText().getText().toString().trim();


        if (namePr.isEmpty()) {
            ed_name.setError("Không được để trống tên sản phẩm");
            ed_mo_ta_ngan.setError(null);
            ed_mo_ta_chi_tiet.setError(null);
            ed_gia.setError(null);
            ed_soluong.setError(null);
            return false;
        } else if (namePr.length() >= 11) {
            ed_name.setError("Tên sản phẩm phải < 11 kí tự");
            ed_mo_ta_ngan.setError(null);
            ed_mo_ta_chi_tiet.setError(null);
            ed_gia.setError(null);
            ed_soluong.setError(null);
            return false;
        } else if (mo_ta_ngan.isEmpty()) {
            ed_name.setError(null);
            ed_mo_ta_ngan.setError("Không được để trống mô tả ngắn");
            ed_mo_ta_chi_tiet.setError(null);
            ed_gia.setError(null);
            ed_soluong.setError(null);
            return false;
        } else if (mo_ta_chi_tiet.isEmpty()) {
            ed_name.setError(null);
            ed_mo_ta_ngan.setError(null);
            ed_mo_ta_chi_tiet.setError("Không được để trống mô tả chi tiết");
            ed_gia.setError(null);
            ed_soluong.setError(null);
            return false;
        } else if (gia.isEmpty()) {
            ed_name.setError(null);
            ed_mo_ta_ngan.setError(null);
            ed_mo_ta_chi_tiet.setError(null);
            ed_gia.setError("Không được để trống giá tiền");
            ed_soluong.setError(null);
            return false;
        } else if (Double.parseDouble(gia) <= 0) {
            ed_name.setError(null);
            ed_mo_ta_ngan.setError(null);
            ed_mo_ta_chi_tiet.setError(null);
            ed_gia.setError("Giá tiền phải lớn hơn 0");
            ed_soluong.setError(null);
            return false;
        } else if (soLuong.isEmpty()) {
            ed_name.setError(null);
            ed_mo_ta_ngan.setError(null);
            ed_mo_ta_chi_tiet.setError(null);
            ed_gia.setError(null);
            ed_soluong.setError("Không được để trống số lượng");
            return false;
        }
        return true;
    }

    private void anhxa() {
        cardViewBack = findViewById(R.id.cv_backUpdatePR);
        ed_name = findViewById(R.id.edNameProduct);
        ed_mo_ta_ngan = findViewById(R.id.ed_mo_ta_ngan);
        ed_mo_ta_chi_tiet = findViewById(R.id.edmo_ta_chi_tiet_Product);
        ed_gia = findViewById(R.id.edGia_Product);
        ed_soluong = findViewById(R.id.edSoLuongProduct);
        img_pr = findViewById(R.id.imgProduct);
        btn_capnhat = findViewById(R.id.btnCapNhat);
        btn_xoa = findViewById(R.id.btnXoa);
    }
}
