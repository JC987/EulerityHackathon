package com.example.eulerityhackathon;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {
    @GET("image")
    public Call<List<JsonListModel>> getJson();
}
