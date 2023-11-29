package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import toanndph37473.com.example.duanoderfoodnhom9.Fragment.RechargeNoticeFragment;
import toanndph37473.com.example.duanoderfoodnhom9.Fragment.OrderNoticeFragment;

public class PagerNotificationAdapter extends FragmentStateAdapter {
    int soLuongPage = 2; // sau co the tang
    OrderNoticeFragment f01 = new OrderNoticeFragment();
    RechargeNoticeFragment f02 = new RechargeNoticeFragment();

    public PagerNotificationAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //khoi tao fragment
        switch (position){
            case 0:
                return f01;
            case 1:
                return f02;
        }



        return null;
    }

    @Override
    public int getItemCount() {
        return soLuongPage;
    }

}
