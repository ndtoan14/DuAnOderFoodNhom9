package toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ChatAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.AdminAdapter.ChatAdminAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Hamburger;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Messenger;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Users;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class ChatAdminActivity extends AppCompatActivity {
    RecyclerView rcvChat;
    EditText edMess;
    ImageView btnSendMess;
    List<Messenger> list = new ArrayList<>();
    ChatAdminAdapter adapter;
    int idUser;
    public static String nameUser, avtUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_admin);
        rcvChat=findViewById(R.id.rcvChatofAdmin);
        edMess=findViewById(R.id.edMessengerofAdmin);
        btnSendMess=findViewById(R.id.btnSendMessofAdmin);
        //get bundle
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Users user = (Users) bundle.getSerializable("user");
        idUser = user.getIdUser();
        nameUser = user.getTen();
        avtUser = user.getHinhAnh();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getListFromFireBase();
        adapter = new ChatAdminAdapter(list,this);
        rcvChat.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        btnSendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edMess.getText().toString().trim().isEmpty()) {
                    sendMess();
                }
            }
        });
    }
    private void getListFromFireBase() {
        String conversationId = idUser + "_" + "admin";
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("conversations").child(conversationId).child("messages");
        // Sử dụng addChildEventListener để lắng nghe sự kiện thêm mới của Firebase Realtime Database
        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Messenger messenger = snapshot.getValue(Messenger.class);
                list.add(messenger);
                adapter = new ChatAdminAdapter(list,ChatAdminActivity.this);
                rcvChat.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                rcvChat.smoothScrollToPosition(list.size() - 1);
            }
            // Các phương thức còn lại của ChildEventListener không cần thực hiện gì
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    private void sendMess() {
        DatabaseReference conversationsRef = FirebaseDatabase.getInstance().getReference().child("conversations");
        String conversationId = idUser + "_" + "admin";
        DatabaseReference messagesRef = conversationsRef.child(conversationId).child("messages");
        Messenger messenger = new Messenger(UserSession.getCurrentUser(ChatAdminActivity.this).getIdUser(), edMess.getText().toString().trim());
        messagesRef.push().setValue(messenger);
        edMess.setText("");

    }
}