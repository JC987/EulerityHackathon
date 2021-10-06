package com.example.eulerityhackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.eulerityhackathon.databinding.ActivityMainBinding;
import com.example.eulerityhackathon.models.ImageModel;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "EulerityTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<ImageModel> list = new ArrayList<>();

        Log.i(TAG, "onCreate: " + list.size());
        CustomAdapter adapter = new CustomAdapter(this);

        binding.mainRv.setAdapter(adapter);
        binding.mainRv.addItemDecoration( new DividerItemDecoration(this, LinearLayout.VERTICAL));
        binding.mainRv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setList(list);
        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);

        vm.image.observe(this, new Observer<ImageModel>() {
            @Override
            public void onChanged(ImageModel imageModel) {
                if (imageModel != null) {
                    list.add(imageModel);
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "onChanged: bitmap");
                }
            }
        });
        vm.isLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.pb.setVisibility(View.VISIBLE);
                } else {
                    binding.pb.setVisibility(View.GONE);
                }
            }
        });
    }
}