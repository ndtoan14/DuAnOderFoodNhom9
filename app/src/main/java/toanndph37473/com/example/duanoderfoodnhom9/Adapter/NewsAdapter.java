package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.Interface.Service;
import toanndph37473.com.example.duanoderfoodnhom9.Model.CommentNews;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.R;
import toanndph37473.com.example.duanoderfoodnhom9.Model.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{
    Context context;
    List<News> list = new ArrayList<>();
    Service service;


    public NewsAdapter(Context context, List<News> list) {
        this.context = context;
        this.list = list;
    }

    public NewsAdapter(Context context, List<News> list, Service service) {
        this.context = context;
        this.list = list;
        this.service = service;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        News item  = list.get(position);
        holder.dao = new DAO();
        holder.listcomment.clear();
        try{
            Connection_SQL connection = new Connection_SQL();
            String sql = "SELECT TOP 2 * FROM COMMENT WHERE IDTINTUC = ? ORDER BY COMMENTID DESC";
            PreparedStatement ptm = connection.SQLconnection().prepareStatement(sql);
            ptm.setInt(1,item.getIdTinTuc());
            ResultSet rs = ptm.executeQuery();
            while(rs.next()){
                int id= rs.getInt("COMMENTID");
                String noidung = rs.getString("NOIDUNG");
                String ten = rs.getString("TENUSER");
                int idTInTuc = rs.getInt("IDTINTUC");
                String ngaycmt = rs.getString("NGAYCMT");
                String hinhanh = rs.getString("HINHANH");
                int idUsers= rs.getInt("IDUSERS");
                CommentNews items = new CommentNews(id,idUsers,idTInTuc,noidung,ngaycmt,ten,hinhanh);
                holder.listcomment.add(items);
            }
            rs.close();
            ptm.close();
            connection.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.adapter = new CommentAdapter(holder.listcomment,context);
        holder.rcv.setAdapter(holder.adapter);
        holder.adapter.notifyDataSetChanged();
        final int a = position;
        holder.layoutComment.setVisibility(View.GONE);
        holder.tvTieuDe.setText(item.getTieuDe());
        holder.tvNoiDung.setText(item.getNoiDung());
        holder.tvNgayDangTin.setText(item.getNgayDangTin());
        holder.tvLikeCount.setText(""+item.getLikeCount());
        holder.tvCommentCount.setText(""+item.getCommentCount());
        holder.tvShareCount.setText(""+item.getShareCount());
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        opts.inMutable = true;
        byte[] decodedString = Base64.decode(item.getHinhAnh(), Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
        myBitmap.setHasAlpha(true);
        holder.imgNews.setImageBitmap(myBitmap);
        //xu li nut like
        // Lấy trạng thái Like của item tại vị trí position
        boolean isLiked = item.isIsliked();

        // Thiết lập trạng thái Like và màu sắc cho nút Like
        if (isLiked) {
            holder.imgLike.setImageResource(R.drawable.heartred);
        } else {
            holder.imgLike.setImageResource(R.drawable.heart);
        }
        holder.linearShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieuDe = item.getTieuDe();
                String noidung = item.getNoiDung();
                String ngaydang = item.getNgayDangTin();

                String url_img = "https://www.tasteofhome.com/wp-content/uploads/2018/01/exps28800_UG143377D12_18_1b_RMS.jpg";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, tieuDe);
                shareIntent.putExtra(Intent.EXTRA_TEXT, tieuDe + "\n" + noidung +"\n"+ ngaydang +"\n" + "\n" + url_img);
                Intent intent1 = Intent.createChooser(shareIntent,null);

                    try{
                        Connection_SQL connection_sql = new Connection_SQL();
                        String updateNews = "UPDATE TINTUC SET SHARECOUNT = SHARECOUNT + ? WHERE IDTINTUC = ?";
                        PreparedStatement statement = connection_sql.SQLconnection().prepareStatement(updateNews);
                        statement.setInt(1,1);
                        statement.setInt(2,list.get(a).getIdTinTuc());
                        int row = statement.executeUpdate();
                        if(row>0){
                            int likeCount = list.get(a).getLikeCount() + 1;
                            list.get(a).setLikeCount(likeCount);
                            notifyDataSetChanged();
                        }
                        statement.close();
                        connection_sql.SQLconnection().close();
                        try{
                        context.startActivity(intent1);
                            } catch (android.content.ActivityNotFoundException ex) {
                             // Thông báo nếu không cài đặt Zalo trên điện thoại.
                                Toast.makeText(context, "Ứng dụng zalo chưa được cài đặt trên máy bạn!", Toast.LENGTH_SHORT).show();
                    }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

            }
        });
        holder.linearComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layoutComment.setVisibility(View.VISIBLE);
            }
        });
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidungcmt = holder.edComment.getText().toString().trim();
                if(noidungcmt.isEmpty()){
                    Toast.makeText(context, "rong", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    try{
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        int second = calendar.get(Calendar.SECOND);
// Hiển thị thời điểm hiện tại
                        String currentTime = "Ngày " + day + "/" + (month + 1) + "/" + year + " " + hour + ":" + minute + ":" + second;

                        Connection_SQL connection_sql = new Connection_SQL();
                        String insertComment = "INSERT INTO COMMENT(IDTINTUC,NOIDUNG,NGAYCMT,IDUSERS,TENUSER,HINHANH) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection_sql.SQLconnection().prepareStatement(insertComment);
                        preparedStatement.setInt(1,item.getIdTinTuc());
                        preparedStatement.setString(2,noidungcmt);
                        preparedStatement.setString(3,currentTime);
                        preparedStatement.setInt(4, UserSession.getCurrentUser(context).getIdUser());
                        preparedStatement.setString(5,UserSession.getCurrentUser(context).getTen());
                        preparedStatement.setString(6,UserSession.getCurrentUser(context).getHinhAnh());
                        int row = preparedStatement.executeUpdate();
                        if(row>0){
                            holder.edComment.setText("");
                                String updateNews = "UPDATE TINTUC SET COMMENTCOUNT = COMMENTCOUNT + ? WHERE IDTINTUC = ?";
                                PreparedStatement statement = connection_sql.SQLconnection().prepareStatement(updateNews);
                                statement.setInt(1,1);
                                statement.setInt(2,list.get(a).getIdTinTuc());
                                int rows = statement.executeUpdate();
                                if(rows>0){
                                    int commentCount = list.get(a).getCommentCount() + 1;
                                    list.get(a).setCommentCount(commentCount);
                                    notifyDataSetChanged();
                                }

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTieuDe, tvNoiDung, tvNgayDangTin, tvLikeCount,tvCommentCount,tvShareCount;
        RecyclerView rcv;
        ImageView imgNews,imgAvatarUser,btnComment;
        EditText edComment;
        LinearLayout linearLike,linearComment,linearShare;
        private boolean isLiked;
        ImageView imgLike;
        ConstraintLayout layoutComment;
        List<CommentNews> listcomment = new ArrayList<>();
        CommentAdapter adapter;
        DAO dao ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTieuDe = itemView.findViewById(R.id.tvTieuDeItem);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDungItem);
            tvNgayDangTin = itemView.findViewById(R.id.tvNgayDangTin);
            imgNews = itemView.findViewById(R.id.imgNews);
            tvLikeCount= itemView.findViewById(R.id.tvLike);
            tvCommentCount= itemView.findViewById(R.id.tvComment);
            tvShareCount= itemView.findViewById(R.id.tvShare);
            linearLike = itemView.findViewById(R.id.linearLike);
            linearComment = itemView.findViewById(R.id.linearComment);
            linearShare = itemView.findViewById(R.id.linearShare);
            imgLike= itemView.findViewById(R.id.imgLike);
            layoutComment = itemView.findViewById(R.id.layoutComment);
            imgAvatarUser=itemView.findViewById(R.id.imgAvtUser);
            edComment=itemView.findViewById(R.id.edComment);
            btnComment=itemView.findViewById(R.id.btnComment);
            rcv=itemView.findViewById(R.id.rcvComment);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
            linearLayoutManager1.setOrientation(RecyclerView.VERTICAL);
            isLiked = false;
            linearLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        isLiked = !isLiked;
                        service.onLikeClick(position);

                        if(isLiked==true){
                            try{
                                Connection_SQL connection_sql = new Connection_SQL();
                                String updateNews = "UPDATE TINTUC SET LIKECOUNT = LIKECOUNT + ? WHERE IDTINTUC = ?";
                                PreparedStatement statement = connection_sql.SQLconnection().prepareStatement(updateNews);
                                statement.setInt(1,1);
                                statement.setInt(2,list.get(position).getIdTinTuc());
                                int row = statement.executeUpdate();
                                if(row>0){
                                    int likeCount = list.get(position).getLikeCount() + 1;
                                    list.get(position).setLikeCount(likeCount);
                                    notifyDataSetChanged();
                                }
                                statement.close();
                                connection_sql.SQLconnection().close();


                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            // Update the number of likes in the database
                            try {
                                Connection_SQL connection_sql = new Connection_SQL();
                                String updateNews = "UPDATE TINTUC SET LIKECOUNT = LIKECOUNT - ? WHERE IDTINTUC = ?";
                                PreparedStatement statement = connection_sql.SQLconnection().prepareStatement(updateNews);
                                statement.setInt(1, 1);
                                statement.setInt(2, list.get(position).getIdTinTuc());
                                int row = statement.executeUpdate();
                                statement.close();
                                connection_sql.SQLconnection().close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // Update the number of likes in the adapter
                            int likeCount = list.get(position).getLikeCount() - 1;
                            list.get(position).setLikeCount(likeCount);
                            notifyDataSetChanged();
                        }
        }
                }
            });
        }
    }
}
