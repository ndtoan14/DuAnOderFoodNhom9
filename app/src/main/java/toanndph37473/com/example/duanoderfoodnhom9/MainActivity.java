package toanndph37473.com.example.duanoderfoodnhom9;
import toanndph37473.com.example.duanoderfoodnhom9.R.id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.CartActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Fragment.ChatFragment;
import toanndph37473.com.example.duanoderfoodnhom9.Fragment.HomeFragment;
import toanndph37473.com.example.duanoderfoodnhom9.Fragment.NewsFragment;
import toanndph37473.com.example.duanoderfoodnhom9.Fragment.NotificationFragment;
import toanndph37473.com.example.duanoderfoodnhom9.Fragment.UserFragment;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Notification;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FloatingActionButton fab;
    TextView tvTieuDe;
    Class fragmentClass;
    public static Fragment fragment;
    ImageView gioHangMain;
    public static TextView tvSoLuongGioHang;
    int soluongtronggio = 0;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private ValueEventListener notificationListener;
    private DatabaseReference notificationReference;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fab=findViewById(R.id.fab);
        tvSoLuongGioHang=findViewById(R.id.tvSoLuongTrongGio);
        tvTieuDe=findViewById(R.id.tvTieuDe);
        gioHangMain =findViewById(R.id.imgGioHangMain);
//        langNgheThongBao();


        gioHangMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentClass= NotificationFragment.class;
                tvTieuDe.setText("Notification");
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
                }
            }
        });
//        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
//            switch (item.getItemId()){
//                case R.id.home:
//                    fragmentClass= HomeFragment.class;
//                    tvTieuDe.setText("Yummi Food");
//                    try {
//                        fragment = (Fragment) fragmentClass.newInstance();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (fragment != null) {
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
//                    }
//
//                    break;
//                case R.id.news:
//                    fragmentClass= NewsFragment.class;
//                    tvTieuDe.setText("News");
//                    try {
//                        fragment = (Fragment) fragmentClass.newInstance();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (fragment != null) {
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
//                    }
//                    break;
//
//                case R.id.user:
//                    fragmentClass= UserFragment.class;
//                    tvTieuDe.setText("User");
//                    try {
//                        fragment = (Fragment) fragmentClass.newInstance();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (fragment != null) {
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
//                    }
//                    break;
//                case R.id.chat:
//                    fragmentClass= ChatFragment.class;
//                    tvTieuDe.setText("Chat");
//                    try {
//                        fragment = (Fragment) fragmentClass.newInstance();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (fragment != null) {
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
//                    }
//                    break;
//            }
//
//
//            return true;
//        });

    }



    public void langNgheThongBao() {
        Users user = UserSession.getCurrentUser(MainActivity.this);
        int idUsers = user.getIdUser();
        String stringIdUsers = String.valueOf(idUsers);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        notificationReference = database.getReference("ThongBao").child(stringIdUsers);

        // Tạo sự kiện lắng nghe giá trị của đối tượng DatabaseReference
        notificationListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Kiểm tra nếu có ít nhất một thông báo
                if (snapshot.getChildrenCount() > 0) {
                    // Lấy thông tin của thông báo cuối cùng
                    DataSnapshot lastNotificationSnapshot = snapshot.getChildren().iterator().next();
                    Notification notification = lastNotificationSnapshot.getValue(Notification.class);

                    if(notification.getTrangThai()==1){
                        showNotification("Bạn có thông báo mới", "Đơn hàng của bạn đã được xác nhận");
                    }else{
                        showNotification("Bạn có thông báo mới", "Đơn hàng của bạn đã bị huỷ");
                    }
                    // Xoá thông báo vừa hiển thị
//                    lastNotificationSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("NotificationListener", "Failed to read value.", error.toException());
            }
        };

        // Đăng ký sự kiện lắng nghe
        notificationReference.addValueEventListener(notificationListener);
    }
//public void langNgheThongBao() {
//    Users user = UserSession.getCurrentUser(MainActivity.this);
//    int idUsers = user.getIdUser();
//    String stringIdUsers = String.valueOf(idUsers);
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference reference = database.getReference("ThongBao").child(stringIdUsers);
//    reference.addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            Notification notification = null;
//            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                notification = dataSnapshot.getValue(Notification.class);
//
//            }
//            if(notification == null){
//                return;
//            }
//            if(notification.getTrangThai()==1){
//                showNotification("Bạn có thông báo mới", "Đơn hàng của bạn đã được xác nhận");
//            }else{
//                showNotification("Bạn có thông báo mới", "Đơn hàng của bạn đã bị huỷ");
//            }
//
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//        }
//    });
//}

    public void soLuongTrongGio(){
        try{
            Connection_SQL connection_sql = new Connection_SQL();
            String count = "SELECT COUNT(IDDANHSACHSANPHAM) AS SOLUONGINCART FROM DANHSACHSANPHAM";
            PreparedStatement ptm = connection_sql.SQLconnection().prepareStatement(count);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                soluongtronggio = rs.getInt("SOLUONGINCART");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showNotification(String title, String message) {
        // Định danh thông báo bằng thời gian hiện tại
        int notificationId = (int) System.currentTimeMillis();

        // Tạo đối tượng NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Tạo Intent cho PendingIntent
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        // Hiển thị thông báo bằng NotificationManager
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Tạo Notification Channel cho Android 8.0 trở lên
            NotificationChannel channel = new NotificationChannel("default", "Channel name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(notificationId, builder.build());
    }
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
//        langNgheThongBao();
//        if(PayBillActivity.check==false){
//            PayBillActivity.thanhToanZaloThanhCong();
//        }
        soLuongTrongGio();
        tvSoLuongGioHang.setText(""+soluongtronggio);
        super.onStart();

    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    protected void onResume() {
        soLuongTrongGio();
        tvSoLuongGioHang.setText(""+soluongtronggio);
        super.onResume();
    }
}