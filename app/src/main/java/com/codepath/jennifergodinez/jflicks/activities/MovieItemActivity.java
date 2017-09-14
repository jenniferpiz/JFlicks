package com.codepath.jennifergodinez.jflicks.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.jennifergodinez.jflicks.R;

/**
 * Created by jennifergodinez on 9/13/17.
 */

public class MovieItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        // customize action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#004D40")));

        String title = getIntent().getStringExtra("title");
        String overview = getIntent().getStringExtra("overview");
        String rating = getIntent().getStringExtra("rating");
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        RatingBar rbRating = (RatingBar)findViewById(R.id.rbRating);
        rbRating.setRating(Float.parseFloat(rating));
        TextView tvOverview = (TextView)findViewById(R.id.tvOverview);
        tvOverview.setText(overview);
    }
}
