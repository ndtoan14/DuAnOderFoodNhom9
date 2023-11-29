package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.ThongKeThang;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class ThongKeThangAdapter extends RecyclerView.Adapter<ThongKeThangAdapter.MyViewHolder>{

    List<ThongKeThang> list = new ArrayList<>();
    Context context;
    List<Users> listUser = new ArrayList<>();
    Connection_SQL connection_sql;

    public ThongKeThangAdapter(List<ThongKeThang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_ke_thang,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int a = position;
        ThongKeThang thongKeThang = list.get(position);
        holder.tvTenSanPham.setText(thongKeThang.getTenHamburger());
        holder.tvGiaTien.setText("Đơn giá: "+thongKeThang.getDonGia());
        holder.tvSoSanPhamDaBan.setText("Số lượng đã bán: "+thongKeThang.getTongDaBan());

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        opts.inMutable = true;
        byte[] decodedString = Base64.decode(thongKeThang.getHinhAnhHamburger(), Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
        myBitmap.setHasAlpha(true);
        holder.imgBurger.setImageBitmap(myBitmap);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTenSanPham, tvGiaTien, tvSoSanPhamDaBan;
        ImageView imgBurger;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenSanPham = itemView.findViewById(R.id.tv_ten_Hamburger_Thong_ke_thang);
            tvGiaTien = itemView.findViewById(R.id.tv_gia_Hamburger_Thong_ke_thang);
            tvSoSanPhamDaBan = itemView.findViewById(R.id.tv_So_san_pham_da_ban_Hamburger_Thong_ke_thang);
            imgBurger = itemView.findViewById(R.id.image_anh_thong_ke_thang);
        }
    }
}
