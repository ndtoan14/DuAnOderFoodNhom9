package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Model.ListProductCart;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class DSSPinNewOrderAdapter extends RecyclerView.Adapter<DSSPinNewOrderAdapter.MyViewHolder>{
    List<ListProductCart> list = new ArrayList<>();
    Context context;

    public DSSPinNewOrderAdapter(List<ListProductCart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListProductCart item = list.get(position);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAnhSanPham;
        TextView tenSanPham,tvGiaTien,tvSoLuong;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhSanPham = itemView.findViewById(R.id.imgAnhSanPhamPay);
            tenSanPham = itemView.findViewById(R.id.tvTenSanPhamPay);
            tvGiaTien = itemView.findViewById(R.id.tvGiaTienPayed);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuongPayed);

        }
    }
}
