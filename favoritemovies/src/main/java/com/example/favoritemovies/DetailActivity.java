package com.example.favoritemovies;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.favoritemovies.helper.Config;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

public class DetailActivity extends AppCompatActivity {

    private String imagePoster, tittle, overview, release, voteAverage, language, backdrophImage;

    private AppBarLayout appBar;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;
    private ImageView imgPosterFav;
    private ImageView imgBackdropFav;
    private TextView tvReleaseDateFav;
    private TextView tvVoteFav;
    private TextView tvLangFav;
    private TextView tvOverviewFav;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        imagePoster = intent.getStringExtra(Config.BUNDLE_POSTER_IMAGE);
        tittle = intent.getStringExtra(Config.BUNDLE_TITTLE);
        getSupportActionBar().setTitle(tittle);

        overview = intent.getStringExtra(Config.BUNDLE_OVERVIEW);
        release = intent.getStringExtra(Config.BUNDLE_RELEASE_DATE);
        voteAverage = intent.getStringExtra(Config.BUNDLE_VOTE_AVERAGE);
        language = intent.getStringExtra(Config.BUNDLE_ORIGINAL_LANGUAGE);
        backdrophImage = intent.getStringExtra(Config.BUNDLE_BACKDROPH_IMAGE);

        Glide.with(this).load(backdrophImage).error(R.drawable.ic_launcher_background).into(imgBackdropFav);
        Glide.with(this).load(imagePoster).error(R.drawable.ic_launcher_background).into(imgPosterFav);

        tvReleaseDateFav.setText(release);
        tvVoteFav.setText(voteAverage);
        tvLangFav.setText(language);
        tvOverviewFav.setText(overview);

    }

    private void initView() {
        appBar = findViewById(R.id.app_bar);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbar = findViewById(R.id.toolbar);
        imgPosterFav = findViewById(R.id.poster);
        tvReleaseDateFav = findViewById(R.id.release_date);
        tvVoteFav = findViewById(R.id.vote);
        tvLangFav = findViewById(R.id.language);
        tvOverviewFav = findViewById(R.id.overview);
        fab = findViewById(R.id.fab);
        imgBackdropFav = findViewById(R.id.backdrop);
    }


}
