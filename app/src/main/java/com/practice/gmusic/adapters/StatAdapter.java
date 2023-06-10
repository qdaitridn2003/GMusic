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
import com.practice.gmusic.entities.Category;
import com.practice.gmusic.entities.Stat;
import com.practice.gmusic.entities.Track;
import com.practice.gmusic.handletrack.TrackService;
import com.practice.gmusic.others.Const;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StatAdapter extends RecyclerView.Adapter<StatAdapter.ViewHolder> {

    private ArrayList<Stat> listStat;
    private Context context;

    public StatAdapter(ArrayList<Stat> listStat, Context context) {
        this.listStat = listStat;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_library,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<Track> listTrack = new ArrayList<>();
        String trackName = listStat.get(position).getTrackName();
        String trackAuthor = listStat.get(position).getTrackAuthor();
        String trackImg = listStat.get(position).getTrackImg();

        for (Stat stat: listStat){
            Track track = new Track();
            track.setId(stat.getTrackId());
            track.setTrackName(stat.getTrackName());
            track.setTrackAuthor(stat.getTrackAuthor());
            track.setTrackUrl(stat.getTrackUrl());
            track.setTrackImg(stat.getTrackImg());
            listTrack.add(track);
        }

        holder.tvTrackName.setText(trackName);
        holder.tvTrackAuthor.setText(trackAuthor);
        holder.ivAddToList.setVisibility(View.GONE);
        Picasso.get().load(trackImg)
                .resize(200,200)
                .into(holder.imgIcon);
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
        return listStat.size();
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
