package ph29152.fptpoly.duanoderfoodnhom1.AdminAdapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ph29152.fptpoly.duanoderfoodnhom1.Activity.Admin.TopDetailActivity;
import ph29152.fptpoly.duanoderfoodnhom1.Model.Top;
import ph29152.fptpoly.duanoderfoodnhom1.R;


public class TopAdapter extends RecyclerView.Adapter<TopAdapter.Topholder>{
    public TopAdapter(List<Top> list, Context context) {
        this.list = list;
        this.context = context;
    }

    List<Top> list;
    Context context;
    @NonNull
    @Override
    public Topholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.top_item,parent,false);
        Topholder topholder = new Topholder(view);
        return topholder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull Topholder holder, int position) {
        Top top = list.get(position);
        holder.tv_iduser.setText("ID KHACH HANG : " + top.getIduser() + "");
        holder.tv_ten.setText("TEN KHACH HANG :"+top.getTen());
        holder.tv_phone.setText("PHONE KHACH HANG :"+top.getSodienthoai());
        holder.tv_ttien.setText("TONG TIEN MUA :"+top.getTtien()+"");

        holder.card_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TopDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("top",top);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

        if (top.getHinhanh()==null){
            holder.img_user.setImageResource(R.drawable.avtuser);
        }
        else {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
            opts.inMutable = true;
            byte[] decodedString = Base64.decode(top.getHinhanh(), Base64.DEFAULT);
            Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
            myBitmap.setHasAlpha(true);
            holder.img_user.setImageBitmap(myBitmap);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Topholder extends RecyclerView.ViewHolder{
        ImageView img_user;
        TextView tv_iduser,tv_ten,tv_phone,tv_ttien;
        CardView card_top;
        public Topholder(@NonNull View itemView) {
            super(itemView);
            tv_iduser = itemView.findViewById(R.id.tv_iduser);
            tv_ten = itemView.findViewById(R.id.tv_ten);
            tv_phone = itemView.findViewById(R.id.tv_sdt);
            tv_ttien = itemView.findViewById(R.id.tv_ttien);
            img_user = itemView.findViewById(R.id.img_top);
            card_top = itemView.findViewById(R.id.card_top);


        }
    }
}
