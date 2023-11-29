package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.CartActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.Interface.Service;
import toanndph37473.com.example.duanoderfoodnhom9.Model.CartManager;
import toanndph37473.com.example.duanoderfoodnhom9.Model.ListProductCart;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    List<ListProductCart> list = new ArrayList<>();
    Context context ;
    static double total = 0;
    Connection_SQL connection;
    Service service;
    public static String tenHamburger;
    DAO dao;
    public static boolean checksl;


    public CartAdapter(List<ListProductCart> list, Context context, Service service) {
        this.list = list;
        this.context = context;
        this.service = service;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ListProductCart cart = list.get(position);
        dao = new DAO();
        final int a = position;
        holder.tvTenSanPhamCart.setText(cart.getTenHamburger());
        tenHamburger = cart.getTenHamburger();
        holder.tvSoLuongCart.setText(""+cart.getSoLuong());
        holder.tvGiaTienCart.setText(""+cart.getGiaTien());
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        opts.inMutable = true;
        byte[] decodedString = Base64.decode(cart.getAnhSanPham(), Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length,opts);
        myBitmap.setHasAlpha(true);
        holder.anhSanPhamCart.setImageBitmap(myBitmap);
        holder.cardCong.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n", "ResourceAsColor"})
                    @Override
                    public void onClick(View v) {
                        int sl = Integer.parseInt(holder.tvSoLuongCart.getText().toString());
                        if(Integer.parseInt(holder.tvSoLuongCart.getText().toString().trim())<=dao.getSoLuongSanPham(cart.getIdHamburger())){
                            holder.tvSoLuongCart.setTextColor(R.color.black);
                            checksl =false;
                        }
                        if(sl<dao.getSoLuongSanPham(cart.getIdHamburger())) {
                            sl = sl + 1;
                            holder.tvSoLuongCart.setText("" + sl);
                            service.addp(cart.getIdHamburger());
                        }else{
                            Toast.makeText(context, "Xin loi, chung toi hien khong con du hang de dap ung nhu cau", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        holder.cardTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.tvSoLuongCart.getText().toString())>1) {
                    int sl = Integer.parseInt(holder.tvSoLuongCart.getText().toString());
                    if(Integer.parseInt(holder.tvSoLuongCart.getText().toString().trim())<=dao.getSoLuongSanPham(cart.getIdHamburger())){
                        holder.tvSoLuongCart.setTextColor(R.color.black);
                        checksl =false;
                    }

                        sl = sl - 1;
                        holder.tvSoLuongCart.setText("" + sl);
                    if(sl==dao.getSoLuongSanPham(cart.getIdHamburger())) {
                        holder.tvSoLuongCart.setTextColor(R.color.black);
                    }
                    service.subtraction(cart.getIdHamburger());



                }
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.deleteincart(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView anhSanPhamCart,imgDelete;
        TextView tvTenSanPhamCart,tvGiaTienCart;
        TextView tvSoLuongCart;
        CardView cardTru,cardCong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhSanPhamCart=itemView.findViewById(R.id.anhSanPhamCart);
            tvTenSanPhamCart=itemView.findViewById(R.id.tvTenSanPhamCart);
            tvGiaTienCart=itemView.findViewById(R.id.tvGiaTienCart);
            tvSoLuongCart=itemView.findViewById(R.id.tvSoLuongCart);
            cardTru=itemView.findViewById(R.id.cardTru);
            cardCong=itemView.findViewById(R.id.cardCong);
            imgDelete=itemView.findViewById(R.id.imgDeleteinCart);
        }
    }


}
