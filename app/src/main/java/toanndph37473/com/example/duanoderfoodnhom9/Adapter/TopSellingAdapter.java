package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.BurgerDetailActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Activity.CartActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.MainActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class TopSellingAdapter extends RecyclerView.Adapter<TopSellingAdapter.MyViewHolder>{
    List<Hamburger> list = new ArrayList<>();
    Context context;

    public TopSellingAdapter(List<Hamburger> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_burger,parent,false);
        return new MyViewHolder(view);    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Hamburger item = list.get(position);
        holder.tvTenSPact.setText(item.getTen());
        holder.tvGiaTienact.setText(""+item.getGiaTien());
        holder.tvMoTaNgan.setText(item.getMoTaNgan());
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        opts.inMutable = true;
        byte[] decodedString = Base64.decode(item.getHinhAnh(), Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length,opts);
        myBitmap.setHasAlpha(true);
        holder.imgBurger.setImageBitmap(myBitmap);
        //kiem tra xem co khuyen mai khong
        if(item.getGiaKM()<=0){
            holder.tvGiaKM.setVisibility(View.GONE);
//            holder.tvGiaTien.setPaintFlags(holder.tvGiaTien.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
//            holder.tvGiaTien.setPaintFlags(holder.tvGiaTien.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvGiaTienact.setPaintFlags(holder.tvGiaTienact.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvGiaKM.setVisibility(View.VISIBLE);
            holder.tvGiaKM.setText("$"+Double.parseDouble(String.valueOf(item.getGiaKM())));
        }

        holder.cardViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users user = UserSession.getCurrentUser(context);
                int userId = user.getIdUser();
                try {
                    Connection_SQL connection_sql = new Connection_SQL();

                    // Kiểm tra sản phẩm đã có trong giỏ hàng hay chưa
                    String queryCheck = "SELECT * FROM DANHSACHSANPHAM WHERE IDUSERS=? AND IDHAMBURGER=?";
                    PreparedStatement pstmtCheck = connection_sql.SQLconnection().prepareStatement(queryCheck);
                    pstmtCheck.setInt(1, userId);
                    pstmtCheck.setInt(2, item.getId());
                    ResultSet rs = pstmtCheck.executeQuery();

                    if (rs.next()) {
                        // Sản phẩm đã có trong giỏ hàng, tăng số lượng sản phẩm lên
                        int sl = rs.getInt("SOLUONG");
                        String queryUpdate = "UPDATE DANHSACHSANPHAM SET SOLUONG=? WHERE IDUSERS=? AND IDHAMBURGER=?";
                        PreparedStatement pstmtUpdate = connection_sql.SQLconnection().prepareStatement(queryUpdate);
                        pstmtUpdate.setInt(1, sl+1);
                        pstmtUpdate.setInt(2, userId);
                        pstmtUpdate.setInt(3, item.getId());
                        pstmtUpdate.executeUpdate();
                        Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        // Sản phẩm chưa có trong giỏ hàng, thêm sản phẩm mới vào giỏ hàng
                        String queryInsert = "INSERT INTO DANHSACHSANPHAM (IDUSERS, IDHAMBURGER, MADANHSACHSANPHAM, TENHAMBURGER, SOLUONG, GIATIEN, ANHSANPHAM) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement pstmtInsert = connection_sql.SQLconnection().prepareStatement(queryInsert);
                        pstmtInsert.setInt(1, userId);
                        pstmtInsert.setInt(2, item.getId());
                        pstmtInsert.setInt(3, CartActivity.maDssp);
                        pstmtInsert.setString(4, item.getTen());
                        pstmtInsert.setInt(5, 1);
                        pstmtInsert.setDouble(6, item.getGiaTien());
                        pstmtInsert.setString(7, item.getHinhAnh());
                        pstmtInsert.executeUpdate();
                        Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        String soluongtronggio = (String) MainActivity.tvSoLuongGioHang.getText();
                        int soluong = Integer.parseInt(soluongtronggio)+1;
                        MainActivity.tvSoLuongGioHang.setText(String.valueOf(soluong));
                    }

                    rs.close();
                    pstmtCheck.close();
                    connection_sql.SQLconnection().close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        //xu li so luong
        if(item.getSoLuong()>0){
            holder.imgSoldOut.setVisibility(View.GONE);
            holder.constraintItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), BurgerDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("burger", item);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });
        }else{
            holder.imgSoldOut.setVisibility(View.VISIBLE);
            holder.constraintItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Xin loi, san pham hien tai dang het hang", Toast.LENGTH_SHORT).show();
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenSPact, tvMoTaNgan, tvGiaTienact,tvGiaKM;
        ImageView imgBurger,imgSoldOut;
        CardView cardViewList;
        ConstraintLayout constraintItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGiaTienact = itemView.findViewById(R.id.tvGiaTienCheckp);
            tvMoTaNgan = itemView.findViewById(R.id.tvMotaNganCheckp);
            tvTenSPact = itemView.findViewById(R.id.tvTenSPCheckp);
            imgBurger = itemView.findViewById(R.id.imgBurgerCheckp);
            cardViewList = itemView.findViewById(R.id.btnThemVaoGioact);
            constraintItem = itemView.findViewById(R.id.constraintItemCheck);
            imgSoldOut=itemView.findViewById(R.id.imgSoldOut);
            tvGiaKM=itemView.findViewById(R.id.tvGiaKM);

        }

    }
}
