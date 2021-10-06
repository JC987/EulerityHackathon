package com.example.eulerityhackathon;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.eulerityhackathon.models.JsonUrlModel;
import com.example.eulerityhackathon.web.WebRepo;
import com.example.eulerityhackathon.web.WebService;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                if (urlModel != null) {
                    uploadImg(filepath, originalUrl, urlModel.getUrl());
                }
            }

            @Override
            public void onFailure(Call<JsonUrlModel> call, Throwable t) {
                hasFailed.setValue(true);
                t.printStackTrace();
            }
        });
    }
    public void uploadImg(String filepath, String originalUrl, String postUrl) {
        WebService postService = WebRepo.createService(postUrl);
        RequestBody appid = RequestBody.create(MediaType.parse("multipart/form-data"), "jonathan.cali@outlook.com");
        RequestBody original = RequestBody.create(MediaType.parse("multipart/form-data"),originalUrl);
        MultipartBody.Part filePart = null;
        if(filepath!=null) {
            File file = new File(filepath);
            filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        }


        postService.postImg(filePart, appid, original).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    isLoaded.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isLoaded.postValue(false);
                hasFailed.setValue(true);
                t.printStackTrace();
            }
        });
    }

}
