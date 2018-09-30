package com.example.favoritemovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.favoritemovies.DetailActivity;
import com.example.favoritemovies.R;
import com.example.favoritemovies.helper.Config;
import com.example.favoritemovies.model.FavModel;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.MyViewHolder>{

    private Cursor cursor;
    private Context context;

    public void setListMovie(Cursor listMovie) {
        this.cursor = listMovie;
    }

    public FavoriteMovieAdapter(Activity context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;
        private TextView tvTittle;
        private TextView tvOverview;
        private TextView tvDate;
        private TextView tvRate;
        private Button btnDetail;
        private Button btnShare;
        public MyViewHolder(View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.poster);
            tvTittle = itemView.findViewById(R.id.tittle);
            tvOverview = itemView.findViewById(R.id.deskripsi);
            tvDate = itemView.findViewById(R.id.date);
            tvRate = itemView.findViewById(R.id.rating);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            btnShare = itemView.findViewById(R.id.btnShare);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_fav, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final FavModel favModel = getItem(position);
        Glide.with(context).load(favModel.getPosterPath()).error(R.drawable.ic_launcher_background).into(holder.imgPoster);
        holder.tvTittle.setText(favModel.getTitle());
        holder.tvOverview.setText(favModel.getOverview());
        holder.tvDate.setText(favModel.getReleaseDate());
        holder.tvRate.setText(favModel.getVoteAverage());

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(Config.BUNDLE_ID, favModel.getId());

                intent.putExtra(Config.BUNDLE_POSTER_IMAGE, favModel.getPosterPath());
                intent.putExtra(Config.BUNDLE_TITTLE, favModel.getTitle());
                intent.putExtra(Config.BUNDLE_OVERVIEW, favModel.getOverview());
                intent.putExtra(Config.BUNDLE_RELEASE_DATE, favModel.getReleaseDate());
                intent.putExtra(Config.BUNDLE_VOTE_AVERAGE, favModel.getVoteAverage());
                intent.putExtra(Config.BUNDLE_ORIGINAL_LANGUAGE, favModel.getOriginalLanguage());
                intent.putExtra(Config.BUNDLE_BACKDROPH_IMAGE, favModel.getBackdropPath());

                context.startActivity(intent);

            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = holder.tvTittle.getText().toString().trim();
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.SUBJEK));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.SHARE_VIA)));
            }
        });
    }

    private FavModel getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Postion Invalid");
        }
        return new FavModel(cursor);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }


}
