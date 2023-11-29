package toanndph37473.com.example.duanoderfoodnhom9.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import toanndph37473.com.example.duanoderfoodnhom9.Activity.LoginActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Activity.RechargeMoneyActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.adapterSideshow;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.Model.sideshow;
import toanndph37473.com.example.duanoderfoodnhom9.R;


public class UserFragment extends Fragment {
    private TextView tvNameUser, tvPhoneUser, tvPasswordUser, tvEmailUser, tvAddressUser, tvViTien, tvNapThemTien;
    private ImageView imgAvatarUser, btnUpdateUser, imgUpdateName, imgUpdateEmail, imgUpdateDiaChi;
    private Button btn_logout;
    private final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private Uri filePath;
    private String encodedImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNameUser = view.findViewById(R.id.tvNameUser);
        tvPhoneUser = view.findViewById(R.id.tvPhoneUser);
        tvPasswordUser = view.findViewById(R.id.tvPasswordUser);
        tvEmailUser = view.findViewById(R.id.tvEmailUser);
        tvAddressUser = view.findViewById(R.id.tvAddressUser);
        tvViTien = view.findViewById(R.id.tvViTien);
        tvNapThemTien = view.findViewById(R.id.tvNapTien);
        imgAvatarUser = view.findViewById(R.id.imgAvatarUser);
        btnUpdateUser = view.findViewById(R.id.btnUpdateUser);
        btn_logout = view.findViewById(R.id.btnlogout);
        imgUpdateName = view.findViewById(R.id.img_updateName);
        imgUpdateEmail = view.findViewById(R.id.img_updateEmail);
        imgUpdateDiaChi = view.findViewById(R.id.img_updateDiaChi);

        tvNapThemTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RechargeMoneyActivity.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.app_name);
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất ?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                        UserSession.clearUser(getContext());
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        Users users = UserSession.getCurrentUser(getContext());
        tvNameUser.setText("Tên người dùng: " + users.getTen());
        tvPhoneUser.setText("Số điện thoại: " + users.getSoDienThoai());
        tvPasswordUser.setText("Mật Khẩu: " + users.getMatKhau());
        tvEmailUser.setText("Email: " + users.getEmail());
        tvAddressUser.setText("Địa Chỉ: " + users.getDiaChi());
        tvPhoneUser.setText(users.getSoDienThoai());
