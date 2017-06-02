package com.example.challenge.moviedb.utils;

import org.json.JSONObject;

public interface NetworkingInterface {
    void onResult(JSONObject response);
    void onFailure(String errorMessage);
}
