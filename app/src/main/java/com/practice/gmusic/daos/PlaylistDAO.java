package com.practice.gmusic.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.practice.gmusic.entities.Playlist;
import com.practice.gmusic.others.DBHelper;

import java.util.ArrayList;

public class PlaylistDAO {

    DBHelper dbHelper;
    SQLiteDatabase database;

    public PlaylistDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public long insert(Playlist playlist){
        ContentValues values = new ContentValues();
        values.put("name",playlist.getName());
        values.put("uid",playlist.getUserId());
        database = dbHelper.getWritableDatabase();
        return database.insert("Playlist",null,values);
    }

    public long update(Playlist playlist){
        ContentValues values = new ContentValues();
        values.put("name",playlist.getName());
        values.put("uid",playlist.getUserId());
        database = dbHelper.getWritableDatabase();
        return database.update("Playlist",
                values,"pid=?",new String[]{String.valueOf(playlist.getId())});
    }

    public long remove(int playlistId){
        database = dbHelper.getWritableDatabase();
        return database.delete("Playlist",
                "pid=?",new String[]{String.valueOf(playlistId)});
    }

    public ArrayList<Playlist> getAll(int userid){
        String SQL = "SELECT * FROM Playlist WHERE uid=?";
        ArrayList<Playlist> listPlaylist = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(SQL,new String[]{String.valueOf(userid)});
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Playlist playlist = new Playlist();
                playlist.setId(cursor.getInt(0));
                playlist.setName(cursor.getString(1));
                playlist.setUserId(cursor.getInt(2));
                listPlaylist.add(playlist);
            }
        }
        return listPlaylist;
    }

}
