package com.example.eulerityhackathon.models;

import android.graphics.Bitmap;

public class ImageModel {
    private Bitmap bitmap;
    private String url;

    public ImageModel(Bitmap bitmap, String url) {
        this.bitmap = bitmap;
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
