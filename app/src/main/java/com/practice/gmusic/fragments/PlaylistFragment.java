package com.practice.gmusic.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.practice.gmusic.R;
import com.practice.gmusic.adapters.DetailAdapter;
import com.practice.gmusic.adapters.PlaylistAdapter;
import com.practice.gmusic.daos.DetailDAO;
import com.practice.gmusic.daos.PlaylistDAO;
import com.practice.gmusic.entities.Detail;
import com.practice.gmusic.entities.Playlist;

import java.util.ArrayList;

public class PlaylistFragment extends Fragment {

    RecyclerView rvPlaylist,rvDetail;
    ImageView ivAdd;
    PlaylistDAO playlistDAO;
    DetailDAO detailDAO;
    ArrayList<Playlist> listPlaylist;
    ArrayList<Detail> listDetail;
    SharedPreferences preferences;
    int userId;

    public PlaylistFragment() {
        // Required empty public constructor
    }

    public static PlaylistFragment newInstance() {
        PlaylistFragment fragment = new PlaylistFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        rvPlaylist = view.findViewById(R.id.rvPlaylist);
        rvPlaylist.setNestedScrollingEnabled(false);

        ivAdd = view.findViewById(R.id.ivAdd);

        playlistDAO = new PlaylistDAO(getContext());

        preferences = getContext().getSharedPreferences("User Data",Context.MODE_PRIVATE);
        userId = preferences.getInt("Id",0);

        loadData();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvPlaylist);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlaylistDialog("Add",null);
            }
        });

        return view;
    }

    public void showPlaylistDialog(String type,Playlist list){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_handle_playlist);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(layoutParams);

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextInputEditText tietPlaylist = dialog.findViewById(R.id.inputPlaylistName);
        Button btnSave  = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        if (type.equals("Add")){
            txtTitle.setText("Add Playlist");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playlistName = tietPlaylist.getText().toString().trim();
                    if (!playlistName.equals("")){
                        Playlist playlist = new Playlist();
                        playlist.setName(playlistName);
                        playlist.setUserId(userId);
                        if (playlistDAO.insert(playlist) > 0){
                            Toast.makeText(getContext(),"Successfully Added !",Toast.LENGTH_SHORT).show();
                            loadData();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(),"Failure Added !",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(),"Don't leave the input blank !",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else if (type.equals("Edit")){
            txtTitle.setText("Edit Playlist");
            tietPlaylist.setText(list.getName());

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String playlistName = tietPlaylist.getText().toString().trim();
                    if (!playlistName.equals("")){
                        Playlist playlist = new Playlist();
                        playlist.setId(list.getId());
                        playlist.setName(playlistName);
                        playlist.setUserId(list.getUserId());
                        if (playlistDAO.update(playlist) > 0){
                            Toast.makeText(getContext(),"Successfully Updated !",Toast.LENGTH_SHORT).show();
                            loadData();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(),"Failure Updated !",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(),"Don't leave the name blank !",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDetailDialog(Playlist playlist){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_show_detail);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(layoutParams);

        TextView tvPlaylistName = dialog.findViewById(R.id.tvPlaylistName);
        ImageView ivExit = dialog.findViewById(R.id.ivExit);
        rvDetail = dialog.findViewById(R.id.rvDetail);

        tvPlaylistName.setText(playlist.getName());
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        detailDAO = new DetailDAO(getContext());
        listDetail = detailDAO.getAll(playlist.getId());

        loadDetailData(playlist.getId());

        dialog.show();
    }

    public void loadDetailData(int playlistId){
        listDetail = detailDAO.getAll(playlistId);
        DetailAdapter detailAdapter = new DetailAdapter(listDetail,getContext(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvDetail.setAdapter(detailAdapter);
        rvDetail.setLayoutManager(layoutManager);
        detailAdapter.notifyDataSetChanged();
    }

    private void loadData(){
        SharedPreferences preferences = getContext().getSharedPreferences("User Data", Context.MODE_PRIVATE);
        int userId = preferences.getInt("Id",0);
        listPlaylist = playlistDAO.getAll(userId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(listPlaylist,getContext(),this);
        rvPlaylist.setLayoutManager(layoutManager);
        rvPlaylist.setAdapter(playlistAdapter);
        playlistAdapter.notifyDataSetChanged();
    }

    public void deleteDetail(Detail detail){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("");
        alertDialog.setMessage("Do You Want To Remove This Track Out Of Playlist ?");
        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (detailDAO.remove(detail.getId()) > 0){
                    Toast.makeText(getContext(),"Successfully Deleted",Toast.LENGTH_SHORT).show();
                    loadDetailData(detail.getPlaylistId());
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"Your Removing Is Fail!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Do You Want To Delete This Playlist?");
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    loadData();
                }
            });
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int playlistId = listPlaylist.get(viewHolder.getAdapterPosition()).getId();
                    if (playlistDAO.remove(playlistId) > 0){
                        Toast.makeText(getContext(),"Successfully Deleted !",Toast.LENGTH_SHORT).show();
                        loadData();
                    }else{
                        Toast.makeText(getContext(),"Failure Deleted !",Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    };
}