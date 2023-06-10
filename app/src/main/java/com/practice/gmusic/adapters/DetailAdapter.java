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
import com.practice.gmusic.entities.Detail;
import com.practice.gmusic.entities.Track;
import com.practice.gmusic.fragments.PlaylistFragment;
import com.practice.gmusic.handletrack.TrackService;
import com.practice.gmusic.others.Const;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    ArrayList<Detail> listDetail;
    Context context;
    PlaylistFragment fragment;

    public DetailAdapter(ArrayList<Detail> listDetail, Context context,PlaylistFragment fragment) {
        this.listDetail = listDetail;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<Track> listTrack = new ArrayList<>();
        Detail paramDetail = listDetail.get(position);
        String trackName = listDetail.get(position).getTrackName();
        String trackAuthor = listDetail.get(position).getTrackAuthor();
        String trackImg = listDetail.get(position).getTrackImg();

        for (Detail detail:listDetail){
            Track track = new Track();
            track.setId(detail.getTrackId());
            track.setTrackName(detail.getTrackName());
            track.setTrackAuthor(detail.getTrackAuthor());
            track.setTrackUrl(detail.getTrackUrl());
            track.setTrackImg(detail.getTrackImg());
            listTrack.add(track);
        }

        holder.tvTrackName.setText(trackName);
        holder.tvTrackAuthor.setText(trackAuthor);
        Picasso.get().load(trackImg)
                .resize(200,200)
                .centerCrop()
                .into(holder.imgIcon);
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.deleteDetail(paramDetail);
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
        return listDetail.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTrackName,tvTrackAuthor;
        ImageView ivRemove,imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTrackName = itemView.findViewById(R.id.tvTrackName);
            tvTrackAuthor = itemView.findViewById(R.id.tvTrackAuthor);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }

    }

}
