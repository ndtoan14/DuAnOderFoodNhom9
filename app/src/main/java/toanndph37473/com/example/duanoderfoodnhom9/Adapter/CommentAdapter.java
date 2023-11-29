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

import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Model.CommentNews;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>{
    List<CommentNews> list = new ArrayList<>();
    Context context;

    public CommentAdapter(List<CommentNews> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CommentNews item = list.get(position);
        holder.tvTenuserComment.setText(item.getTenUser());
        holder.tvNoiDungComment.setText(item.getNoiDung());
        holder.tvNgaycomment.setText(item.getNgayComment());
//        if(UserSession.getCurrentUser(context).getHinhAnh()==null){
//            holder.imgAvatarUsersComment.setImageResource(R.drawable.avtuser);
//        }else{
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
            opts.inMutable = true;
            byte[] decodedString = Base64.decode(UserSession.getCurrentUser(context).getHinhAnh(), Base64.DEFAULT);
            Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
            myBitmap.setHasAlpha(true);
            holder.imgAvatarUsersComment.setImageBitmap(myBitmap);
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAvatarUsersComment;
        TextView tvTenuserComment,tvNgaycomment,tvNoiDungComment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatarUsersComment=itemView.findViewById(R.id.imgAvatarUsersComment);
            tvTenuserComment=itemView.findViewById(R.id.tvTenuserComment);
            tvNgaycomment=itemView.findViewById(R.id.tvNgaycomment);
            tvNoiDungComment=itemView.findViewById(R.id.tvNoiDungComment);
        }
    }
}
