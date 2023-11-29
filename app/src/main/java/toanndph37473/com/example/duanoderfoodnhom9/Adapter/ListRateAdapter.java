package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Rate;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class ListRateAdapter extends RecyclerView.Adapter<ListRateAdapter.MyViewHolder>{
    List<Rate> list = new ArrayList<>();
    Context context;

    public ListRateAdapter(List<Rate> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhgia,parent,false);
        return new MyViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Rate item = list.get(position);
        holder.tvNoiDungDanhGia.setText(item.getNoiDung());
        holder.tvNgayDanhGia.setText(item.getNgayDanhGia());
        if(item.getRate()==1){
            holder.rate1.setImageResource(R.drawable.ic_saovang);
            holder.rate2.setImageResource(R.drawable.ic_saotrang);
            holder.rate3.setImageResource(R.drawable.ic_saotrang);
            holder.rate4.setImageResource(R.drawable.ic_saotrang);
            holder.rate5.setImageResource(R.drawable.ic_saotrang);
        }else if(item.getRate()==2){
            holder.rate1.setImageResource(R.drawable.ic_saovang);
            holder.rate2.setImageResource(R.drawable.ic_saovang);
            holder.rate3.setImageResource(R.drawable.ic_saotrang);
            holder.rate4.setImageResource(R.drawable.ic_saotrang);
            holder.rate5.setImageResource(R.drawable.ic_saotrang);
        }else if(item.getRate()==3){
            holder.rate1.setImageResource(R.drawable.ic_saovang);
            holder.rate2.setImageResource(R.drawable.ic_saovang);
            holder.rate3.setImageResource(R.drawable.ic_saovang);
            holder.rate4.setImageResource(R.drawable.ic_saotrang);
            holder.rate5.setImageResource(R.drawable.ic_saotrang);
        }else if(item.getRate()==4){
            holder.rate1.setImageResource(R.drawable.ic_saovang);
            holder.rate2.setImageResource(R.drawable.ic_saovang);
            holder.rate3.setImageResource(R.drawable.ic_saovang);
            holder.rate4.setImageResource(R.drawable.ic_saovang);
            holder.rate5.setImageResource(R.drawable.ic_saotrang);
        }else{
            holder.rate1.setImageResource(R.drawable.ic_saovang);
            holder.rate2.setImageResource(R.drawable.ic_saovang);
            holder.rate3.setImageResource(R.drawable.ic_saovang);
            holder.rate4.setImageResource(R.drawable.ic_saovang);
            holder.rate5.setImageResource(R.drawable.ic_saovang);
        }
        //lay thong tin user
        try{
            Connection_SQL connection_sql = new Connection_SQL();
            String selectUsers = "SELECT * FROM USERS WHERE IDUSERS = ? ";
            PreparedStatement ptm = connection_sql.SQLconnection().prepareStatement(selectUsers);
            ptm.setInt(1,item.getIdUsers());
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                String tenUser = rs.getString("TEN");
                String hinhAnh = rs.getString("HINHANH");
                holder.tvTenUsers.setText(tenUser);
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
                opts.inMutable = true;
                byte[] decodedString = Base64.decode(hinhAnh, Base64.DEFAULT);
                Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length,opts);
                myBitmap.setHasAlpha(true);
                holder.imgAvatarUser.setImageBitmap(myBitmap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenUsers, tvNoiDungDanhGia, tvNgayDanhGia;
        ImageView imgAvatarUser, rate1,rate2,rate3,rate4,rate5;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenUsers = itemView.findViewById(R.id.tvTenUsersdanhGia);
            tvNoiDungDanhGia = itemView.findViewById(R.id.tvNoiDungItemdanhgia);
            imgAvatarUser = itemView.findViewById(R.id.imgAvatarUsersDanhGia);
            tvNgayDanhGia = itemView.findViewById(R.id.tvNgayDangDanhGIa);
            rate1 = itemView.findViewById(R.id.rate1);
            rate2 = itemView.findViewById(R.id.rate2);
            rate3 = itemView.findViewById(R.id.rate3);
            rate4 = itemView.findViewById(R.id.rate4);
            rate5 = itemView.findViewById(R.id.rate5);
        }
    }
}
