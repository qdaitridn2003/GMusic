package com.practice.gmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.gmusic.R;
import com.practice.gmusic.entities.Track;
import com.practice.gmusic.fragments.LibraryFragment;
import com.practice.gmusic.handletrack.TrackService;
import com.practice.gmusic.others.Const;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    ArrayList<Track> listTrack;
    Context context;
    LibraryFragment mFragment;

    public LibraryAdapter(ArrayList<Track> listTrack, Context context,LibraryFragment mFragment) {
        this.listTrack = listTrack;
        this.context = context;
        this.mFragment = mFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_library,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int trackId = listTrack.get(position).getId();
        String trackName = listTrack.get(position).getTrackName();
        String trackAuthor = listTrack.get(position).getTrackAuthor();
        String trackImg = listTrack.get(position).getTrackImg();

        holder.tvTrackName.setText(trackName);
        holder.tvTrackAuthor.setText(trackAuthor);
        Picasso.get().load(trackImg)
                .resize(200,200)
                .centerCrop()
                .into(holder.imgIcon);
        holder.ivAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.showDialog(trackId);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrackService.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Const.keyListTrack,listTrack);
                bundle.putInt(Const.keyPosition,holder.getAdapterPosition());
                intent.putExtras(bundle);
                context.startService(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTrack.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTrackName,tvTrackAuthor;
        ImageView ivAddToList,imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTrackName = itemView.findViewById(R.id.tvTrackName);
            tvTrackAuthor = itemView.findViewById(R.id.tvTrackAuthor);
            ivAddToList = itemView.findViewById(R.id.ivAddToList);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
