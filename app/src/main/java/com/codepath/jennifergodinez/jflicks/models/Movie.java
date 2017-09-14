package com.codepath.jennifergodinez.jflicks.models;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jennifergodinez on 9/7/17.
 */

public class Movie {
    String title;
    String posterPath;
    String voteAvg;
    String overview;
    String backdropPath;
    String youtubeKey;
    String id;

    public static String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w342"+posterPath;
    }

    public String getVoteAvg() {
        return voteAvg;
    }

    public String getOverview() {
        return overview;
    }

    public String getYouTubeKey() { return youtubeKey; }


    private String getKey() {
        if (youtubeKey == null) {
            String url = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray trailerJsonResults = null;

                    try {
                        trailerJsonResults = response.getJSONArray("results");
                        Log.d("DEBUG", Integer.toString(trailerJsonResults.length()));
                        if (trailerJsonResults.length() > 0) {
                            JSONObject trailer = (JSONObject)trailerJsonResults.get(0);
                            youtubeKey = trailer.getString("key");
                        }
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
        return youtubeKey;
    }


    public String getBackdropPath() {
        return "https://image.tmdb.org/t/p/w342"+backdropPath;
    }

    public Movie (JSONObject obj) throws JSONException {
        this.title = obj.getString("title");
        this.overview = obj.getString("overview");
        this.posterPath = obj.getString("poster_path");
        this.voteAvg = obj.getString("vote_average");
        this.backdropPath = obj.getString("backdrop_path");
        this.id = obj.getString("id");
        this.youtubeKey = getKey();
    }

    public static ArrayList<Movie> parseResults(JSONArray resultsArray) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int i=0; i<resultsArray.length(); i++) {
            try {
                results.add(new Movie(resultsArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
