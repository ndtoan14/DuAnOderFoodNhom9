package toanndph37473.com.example.duanoderfoodnhom9.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import toanndph37473.com.example.duanoderfoodnhom9.Adapter.InformationPayAdapter;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.Helper.DAO;
import toanndph37473.com.example.duanoderfoodnhom9.Model.InformationPay;
import toanndph37473.com.example.duanoderfoodnhom9.Model.UserSession;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class SelectInfomation extends AppCompatActivity {
    RecyclerView rcv;
    CardView btnBack;
    List<InformationPay> list = new ArrayList<>();
    InformationPayAdapter adapter;
    Button btnThemMoiDiaChi;
    String name,phone,address;
    DAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_infomation);
        btnThemMoiDiaChi = findViewById(R.id.btnThemMoiDiaChi);
        rcv = findViewById(R.id.rcvInformation);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        dao = new DAO();
        btnBack=findViewById(R.id.btnBackInformation);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectInfomation.this,PayBillActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getList();
        adapter = new InformationPayAdapter(list,this);
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        btnThemMoiDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(SelectInfomation.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_them_moi_dia_chi);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams winLayoutParams = window.getAttributes();
                winLayoutParams.gravity = Gravity.CENTER;
                window.setAttributes(winLayoutParams);
                TextInputLayout edNameInfor, edPhoneInfor, edAddressInfor;
                Button btnAddnewInfor;
                edNameInfor=dialog.findViewById(R.id.edNameInfor);
                edPhoneInfor=dialog.findViewById(R.id.edPhoneInfor);
                edAddressInfor=dialog.findViewById(R.id.edAddressInfor);
                btnAddnewInfor = dialog.findViewById(R.id.btnAddNewInfor);
                btnAddnewInfor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = edNameInfor.getEditText().getText().toString().trim();
                        phone = edPhoneInfor.getEditText().getText().toString().trim();
                        address = edAddressInfor.getEditText().getText().toString().trim();
                        if(name.isEmpty()){
                            edNameInfor.setError("Ten khong duoc de trong");
                        }else{
                            edNameInfor.setError(null);
                        }
                        if(phone.isEmpty()){
                            edPhoneInfor.setError("So Dien Thoai khong duoc de trong");
                        }else{
                            edPhoneInfor.setError(null);
                        }
                        if(address.isEmpty()){
                            edAddressInfor.setError("Dia Chi khong duoc de trong");
                        }else{
                            edAddressInfor.setError(null);
                        }
                        dao = new DAO();
                        if(dao.insertThongTinNhanHang(name,phone,address, UserSession.getCurrentUser(getApplicationContext()).getIdUser())>0){
                            Toast.makeText(SelectInfomation.this, "Them moi dia chi thanh cong", Toast.LENGTH_SHORT).show();
                            list.clear();
                            getList();
                            adapter = new InformationPayAdapter(list,SelectInfomation.this);
                            rcv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }

                    }
                });
                dialog.show();
            }
        });
    }




    private void getList() {
        try {
            Connection_SQL connection_sql = new Connection_SQL();
            String truyvan = "SELECT * FROM THONGTINNHANHANG WHERE IDUSERS = ?";
            PreparedStatement preparedStatement = connection_sql.SQLconnection().prepareStatement(truyvan);
            preparedStatement.setInt(1,UserSession.getCurrentUser(getApplicationContext()).getIdUser());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("IDTHONGTINNHANHANG");
                String ten=rs.getString("TEN");
                String sdt = rs.getString("SODIENTHOAI");
                String dc = rs.getString("DIACHI");
                int idUser = rs.getInt("IDUSERS");
                InformationPay item = new InformationPay(id,idUser,ten,sdt,dc);
                list.add(item);


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}