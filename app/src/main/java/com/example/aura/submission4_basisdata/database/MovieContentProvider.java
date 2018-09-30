package com.example.aura.submission4_basisdata.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.aura.submission4_basisdata.helper.Config;

public class MovieContentProvider extends ContentProvider {
    public static final int ALL_FILM = 100;
    public static final int FILM_WITH_ID = 101;

    private static final UriMatcher URI_MATCHER = buildurimatcher();

    private static UriMatcher buildurimatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Config.AUTHORITY, Config.PATH_TASKS,ALL_FILM);
        uriMatcher.addURI(Config.AUTHORITY, Config.PATH_TASKS + "/#", FILM_WITH_ID);
        return uriMatcher;
    }


    FavoriteDataHelper movieDBHelper;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        movieDBHelper = new FavoriteDataHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        int match = URI_MATCHER.match(uri);
        Cursor retCursor;
        switch (match) {
            case ALL_FILM:
                retCursor =  db.query(Config.Movies.DATABASE_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

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
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = movieDBHelper.getWritableDatabase();

        int match = URI_MATCHER.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case ALL_FILM:
                long id = db.insert(Config.Movies.DATABASE_TABLE, null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(Config.Movies.CONTENT_URI, id);
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

        switch (URI_MATCHER.match(uri)) {

            case ALL_FILM:
                numRowsDeleted = movieDBHelper.getWritableDatabase().delete(
                        Config.Movies.DATABASE_TABLE,
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
