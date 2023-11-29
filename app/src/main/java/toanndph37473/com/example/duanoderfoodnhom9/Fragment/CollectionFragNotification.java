package toanndph37473.com.example.duanoderfoodnhom9.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.PagerNotificationAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class CollectionFragNotification extends Fragment {
    PagerNotificationAdapter adapter;
    ViewPager2 viewPager2;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.pager01);
        adapter=new PagerNotificationAdapter(this);
        viewPager2.setAdapter(adapter);
        // lam viec voi tab
        TabLayout tabLayout = view.findViewById(R.id.tab01);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // thiet lap cau hinh
                if(position==0){
                    tab.setText("Đơn Hàng");

                }if(position==1){
                    tab.setText("Nạp Tiền");
                }
            }
        });
        mediator.attach();// gan tab vao hoat dong
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collecttions,container,false);
    }
}
