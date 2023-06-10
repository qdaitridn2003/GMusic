package com.practice.gmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practice.gmusic.R;
import com.practice.gmusic.entities.Category;
import com.practice.gmusic.entities.Track;
import com.practice.gmusic.handletrack.TrackService;
import com.practice.gmusic.others.Const;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<Category> listCategory;
    Context context;

    public CategoryAdapter(ArrayList<Category> listCategory, Context context) {
        this.listCategory = listCategory;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = listCategory.get(position);
        ArrayList<Track> listTrack = new ArrayList<>();
        String trackName = listCategory.get(position).getTrackName();
        String trackAuthor = listCategory.get(position).getTrackAuthor();

        for (Category c: listCategory){
            Track track = new Track();
            track.setId(c.getTrackId());
            track.setTrackName(c.getTrackName());
            track.setTrackAuthor(c.getTrackAuthor());
            track.setTrackUrl(c.getTrackUrl());
            track.setTrackImg(c.getTrackImg());
            listTrack.add(track);
        }

        holder.tvTrackName.setText(trackName);
        holder.tvTrackAuthor.setText(trackAuthor);
        Picasso.get().load(category.getTrackImg())
                .resize(200,200)
                .centerCrop()
                .into(holder.imgTrack);
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
        return listCategory.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTrackName,tvTrackAuthor;
        ImageView imgTrack;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvTrackName = itemView.findViewById(R.id.tvTrackName);
            tvTrackAuthor = itemView.findViewById(R.id.tvTrackAuthor);
            imgTrack = itemView.findViewById(R.id.imgTrack);
        }
    }
}
