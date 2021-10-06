package com.example.eulerityhackathon;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.io.File;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eulerityhackathon.MainActivity.TAG;

public class TextActivityViewModel extends ViewModel {
    public MutableLiveData<Boolean> isLoaded = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> hasFailed = new MutableLiveData<>(false);

    private WebService service = WebRepo.createService("");

    public void startUpload(String filepath, String originalUrl) {
        isLoaded.setValue(false);
        service.getUploadUrl().enqueue(new Callback<JsonUrlModel>() {
            @Override
            public void onResponse(Call<JsonUrlModel> call, Response<JsonUrlModel> response) {
                JsonUrlModel urlModel = response.body();
                Log.i(TAG, "onResponse: url ");
                if (urlModel != null) {
                    Log.i(TAG, "onResponse: " + urlModel.getUrl());
                    uploadImg(filepath, originalUrl, urlModel.getUrl());
                }
            }

            @Override
            public void onFailure(Call<JsonUrlModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                hasFailed.setValue(true);
            }
        });
    }
    public void uploadImg(String filepath, String originalUrl, String postUrl) {
        WebService postService = WebRepo.createService(postUrl);
        RequestBody appid =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "jonathan.cali@outlook.com");
        // add another part within the multipart request
        RequestBody original =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),originalUrl);
        MultipartBody.Part filePart = null;
        if(filepath!=null) {
            File file = new File(filepath);
            Log.i("Register", "Nombre del archivo " + file.getName());
            // create RequestBody instance from file
            // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        }


        postService.postImg(filePart, appid, original).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "onResponse: ");
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    try {
                        Log.i(TAG, "onResponse: " + responseBody.string());
                        isLoaded.postValue(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isLoaded.postValue(false);
                hasFailed.setValue(true);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
