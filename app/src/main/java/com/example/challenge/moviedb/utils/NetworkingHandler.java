package com.example.challenge.moviedb.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NetworkingHandler {
    private final String apiKey = "20bf9b634197a69f61284240be64930f";
    private final String basePath = "https://api.themoviedb.org/3";
    public static final String posterUrl = "http://image.tmdb.org/t/p/w185/";
    private final String movieSearchPath = "/search/movie";
    private Context mContext;
    private RequestQueue requestQueue;


    public NetworkingHandler(Context context) {
        if (context instanceof NetworkingInterface){
            mContext = context;
            requestQueue = Volley.newRequestQueue(mContext);
        } else {
            throw new IllegalArgumentException("Given context does not implement the Networking Interface");
        }
    }
    public void searchMovies(String query, int page, boolean includeAdult){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(basePath);
        stringBuilder.append(movieSearchPath);
        stringBuilder.append("?api_key=" + apiKey);
        stringBuilder.append("&language=en-US"); // TODO: Get language from OS
        stringBuilder.append("&query=" + query);
        stringBuilder.append("&page=" + page);
        stringBuilder.append("&include_adult=" + includeAdult);  // Adult
        String searchUrl = stringBuilder.toString();

        Response.Listener listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ((NetworkingInterface) mContext).onResult(response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((NetworkingInterface) mContext).onFailure(error.getMessage());
            }
        };

        JSONObject jsonRequest = new JSONObject();

        JsonRequest searchRequest = new JsonObjectRequest(
                Request.Method.GET,
                searchUrl,
                jsonRequest,
                listener,
                errorListener
        );
        searchRequest.setTag(mContext.getClass());
        requestQueue.add(searchRequest);
    }

    public void discoverMovies(){
        String discoverURL = basePath + "/discover/movie" + "?api_key=" + apiKey + "&sort_by=popularity.desc";

        Response.Listener listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ((NetworkingInterface) mContext).onResult(response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((NetworkingInterface) mContext).onFailure(error.getMessage());
            }
        };
        JSONObject jsonRequest = new JSONObject();
        JsonRequest discoverRequest = new JsonObjectRequest(
                Request.Method.GET,
                discoverURL,
                jsonRequest,
                listener,
                errorListener
        );
        discoverRequest.setTag(mContext.getClass());
        requestQueue.add(discoverRequest);
    }

    public void cancelRequests(){
        requestQueue.cancelAll(mContext.getClass());
    }

}
