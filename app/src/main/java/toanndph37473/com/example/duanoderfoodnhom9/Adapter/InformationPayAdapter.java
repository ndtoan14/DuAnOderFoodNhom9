package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.PayBillActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.InformationPay;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class InformationPayAdapter extends RecyclerView.Adapter<InformationPayAdapter.MyViewHolder>{
    private List<InformationPay> list = new ArrayList<>();
    private Context context;

    public InformationPayAdapter(List<InformationPay> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InformationPay item = list.get(position);
        holder.tvTen.setText(item.getTen());
        holder.tvSDT.setText(item.getSoDienThoai());
        holder.tvDiachi.setText(item.getDiaChi());
        try{
            Connection_SQL connection_sql = new Connection_SQL();
            String sql = "SELECT * FROM USERS WHERE IDUSERS = ?";
            PreparedStatement ptm = connection_sql.SQLconnection().prepareStatement(sql);
            ptm.setInt(1, UserSession.getCurrentUser(context).getIdUser());
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                if(!rs.getString("TEN").equalsIgnoreCase(item.getTen())
                && !rs.getString("DIACHI").equalsIgnoreCase(item.getDiaChi())
                && !rs.getString("SODIENTHOAI").equalsIgnoreCase(item.getSoDienThoai())){
                    holder.tvdiachimacdinh.setVisibility(View.GONE);
                }else{
                    holder.tvdiachimacdinh.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.itemInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PayBillActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("information",item);
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
        TextView tvTen,tvSDT,tvDiachi,tvUpdate, tvdiachimacdinh;
        RelativeLayout itemInformation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen=itemView.findViewById(R.id.tvTenInformation);
            tvDiachi=itemView.findViewById(R.id.tvAddressInformation);
            tvSDT=itemView.findViewById(R.id.tvSDTInformation);
            tvUpdate=itemView.findViewById(R.id.tvUpdateInformation);
            tvdiachimacdinh =itemView.findViewById(R.id.tvDiachidangchon);
            itemInformation = itemView.findViewById(R.id.itemInformation);
        }
    }
}
