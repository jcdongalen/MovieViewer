package com.exam.jcdongalen.movieviewer.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exam.jcdongalen.movieviewer.Models.*;
import com.exam.jcdongalen.movieviewer.Models.SeatMap;
import com.exam.jcdongalen.movieviewer.R;
import com.exam.jcdongalen.movieviewer.Utilities.Util;
import com.exam.jcdongalen.movieviewer.Utilities.ZoomableLinearLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeatMapping extends Fragment {

    private View view;
    private Gson gson;
    private Util util;
    private ZoomableLinearLayout zoomableLinearLayout;
    private LinearLayout llSelectedSeats;
    private TableLayout tblSeatArea;
    private RequestQueue mRequestQueue;
    private com.exam.jcdongalen.movieviewer.Models.SeatMap seatMap = null;
    private String price = "";


    public SeatMapping() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_seat_mapping, container, false);
        if (getArguments().getString("price") != null) {
            price = getArguments().getString("price");
        }
        initializations();

        return view;
    }

    private void initializations() {
        gson = new Gson();
        util = new Util(getActivity());

        llSelectedSeats = view.findViewById(R.id.llSelectedSeats);
        tblSeatArea = view.findViewById(R.id.tblSeatArea);
        zoomableLinearLayout = view.findViewById(R.id.zoomableLayout);
        zoomableLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomableLinearLayout.init(getActivity());
                return false;
            }
        });

        //Fire the seatmap api
        initializeSeatMap();
    }

    private void initializeSeatMap() {
        String seatmap_url = getActivity().getResources().getString(R.string.api_seatmap);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        StringRequest seatMapRequest = new StringRequest(Request.Method.GET, seatmap_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        seatMap = gson.fromJson(response, new TypeToken<SeatMap>() {
                        }.getType());

                        if (seatMap != null) {
                            populateSeats();
                        } else {
                            Toast.makeText(getActivity(), "Error parsing data.", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Error!!!")
                                .setMessage("Slow internet connection. Please try again later.")
                                .setCancelable(false)
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        initializeSeatMap();
                                    }
                                })
                                .show();
                    }
                }
        );
        mRequestQueue.add(seatMapRequest);
    }

    private void populateSeats() {
        for (int i = 0; i < seatMap.getSeatmap().size(); i++) {
            String Indicator = util.getSeatIndicator(seatMap.getSeatmap().get(i));
            TableRow tblRow = new TableRow(getActivity());
            tblRow.setGravity(Gravity.CENTER);

            TextView tvStartTitle = new TextView(getActivity());
            tvStartTitle.setText(Indicator);
            tvStartTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f);
            tvStartTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            tblRow.addView(tvStartTitle);

            for (int x = 0; x < seatMap.getSeatmap().get(i).size(); x++) {
                TextView tvBox = new TextView(getActivity());
                TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(3, 8, 3, 8);
                tvBox.setLayoutParams(params);
                if (seatMap.getSeatmap().get(i).get(x).contains("(") || seatMap.getSeatmap().get(i).get(x).contains(")")) {

                } else {
                    tvBox.setBackground(getResources().getDrawable(R.drawable.reserved));
                    tvBox.setTag(seatMap.getSeatmap().get(i).get(x));
                }

                //TODO: Add Listener on seat reservation


                tblRow.addView(tvBox);
            }

            TextView tvEndTitle = new TextView(getActivity());
            tvEndTitle.setText(Indicator);
            tvEndTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f);
            tvEndTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            tblRow.addView(tvEndTitle);

            tblSeatArea.addView(tblRow);
        }

        //Fire population of Available Seats
        populateAvailableSeats();
    }

    private void populateAvailableSeats() {
        for (int i = 0; i < seatMap.getAvailable().getSeats().size(); i++) {
            final TextView tvBox = tblSeatArea.findViewWithTag(seatMap.getAvailable().getSeats().get(i));
            tvBox.setBackground(getResources().getDrawable(R.drawable.available));
            tvBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSeatSelected(tvBox.getTag().toString())) {
                        com.exam.jcdongalen.movieviewer.Fragments.SeatMap.listSelectedSeats.remove(tvBox.getTag().toString());
                        tvBox.setBackground(getResources().getDrawable(R.drawable.available));
                        removeViewFromSelectedSeats(tvBox.getTag().toString());
                    } else {
                        if (com.exam.jcdongalen.movieviewer.Fragments.SeatMap.listSelectedSeats.size() < 10) {
                            com.exam.jcdongalen.movieviewer.Fragments.SeatMap.listSelectedSeats.add(tvBox.getTag().toString());
                            tvBox.setBackground(getResources().getDrawable(R.mipmap.ic_check));
                            addViewToSelectedSeats(tvBox.getTag().toString());
                        } else {
                            Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content),
                                    "Maximum of 10 selected seats only.", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }

        //Populate if there is previously selected seats
        if (com.exam.jcdongalen.movieviewer.Fragments.SeatMap.listSelectedSeats.size() > 0) {
            populateExistingSelectedSeats();
        }
    }

    private boolean isSeatSelected(String TAG) {
        boolean isSelected = false;
        for (int i = 0; i < com.exam.jcdongalen.movieviewer.Fragments.SeatMap.listSelectedSeats.size(); i++) {
            if (com.exam.jcdongalen.movieviewer.Fragments.SeatMap.listSelectedSeats.get(i).equalsIgnoreCase(TAG)) {
                isSelected = true;
                break;
            }
        }

        return isSelected;
    }

    private void addViewToSelectedSeats(String TAG) {
        TextView tvIndicator = new TextView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(3, 3, 3, 3);
        tvIndicator.setLayoutParams(params);
        tvIndicator.setPadding(0, 3, 0, 3);
        tvIndicator.setText(TAG);
        tvIndicator.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvIndicator.setBackgroundColor(getResources().getColor(R.color.colorSelected));
        tvIndicator.setTextColor(getResources().getColor(android.R.color.white));
        tvIndicator.setTag(TAG);
        llSelectedSeats.addView(tvIndicator);

        updateTotalAmount();
    }

    private void removeViewFromSelectedSeats(String TAG) {
        TextView tvIndicator = llSelectedSeats.findViewWithTag(TAG);
        llSelectedSeats.removeView(tvIndicator);

        updateTotalAmount();
    }

    private void populateExistingSelectedSeats() {
        List<String> listSelectedSeats = com.exam.jcdongalen.movieviewer.Fragments.SeatMap.listSelectedSeats;
        if (listSelectedSeats.size() > 0) {
            for (int i = 0; i < listSelectedSeats.size(); i++) {
                TextView tvIndicator = tblSeatArea.findViewWithTag(listSelectedSeats.get(i));
                tvIndicator.setBackground(getResources().getDrawable(R.mipmap.ic_check));
                addViewToSelectedSeats(listSelectedSeats.get(i));
            }
        }
        updateTotalAmount();
    }

    private void updateTotalAmount(){
        Float totalAmount = com.exam.jcdongalen.movieviewer.Fragments.SeatMap.listSelectedSeats.size() * Float.parseFloat(price);
        com.exam.jcdongalen.movieviewer.Fragments.SeatMap.tvTotalAmount.setText(util.getFormattedAmount(totalAmount));
    }
}
