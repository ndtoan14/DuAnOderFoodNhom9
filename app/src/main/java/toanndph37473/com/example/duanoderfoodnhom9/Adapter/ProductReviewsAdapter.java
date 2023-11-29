package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.OrderHistory;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class ProductReviewsAdapter extends RecyclerView.Adapter<ProductReviewsAdapter.MyViewHolder>{
    List<OrderHistory> list = new ArrayList<>();
    Context context;
    int rate = 5;


    public ProductReviewsAdapter(List<OrderHistory> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_reviews_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderHistory item = list.get(position);
        final int a = position;

        holder.tvTenSanPhamPayed.setText(item.getTenHamburger());
        holder.tvGiaTienPayed.setText(""+item.getGiaTien());
        holder.tvSoLuongPayed.setText(""+item.getSoLuong());
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        opts.inMutable = true;
        byte[] decodedString = Base64.decode(item.getAnhSanPham(), Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
        myBitmap.setHasAlpha(true);
        holder.imgAnhSanPhamPayed.setImageBitmap(myBitmap);
        holder.star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate = 1;
                holder.star1.setImageResource(R.drawable.ic_saovang);
                holder.star2.setImageResource(R.drawable.ic_saotrang);
                holder.star3.setImageResource(R.drawable.ic_saotrang);
                holder.star4.setImageResource(R.drawable.ic_saotrang);
                holder.star5.setImageResource(R.drawable.ic_saotrang);
            }
        });
        holder.star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate = 2;
                holder.star1.setImageResource(R.drawable.ic_saovang);
                holder.star2.setImageResource(R.drawable.ic_saovang);
                holder.star3.setImageResource(R.drawable.ic_saotrang);
                holder.star4.setImageResource(R.drawable.ic_saotrang);
                holder.star5.setImageResource(R.drawable.ic_saotrang);
            }
        });
        holder.star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate = 3 ;
                holder.star1.setImageResource(R.drawable.ic_saovang);
                holder.star2.setImageResource(R.drawable.ic_saovang);
                holder.star3.setImageResource(R.drawable.ic_saovang);
                holder.star4.setImageResource(R.drawable.ic_saotrang);
                holder.star5.setImageResource(R.drawable.ic_saotrang);
            }
        });
        holder.star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate = 4;
                holder.star1.setImageResource(R.drawable.ic_saovang);
                holder.star2.setImageResource(R.drawable.ic_saovang);
                holder.star3.setImageResource(R.drawable.ic_saovang);
                holder.star4.setImageResource(R.drawable.ic_saovang);
                holder.star5.setImageResource(R.drawable.ic_saotrang);
            }
        });
        holder.star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate = 5;
                holder.star1.setImageResource(R.drawable.ic_saovang);
                holder.star2.setImageResource(R.drawable.ic_saovang);
                holder.star3.setImageResource(R.drawable.ic_saovang);
                holder.star4.setImageResource(R.drawable.ic_saovang);
                holder.star5.setImageResource(R.drawable.ic_saovang);
            }
        });

        holder.btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Bạn có chắc chắn đánh giá sản phẩm này không ? ");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String noidung = holder.edDanhGia.getEditText().getText().toString().trim();
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        int second = calendar.get(Calendar.SECOND);
// Hiển thị thời điểm hiện tại
                        String currentTime = "Ngày " + day + "/" + (month + 1) + "/" + year + " " + hour + ":" + minute + ":" + second;

                        try{
                            Connection_SQL connection_sql = new Connection_SQL();
                            String insertDanhGia = "INSERT INTO DANHGIA (IDUSERS,IDHAMBURGER,RATE,NOIDUNG,NGAYDANHGIA) VALUES (?, ?, ?, ?, ?)";
                            PreparedStatement ptm = connection_sql.SQLconnection().prepareStatement(insertDanhGia);
                            ptm.setInt(1,item.getIdUsers());
                            ptm.setInt(2,item.getIdHamBurger());
                            ptm.setDouble(3,rate);
                            ptm.setString(4,noidung);
                            ptm.setString(5,currentTime);
                            int row =ptm.executeUpdate();
                            if(row>0){
                                Log.e("DanhGia", "Danh gia thanh cong");
                                holder.btnDanhGia.setText("Đã đánh giá");
                                holder.btnDanhGia.setBackgroundColor(R.color.green);
                                holder.btnDanhGia.setEnabled(false);
                                holder.edDanhGia.setEnabled(false);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
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

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAnhSanPhamPayed,star1,star2,star3,star4,star5;
        TextView tvTenSanPhamPayed, tvGiaTienPayed, tvSoLuongPayed;
        TextInputLayout edDanhGia;
        Button btnDanhGia;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhSanPhamPayed= itemView.findViewById(R.id.imgAnhSanPhamPayed);
            star1= itemView.findViewById(R.id.star1);
            star2= itemView.findViewById(R.id.star2);
            star3= itemView.findViewById(R.id.star3);
            star4= itemView.findViewById(R.id.star4);
            star5= itemView.findViewById(R.id.star5);
            tvTenSanPhamPayed= itemView.findViewById(R.id.tvTenSanPhamPayed);
            tvGiaTienPayed= itemView.findViewById(R.id.tvGiaTienPayed);
            tvSoLuongPayed= itemView.findViewById(R.id.tvSoLuongPayed);
            edDanhGia= itemView.findViewById(R.id.edDanhGia);
            btnDanhGia= itemView.findViewById(R.id.btnDanhGia);

        }
    }
}
