package com.example.challenge.moviedb.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie implements Parcelable{
    private int mID;
    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private String mReleaseDate;
    private double mRating;

    public Movie(int id, String title, String posterPath, String overview, String releaseDate, double rating){
        mID = id;
        mTitle = title;
        mPosterPath = posterPath;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mRating = rating;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPosterPath() {
        return mPosterPath;
    }

    public void setmPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public double getmRating() {
        return mRating;
    }

    public void setmRating(float mRating) {
        this.mRating = mRating;
    }

    public static Movie JSONMovieParser(JSONObject movie) {
        // TODO: Parse movie and return results
        Movie newMovie = null;
        try {
            int id = movie.getInt("id");
            String title = movie.getString("title");
            String posterPath = movie.getString("backdrop_path");
            String overview = movie.getString("overview");
            String releaseDate = movie.getString("release_date");
            double rating = movie.getDouble("vote_average");
            newMovie = new Movie(id, title, posterPath, overview, releaseDate, rating);
        } catch (JSONException jsonException){
            Log.d(Movie.class.getSimpleName(), "Error while parsing the movie");
        }
        return newMovie;
    }

    public static ArrayList<Movie> JSONMoviesParser(JSONObject movies){
        ArrayList<Movie> moviesList = new ArrayList<>();
        try {
            JSONArray moviesArray = movies.getJSONArray("results");
            for(int i = 0; i < moviesArray.length(); i++){
                moviesList.add(JSONMovieParser((JSONObject) moviesArray.get(i)));
            }
        } catch (JSONException jsonException){
            Log.d(Movie.class.getSimpleName(), "Exception while parsing the movies results.");
        }

        return moviesList;
    }

    // Parcelable implementation
    private Movie(Parcel parcel){
        mID = parcel.readInt();
        mTitle = parcel.readString();
        mPosterPath = parcel.readString();
        mOverview = parcel.readString();
        mReleaseDate = parcel.readString();
        mRating = parcel.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mID);
        dest.writeString(mTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
        dest.writeDouble(mRating);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
