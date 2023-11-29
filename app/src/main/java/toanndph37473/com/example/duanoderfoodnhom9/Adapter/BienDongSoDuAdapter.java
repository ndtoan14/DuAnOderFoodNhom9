package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Model.HoaDonNaptien;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class BienDongSoDuAdapter extends RecyclerView.Adapter<BienDongSoDuAdapter.MyViewHolder>{
    List<HoaDonNaptien> list = new ArrayList<>();
    Context context;

    public BienDongSoDuAdapter(List<HoaDonNaptien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bien_dong_so_du, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HoaDonNaptien item = list.get(position);
        if(item.getTrangThai()==1){
            holder.tvTrangThai.setText("Yêu cầu đang được xử lý");
        }else if(item.getTrangThai()==2){
            holder.tvTrangThai.setText("Thành công");
            holder.tvTrangThai.setTextColor(Color.parseColor("#4CAF50"));
        }else{
            holder.tvTrangThai.setText("Đã bị huỷ \nLý do: Chưa nhận được bên admin, để được hỗ trợ vui lòng liên hệ admin");
            holder.tvTrangThai.setTextColor(Color.RED);
        }
        holder.tvMaHoaDon.setText(""+item.getIdHoaDonNap());
        holder.tvSoTienNap.setText(""+item.getSoTienNap());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaHoaDon,tvSoTienNap,tvTrangThai,tvSoDu;
        CardView cardBienDong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHoaDon=itemView.findViewById(R.id.tvMaHoaDon);
            tvSoTienNap=itemView.findViewById(R.id.tvSoTienNap);
            tvTrangThai=itemView.findViewById(R.id.tvTrangThai);
//            tvSoDu =itemView.findViewById(R.id.tvSoDu);
            cardBienDong = itemView.findViewById(R.id.cardBienDong);

        }
    }
}
