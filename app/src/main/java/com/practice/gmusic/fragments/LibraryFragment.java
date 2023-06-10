package com.practice.gmusic.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.practice.gmusic.R;
import com.practice.gmusic.adapters.LibraryAdapter;
import com.practice.gmusic.adapters.SpinnerAdapter;
import com.practice.gmusic.daos.DetailDAO;
import com.practice.gmusic.daos.PlaylistDAO;
import com.practice.gmusic.daos.TrackDAO;
import com.practice.gmusic.entities.Detail;
import com.practice.gmusic.entities.Playlist;
import com.practice.gmusic.entities.Track;

import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    RecyclerView rvLib;
    int userId,playlistId;

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance(String param1, String param2) {
        LibraryFragment fragment = new LibraryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        rvLib = view.findViewById(R.id.rvLibrary);
        rvLib.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        TrackDAO DAO = new TrackDAO(getContext());
        ArrayList<Track> listTrack = DAO.getAll();
        LibraryAdapter libAdapter = new LibraryAdapter(listTrack,getContext(),this);

        rvLib.setLayoutManager(layoutManager);
        rvLib.setAdapter(libAdapter);

        SharedPreferences preferences = getContext().getSharedPreferences("User Data", Context.MODE_PRIVATE);
        userId = preferences.getInt("Id",0);

        return view ;
    }

    public void showDialog(int trackId){
            PlaylistDAO playlistDAO = new PlaylistDAO(getContext());
            ArrayList<Playlist> listPlaylist = playlistDAO.getAll(userId);

            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_addtracktolist);

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = Gravity.CENTER;

            dialog.getWindow().setAttributes(layoutParams);

            Spinner spnPlaylist = dialog.findViewById(R.id.spnPlaylist);
            Button btnSave = dialog.findViewById(R.id.btnSave);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);

            pushDataToSpinner(spnPlaylist,listPlaylist);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailDAO detailDAO = new DetailDAO(getContext());
                    Detail detail = new Detail(playlistId,trackId);
                    if (detailDAO.insert(detail) > 0){
                        Toast.makeText(getContext(),"Successfully Added" +
                                " Track To That Playlist !",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(),"Failure Added" +
                                " Track To That Playlist !",Toast.LENGTH_SHORT).show();
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
    
    private void pushDataToSpinner(Spinner spinner,ArrayList<Playlist> list){
        SpinnerAdapter spnPlaylistAdapter = new SpinnerAdapter(getContext(),list);
        spinner.setAdapter(spnPlaylistAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Playlist playlist = (Playlist) parent.getItemAtPosition(position);
                playlistId = playlist.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}