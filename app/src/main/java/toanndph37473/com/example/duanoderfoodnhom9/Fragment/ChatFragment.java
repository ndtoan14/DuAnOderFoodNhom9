package toanndph37473.com.example.duanoderfoodnhom9.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Activity.Admin.CallActivity;
import toanndph37473.com.example.duanoderfoodnhom9.Adapter.ChatAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Model.Messenger;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.R;


public class ChatFragment extends Fragment {
    RecyclerView rcvChat;
    EditText edMess;
    ImageView btnSendMess;
    ImageView btnCall;
    List<Messenger> list = new ArrayList<>();
    ChatAdapter adapter;
    private DatabaseReference mMessagesRef;
    private ChildEventListener mMessagesListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edMess=view.findViewById(R.id.edMessenger);
        btnSendMess=view.findViewById(R.id.btnSendMess);
        btnCall = view.findViewById(R.id.btnCall);
        rcvChat=view.findViewById(R.id.rcvChat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getListFromFireBase();
        adapter = new ChatAdapter(list,getContext());
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
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CallActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getListFromFireBase() {
        String conversationId = UserSession.getCurrentUser(getContext()).getIdUser() + "_" + "admin";
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("conversations").child(conversationId).child("messages");
        // Sử dụng addChildEventListener để lắng nghe sự kiện thêm mới của Firebase Realtime Database
        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Messenger messenger = snapshot.getValue(Messenger.class);
                list.add(messenger);
                adapter = new ChatAdapter(list,getContext());
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
        String conversationId = UserSession.getCurrentUser(getContext()).getIdUser() + "_" + "admin";
        DatabaseReference messagesRef = conversationsRef.child(conversationId).child("messages");
        Messenger messenger = new Messenger(UserSession.getCurrentUser(getContext()).getIdUser(), edMess.getText().toString().trim());
        messagesRef.push().setValue(messenger);
        edMess.setText("");

    }



}
