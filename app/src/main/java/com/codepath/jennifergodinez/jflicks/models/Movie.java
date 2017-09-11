package com.codepath.jennifergodinez.jflicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jennifergodinez on 9/7/17.
 */

public class Movie {
    String title;
    String posterPath;
    String voteAvg;
    String overview;
    String backdropPath;

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

    public String getBackdropPath() {
        return "https://image.tmdb.org/t/p/w342"+backdropPath;
    }

    public Movie (JSONObject obj) throws JSONException {
        this.title = obj.getString("title");
        this.overview = obj.getString("overview");
        this.posterPath = obj.getString("poster_path");
        this.voteAvg = obj.getString("vote_average");
        this.backdropPath = obj.getString("backdrop_path");
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
