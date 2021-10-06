package com.example.eulerityhackathon;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eulerityhackathon.models.ImageModel;
import com.example.eulerityhackathon.models.JsonListModel;
import com.example.eulerityhackathon.web.WebRepo;
import com.example.eulerityhackathon.web.WebService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityViewModel extends AndroidViewModel {

    public MutableLiveData<ImageModel> image = new MutableLiveData<ImageModel>();
    private Context context;
    private WebService service = null;
    private ArrayList<JsonListModel> list;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        service = WebRepo.createService("");
        getJsonList();
    }

    public void getJsonList() {
        service.getJson().enqueue(new Callback<List<JsonListModel>>() {
            @Override
            public void onResponse(Call<List<JsonListModel>> call, Response<List<JsonListModel>> response) {
                list = (ArrayList<JsonListModel>) response.body();
                if (list != null) {
                    for (JsonListModel m : list) {
                        getImg(m.getUrl());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonListModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getImg(String url) {

        Runnable r = () -> {
            try {
                Drawable d = Glide
                        .with(context)
                        .load(url)
                        .apply(new RequestOptions()
                                .override(300,300)
                                .fitCenter()
                        )
                        .submit()
                        .get();
                BitmapDrawable bd = (BitmapDrawable) d;
                image.postValue(new ImageModel(bd.getBitmap(), url));

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(r).start();

    }
}
