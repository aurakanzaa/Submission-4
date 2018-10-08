package com.example.aura.submission4_basisdata.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.aura.submission4_basisdata.helper.Config;

public class MovieContentProvider extends ContentProvider {
    public static final int ALL_MOVIE = 100;
    public static final int MOVIE_ID = 101;
    FavoriteDataHelper dbHelper;

    private static final UriMatcher URI_MATCHER = buildurimatcher();

    private static UriMatcher buildurimatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Config.AUTHORITY, Config.PATH_TASKS,ALL_MOVIE);
        uriMatcher.addURI(Config.AUTHORITY, Config.PATH_TASKS + "/#", MOVIE_ID);
        return uriMatcher;
    }


    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(Config.AUTHORITY, Config.PATH_TASKS,ALL_MOVIE);
        sURIMatcher.addURI(Config.AUTHORITY, Config.PATH_TASKS + "/#", MOVIE_ID);
 //       return sURIMatcher;
    }



    FavoriteDataHelper favoriteDataBHelper;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        favoriteDataBHelper = new FavoriteDataHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
//        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
//        queryBuilder.setTables(Config.Movies.TABLE_FAV);

        final SQLiteDatabase db = favoriteDataBHelper.getReadableDatabase();
        int match = sURIMatcher.match(uri);
        Cursor retCursor;
        switch (match) {
            case ALL_MOVIE:
                retCursor =  db.query(Config.Movies.TABLE_FAV,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
//                queryBuilder.appendWhere(Config.Movies.ID + "="
//                + uri.getLastPathSegment());
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
//                throw new IllegalArgumentException("UNKNOWN URI");
        }
//        Cursor cursor = queryBuilder.query(favoriteDataBHelper.getReadableDatabase(),
//                projection,selection,selectionArgs, null, null, sortOrder);
//        cursor.setNotificationUri(getContext().getContentResolver(), uri);
//        return cursor;

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {


        int match = sURIMatcher.match(uri);

        final SQLiteDatabase db = favoriteDataBHelper.getWritableDatabase();


        Uri returnUri; // URI to be returned

        switch (match) {
            case ALL_MOVIE:
                long id = db.insert(Config.Movies.TABLE_FAV, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(Config.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        if (null == selection) selection = "1";

        switch (sURIMatcher.match(uri)) {

            case ALL_MOVIE:
                numRowsDeleted = favoriteDataBHelper.getWritableDatabase().delete(
                        Config.Movies.TABLE_FAV,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }



}
