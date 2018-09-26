package com.example.aura.submission4_basisdata.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aura.submission4_basisdata.DetailActivity;
import com.example.aura.submission4_basisdata.R;
import com.example.aura.submission4_basisdata.helper.Config;
import com.example.aura.submission4_basisdata.model.FavModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<FavModel> listMovie;
    private List<FavModel> search;

    public FavoriteAdapter(Context context, ArrayList<FavModel> listMovie) {
        this.context = context;
        this.listMovie = listMovie;
    }

    @Override
    public FavoriteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteAdapter.MyViewHolder holder, final int position) {
        holder.tvJudul.setText(listMovie.get(position).getTitle());
        holder.tvOverview.setText(listMovie.get(position).getOverview() + " ...");
        holder.tvRelease.setText(listMovie.get(position).getReleaseDate());
        Glide.with(context)
                .load(listMovie.get(position).getPosterPath())
                .into(holder.imgPoster);

        holder.btn_item_list_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(Config.BUNDLE_ID, listMovie.get(position).getId());
                intent.putExtra(Config.BUNDLE_POSTER_IMAGE, listMovie.get(position).getPosterPath());
                intent.putExtra(Config.BUNDLE_TITTLE, holder.tvJudul.getText().toString().trim());
                intent.putExtra(Config.BUNDLE_OVERVIEW, listMovie.get(position).getOverview());
                intent.putExtra(Config.BUNDLE_RELEASE_DATE, holder.tvRelease.getText().toString().trim());
                intent.putExtra(Config.BUNDLE_VOTE_AVERAGE, listMovie.get(position).getVoteAverage());
                intent.putExtra(Config.BUNDLE_ORIGINAL_LANGUAGE, listMovie.get(position).getOriginalLanguage());
                intent.putExtra(Config.BUNDLE_BACKDROPH_IMAGE, listMovie.get(position).getBackdropPath());
                context.startActivity(intent);
            }
        });

        holder.btn_item_list_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = holder.tvJudul.getText().toString().trim();
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.SUBJEK));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.SHARE_VIA)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<FavModel> FavoriteModels = new ArrayList<>();

                if (search == null)
                    search = listMovie;
                if (constraint != null) {
                    if (listMovie != null & search.size() > 0) {
                        for (final FavModel g : search) {
                            if (g.getTitle().toLowerCase().contains(constraint.toString()))
                                FavoriteModels.add(g);
                        }
                    }
                    oReturn.values = FavoriteModels;
                }

                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listMovie = (ArrayList<FavModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvJudul;
        TextView tvOverview;
        TextView tvRelease;
        TextView tvRating;
        CardView cv_klick_detail;
        Button btn_item_list_share;
        Button btn_item_list_detail;

        MyViewHolder(View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.poster);
            tvJudul = itemView.findViewById(R.id.tittle);
            tvOverview = itemView.findViewById(R.id.deskripsi);
            tvRelease = itemView.findViewById(R.id.date);
            tvRating = itemView.findViewById(R.id.rating);
            cv_klick_detail = itemView.findViewById(R.id.detailKlik);
            btn_item_list_share = itemView.findViewById(R.id.btnShare);
            btn_item_list_detail = itemView.findViewById(R.id.btnDetail);
        }
    }
}
