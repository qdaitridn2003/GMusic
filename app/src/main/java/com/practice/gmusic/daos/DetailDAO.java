package com.practice.gmusic.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.practice.gmusic.entities.Detail;
import com.practice.gmusic.entities.Stat;
import com.practice.gmusic.others.DBHelper;

import java.util.ArrayList;

public class DetailDAO {

    DBHelper dbHelper;
    SQLiteDatabase database;

    public DetailDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public ArrayList<Detail> getAll(int playlistId){
        String SQL = "SELECT d.did,d.pid,d.tid,t.name,t.author,t.url,t.img FROM Detail d" +
                " INNER JOIN Track t ON d.tid = t.tid AND d.pid = ?";
        ArrayList<Detail> listDetail = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(SQL,new String[]{String.valueOf(playlistId)});
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Detail detail = new Detail();
                detail.setId(cursor.getInt(0));
                detail.setPlaylistId(cursor.getInt(1));
                detail.setTrackId(cursor.getInt(2));
                detail.setTrackName(cursor.getString(3));
                detail.setTrackAuthor(cursor.getString(4));
                detail.setTrackUrl(cursor.getString(5));
                detail.setTrackImg(cursor.getString(6));
                listDetail.add(detail);
            }
        }
        return listDetail;
    }

    public long insert(Detail detail){
        ContentValues values = new ContentValues();
        values.put("pid",detail.getPlaylistId());
        values.put("tid",detail.getTrackId());
        database = dbHelper.getWritableDatabase();
        return database.insert("Detail",null,values);
    }

    public long remove(int detailId){
        database = dbHelper.getWritableDatabase();
        return database.delete("Detail","did=?",new String[]{String.valueOf(detailId)});
    }

}
