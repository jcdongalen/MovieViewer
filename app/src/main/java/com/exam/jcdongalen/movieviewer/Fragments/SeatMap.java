package com.exam.jcdongalen.movieviewer.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exam.jcdongalen.movieviewer.Models.Cinema;
import com.exam.jcdongalen.movieviewer.Models.Cinemas;
import com.exam.jcdongalen.movieviewer.Models.Dates;
import com.exam.jcdongalen.movieviewer.Models.Time;
import com.exam.jcdongalen.movieviewer.Models.Times;
import com.exam.jcdongalen.movieviewer.R;
import com.exam.jcdongalen.movieviewer.Utilities.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeatMap extends Fragment {

    private View view;
    private Spinner sDate, sCinema, sTime;
    private TextView tvTheaterName;
    private LinearLayout llSeatMapArea, llSelectedSeats;
    private Util util;
    private Gson gson;
    private RequestQueue mRequestQueue;
    private List<Dates> dates;
    private List<Cinemas> cinemas;
    private List<Times> times;
    private List<Time> listSelectedTime = null;
    private SeatMapping seatMapping = null;

    public static List<String> listSelectedSeats = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    public static TextView tvTotalAmount;

    public SeatMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_seat_map, container, false);

        initializations();

        return view;
    }

    private void initializations() {
        mRequestQueue = Volley.newRequestQueue(getActivity());
        gson = new Gson();
        util = new Util(getActivity());

        sDate = view.findViewById(R.id.sDate);
        sCinema = view.findViewById(R.id.sCinema);
        sTime = view.findViewById(R.id.sTime);
        tvTheaterName = view.findViewById(R.id.tvTheaterName);
        tvTotalAmount = view.findViewById(R.id.tvTotalAmount);
        llSeatMapArea = view.findViewById(R.id.llSeatMapArea);
        llSelectedSeats = view.findViewById(R.id.llSelectedSeats);

        if (getArguments().getString("theater_name") != null) {
            tvTheaterName.setText(getArguments().getString("theater_name"));
        }

        initializeSchedule();
    }

    private void initializeSchedule() {
        String schedule_url = getActivity().getResources().getString(R.string.api_schedule);
        StringRequest movieScheduleRequest = new StringRequest(Request.Method.GET, schedule_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject scheduleJSON = new JSONObject(response);
                            dates = gson.fromJson(scheduleJSON.getString("dates"), new TypeToken<List<Dates>>() {
                            }.getType());
                            cinemas = gson.fromJson(scheduleJSON.getString("cinemas"), new TypeToken<List<Cinemas>>() {
                            }.getType());
                            times = gson.fromJson(scheduleJSON.getString("times"), new TypeToken<List<Times>>() {
                            }.getType());

                            if (dates.size() > 0) {
                                populateDates();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Error parsing data. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), "Please try again.", BaseTransientBottomBar.LENGTH_INDEFINITE);
                        snackbar.setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initializeSchedule();
                            }
                        });
                        snackbar.show();
                    }
                }
        );

        mRequestQueue.add(movieScheduleRequest);
    }

    //Method for displaying dates to the Spinner
    private void populateDates() {
        String[] sDateString = new String[dates.size()];
        for (int i = 0; i < dates.size(); i++) {
            sDateString[i] = dates.get(i).getLabel();
        }

        ArrayAdapter<String> datesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sDateString);
        sDate.setAdapter(datesAdapter);

        sDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateCinemas(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Method for displaying cinemas to the Spinner
    private void populateCinemas(int datePosition) {
        String DateID = dates.get(datePosition).getId();
        String[] sCinemasString = null;
        for (int i = 0; i < cinemas.size(); i++) {
            if (cinemas.get(i).getParent().equalsIgnoreCase((DateID))) {
                sCinemasString = new String[cinemas.get(i).getCinemas().size()];
                for (int x = 0; x < cinemas.get(i).getCinemas().size(); x++) {
                    sCinemasString[x] = cinemas.get(i).getCinemas().get(x).getLabel();
                }
            }
        }

        if (sCinemasString != null) {
            ArrayAdapter<String> cinemasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sCinemasString);
            sCinema.setAdapter(cinemasAdapter);
        } else {
            sCinema.setAdapter(null);
            sTime.setAdapter(null);

            tvTotalAmount.setText("Php 0.00");
            if(seatMapping != null){
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .remove(seatMapping)
                        .commit();
            }
        }

        sCinema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateTimes(sDate.getSelectedItemPosition(), sCinema.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Method for displaying times to the Spinner
    private void populateTimes(int DatePosition, int CinemaPosition) {
        String DateID = dates.get(DatePosition).getId();
        String CinemaID = "";
        for (int searchCinemaID = 0; searchCinemaID < cinemas.size(); searchCinemaID++) {
            if (cinemas.get(searchCinemaID).getParent().equalsIgnoreCase(DateID)) {
                for (int CID = 0; CID < cinemas.get(searchCinemaID).getCinemas().size(); CID++) {
                    CinemaID = cinemas.get(searchCinemaID).getCinemas().get(CinemaPosition).getId();
                    break;
                }
            }
        }

        String[] sTimeString = null;
        for (int i = 0; i < times.size(); i++) {
            if (times.get(i).getParent().equalsIgnoreCase(CinemaID)) {
                listSelectedTime = times.get(i).getTimes();
                sTimeString = new String[times.get(i).getTimes().size()];
                for (int x = 0; x < times.get(i).getTimes().size(); x++) {
                    sTimeString[x] = times.get(i).getTimes().get(x).getLabel();
                }
            }
        }

        if (sTimeString != null) {
            ArrayAdapter<String> timesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sTimeString);
            sTime.setAdapter(timesAdapter);
        } else {
            sTime.setAdapter(null);
            tvTotalAmount.setText("Php 0.00");
            if(seatMapping != null){
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .remove(seatMapping)
                        .commit();
            }
        }

        sTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seatMapping = new SeatMapping();
                Bundle bundle = new Bundle();
                bundle.putString("price", listSelectedTime.get(position).getPrice());
                seatMapping.setArguments(bundle);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frmSeatMap, seatMapping)
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



}
