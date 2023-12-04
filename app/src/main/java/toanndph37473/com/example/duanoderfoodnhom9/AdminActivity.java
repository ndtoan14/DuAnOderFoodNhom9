package toanndph37473.com.example.duanoderfoodnhom9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.LoginActivity;
import toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin.MessageFragment;
import toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin.NewOrderFragment;
import toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin.NewsFragmentAdmin;
import toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin.ProductFragment;
import toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin.RequestMoneyFragment;
import toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin.RevenueStatisticFragment;
import toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin.SaleProductFragment;
import toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin.ThongKeDonHangBiHuyFragment;
import toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin.ThongKeThangFragment;
import toanndph37473.com.example.duanoderfoodnhom9.FragmentAdmin.TopFragment;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Notification;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int Fragment_product=0;
    DrawerLayout drawerLayout;
    private int mCurrentFragment = Fragment_product;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    Toolbar toolbar;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.toolbar);
        //add tool bar
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new ProductFragment());
        //set check cho menu
        navigationView.getMenu().findItem(R.id.sanPham).setChecked(true);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.sanPham){
            // frag hien tai la frag addnew roi neu khac frag thi moi xu li logic
            replaceFragment(new ProductFragment());
            mCurrentFragment = Fragment_product;

        }else if(id==R.id.tinNhan){

            replaceFragment(new MessageFragment());
            mCurrentFragment=Fragment_product;
        }else if(id==R.id.xacNhanDH){

            replaceFragment(new NewOrderFragment());
            mCurrentFragment=Fragment_product;
        }
        else if(id==R.id.donHangThanhCong){

            replaceFragment(new RevenueStatisticFragment());
            mCurrentFragment=Fragment_product;
        }else if(id==R.id.donHangBiHuy){

            replaceFragment(new ThongKeDonHangBiHuyFragment());
            mCurrentFragment=Fragment_product;
        }else if(id==R.id.yeuCauNaptien) {

            replaceFragment(new RequestMoneyFragment());
            mCurrentFragment = Fragment_product;

        }else if(id==R.id.thongKeSanPham){
            replaceFragment(new ThongKeThangFragment());
            mCurrentFragment=Fragment_product;
        } else if(id==R.id.dangXuat){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Bạn có chắc chắn muốn đăng xuất ?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                    startActivity(intent);
                    UserSession.clearUser(AdminActivity.this);
                    finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();

        }
        else if(id==R.id.khuyenMai){

            replaceFragment(new SaleProductFragment());
            mCurrentFragment=Fragment_product;
        }else if(id==R.id.tinNhan){

            replaceFragment(new MessageFragment());
            mCurrentFragment=Fragment_product;
        }else if (id==R.id.thongKeHoaDon){
            replaceFragment(new TopFragment());
            mCurrentFragment = Fragment_product;
        }
        else if(id==R.id.tintuc){

            replaceFragment(new NewsFragmentAdmin());
            mCurrentFragment=Fragment_product;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        toolbar.setTitle(item.getTitle());
//return true
        return true;
    }
    @Override
    public void onBackPressed() {
        //check neu nhu drawable dang mo ma an nut back cua ung dung thi tat drawable
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            //nguoc lai neu draw da dong roi thi thoat app
            super.onBackPressed();
        }
    }



    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    public void lanNgheThongBao() {
        Users user = UserSession.getCurrentUser(AdminActivity.this);
        int idUsers = user.getIdUser();
        String stringIdUsers = String.valueOf(idUsers);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("ThongBao").child(stringIdUsers);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Notification notification = dataSnapshot.getValue(Notification.class);
                    if(notification.getTrangThai()==1){
                        showNotification("Bạn có thông báo mới", "Đơn hàng của bạn đã được xác nhận");
                    }else{
                        showNotification("Bạn có thông báo mới", "Đơn hàng của bạn đã bị huỷ");
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
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



}