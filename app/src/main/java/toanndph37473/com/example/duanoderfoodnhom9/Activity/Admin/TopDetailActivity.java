package toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import toanndph37473.com.example.duanoderfoodnhom9.Model.Top;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class TopDetailActivity extends AppCompatActivity {
    ImageView img_user;
    TextView tv_iduser,tv_ten,tv_email,tv_phone,tv_ttien,tv_diachi;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_detail);
        img_user = findViewById(R.id.img_top_detail);
        tv_iduser = findViewById(R.id.tv_iduser_detail);
        tv_ten = findViewById(R.id.tv_ten_detail);
        tv_email = findViewById(R.id.tv_email_detail);
        tv_phone = findViewById(R.id.tv_phone_detail);
        tv_diachi = findViewById(R.id.tv_diachi_detail);
        tv_ttien= findViewById(R.id.tv_ttien_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Top top = (Top) bundle.getSerializable("top");
        tv_iduser.setText("ID KHACH HANG : "+top.getIduser()+"");
        tv_ten.setText("TEN KHACH HANG : "+top.getTen());
        tv_email.setText("EMAIL KHACH HANG : "+top.getEmail());
        tv_phone.setText("SO DIEN THOAI KHACH HANG : "+top.getSodienthoai());
        tv_diachi.setText("DIA CHI KHACH HANG : "+top.getDiachi());
        tv_ttien.setText("TIEN KHACH HANG MUA :"+top.getTtien()+"");
        if (top.getHinhanh() == null){
            img_user.setImageResource(R.drawable.avtuser);
        }
        else {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGBA_F16;
            opts.inMutable = true;
            byte[] decodedString = Base64.decode(top.getHinhanh(), Base64.DEFAULT);
            Bitmap myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, opts);
            myBitmap.setHasAlpha(true);
            img_user.setImageBitmap(myBitmap);

        }
    }
}