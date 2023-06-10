package com.practice.gmusic.daos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.practice.gmusic.entities.Track;
import com.practice.gmusic.others.DBHelper;

import java.util.ArrayList;

public class TrackDAO {

    DBHelper dbHelper;
    SQLiteDatabase database;

    public TrackDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public ArrayList<Track> getAll(){
        String SQL = "SELECT * FROM Track";
        ArrayList<Track> listTrack = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(SQL,null);
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Track track = new Track();
                track.setId(cursor.getInt(0));
                track.setTrackName(cursor.getString(1));
                track.setTrackAuthor(cursor.getString(2));
                track.setTrackUrl(cursor.getString(3));
                track.setTrackImg(cursor.getString(4));
                listTrack.add(track);
            }
        }
        return listTrack;
    }

}
