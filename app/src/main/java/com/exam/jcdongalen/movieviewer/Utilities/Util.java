package com.exam.jcdongalen.movieviewer.Utilities;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jc on 09/01/2018.
 */

public class Util {

    private Context mContext;

    public Util(Context context){
        mContext = context;
    }

    public String refactorDuration(String T) {
        int duration = (int) Float.parseFloat(T);
        int hours = duration / 60;
        int minutes = duration % 60;
        String response = "";

        response = hours + "hrs";

        if (minutes > 0) {
            response = response + " and " + minutes + "mins";
        }

        return response;
    }

    public String refactorReleaseDate(String date){
        Date parsedDate = null;
        SimpleDateFormat sdfReceivedFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat sdfReturnFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        try {
            parsedDate = sdfReceivedFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdfReturnFormat.format(parsedDate);
    }

    public String getSeatIndicator(List<String> list){
        String temp = "";
        for(int i = 0; i<list.size(); i++){
            if(!list.get(i).contains("(") && !list.get(i).contains(")")){
                temp = list.get(i).substring(0, 1);
                break;
            }
        }

        return temp;
    }

    public String getFormattedAmount(Float amount){
        DecimalFormat df = new DecimalFormat("##,###.00");
        return "Php " + df.format(amount);
    }
}
