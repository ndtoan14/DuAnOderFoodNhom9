package ph29152.fptpoly.duanoderfoodnhom1.AdminAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PublicKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Activity.Admin.DonHangBiHuyDetailActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Activity.Admin.DonHangThanhCongDetailActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Helper.Connection_SQL;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Invoice;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Users;
import ph29152.fptpoly.duanoderfoodnhom1.R;

public class RevenueStatisticAdapter extends RecyclerView.Adapter<RevenueStatisticAdapter.MyViewHolder> implements Filterable {
    List<Invoice> list = new ArrayList<>();
    List<Invoice> listOld = new ArrayList<>();
    List<Users> listUser = new ArrayList<>();

    Context context ;
    Connection_SQL connection;

    public RevenueStatisticAdapter(List<Invoice> list, Context context) {
        this.list = list;
        this.listOld = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_revenue_statistic,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Invoice invoice = list.get(position);


        holder.tvTenRevenue.setText("Tên khách hàng: "+invoice.getTenUsers());
        holder.tvDiaChiRevenue.setText("Địa chỉ: "+invoice.getDiaChi());
        holder.tvThoiGianRevenue.setText("Ngày: "+invoice.getNgayXuat());
        holder.tvSoDienThoaiRevenue.setText("Số điện thoại: "+invoice.getSoDienThoai());
        holder.tvTrangThaiRevenue.setText("Trạng thái: "+"Thành công");
        holder.tvTongTienRevenue.setText("Đơn giá: "+invoice.getTongTien());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DonHangThanhCongDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("itemDonHangThanhCong",invoice);
                bundle.putInt("idUsers",invoice.getIdUsers());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }//adapter san pham da ban => thong ke doanh thu


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvThoiGianRevenue,tvTenRevenue,tvDiaChiRevenue,tvSoDienThoaiRevenue, tvTongTienRevenue, tvTrangThaiRevenue;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvThoiGianRevenue=itemView.findViewById(R.id.tvThoiGianRevenue);
            tvTenRevenue=itemView.findViewById(R.id.tvTenUserRevenue);
            tvDiaChiRevenue=itemView.findViewById(R.id.tvDiaChiRevenue);
            tvSoDienThoaiRevenue=itemView.findViewById(R.id.tvSoDienThoaiRevenue);
            tvTongTienRevenue=itemView.findViewById(R.id.tvTongTienRevenue);
            tvTrangThaiRevenue=itemView.findViewById(R.id.tvTrangThaiRevenue);
            cardView = itemView.findViewById(R.id.cardItemDonHangThanhCong);

        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSrearch = charSequence.toString();
                if (strSrearch.isEmpty()){
                    list = listOld;
                }else {
                    List<Invoice> listNew = new ArrayList<>();
                    for (Invoice invoice : listOld){
                        if (invoice.getTenUsers().toLowerCase().contains(strSrearch.toLowerCase())){
                            listNew.add(invoice);
                        }
                    }

                    list = listNew;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<Invoice>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }





}
