package toanndph37473.com.example.duanoderfoodnhom9.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.NotificationAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Notification;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class OrderNoticeFragment extends Fragment {
    RecyclerView rcv;
    NotificationAdapter adapter;
    List<Notification> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_notice,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcvNotification);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getListFromFireBase();
    }


    public void getListFromFireBase() {
        Users user = UserSession.getCurrentUser(getContext());
        int idUsers = user.getIdUser();
        String stringIdUsers = String.valueOf(idUsers);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("ThongBao").child(stringIdUsers);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
//                Notification notification = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Notification notification = dataSnapshot.getValue(Notification.class);
                    list.add(notification);
                    NotificationAdapter adapter = new NotificationAdapter(getContext(),list);
                    rcv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                Collections.reverse(list);
//                NotificationAdapter adapter = new NotificationAdapter(getContext(),list);
//                rcv.setAdapter(adapter);
//                if(notification.getTrangThai()==1){
//                    showNotification("Bạn có thông báo mới", "Đơn hàng của bạn đã được xác nhận");
//                }else{
//                    showNotification("Bạn có thông báo mới", "Đơn hàng của bạn đã bị huỷ");
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

//    public void showNotification(String title, String message) {
//        // Định danh thông báo bằng thời gian hiện tại
//        int notificationId = (int) System.currentTimeMillis();
//
//        // Tạo đối tượng NotificationCompat.Builder
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "default")
//                .setSmallIcon(R.drawable.ic_notification)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true);
//
//        // Tạo Intent cho PendingIntent
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);
//        builder.setContentIntent(pendingIntent);
//
//        // Hiển thị thông báo bằng NotificationManager
//        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Tạo Notification Channel cho Android 8.0 trở lên
//            NotificationChannel channel = new NotificationChannel("default", "Channel name", NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//        notificationManager.notify(notificationId, builder.build());
//    }

}
