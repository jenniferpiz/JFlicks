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

    // View lookup cache
    private static class ViewHolder {
        TextView title;
        TextView overview;
        ImageView poster;
        ImageView backdrop;
        RatingBar rating;
    }

    public enum MovieTypes {
        POPULAR, UNPOPULAR
    }

    public MovieArrayAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    /*
    @Override
    public int getViewTypeCount() {
        // since we are only checking of rating >5 there are only 2 types of views
        return MovieTypes.values().length;
    }


    @Override
    public int getItemViewType(int position) {
        Movie m = getItem(position);
        if (Integer.parseInt(m.getVoteAvg())>5) return MovieTypes.POPULAR.ordinal();
        else return MovieTypes.POPULAR.ordinal();
    }
*/

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        Movie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
            viewHolder.title = convertView.findViewById(R.id.tvTitle);
            viewHolder.overview = convertView.findViewById(R.id.tvOverview);
            viewHolder.poster = convertView.findViewById(R.id.imgPoster);
            viewHolder.rating = convertView.findViewById(R.id.ratingVote);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.title.setText(movie.getTitle());
        viewHolder.overview.setText(movie.getOverview());//imgPoster.setImageURI(new URL(movie.getPosterPath()));
        viewHolder.poster.setImageResource(0);
        viewHolder.rating.setRating(Float.parseFloat(movie.getVoteAvg()));
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getBackdropPath()).fit().centerCrop().placeholder(R.drawable.img_placeholder_land).into(viewHolder.poster);
        } else if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath()).fit().centerCrop().placeholder(R.drawable.img_placeholder_port).into(viewHolder.poster);
        }

        // Return the completed view to render on screen
        return convertView;
    }

    /* Given the item type, responsible for returning the correct inflated XML layout file
    private View getInflatedLayoutForType(int type) {
        if (type == MovieTypes.POPULAR.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_blue_color, null);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_red_color, null);
        }
    }
*/
}
