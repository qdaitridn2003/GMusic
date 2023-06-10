package com.practice.gmusic.daos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.practice.gmusic.entities.Category;
import com.practice.gmusic.entities.Stat;
import com.practice.gmusic.others.DBHelper;

import java.util.ArrayList;

public class CategoryDAO {

    DBHelper dbHelper;
    SQLiteDatabase database;

    public CategoryDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public ArrayList<Category> getAll(String name){
        String SQL = "SELECT c.cid,c.name,c.tid,t.name,t.author,t.url,t.img FROM Category c " +
                "INNER JOIN Track t ON c.tid = t.tid AND c.name=?";
        ArrayList<Category> listCategory = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(SQL,new String[]{name});
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Category category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));
                category.setTrackId(cursor.getInt(2));
                category.setTrackName(cursor.getString(3));
                category.setTrackAuthor(cursor.getString(4));
                category.setTrackUrl(cursor.getString(5));
                category.setTrackImg(cursor.getString(6));
                listCategory.add(category);
            }
        }
        return listCategory;
    }

    public ArrayList<Stat> getTop5Track(){
        String SQL = "SELECT d.tid,t.name,t.author,t.url,t.img,COUNT(d.tid) AS amount FROM Detail d " +
                "INNER JOIN Track t ON d.tid = t.tid " +
                "GROUP BY d.tid ORDER BY amount DESC LIMIT 5";
        ArrayList<Stat> listStat = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(SQL,null);
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Stat stat = new Stat();
                stat.setTrackId(cursor.getInt(0));
                stat.setTrackName(cursor.getString(1));
                stat.setTrackAuthor(cursor.getString(2));
                stat.setTrackUrl(cursor.getString(3));
                stat.setTrackImg(cursor.getString(4));
                stat.setAmount(cursor.getInt(5));
                listStat.add(stat);
            }
        }
        return listStat;
    }

}
