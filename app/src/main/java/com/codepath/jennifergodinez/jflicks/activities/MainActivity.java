package com.codepath.jennifergodinez.jflicks.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.jennifergodinez.jflicks.R;
import com.codepath.jennifergodinez.jflicks.adapters.MovieArrayAdapter;
import com.codepath.jennifergodinez.jflicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    ArrayList<Movie> movies = null;
    MovieArrayAdapter movieAdapter;
    ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // customize action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#004D40")));

        lvMovies = (ListView)findViewById(R.id.lvMovies);
        movies = new ArrayList<Movie>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvMovies.setAdapter(movieAdapter);
        setupListViewListener();

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;

                try {
                    movieJsonResults = response.getJSONArray("results");
                    Log.d("DEBUG", Integer.toString(movieJsonResults.length()));
                    movies.addAll(Movie.parseResults(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    private void setupListViewListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
                Movie movie = (Movie) adapterView.getItemAtPosition(pos);
                launchMovieActivity(movie);

            }
        });
    }


    private void launchMovieActivity(Movie movie) {
        Intent i = new Intent(MainActivity.this, MovieItemActivity.class);
        i.putExtra("title", movie.getTitle());
        i.putExtra("rating", movie.getVoteAvg());
        i.putExtra("overview", movie.getOverview());
        i.putExtra("youtubeKey", movie.getYouTubeKey());
        startActivity(i);
    }

}
