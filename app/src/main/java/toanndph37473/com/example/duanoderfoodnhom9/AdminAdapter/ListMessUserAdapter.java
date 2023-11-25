package toanndph37473.com.example.duanoderfoodnhom9.AdminAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin.CallActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin.ChatAdminActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ChatAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class ListMessUserAdapter extends RecyclerView.Adapter<ListMessUserAdapter.MyViewHolder>{
    List<Users> list = new ArrayList<>();
    Context context;

    public ListMessUserAdapter(List<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mess_user, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Users u = list.get(position);
        holder.tvTenUser.setText(u.getTen());
        if(u.getHinhAnh()==null){
            holder.imgAvtUser.setImageResource(R.drawable.avtuser);
        }else{
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
            opts.inMutable = true;
            byte[] decodedString = Base64.decode(u.getHinhAnh(), Base64.DEFAULT);
            Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
//            myBitmap.setHasAlpha(true);
            holder.imgAvtUser.setImageBitmap(myBitmap);
        }
        holder.itemUserofList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ChatAdminActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", u);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.item_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), CallActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user1", u);
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
        ImageView imgAvtUser;
        TextView tvTenUser;
        CardView itemUserofList,item_call;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvtUser=itemView.findViewById(R.id.imgAvatarUserListMess);
            tvTenUser=itemView.findViewById(R.id.tvTenUserListMess);
            itemUserofList=itemView.findViewById(R.id.itemUserofList);
            item_call = itemView.findViewById(R.id.cardview_call_admin);

        }
    }
}
