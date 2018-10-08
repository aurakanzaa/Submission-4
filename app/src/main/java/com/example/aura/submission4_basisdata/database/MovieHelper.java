package com.example.aura.submission4_basisdata.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.aura.submission4_basisdata.helper.Config;

public class MovieHelper {

    public static String DATABASE_TABLE = Config.Movies.TABLE_FAV;
    private Context context;
    private FavoriteDataHelper favoriteDataHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MovieHelper(Context context){
        this.context = context;
    }

    public MovieHelper open() throws SQLException{
        favoriteDataHelper = new FavoriteDataHelper(context);
        sqLiteDatabase = favoriteDataHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public Cursor queryProvider(){
        return sqLiteDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                null,
                Config.Movies.ID + " DESC"
        );
    }

    public Cursor queryByIdProvider (String id){
        return sqLiteDatabase.query(DATABASE_TABLE, null, Config.Movies.ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insertProvider(ContentValues values){
        return sqLiteDatabase.insert(DATABASE_TABLE,null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return sqLiteDatabase.update(DATABASE_TABLE, values, Config.Movies.ID+ " = ?", new String[]{id});
    }

    public int deleteProvider(String id){
        return sqLiteDatabase.delete(DATABASE_TABLE,Config.Movies.ID+ " = ?", new String[]{id});
    }
}
