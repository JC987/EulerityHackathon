package com.example.eulerityhackathon;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebRepo {
    /*private static WebRepo single = null;
    public static WebRepo getInstance() {
        if (single == null) {
            single = new WebRepo();
        }
        return single;
    }
    private WebRepo() {

    }*/

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).build();

    private static String baseUrl = "http://eulerity-hackathon.appspot.com/";


    /*public ArrayList<JsonListModel> getJsonList() {
        ArrayList<JsonListModel> list;
        Call<List<JsonListModel>> res = service.getJson();
        res.enqueue(new Callback<List<JsonListModel>>() {
            @Override
            public void onResponse(Call<List<JsonListModel>> call, Response<List<JsonListModel>> response) {
                ArrayList<JsonListModel> list = (ArrayList<JsonListModel>) response.body();
                if (list != null) {
                    for (JsonListModel j : list) {
                        Log.i(TAG, "onResponse: " + j.getUrl() + " -> " + j.getCreated());
                    }


                }
            }

            @Override
            public void onFailure(Call<List<JsonListModel>> call, Throwable t) {

            }
        });

        /*Call<ResponseBody> rb = service.getJson();
        rb.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    Log.i(TAG, "onResponse: s is "+ s);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }*/

    public static WebService createService(String url) {
        if (!url.equals("")){
            baseUrl = url;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(WebService.class);
    }
}
