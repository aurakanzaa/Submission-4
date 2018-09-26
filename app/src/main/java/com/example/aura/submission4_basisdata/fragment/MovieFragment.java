package com.example.aura.submission4_basisdata.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aura.submission4_basisdata.R;
import com.example.aura.submission4_basisdata.adapter.MovieAdapter;
import com.example.aura.submission4_basisdata.database.FavoriteDataHelper;
import com.example.aura.submission4_basisdata.helper.Config;
import com.example.aura.submission4_basisdata.model.MovieModel;
import com.example.aura.submission4_basisdata.model.ResultItem;
import com.example.aura.submission4_basisdata.rest.ApiService;
import com.example.aura.submission4_basisdata.rest.Client;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {
    private EditText edtSearch;
    private RecyclerView rvMovie;
    private MovieAdapter movieAdapter ;
    private ArrayList<ResultItem> resultsItems;
    private SQLiteDatabase sqLiteDatabase;
    FavoriteDataHelper dbHelper;
    Activity activity;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtSearch = view.findViewById(R.id.edt_search);
        rvMovie = view.findViewById(R.id.rv_movie);
        resultsItems = new ArrayList<>();
        dbHelper = new FavoriteDataHelper(getActivity());

        getDataPopular();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                movieAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void getDataPopular() {
        ApiService apiService = Client.getInstanceRetrofit();
        apiService.getMovie().enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.isSuccessful()){
                    resultsItems = response.body().getResults();
                    movieAdapter = new MovieAdapter(getActivity(), resultsItems);
                    rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvMovie.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Toast.makeText(getActivity(), "" + Config.ERROR_NETWORK, Toast.LENGTH_SHORT).show();
            }
        });
    }
}