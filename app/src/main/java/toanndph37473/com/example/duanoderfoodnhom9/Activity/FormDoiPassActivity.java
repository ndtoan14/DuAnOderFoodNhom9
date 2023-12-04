package toanndph37473.com.example.duanoderfoodnhom9.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import toanndph37473.com.example.duanoderfoodnhom9.Helper.Connection_SQL;
import toanndph37473.com.example.duanoderfoodnhom9.R;

public class FormDoiPassActivity extends AppCompatActivity {
    String phone;
    TextInputLayout edPass,edRePass;
    Button btnXacNhanDoi;
    int idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_doi_pass);
        edPass = findViewById(R.id.edPassMoi);
        edRePass = findViewById(R.id.edPassMoiRE);
        btnXacNhanDoi = findViewById(R.id.btnXacNhanDoiMK);
        phone = getIntent().getStringExtra("phoneUpdatePass");
        btnXacNhanDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = edPass.getEditText().getText().toString().trim();
                String pass2 = edRePass.getEditText().getText().toString().trim();
                if(!pass1.equals(pass2)){
                    edRePass.setError("Mật khẩu phải giống nhau");
                    return;
                }else {
                    edRePass.setError(null);
                }
                layIDUser();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("USERS").child(String.valueOf(idUser)).child("matKhau");
                ref.setValue(edPass.getEditText().getText().toString().trim());
                updateDB();
            }
        });


    }

    private void layIDUser() {
        try{
            Connection_SQL connection_sql = new Connection_SQL();
            String select ="SELECT IDUSERS FROM USERS WHERE SODIENTHOAI = ? " ;
            PreparedStatement ptm =  connection_sql.SQLconnection().prepareStatement(select);
            ptm.setString(1,phone);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                idUser = rs.getInt("IDUSERS");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void updateDB() {
        try{
            Connection_SQL connection_sql = new Connection_SQL();
            String updateUser ="UPDATE USERS " +
                    "SET MATKHAU = ? " +
                    "WHERE SODIENTHOAI = ?";
            PreparedStatement ptm =  connection_sql.SQLconnection().prepareStatement(updateUser);
            ptm.setString(1,edPass.getEditText().getText().toString().trim());
            ptm.setString(2,phone);
            int row = ptm.executeUpdate();
            if(row>0){
                Intent intent = new Intent(FormDoiPassActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}