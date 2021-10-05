package com.example.eulerityhackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eulerityhackathon.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "EulerityTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Bitmap> list = new ArrayList<>();

        list.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.halo2));

        list.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.halo3));

        list.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.halo4));

        Log.i(TAG, "onCreate: " + list.size());
        CustomAdapter adapter = new CustomAdapter(this);
        adapter.setList(list);
        binding.mainRv.setAdapter(adapter);
        binding.mainRv.addItemDecoration( new DividerItemDecoration(this, LinearLayout.VERTICAL));
        binding.mainRv.setLayoutManager(new LinearLayoutManager(this));


    }
}