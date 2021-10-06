package com.example.eulerityhackathon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.eulerityhackathon.databinding.ActivityTextBinding;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextActivity extends AppCompatActivity {
    private int xOffset = 0;
    private int yOffset = 0;
    private int colorId = 0;
    private String url;
    private TextActivityViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTextBinding binding = ActivityTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Add Text");


        vm = new ViewModelProvider(this).get(TextActivityViewModel.class);

        vm.isLoaded.observe(this, aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    binding.pb.setVisibility(View.GONE);
                    setResult(Activity.RESULT_OK);
                    TextActivity.this.finish();
                } else {
                    binding.pb.setVisibility(View.VISIBLE);
                }
            }
        });

        vm.hasFailed.observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(this, "Something went wrong while uploading", Toast.LENGTH_SHORT).show();
            }
        });

        String path = getIntent().getStringExtra("filepath");
        url = getIntent().getStringExtra("url");
        Bitmap originalBitmap = BitmapFactory.decodeFile(path);

        binding.ifv.setImageBitmap(originalBitmap);
        binding.ifv.setDrawingCacheEnabled(true);

        String[] colors = new String[]{"Black", "White", "Yellow", "Blue", "Green", "Red"};
        ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, colors);
        binding.spnColor.setAdapter(cAdapter);

        binding.spnColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                   case 0:
                       colorId = Color.BLACK;
                       break;
                   case 1:
                        colorId = Color.WHITE;
                        break;
                    case 2:
                        colorId = Color.YELLOW;
                        break;
                    case 3:
                        colorId = Color.BLUE;
                        break;
                    case 4:
                        colorId = Color.GREEN;
                        break;
                    case 5:
                        colorId = Color.RED;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnUp.setOnClickListener(view -> {
            yOffset += 10;
            updateImage(binding, originalBitmap);
        });

        binding.btnDown.setOnClickListener(view -> {
            yOffset -= 10;
            updateImage(binding, originalBitmap);
        });

        binding.btnLeft.setOnClickListener(view -> {
            xOffset += 10;
            updateImage(binding, originalBitmap);
        });

        binding.btnRight.setOnClickListener(view -> {
            xOffset -= 10;
            updateImage(binding, originalBitmap);
        });

        binding.btnSave.setOnClickListener(view -> createSubmissionDialog(binding));

        binding.btnApply.setOnClickListener(view -> updateImage(binding, originalBitmap));

    }

    public  void updateImage(ActivityTextBinding binding, Bitmap originalBitmap) {
        int textSize = 0;
        if (binding.etSize.getText().toString().length() > 0) {
            textSize = Integer.parseInt(binding.etSize.getText().toString());
        }
        Bitmap drawMultilineBitmap = drawMultilineTextToBitmap(
                originalBitmap,
                binding.etMessage.getText().toString(),
                textSize
        );
        binding.ifv.setImageBitmap(drawMultilineBitmap);
    }

    public void  createSubmissionDialog(ActivityTextBinding binding){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Save Changes")
                .setMessage("Do you want to save and upload changes?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {

                    String savedFilePath = saveFile(binding);
                    vm.startUpload(savedFilePath, url);

                })
                .setNegativeButton("No", (dialogInterface, i) -> {

                })
                .create();
            dialog.show();
    }

    public String saveFile(ActivityTextBinding binding) {
        Bitmap bitmap = binding.ifv.getDrawingCache();

        FileOutputStream outStream = null;
        File cache = getCacheDir();
        File dir = new File(cache.getPath());
        String fileName = "filtered_img.png";
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            String filePath = outFile.getPath();
            binding.ifv.destroyDrawingCache();

            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public Bitmap drawMultilineTextToBitmap(Bitmap bitmap, String text, int textSize) {
        Resources resources = getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(colorId);
        paint.setTextSize((int) (textSize * scale));

        int textWidth = canvas.getWidth() - (int) (16 * scale);
        StaticLayout textLayout =new StaticLayout(text, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        int textHeight = textLayout.getHeight();
        float x = (bitmap.getWidth() - textWidth)/2f;
        float y = (bitmap.getHeight() - textHeight)/2f;

        canvas.save();
        canvas.translate(x - xOffset, y - yOffset);
        textLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }
}