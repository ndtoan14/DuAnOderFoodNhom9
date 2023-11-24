package toanndph37473.com.example.duanoderfoodnhom9.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Model.sideshow;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class adapterSideshow extends PagerAdapter {
    Context context;

    public adapterSideshow(Context context, List<sideshow> list) {
        this.context = context;
        this.list = list;
    }

    List<sideshow> list;


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_sideshow,container,false);
        ImageView img_sideshow =   view.findViewById(R.id.img_sideshow);
        sideshow sd = list.get(position);
        if (sd != null){
            Glide.with(context).load(sd.getId_img()).into(img_sideshow);
        }
        container.addView(view);

        return view;

    }

    @Override
    public int getCount() {
        if (list != null){
            return list.size();
        }

        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
    }
}
