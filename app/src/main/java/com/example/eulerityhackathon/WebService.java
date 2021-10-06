package com.example.eulerityhackathon;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface WebService {
    @GET("image")
    Call<List<JsonListModel>> getJson();

    @GET("upload")
    Call<JsonUrlModel> getUploadUrl();

    @Multipart
    @POST(".")
    Call<ResponseBody> postImg(
            @Part MultipartBody.Part filePart,
            @Part("appid") RequestBody appid,
            @Part("original") RequestBody original
    );
}
