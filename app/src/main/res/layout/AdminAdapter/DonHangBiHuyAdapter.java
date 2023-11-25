package ph29152.fptpoly.duanoderfoodnhom1.AdminAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Activity.Admin.DonHangBiHuyDetailActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Model.ThongkeDonHangBiHuy;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class DonHangBiHuyAdapter extends RecyclerView.Adapter<DonHangBiHuyAdapter.DonHangBiHuyViewHolder>{

    Context context;
    List<ThongkeDonHangBiHuy> list ;

    public DonHangBiHuyAdapter(Context context, List<ThongkeDonHangBiHuy> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DonHangBiHuyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang_bi_huy,parent,false);
        return new DonHangBiHuyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangBiHuyViewHolder holder, int position) {

        ThongkeDonHangBiHuy thongkeDonHangBiHuy = list.get(position);
        holder.tv_ten.setText("Tên: "+thongkeDonHangBiHuy.getTenUserHuyDonHang());
        holder.tv_diaChi.setText("Địa chỉ: "+thongkeDonHangBiHuy.getDiaChiUserHuyDonHang());
        holder.tv_Ngay.setText("Ngày: "+thongkeDonHangBiHuy.getNgayUserHuyDonHang());
        holder.tv_soDienThoai.setText("Số điện thoại: "+thongkeDonHangBiHuy.getSdtUserHuyDonHang());
        holder.tv_donGia.setText("Đơn giá: "+thongkeDonHangBiHuy.getDonGiaUserHuyDonHang());
        holder.tv_trangThai.setText("Đã hủy");
        holder.tv_lido.setText("Lí do: "+thongkeDonHangBiHuy.getLiDoUserHuyDonHang());
        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DonHangBiHuyDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemDonHangBiHuy",thongkeDonHangBiHuy);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DonHangBiHuyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ten,tv_diaChi,tv_soDienThoai,tv_Ngay,tv_donGia,tv_trangThai,tv_lido;
        CardView itemCard;
        public DonHangBiHuyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten = itemView.findViewById(R.id.tvTenUserDonHangBiHuy);
            tv_diaChi = itemView.findViewById(R.id.tvDiaChiDonHangBiHuy);
            tv_soDienThoai = itemView.findViewById(R.id.tvSoDienThoaiDonHangBiHuy);
            tv_Ngay = itemView.findViewById(R.id.tvThoiGianDonHangBiHuy);
            tv_donGia = itemView.findViewById(R.id.tvTongTienDonHangBiHuy);
            tv_trangThai = itemView.findViewById(R.id.tvTrangThaiDonHangBiHuy);
            tv_lido = itemView.findViewById(R.id.tvLiDoDonHangBiHuy);
            itemCard = itemView.findViewById(R.id.cardItemDonHangBiHuy);
        }
    }
}
