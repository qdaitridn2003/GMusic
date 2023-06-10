package com.practice.gmusic.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.practice.gmusic.MainActivity;
import com.practice.gmusic.R;
import com.practice.gmusic.activities.LoginActivity;
import com.practice.gmusic.daos.UserDAO;
import com.practice.gmusic.entities.User;
import com.practice.gmusic.handletrack.TrackService;

public class MoreFragment extends Fragment {

    TextView tvUsername;
    LinearLayout llChangeInfo,llSignOut;
    SharedPreferences preferences;
    UserDAO DAO;

    public MoreFragment() {
        // Required empty public constructor
    }

    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        preferences = getContext().getSharedPreferences("User Data", Context.MODE_PRIVATE);

        tvUsername = view.findViewById(R.id.tvUsername);
        llSignOut = view.findViewById(R.id.llSignOut);
        llChangeInfo = view.findViewById(R.id.llChangeInfo);

        DAO = new UserDAO(getContext());

        tvUsername.setText(preferences.getString("Name",""));

        llChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfoDialog();
            }
        });

        llSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        return view;
    }

    private void changeInfoDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_changeinfo_account);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(layoutParams);
        dialog.setCancelable(false);

        TextInputEditText tietName = dialog.findViewById(R.id.tietName);
        TextInputEditText tietOldPass = dialog.findViewById(R.id.tietOldPass);
        TextInputEditText tietNewPass = dialog.findViewById(R.id.tietNewPass);
        TextInputEditText tietRePass = dialog.findViewById(R.id.tietRePass);
        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        int id = preferences.getInt("Id",0);
        String mail = preferences.getString("Email","");
        String name = preferences.getString("Name","");

        tietName.setText(name);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNewName = tietName.getText().toString().trim();
                String inputOldPass = tietOldPass.getText().toString().trim();
                String inputNewPass = tietNewPass.getText().toString().trim();
                String inputRePass = tietRePass.getText().toString().trim();

                if (validate(inputOldPass,inputNewPass,inputRePass)){
                    User user = new User(id,mail,inputNewPass,inputNewName);
                    if (DAO.update(user) > 0){
                        Toast.makeText(getContext(),"Successfully Edit Information !"
                                ,Toast.LENGTH_SHORT).show();
                        tvUsername.setText(user.getName());
                        dialog.dismiss();
                        Intent intent = new Intent(getContext(),LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }else{
                        Toast.makeText(getContext(),"Failure Edit Information !"
                                ,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(),"Please Check Input Again !"
                            ,Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private boolean validate(String inputOldPass,String newPass,String rePass){
        String oldPass = preferences.getString("Pass","");
        if (!inputOldPass.equals("") && !newPass.equals("") && !rePass.equals("")){
            return true;
        }else if (inputOldPass == oldPass){
            return true;
        }else if (newPass == oldPass){
            return true;
        }
        return false;
    }

    private void signOut(){
        Intent serviceIntent = new Intent(getContext(), TrackService.class);
        getContext().stopService(serviceIntent);

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);

        getActivity().finish();

    }
}