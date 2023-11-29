package toanndph37473.com.example.duanoderfoodnhom9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView tvTieuDe;
    Class fragmentClass;
    public static Fragment fragment;
    ImageView gioHangMain;
    public static TextView tvSoLuongGioHang;
    int soluongtronggio = 0;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private ValueEventListener notificationListener;
    private DatabaseReference notificationReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}