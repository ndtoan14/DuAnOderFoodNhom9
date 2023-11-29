package toanndph37473.com.example.duanoderfoodnhom9.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import toanndph37473.com.example.duanoderfoodnhom9.Activity.BurgerActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.NotificationAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ProductAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.TopSellingAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.adapterSideshow;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.MainActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Notification;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.Model.sideshow;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class HomeFragment extends Fragment {
    RecyclerView rcvTopSelling,rcvListBurgerHome,rcvTopSaleHome;
    TopSellingAdapter adapter;
    List<Hamburger> list  = new ArrayList<>();
    Connection_SQL connection_sql;
    ProductAdapter productAdapter;
    List<Hamburger> list2 = new ArrayList<>();
    TextView tvSeeAll;
    List<Hamburger> listTopSale = new ArrayList<>();

    ViewPager viewPager;
    List<sideshow> list_sideshow;
    CircleIndicator circleIndicator;
    toanndph37473.com.example.duanoderfoodnhom9.Adapter.adapterSideshow adapterSideshow;
    Timer timer;
    DAO dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTopSelling=view.findViewById(R.id.rcvTopSelling);
        rcvListBurgerHome=view.findViewById(R.id.rcvListBurgerHome);
        rcvTopSaleHome=view.findViewById(R.id.rcvTopSaleHome);
        tvSeeAll = view.findViewById(R.id.tvSeeAll);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getList();
        adapter= new TopSellingAdapter(list,getContext());
        rcvTopSelling.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getListBurgerHome();
        productAdapter = new ProductAdapter(list2,getContext());
        rcvListBurgerHome.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();


        viewPager = view.findViewById(R.id.pager_sideshow);
        circleIndicator = view.findViewById(R.id.indicator_img);
        list_sideshow  = getListSideShow();

        adapterSideshow = new adapterSideshow(getContext(),list_sideshow);
        viewPager.setAdapter(adapterSideshow);
        circleIndicator.setViewPager(viewPager);
        adapterSideshow.registerDataSetObserver(circleIndicator.getDataSetObserver());
        auto_sideshow();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setSpanCount(3);
        getListSale();
        ProductAdapter productAdapter1 = new ProductAdapter(listTopSale,getContext());
        rcvTopSaleHome.setAdapter(productAdapter1);
        productAdapter1.notifyDataSetChanged();
        tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BurgerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getListSale() {
        try{
            connection_sql = new Connection_SQL();
            String sql = "SELECT * FROM HAMBURGER WHERE GIAKM > 0 AND SOLUONG > 0";
            PreparedStatement stm = connection_sql.SQLconnection().prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("IDHAMBURGER");
                String ten = resultSet.getString("TEN");
                String moTaNgan = resultSet.getString("MOTANGAN");
                String moTaChiTiet = resultSet.getString("MOTACHITIET");
                double giaTien = resultSet.getDouble("GIATIEN");
                int soLuong = resultSet.getInt("SOLUONG");
                String hinhAnh = resultSet.getString("HINHANH");
                int daBan = resultSet.getInt("DABAN");
                double giaKM = resultSet.getDouble("GIAKM");
                Hamburger hamburger = new Hamburger(id,ten,moTaNgan,moTaChiTiet,hinhAnh,soLuong,giaTien,daBan,giaKM);
                listTopSale.add(hamburger);
            }
            resultSet.close();
            stm.close();
            connection_sql.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getListBurgerHome() {
        try{
            connection_sql = new Connection_SQL();
            String sql = "SELECT * FROM HAMBURGER";
            PreparedStatement stm = connection_sql.SQLconnection().prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("IDHAMBURGER");
                String ten = resultSet.getString("TEN");
                String moTaNgan = resultSet.getString("MOTANGAN");
                String moTaChiTiet = resultSet.getString("MOTACHITIET");
                double giaTien = resultSet.getDouble("GIATIEN");
                int soLuong = resultSet.getInt("SOLUONG");
                String hinhAnh = resultSet.getString("HINHANH");
                int daBan = resultSet.getInt("DABAN");
                double giaKM = resultSet.getDouble("GIAKM");
                Hamburger hamburger = new Hamburger(id,ten,moTaNgan,moTaChiTiet,hinhAnh,soLuong,giaTien,daBan,giaKM);
                list2.add(hamburger);
            }
            resultSet.close();
            stm.close();
            connection_sql.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getList() {
        try{
            connection_sql = new Connection_SQL();
            String sql = "SELECT TOP 10 * FROM HAMBURGER ORDER BY DABAN DESC";
            PreparedStatement stm = connection_sql.SQLconnection().prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("IDHAMBURGER");
                String ten = resultSet.getString("TEN");
                String moTaNgan = resultSet.getString("MOTANGAN");
                String moTaChiTiet = resultSet.getString("MOTACHITIET");
                double giaTien = resultSet.getDouble("GIATIEN");
                int soLuong = resultSet.getInt("SOLUONG");
                String hinhAnh = resultSet.getString("HINHANH");
                int daBan = resultSet.getInt("DABAN");
                double giaKM = resultSet.getDouble("giaKM");
                Hamburger hamburger = new Hamburger(id,ten,moTaNgan,moTaChiTiet,hinhAnh,soLuong,giaTien,daBan,giaKM);
                list.add(hamburger);
            }
            resultSet.close();
            stm.close();
            connection_sql.SQLconnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void auto_sideshow() {
        if (list_sideshow ==null || list_sideshow.isEmpty() || viewPager == null){
            return;
        }
        if (timer == null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int curentItem = viewPager.getCurrentItem();
                        int totalItem = list_sideshow.size()-1;
                        if (curentItem < totalItem){
                            curentItem++;
                            viewPager.setCurrentItem(curentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,2500);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer!= null){
            timer.cancel();
            timer = null;
        }
    }

    private List<sideshow> getListSideShow() {
        List<sideshow> list = new ArrayList<>();
        list.add(new sideshow(R.drawable.sale1));
        list.add(new sideshow(R.drawable.ham311));
        list.add(new sideshow(R.drawable.sale2));
        list.add(new sideshow(R.drawable.hamberger3));
        list.add(new sideshow(R.drawable.hamberger4));
        list.add(new sideshow(R.drawable.sale3));
        return list;
    }

    @Override
    public void onStart() {
        super.onStart();
        dao = new DAO();        //dao hết ngày hết KM
        dao.UpdateKM();
        dao.CallKM();
        dao.DeleteKM();
    }

    @Override
    public void onResume() {
        super.onResume();
        dao = new DAO();        //dao hết ngày hết KM
        dao.UpdateKM();
        dao.CallKM();
        dao.DeleteKM();
    }
}
