package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.ProductReviewsActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Notification;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class NotificationAdapter {
    private Context context;
    private List<Notification> list = new ArrayList<>();

    public NotificationAdapter(Context context, List<Notification> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        return new MyViewHolder(view);    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notification notification = list.get(position);
        holder.tvMaThongBao.setText("Đơn hàng đặt "+notification.getMaThongBao());
        holder.tvTongTienThongBao.setText("Tổng tiền đơn hàng: "+notification.getTongTien());
        holder.tvNoiDung.setText(notification.getNoiDung());
        if(notification.getTrangThai()==1){
            holder.cardThongBao.setCardBackgroundColor(Color.parseColor("#4CAF50"));
            holder.cardThongBao.setRadius(70);
            holder.cardThongBao.setCardElevation(8);
            holder.cardThongBao.setPadding(10,10,10,10);
        }else{
            holder.cardThongBao.setRadius(70);
            holder.cardThongBao.setCardElevation(8);
            holder.cardThongBao.setCardBackgroundColor(Color.parseColor("#ea4445"));
            holder.cardThongBao.setPadding(10,10,10,10);
        }
        holder.cardThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductReviewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("notification",notification);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaThongBao, tvNoiDung, tvTongTienThongBao;
        CardView cardThongBao;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaThongBao=itemView.findViewById(R.id.tvMaThongBao);
            tvNoiDung=itemView.findViewById(R.id.tvNoiDung);
            tvTongTienThongBao=itemView.findViewById(R.id.tvTongTienThongBao);
            cardThongBao=itemView.findViewById(R.id.cardThongBao);
        }
    }
}
