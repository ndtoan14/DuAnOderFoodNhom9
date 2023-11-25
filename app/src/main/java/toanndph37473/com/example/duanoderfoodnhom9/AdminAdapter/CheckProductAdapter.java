package toanndph37473.com.example.duanoderfoodnhom9.AdminAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class CheckProductAdapter extends RecyclerView.Adapter<CheckProductAdapter.MyViewHolder>{
    List<Hamburger> list = new ArrayList<>();
    Context context;
    private List<Boolean> checkedList;

    public CheckProductAdapter(List<Hamburger> list, Context context) {
        this.list = list;
        this.context = context;
        checkedList = new ArrayList<>(Collections.nCopies(list.size(), false));
    }
    public void setChecked(int position, boolean isChecked) {
        checkedList.set(position, isChecked);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_pproduct, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Hamburger hamburger = list.get(position);
        holder.tvTenSanPham.setText(hamburger.getTen());
        holder.tvMoTaNgan.setText(hamburger.getMoTaNgan());
        holder.tvGiaTien.setText(""+hamburger.getGiaTien());
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
        opts.inMutable = true;
        byte[] decodedString = Base64.decode(hamburger.getHinhAnh(), Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
        myBitmap.setHasAlpha(true);
        holder.imgBurger.setImageBitmap(myBitmap);
        //xu li check
        holder.cb.setChecked(checkedList.get(position));
        holder.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setChecked(position, isChecked);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public List<Hamburger> getCheckedProducts() {
        List<Hamburger> checkedProducts = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (checkedList.get(i)) {
                checkedProducts.add(list.get(i));
            }
        }
        return checkedProducts;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenSanPham, tvMoTaNgan, tvGiaTien;
        ImageView imgBurger;
        CheckBox cb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSanPham=itemView.findViewById(R.id.tvTenSPCheckp);
            tvMoTaNgan=itemView.findViewById(R.id.tvMotaNganCheckp);
            tvGiaTien=itemView.findViewById(R.id.tvGiaTienCheckp);
            imgBurger=itemView.findViewById(R.id.imgBurgerCheckp);
            cb = itemView.findViewById(R.id.btnCheck);
        }
    }
}
