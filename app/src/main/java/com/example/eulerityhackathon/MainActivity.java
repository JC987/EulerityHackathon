package com.example.eulerityhackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.eulerityhackathon.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "EulerityTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<ImageModel> list = new ArrayList<>();

        /*
        list.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.halo2));

        list.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.halo3));

        list.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.halo4));
*/

      //https://images.pexels.com/photos/160846/french-bulldog-summer-smile-joy-160846.jpeg

        Log.i(TAG, "onCreate: " + list.size());
        CustomAdapter adapter = new CustomAdapter(this);

        binding.mainRv.setAdapter(adapter);
        binding.mainRv.addItemDecoration( new DividerItemDecoration(this, LinearLayout.VERTICAL));
        binding.mainRv.setLayoutManager(new LinearLayoutManager(this));

        int width = 60;
        int height = 60;
        ImageView iv = new ImageView(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
        iv.setLayoutParams(parms);
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


    }
}