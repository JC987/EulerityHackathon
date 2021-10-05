package com.example.eulerityhackathon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.eulerityhackathon.databinding.ActivityFilterBinding;
import com.example.eulerityhackathon.databinding.ActivityTextBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.eulerityhackathon.MainActivity.TAG;

public class TextActivity extends AppCompatActivity {
    int xOffset = 0;
    int yOffset = 0;
    int colorId = 0;
    String url;
    TextActivityViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTextBinding binding = ActivityTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         vm = new ViewModelProvider(this).get(TextActivityViewModel.class);

         vm.isLoading.observe(this, new Observer<Boolean>() {
             @Override
             public void onChanged(Boolean aBoolean) {
                 if (aBoolean) {
                     binding.pb.setVisibility(View.GONE);
                 } else {
                     binding.pb.setVisibility(View.VISIBLE);
                 }
             }
         });
        String path = getIntent().getStringExtra("filepath");
        url = getIntent().getStringExtra("url");
        Bitmap originalBitmap = BitmapFactory.decodeFile(path);

        binding.ifv.setImageBitmap(originalBitmap);
        binding.ifv.setDrawingCacheEnabled(true);

        String[] colors = new String[]{"Black", "White", "Yellow", "Blue", "Green", "Red"};
        ArrayAdapter cAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, colors);
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
        ;
        binding.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yOffset += 10;
                updateImage(binding, originalBitmap);
            }
        });
        binding.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yOffset -= 10;
                updateImage(binding, originalBitmap);
            }
        });
        binding.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xOffset += 10;
                updateImage(binding, originalBitmap);
            }
        });
        binding.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xOffset -= 10;
                updateImage(binding, originalBitmap);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSubmissionDialog(binding);
            }
        });

        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImage(binding, originalBitmap);
            }
        });

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
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //show pb
                        //send file
                        //wait for confirm
                        //go back to main and clear stack
                        String savedFilePath = saveFile(binding);
                        vm.startUpload(savedFilePath, url);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(TextActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        TextActivity.this.finish();
                    }
                })
                .create();
            dialog.show();
    }

    public String saveFile(ActivityTextBinding binding) {
        //BitmapDrawable draw = (BitmapDrawable) binding.ifv.getDrawable();
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

            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public Bitmap drawMultilineTextToBitmap(Bitmap inBitmap, String text, int textSize) {
        Log.i(TAG, "drawMultilineTextToBitmap: ");
        // prepare canvas
        Resources resources = getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = inBitmap;

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);

        // new antialiased Paint
        TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(colorId);
        // text size in pixels
        paint.setTextSize((int) (textSize * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // set text width to canvas width minus 16dp padding
        int textWidth = canvas.getWidth() - (int) (16 * scale);

        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(text, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth)/2;
        float y = (bitmap.getHeight() - textHeight)/2;

        // draw text to the Canvas center
        canvas.save();
        canvas.translate(x - xOffset, y - yOffset);
        textLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }


}