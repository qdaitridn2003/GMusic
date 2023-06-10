package com.practice.gmusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.practice.gmusic.MainActivity;
import com.practice.gmusic.R;
import com.practice.gmusic.daos.UserDAO;
import com.practice.gmusic.entities.User;
import com.practice.gmusic.handletrack.TrackService;

public class LoginActivity extends AppCompatActivity{

    TextInputEditText tietMail,tietPass;
    CheckBox ckbRemember;
    Button btnLogin;
    RelativeLayout rlHaveConnect,rlNoConnect;
    SharedPreferences preferences;
    ImageView ivFacebook,ivGmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        tietMail = findViewById(R.id.inputEmail);
        tietPass = findViewById(R.id.inputPassword);
        ckbRemember = findViewById(R.id.ckbRemember);
        btnLogin = findViewById(R.id.btnLogin);
        ivGmail = findViewById(R.id.ivGmail);
        rlHaveConnect = findViewById(R.id.rlHaveConnect);
        rlNoConnect = findViewById(R.id.rlNoConnect);

        preferences = getSharedPreferences("User Data",MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = tietMail.getText().toString().trim();
                String pass = tietPass.getText().toString().trim();
                boolean isRemember = ckbRemember.isChecked();
                if (validate(mail,pass)){
                    if (checkSignIn(mail, pass, isRemember)) {
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        alertMessage("This Account Doesn't Exist ! Please Check It Again");
                        tietPass.setText("");
                    }
                }
            }
        });

        ivGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (isNetWorkConnected()){
            rlHaveConnect.setVisibility(View.VISIBLE);
            rlNoConnect.setVisibility(View.GONE);
        }else{
            rlNoConnect.setVisibility(View.VISIBLE);
            rlHaveConnect.setVisibility(View.GONE);
            Intent intent = new Intent(LoginActivity.this, TrackService.class);
            stopService(intent);
        }
    }

    private boolean checkSignIn(String mail,String pass,boolean isRemember){
        UserDAO DAO = new UserDAO(this);
        User user = DAO.checkLogin(mail,pass);
        if (user != null){
            SharedPreferences.Editor preferencesEdit = preferences.edit();
            preferencesEdit.putInt("Id",user.getId());
            preferencesEdit.putString("Email", user.getMail());
            preferencesEdit.putString("Pass",user.getPass());
            preferencesEdit.putString("Name",user.getName());
            preferencesEdit.putBoolean("IsRemember",isRemember);
            preferencesEdit.commit();
            return true;
        }
        return false;
    }

    private boolean validate(String mail,String pass){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (mail.equals("")){
            alertMessage("Mail Can't Be Blank ! Please Check Again !");
            return false;
        }else if (!mail.equals("") && !mail.matches(regex)){
            alertMessage("Mail Is Wrong Format ! Please Check Again !");
            return false;
        }else if (pass.equals("")) {
            alertMessage("Password Can't Be Blank ! Please Check Again !");
            return false;
        }
        return true;
    }

    private void alertMessage(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetWorkConnected()){
            rlHaveConnect.setVisibility(View.VISIBLE);
            rlNoConnect.setVisibility(View.GONE);
        }else{
            rlNoConnect.setVisibility(View.VISIBLE);
            rlHaveConnect.setVisibility(View.GONE);
            Intent intent = new Intent(LoginActivity.this, TrackService.class);
            stopService(intent);
        }
    }

    private boolean isNetWorkConnected(){
        ConnectivityManager connectManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connectManager.getActiveNetworkInfo();
        if (network != null){
            if (network.isConnected()){
                return true;
            }
        }
        return false;
    }

}