package com.practice.gmusic.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.gmusic.R;
import com.practice.gmusic.daos.DetailDAO;
import com.practice.gmusic.entities.Detail;
import com.practice.gmusic.entities.Playlist;
import com.practice.gmusic.fragments.PlaylistFragment;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    ArrayList<Playlist> listPlaylist;
    Context context;
    PlaylistFragment fragment;

    public PlaylistAdapter(ArrayList<Playlist> listPlaylist, Context context,PlaylistFragment fragment) {
        this.listPlaylist = listPlaylist;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_playlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = listPlaylist.get(position);
        String playlistName = playlist.getName();

        holder.tvPlaylistName.setText(playlistName);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fragment.showPlaylistDialog("Edit",playlist);
                return false;
            }
        });

        holder.ivOpenDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.showDetailDialog(playlist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPlaylist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPlaylistName;
        ImageView ivOpenDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPlaylistName = itemView.findViewById(R.id.tvPlaylistName);
            ivOpenDetail = itemView.findViewById(R.id.ivOpenDetail);

        }
    }

}
