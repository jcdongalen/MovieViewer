package com.exam.jcdongalen.movieviewer.Activities;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exam.jcdongalen.movieviewer.Fragments.Viewer;
import com.exam.jcdongalen.movieviewer.R;

public class MovieViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        Viewer mViewer = new Viewer();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frmContainer, mViewer)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag("SEATMAP") != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentByTag("SEATMAP"))
                    .commit();

            Viewer mViewer = new Viewer();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frmContainer, mViewer)
                    .commit();
        }
    }
}
