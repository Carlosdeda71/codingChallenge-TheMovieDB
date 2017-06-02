package com.example.challenge.moviedb;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.challenge.moviedb.models.Movie;
import com.example.challenge.moviedb.utils.NetworkingHandler;
import com.example.challenge.moviedb.utils.NetworkingInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Locale;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment implements NetworkingInterface {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_EXTRA_ITEM = "MOVIE";
    private CollapsingToolbarLayout appBarLayout;
    private Movie mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_EXTRA_ITEM)) {
            mItem = getArguments().getParcelable(ARG_EXTRA_ITEM);

            Activity activity = this.getActivity();
            appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getmTitle());
                ImageView imageView = (ImageView) appBarLayout.findViewById(R.id.movie_image);
                Picasso.with(getContext())
                        .load(NetworkingHandler.posterUrl + mItem.getmPosterPath())
                        .into(imageView);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.movie_detail)).setText(mItem.getmOverview());
            ((TextView) rootView.findViewById(R.id.text_release_date)).setText(mItem.getmReleaseDate());
            String popularity = String.format(Locale.getDefault(),"%,.2f", mItem.getmRating());
            ((TextView) rootView.findViewById(R.id.text_popularity)).setText(popularity);
        }

        return rootView;
    }

    @Override
    public void onResult(JSONObject response) {
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getmTitle());
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        Log.d(this.getClass().getSimpleName(), "Failed to load movie");
    }
}
