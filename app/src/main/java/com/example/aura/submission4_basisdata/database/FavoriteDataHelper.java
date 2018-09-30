package com.example.aura.submission4_basisdata.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.aura.submission4_basisdata.helper.Config;

public class FavoriteDataHelper extends SQLiteOpenHelper {
    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */

    private static final String DATABASE_NAME = "favorite_movie.db";
    private static final int DATABASE_VERSION = 24;
    public FavoriteDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table favorite(_id INTEGER primary key AUTOINCREMENT, id INTEGER not null, tittle text,  vote_average text null, original_language text null, overview text null, status_favorite text null);";
        Log.d("Data", "onCreate: " + CREATE_TABLE);
        String CREATE_TABLE_FAVORITE = "CREATE TABLE favorite (" +
                Config.Movies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Config.Movies.FIELD_ID + " INTEGER NOT NULL, " +
                Config.Movies.FIELD_TITTLE + " TEXT NOT NULL, " +
                Config.Movies.FIELD_VOTE_AVERAGE + " TEXT NOT NULL, " +
                Config.Movies.FIELD_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                Config.Movies.FIELD_OVERVIEW + " TEXT NOT NULL, " +
                Config.Movies.FIELD_STATUS_FAVORITE + " TEXT NOT NULL, " +
                Config.Movies.FIELD_POSTER_PATH + " TEXT NOT NULL, " +
                Config.Movies.FIELD_RELEASE_DATE + " TEXT NOT NULL, " +
                Config.Movies.FIELD_BACKDROP_PATH + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(FavoriteDataHelper.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_MOVIEID+ "=" + id, null);
    }
}
