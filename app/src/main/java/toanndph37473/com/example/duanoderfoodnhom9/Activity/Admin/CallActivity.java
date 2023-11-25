package toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class CallActivity extends AppCompatActivity {
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        cardView = findViewById(R.id.cv_BackCall);


        Users users = UserSession.getCurrentUser(CallActivity.this);
        String sdt = null;
        int quyen = users.getQuyen();
        if (quyen == 0) {
            //get bundle
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Users user = (Users) bundle.getSerializable("user1");
            sdt = user.getSoDienThoai();
        } else {
            sdt = "0338270627";
        }

        //Khai báo intent ẩn
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sdt));

        //Yêu cầu sự đồng ý của người dùng
        if (ActivityCompat.checkSelfPermission(CallActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CallActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            return;
        }
        startActivity(callIntent);

//        Button btn_call = findViewById(R.id.btn_call);
//        btn_call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}