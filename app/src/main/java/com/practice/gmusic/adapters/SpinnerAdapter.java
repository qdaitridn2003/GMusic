package com.practice.gmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.practice.gmusic.R;
import com.practice.gmusic.entities.Playlist;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<Playlist> {

    public SpinnerAdapter(Context context, ArrayList<Playlist> list){
        super(context,0,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initLayout(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initLayout(position,convertView,parent);
    }

    private View initLayout(int position,View convertView,ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.layout_spinner_row,parent,false);
        }

        TextView tvPlaylistName = convertView.findViewById(R.id.tvPlaylistName);

        Playlist playlist = getItem(position);
        tvPlaylistName.setText(playlist.getName());

        return convertView;
    }
}
