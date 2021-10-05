package com.example.eulerityhackathon;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eulerityhackathon.MainActivity.TAG;

public class MainActivityViewModel extends AndroidViewModel {

    public MutableLiveData<Bitmap> bitmap = new MutableLiveData<>();
    private Context context;
    private WebService service;
    private ArrayList<JsonListModel> list;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        service = WebRepo.createService();
        getJsonList();
    }

    private void getJsonList() {
        service.getJson().enqueue(new Callback<List<JsonListModel>>() {
            @Override
            public void onResponse(Call<List<JsonListModel>> call, Response<List<JsonListModel>> response) {
                list = (ArrayList<JsonListModel>) response.body();
                if (list != null) {
                    for (JsonListModel m : list) {
                        Log.i(TAG, "onResponse: " + m.getUrl());
                        getImg(m.getUrl());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonListModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void getImg(String url) {

        Runnable r = new Runnable() {
            public void run() {
                try {
                    Drawable d = Glide
                            .with(context)
                            .load(url)
                            .apply(new RequestOptions()
                                    .override(300,300)
                                    .placeholder(R.mipmap.ic_launcher)
                                    .fitCenter()
                            )
                            .submit()
                            .get();
                    Log.i(TAG, "onCreate: got d");
                    BitmapDrawable bd = (BitmapDrawable) d;
                    bitmap.postValue(bd.getBitmap());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(r).start();

    }
}
