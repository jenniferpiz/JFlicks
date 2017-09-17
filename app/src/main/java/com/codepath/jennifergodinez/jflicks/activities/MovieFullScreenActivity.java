package com.codepath.jennifergodinez.jflicks.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.jennifergodinez.jflicks.R;
import com.codepath.jennifergodinez.jflicks.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by jennifergodinez on 9/13/17.
 */

public class MovieFullScreenActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_fullscreen);

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.fullscreenplayer);

        youTubePlayerView.initialize(Movie.API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        String youtubeKey = getIntent().getStringExtra("youtubeKey");
                        if (youtubeKey != null) {
                            youTubePlayer.loadVideo(youtubeKey);
                            youTubePlayer.play();
                        } else {
                            Log.d("DEBUG", "YouTube Key NOT FOUND");
                        }
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });


    }
}
