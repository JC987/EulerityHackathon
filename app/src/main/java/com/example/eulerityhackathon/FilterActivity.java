package com.example.eulerityhackathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.eulerityhackathon.databinding.ActivityFilterBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.eulerityhackathon.MainActivity.TAG;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFilterBinding binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.seekSat.setProgress(10);
        binding.seekBright.setProgress(10);
        binding.seekWarmth.setProgress(10);
        binding.seekContrast.setProgress(10);
        binding.ifv.setDrawingCacheEnabled(true);

        binding.seekSat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float scaledProgress = i / 10f;
                binding.tvSat.setText("Saturation Scale: " + scaledProgress);
                binding.ifv.setSaturation(scaledProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float scaledProgress = i / 10f;
                binding.tvBright.setText("Bright Scale: " + scaledProgress);
                binding.ifv.setBrightness(scaledProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekWarmth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float scaledProgress = i / 10f;
                binding.tvWarmth.setText("Warmth Scale: " + scaledProgress);
                binding.ifv.setWarmth(scaledProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float scaledProgress = i / 10f;
                binding.tvContrast.setText("Contrast Scale: " + scaledProgress);
                binding.ifv.setContrast(scaledProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                BitmapDrawable draw = (BitmapDrawable) binding.ifv.getDrawable();
                Bitmap bitmap = binding.ifv.getDrawingCache();

                // binding.iv.setImageDrawable(binding.ifv.getDrawable());
                FileOutputStream outStream = null;
                File cache = getCacheDir();
                File dir = new File(cache.getPath());
                String fileName = "fimg.png";
                File outFile = new File(dir, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();

                    Log.i(TAG, "onClick: outfile path " + outFile.getPath());
                    String filePath = outFile.getPath();
                    Bitmap bitmap2 = BitmapFactory.decodeFile(filePath);


                    binding.iv.setImageBitmap(bitmap2);
                    binding.ifv.destroyDrawingCache();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}