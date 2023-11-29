package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.HoaDonNaptien;
import toanndph37473.com.example.duanoderfoodnhom9.Model.RechargeMoney;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class RequestMoneyAdapter extends RecyclerView.Adapter<RequestMoneyAdapter.MyViewHolder> {
    List<HoaDonNaptien> list = new ArrayList<>();
    Context context;

    public RequestMoneyAdapter(List<HoaDonNaptien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request_money, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HoaDonNaptien item = list.get(position);
        final int a = position;
//        holder.tvIdUser.setText(item.getIdUsers()); //bug
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        opts.inMutable = true;
        byte[] decodedString = Base64.decode(item.getHinhAnh(), Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
        myBitmap.setHasAlpha(true);
        holder.imgRequest.setImageBitmap(myBitmap);

        holder.btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_nap_tien);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams winLayoutParams = window.getAttributes();
                winLayoutParams.gravity = Gravity.CENTER;
                window.setAttributes(winLayoutParams);
                TextInputLayout edSoTienNap;
                Button btnXacNhan,btnHuy;
                edSoTienNap = dialog.findViewById(R.id.edSoTienNapDialog);
                btnXacNhan = dialog.findViewById(R.id.btnXacNhanNapTienDialog);
                btnHuy = dialog.findViewById(R.id.btnHuyNapTienDialog);
                edSoTienNap.getEditText().setText(""+item.getSoTienNap());


                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tienNap = edSoTienNap.getEditText().getText().toString().trim();
                        if (tienNap.isEmpty()) {
                            edSoTienNap.setError("Vui lòng nhập số tiền nạp cho khách");
                        } else if (Double.parseDouble(tienNap) < 1) {
                            edSoTienNap.setError("Vui lòng nhập số tiền lớn hơn 0");
                        } else {

                            try {
                                Connection_SQL connection_sql = new Connection_SQL();
                                String sql = "SELECT VITIEN FROM USERS WHERE IDUSERS = ?";
                                PreparedStatement statement = connection_sql.SQLconnection().prepareStatement(sql);
                                statement.setInt(1, item.getIdUsers());
                                ResultSet rs = statement.executeQuery();
                                if (rs.next()) {
                                    double vTien = rs.getDouble("VITIEN");
                                    double viSau = vTien + Double.parseDouble(edSoTienNap.getEditText().getText().toString().trim());
                                    String capnhat = "Update Users " +
                                            "SET VITIEN = ? " +
                                            "WHERE IDUSERS = ?";
                                    PreparedStatement tm = connection_sql.SQLconnection().prepareStatement(capnhat);
                                    tm.setDouble(1, viSau);
                                    tm.setInt(2, item.getIdUsers());
                                    int row = tm.executeUpdate();
                                    if (row > 0) {
                                        Toast.makeText(context, "Update trong database thanh cong", Toast.LENGTH_SHORT).show();
                                    }
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS").child(String.valueOf(item.getIdUsers()));
                                    ref.child("viTien").setValue(viSau);
                                    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("HOADONNAPTIEN").child(String.valueOf(item.getIdHoaDonNap())).child("soTienNap");
                                    myref.setValue(edSoTienNap.getEditText().getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            // update
                                            try {
                                                String delete = "UPDATE HOADONNAPTIEN SET TRANGTHAI = ?, SOTIENNAP = ? WHERE IDHOADONNAPTIEN = ?";
                                                PreparedStatement s = connection_sql.SQLconnection().prepareStatement(delete);
                                                s.setInt(1,2);
                                                s.setDouble(2,Double.parseDouble(edSoTienNap.getEditText().getText().toString().trim()));
                                                s.setInt(3, item.getIdHoaDonNap());

                                                s.executeUpdate();
                                                // UPDATE trong firebase
                                                String idHoaDon = String.valueOf(item.getIdHoaDonNap());
                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HOADONNAPTIEN").child(idHoaDon);
                                                databaseReference.child("trangThai").setValue(2);
                                                dialog.dismiss();
                                                list.remove(a);
                                                notifyDataSetChanged();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });


                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdUser;
        ImageView imgRequest;
        Button btnXacNhan,btnHuy, btnHuyYeuCau;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvIdUser = itemView.findViewById(R.id.tvIdUserRequestMoney);
            imgRequest = itemView.findViewById(R.id.imgRequestMoney);
            btnXacNhan = itemView.findViewById(R.id.btnXacNhanRequest);
            btnHuy = itemView.findViewById(R.id.btnHuyNapTienDialog);
            btnHuyYeuCau = itemView.findViewById(R.id.btnTuChoiRequest);
        }
    }
}
