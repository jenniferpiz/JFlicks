package com.codepath.jennifergodinez.jflicks.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.jennifergodinez.jflicks.R;
import com.codepath.jennifergodinez.jflicks.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by jennifergodinez on 9/13/17.
 */

public class MovieItemActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(Movie.API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        String youtubeKey = getIntent().getStringExtra("youtubeKey");
                        if (youtubeKey != null) {
                            youTubePlayer.cueVideo(youtubeKey);
                        } else {
                            Log.d("DEBUG", "YouTube Key NOT FOUND");
                        }
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

        String title = getIntent().getStringExtra("title");
        String overview = getIntent().getStringExtra("overview");
        String rating = getIntent().getStringExtra("rating");
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        RatingBar rbRating = (RatingBar)findViewById(R.id.rbRating);
        rbRating.setRating(Float.parseFloat(rating)/2);
        TextView tvOverview = (TextView)findViewById(R.id.tvOverview);
        tvOverview.setText(overview);
    }
}