//        tvViTien.setText("Số dư ví: $"+users.getViTien());
        //xu li so du vi
        if (users.getHinhAnh() == null) {
            imgAvatarUser.setImageResource(R.drawable.avtuser);
        } else {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
            opts.inMutable = true;
            byte[] decodedString = Base64.decode(users.getHinhAnh(), Base64.DEFAULT);
            Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
            myBitmap.setHasAlpha(true);
            imgAvatarUser.setImageBitmap(myBitmap);
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS").child(String.valueOf(users.getIdUser())).child("viTien");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double viTien = snapshot.getValue(double.class);
                String v = String.valueOf(viTien);
                tvViTien.setText("Số dư ví: $" + v);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDMK();
            }
        });

        imgUpdateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdateName();
            }
        });
        imgUpdateDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdateDiaChi();
            }
        });
        imgUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEmail();
            }
        });
        imgAvatarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


    }



    private void showDialogEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setNegativeButton("Cập nhật", null).setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_email, null);

        TextInputLayout ed_updateDiaChi = view.findViewById(R.id.ed_updateEmail);
        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = ed_updateDiaChi.getEditText().getText().toString().trim();

                if (Email.isEmpty()) {
                    ed_updateDiaChi.setError("Không được để trống");
                }else {
                    ed_updateDiaChi.setError(null);
                    try {
                        Connection_SQL connection_sql = new Connection_SQL();
                        String sql = "UPDATE USERS " +
                                "SET EMAIL = ? " +
                                "WHERE IDUSERS = ?";
                        PreparedStatement stm = connection_sql.SQLconnection().prepareStatement(sql);
                        stm.setString(1, Email);
                        stm.setInt(2, UserSession.getCurrentUser(getContext()).getIdUser());
                        int row = stm.executeUpdate();
                        if (row > 0) {
                            Toast.makeText(getContext(), "Đổi Email thành công", Toast.LENGTH_SHORT).show();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS").child(String.valueOf(UserSession.getCurrentUser(getContext()).getIdUser()));
                            ref.child("email").setValue(Email);
                            tvEmailUser.setText(Email);
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "Đổi thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void showDialogUpdateDiaChi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setNegativeButton("Cập nhật", null).setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_diachi, null);

        TextInputLayout ed_updateDiaChi = view.findViewById(R.id.ed_updateDiaChi);
        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diaChi = ed_updateDiaChi.getEditText().getText().toString().trim();

                if (diaChi.isEmpty()) {
                    ed_updateDiaChi.setError("Không được để trống");
                }else {
                    ed_updateDiaChi.setError(null);
                    try {
                        Connection_SQL connection_sql = new Connection_SQL();
                        String sql = "UPDATE USERS " +
                                "SET DIACHI = ? " +
                                "WHERE IDUSERS = ?";
                        PreparedStatement stm = connection_sql.SQLconnection().prepareStatement(sql);
                        stm.setString(1, diaChi);
                        stm.setInt(2, UserSession.getCurrentUser(getContext()).getIdUser());
                        int row = stm.executeUpdate();

                        if (row > 0) {
                            Toast.makeText(getContext(), "Đổi địa chỉ thành công", Toast.LENGTH_SHORT).show();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS").child(String.valueOf(UserSession.getCurrentUser(getContext()).getIdUser()));
                            ref.child("diaChi").setValue(diaChi);
                            tvAddressUser.setText(diaChi);
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "Đổi thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void showDialogUpdateName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setNegativeButton("Cập nhật", null).setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_name, null);

        TextInputLayout ed_updateName = view.findViewById(R.id.ed_updateName);
        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = ed_updateName.getEditText().getText().toString().trim();

                if (Name.isEmpty()) {
                    ed_updateName.setError("Không được để trống");
                } else if (Name.length() > 30) {
                    ed_updateName.setError("Tên không được quá 30 kí tự");
                } else {
                    try {
                        Connection_SQL connection_sql = new Connection_SQL();
                        String sql = "UPDATE USERS " +
                                "SET TEN = ? " +
                                "WHERE IDUSERS = ?";
                        PreparedStatement stm = connection_sql.SQLconnection().prepareStatement(sql);
                        stm.setString(1, Name);
                        stm.setInt(2, UserSession.getCurrentUser(getContext()).getIdUser());
                        int row = stm.executeUpdate();

                        if (row > 0) {
                            Toast.makeText(getContext(), "Đổi tên thành công", Toast.LENGTH_SHORT).show();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS").child(String.valueOf(UserSession.getCurrentUser(getContext()).getIdUser()));
                            ref.child("ten").setValue(Name);
                            tvNameUser.setText(Name);
                            alertDialog.dismiss();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                imgAvatarUser.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Connection_SQL connection_sql = new Connection_SQL();
                PreparedStatement ptm = connection_sql.SQLconnection().prepareStatement("UPDATE USERS SET HINHANH = ? WHERE IDUSERS = ?");
                ptm.setString(1, encodedImage);
                ptm.setInt(2, UserSession.getCurrentUser(getContext()).getIdUser());
                ptm.executeUpdate();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS")
                        .child(String.valueOf(UserSession.getCurrentUser(getContext()).getIdUser()))
                        .child("hinhAnh");
                ref.setValue(encodedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogDMK() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setNegativeButton("Cập nhật", null).setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);

        TextInputLayout ed_MatKhauCu_out, ed_matKhauMoi_out, ed_NhapLaiMatKhauMoi_out;
        TextInputEditText ed_matKhauCu, ed_matKhauMoi, ed_NhapLaiMatKhauMoi;

        ed_MatKhauCu_out = view.findViewById(R.id.ed_DMK_matKhauCu_out);
        ed_matKhauMoi_out = view.findViewById(R.id.ed_DMK_matKhauMoi_out);
        ed_NhapLaiMatKhauMoi_out = view.findViewById(R.id.ed_DMK_NhapLaiMatKhauMoi_out);
        ed_matKhauCu = view.findViewById(R.id.ed_DMK_matKhauCu);
        ed_NhapLaiMatKhauMoi = view.findViewById(R.id.ed_DMK_NhapLaiMatKhauMoi);
        ed_matKhauMoi = view.findViewById(R.id.ed_DMK_matkhauMoi);


        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matKhauCu = ed_matKhauCu.getText().toString().trim();
                String matKhauMoi = ed_matKhauMoi.getText().toString().trim();
                String nhapLaiMK = ed_NhapLaiMatKhauMoi.getText().toString().trim();

                if (matKhauCu.equals("")) {
                    ed_MatKhauCu_out.setError("Không được để trống");
                    ed_matKhauMoi_out.setError(null);
                    ed_NhapLaiMatKhauMoi_out.setError(null);
                } else if (matKhauMoi.equals("")) {
                    ed_MatKhauCu_out.setError(null);
                    ed_matKhauMoi_out.setError("Không được để trống");
                    ed_NhapLaiMatKhauMoi_out.setError(null);
                } else if (nhapLaiMK.equals("")) {
                    ed_MatKhauCu_out.setError(null);
                    ed_matKhauMoi_out.setError(null);
                    ed_NhapLaiMatKhauMoi_out.setError("Không được để trống");
                } else if (!matKhauMoi.equals(nhapLaiMK)) {
                    ed_MatKhauCu_out.setError(null);
                    ed_matKhauMoi_out.setError("Mật khẩu không trùng nhau");
                    ed_NhapLaiMatKhauMoi_out.setError("Mật khẩu không trùng nhau");
                } else {
                    try {
                        Connection_SQL connection_sql = new Connection_SQL();
                        String sql = "UPDATE USERS " +
                                "SET MATKHAU = ? " +
                                "WHERE IDUSERS = ?";
                        PreparedStatement stm = connection_sql.SQLconnection().prepareStatement(sql);
                        stm.setString(1, matKhauMoi);
                        stm.setInt(2, UserSession.getCurrentUser(getContext()).getIdUser());
                        int row = stm.executeUpdate();

                        if (row > 0) {
                            Toast.makeText(getContext(), "Doi mat khau thanh cong ", Toast.LENGTH_SHORT).show();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS").child(String.valueOf(UserSession.getCurrentUser(getContext()).getIdUser()));
                            ref.child("matKhau").setValue(matKhauMoi);
                            alertDialog.dismiss();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}
