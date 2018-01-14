package com.exam.jcdongalen.movieviewer.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exam.jcdongalen.movieviewer.Models.Movie;
import com.exam.jcdongalen.movieviewer.R;
import com.exam.jcdongalen.movieviewer.Utilities.Util;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class Viewer extends Fragment {

    private View view;
    private TextView tvMovieName, tvGenre, tvAdvisoryRating, tvDuration, tvReleaseDate, tvSypnosis;
    private NetworkImageView imgLandscape, imgPortrait;
    private Button btnViewSeatMap;
    Gson gson;
    RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Util util;
    private Movie currentMovie = null;

    public Viewer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_viewer, container, false);
        initializations();
        initializeVolley();
        return view;
    }

    //View initializations
    private void initializations() {
        tvMovieName = view.findViewById(R.id.tvName);
        tvGenre = view.findViewById(R.id.tvGenre);
        tvAdvisoryRating = view.findViewById(R.id.tvAdvisoryRating);
        tvDuration = view.findViewById(R.id.tvDuration);
        tvReleaseDate = view.findViewById(R.id.tvReleaseDate);
        tvSypnosis = view.findViewById(R.id.tvSypnosis);

        tvMovieName.setText("...");
        tvGenre.setText("...");
        tvAdvisoryRating.setText("...");
        tvDuration.setText("...");
        tvReleaseDate.setText("...");
        tvSypnosis.setText("...");

        imgLandscape = view.findViewById(R.id.imgLandscape);
        imgPortrait = view.findViewById(R.id.imgPortrait);

        btnViewSeatMap = view.findViewById(R.id.btnViewSeatMap);
        btnViewSeatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentMovie != null){
                    SeatMap seatMap = new SeatMap();
                    Bundle bundle = new Bundle();
                    bundle.putString("theater_name", currentMovie.getTheater());
                    seatMap.setArguments(bundle);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frmContainer, seatMap, "SEATMAP")
                            .commit();
                }
                else{
                    Toast.makeText(getActivity(), "Failed to fetch the movie details.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Method for initialization of volley, gson and the Util
    private void initializeVolley(){
        gson = new Gson();
        util = new Util(getActivity());
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });

        //Fire the movie Details Request
        getMovieDetails();
    }

    //Method for fetching data from api using volley
    private void getMovieDetails() {
        String url = getResources().getString(R.string.api_movie);
        final StringRequest movieDetailsRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        currentMovie = gson.fromJson(response, Movie.class);
                        tvMovieName.setText(currentMovie.getCanonicalTitle());
                        tvGenre.setText(currentMovie.getGenre());
                        tvAdvisoryRating.setText(currentMovie.getAdvisoryRating());
                        String formattedDuration = util.refactorDuration(currentMovie.getRuntimeMins());
                        tvDuration.setText(formattedDuration);
                        String formattedReleaseDate = util.refactorReleaseDate(currentMovie.getReleaseDate());
                        tvReleaseDate.setText(formattedReleaseDate);
                        tvSypnosis.setText(currentMovie.getSynopsis());

                        imgLandscape.setImageUrl(currentMovie.getPosterLandscape(), mImageLoader);
                        imgPortrait.setImageUrl(currentMovie.getPoster(), mImageLoader);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), "Please try again.", BaseTransientBottomBar.LENGTH_INDEFINITE);
                        snackbar.setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getMovieDetails();
                            }
                        });
                        snackbar.show();
                    }
                }
        );
        mRequestQueue.add(movieDetailsRequest);
    }

}
