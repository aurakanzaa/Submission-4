package com.example.aura.submission4_basisdata;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aura.submission4_basisdata.database.FavoriteDataHelper;
import com.example.aura.submission4_basisdata.helper.Config;
import com.sackcentury.shinebuttonlib.ShineButton;

public class DetailActivity extends AppCompatActivity {

    private String poster, tittle, overview, overview_language, releaseDate, vote,  language, backdrop;
    private int id;

    private AppBarLayout appBar;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;
    private ImageView imgPoster;
    private ImageView imgBackdrop;
    private TextView tvReleaseDate;
    private TextView tvVote;
    private TextView tvLang;
    private TextView tvOverview;
    private FloatingActionButton fab;

    private FavoriteDataHelper favoriteDataHelper;
    private ShineButton btnFavorit;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();



        favoriteDataHelper = new FavoriteDataHelper(this);
        SQLiteDatabase sqLiteDatabase  = favoriteDataHelper.getWritableDatabase();
        sqLiteDatabase.isOpen();

        Intent intent = getIntent();
        poster = intent.getStringExtra(Config.BUNDLE_POSTER_IMAGE);
        tittle = intent.getStringExtra(Config.BUNDLE_TITTLE);
        getSupportActionBar().setTitle(tittle);

        id = Integer.parseInt(intent.getStringExtra(Config.BUNDLE_ID));
        overview = intent.getStringExtra(Config.BUNDLE_OVERVIEW);
        overview_language = intent.getStringExtra(Config.BUNDLE_OVERVIEW_LANGUAGE);
        releaseDate = intent.getStringExtra(Config.BUNDLE_RELEASE_DATE);
        vote = intent.getStringExtra(Config.BUNDLE_VOTE_AVERAGE);
        language = intent.getStringExtra(Config.BUNDLE_ORIGINAL_LANGUAGE);
        backdrop = intent.getStringExtra(Config.BUNDLE_BACKDROPH_IMAGE);

        Glide.with(this).load(backdrop).error(R.drawable.ic_launcher_background).into(imgBackdrop);
        Glide.with(this).load(poster).error(R.drawable.ic_launcher_background).into(imgPoster);

        tvReleaseDate.setText(releaseDate);
        tvVote.setText(vote);
        tvLang.setText(language);
        tvOverview.setText(overview);


        sharedPreferences = getApplicationContext().getSharedPreferences("SETTING", 0);
        Boolean favorit = sharedPreferences.getBoolean("FAVORITE"+ tittle, false);
        if (favorit){
            btnFavorit.setChecked(true);
            btnFavorit.setVisibility(View.GONE);
        }
        btnFavorit.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                SharedPreferences.Editor  edit = sharedPreferences.edit();
                if (checked){
                    edit.putBoolean("FAVORITE"+tittle,true);
                    edit.commit();
                    saveDataFavorite();
                } else {
                    edit.putBoolean("FAVORITE"+tittle,false);
                    edit.commit();
                    btnFavorit.setEnabled(false);
                }
            }
        });

    }



    private void saveDataFavorite() {
        ContentValues contentValues  = new ContentValues();
        contentValues.put(Config.Movies.FIELD_ID, String.valueOf(id));
        contentValues.put(Config.Movies.FIELD_TITTLE, tittle);
        contentValues.put(Config.Movies.FIELD_TGL, releaseDate);
        contentValues.put(Config.Movies.FIELD_VOTE_AVERAGE, vote);
        contentValues.put(Config.Movies.FIELD_ORIGINAL_LANGUAGE, language);
        contentValues.put(Config.Movies.FIELD_OVERVIEW, overview);
        contentValues.put(Config.Movies.FIELD_STATUS_FAVORITE, "favorite");
        contentValues.put(Config.Movies.FIELD_POSTER_PATH , poster);
        contentValues.put(Config.Movies.FIELD_RELEASE_DATE , releaseDate);
        contentValues.put(Config.Movies.FIELD_BACKDROP_PATH , backdrop);
        Uri uri = getContentResolver().insert(Config.Movies.CONTENT_URI,contentValues);
        Log.d("uri", "saveDataFavorite: "+ uri);
    }

    private void initView() {
        appBar = findViewById(R.id.app_bar);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbar = findViewById(R.id.toolbar);
        imgPoster = findViewById(R.id.poster);
        tvReleaseDate = findViewById(R.id.release_date);
        tvVote = findViewById(R.id.vote);
        tvLang = findViewById(R.id.language);
        tvOverview = findViewById(R.id.overview);
        fab = findViewById(R.id.fab);
        imgBackdrop = findViewById(R.id.backdrop);
        fab = findViewById(R.id.fab);
        btnFavorit = findViewById(R.id.btnFavorit);
    }

}
