package com.example.eulerityhackathon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.SeekBar;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eulerityhackathon.databinding.ActivityFilterBinding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFilterBinding binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Apply Filter");

        Bitmap originalBitmap = getIntent().getParcelableExtra("bitmap");
        String url = getIntent().getStringExtra("url");
        binding.ifv.setImageBitmap(originalBitmap);
        binding.ifv.setDrawingCacheEnabled(true);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        finish();
                    }
                });

        binding.seekSat.setProgress(10);
        binding.seekBright.setProgress(10);
        binding.seekWarmth.setProgress(10);
        binding.seekContrast.setProgress(10);

        binding.tvSat.setText(String.format(getString(R.string.sat_text), "1"));
        binding.tvBright.setText(String.format(getString(R.string.bright_text), "1"));
        binding.tvContrast.setText(String.format(getString(R.string.contrast_text), "1"));
        binding.tvWarmth.setText(String.format(getString(R.string.warmth_text), "1"));

        binding.seekSat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float scaledProgress = i / 10f;
                binding.ifv.destroyDrawingCache();
                binding.ifv.setSaturation(scaledProgress);
                binding.tvSat.setText(String.format(getString(R.string.sat_text),  scaledProgress + ""));

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
                binding.ifv.destroyDrawingCache();
                binding.ifv.setBrightness(scaledProgress);
                binding.tvBright.setText(String.format(getString(R.string.bright_text), scaledProgress + ""));

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
                binding.ifv.destroyDrawingCache();
                binding.ifv.setWarmth(scaledProgress);
                binding.tvWarmth.setText(String.format(getString(R.string.warmth_text), scaledProgress + ""));
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
                binding.ifv.destroyDrawingCache();
                binding.ifv.setContrast(scaledProgress);
                binding.tvContrast.setText(String.format(getString(R.string.contrast_text), scaledProgress + ""));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.btnCont.setOnClickListener(view -> {
            Intent intent = new Intent(FilterActivity.this, TextActivity.class);
            String s = saveFile(binding);
            intent.putExtra("filepath", s);
            intent.putExtra("url", url);
            binding.ifv.destroyDrawingCache();

            launcher.launch(intent);

        });
    }

    public String saveFile(ActivityFilterBinding binding) {
        Bitmap bitmap = binding.ifv.getDrawingCache();
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

            return outFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}