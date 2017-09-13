package com.codepath.jennifergodinez.jflicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.jennifergodinez.jflicks.R;
import com.codepath.jennifergodinez.jflicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jennifergodinez on 9/8/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie>{
    final static int POPULAR = 0;
    final static int LESSPOPULAR = 1;

    // View lookup cache for popular movies
    private static class ViewHolder {
        ImageView poster;
    }

    // View lookup cache
    private static class LessViewHolder extends ViewHolder {
        TextView title;
        TextView overview;
        RatingBar rating;
    }


    public MovieArrayAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }


    @Override
    public int getViewTypeCount() {
        // since we are only checking if popular or not, there are only 2 types of views
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (isPopular(getItem(position))) {
            return POPULAR;
        }
        return LESSPOPULAR;
    }


    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        Movie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            int type = getItemViewType(position);
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
            convertView = getInflatedLayoutForType(type);
            if (type == LESSPOPULAR) {
                viewHolder = new LessViewHolder();
                ((LessViewHolder)viewHolder).title = convertView.findViewById(R.id.tvTitle);
                ((LessViewHolder)viewHolder).overview = convertView.findViewById(R.id.tvOverview);
                ((LessViewHolder)viewHolder).rating = convertView.findViewById(R.id.ratingVote);
            } else  {
                viewHolder = new ViewHolder();
            }
            viewHolder.poster = convertView.findViewById(R.id.imgPoster);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        if (viewHolder instanceof LessViewHolder) {
            ((LessViewHolder)viewHolder).title.setText(movie.getTitle());
            ((LessViewHolder)viewHolder).overview.setText(movie.getOverview());
            ((LessViewHolder)viewHolder).rating.setRating(Float.parseFloat(movie.getVoteAvg()));
        }
        viewHolder.poster.setImageResource(0);

        if (isPopular(movie)) {
            Picasso.with(getContext()).load(movie.getBackdropPath()).fit().centerInside().placeholder(R.drawable.img_placeholder_popular).into(viewHolder.poster);
        } else if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getBackdropPath()).fit().centerInside().placeholder(R.drawable.img_placeholder_land).into(viewHolder.poster);
        } else { //if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath()).fit().centerCrop().placeholder(R.drawable.img_placeholder_port).into(viewHolder.poster);
        }

        // Return the completed view to render on screen
        return convertView;
    }

    private boolean isPopular(Movie movie) {
        return (Float.parseFloat(movie.getVoteAvg()) > 5.0);
    }

    private View getInflatedLayoutForType(int type) {
        if (type == POPULAR) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_popular, null);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        }
    }


}
