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
    int xOffset = 0;
    int yOffset = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFilterBinding binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bitmap originalBitmap = getIntent().getParcelableExtra("bitmap");
        binding.ifv.setImageBitmap(originalBitmap);
        binding.ifv.setDrawingCacheEnabled(true);

        binding.seekSat.setProgress(10);
        binding.seekBright.setProgress(10);
        binding.seekWarmth.setProgress(10);
        binding.seekContrast.setProgress(10);

      /*  binding.ifv.setDrawingCacheEnabled(true);
        Bitmap b = drawTextWrap("This is a very long message here1",48f, 0, Color.YELLOW, Color.RED);
        binding.ifv.setImageBitmap(b);*/

       /* BitmapDrawable draw = (BitmapDrawable) binding.ifv.getDrawable();
        Bitmap oldb = draw.getBitmap();
        Bitmap.Config bc = oldb.getConfig();
        bc = Bitmap.Config.ARGB_8888;
        Bitmap cp = oldb.copy(bc, true);
        Canvas canvas = new Canvas(cp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        float scale = getResources().getDisplayMetrics().density;
        paint.setTextSize(72 * scale);

        canvas.drawText("My Text\n My Text My \n Text My Text", 0, 72 * scale, paint);
        canvas.drawText("My Text", 0, 72 * scale + 144, paint);
        binding.ifv.setImageBitmap(cp);*/
        //char[] ch = "Hello world this is me the man!".toCharArray();
        //int pos = paint.breakText(ch,0, ch.length, canvas.getWidth(), null);

        //Rect bounds = new Rect();
        //StaticLayout sl = new StaticLayout("This is my text that must fit to a rectangle", textPaint, (int)canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1, 1, false);

        //canvas.save();
        //canvas.translate(rect.left, rect.top);
        //sl.draw(canvas);
        //canvas.restore();


        /*
         paint.setTextSize(72 * scale);
        String s = "My Text My Text My Text My Text My Text My Text My Text My Text";
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(s, 0, s.length(), bounds);
        int x = (cp.getWidth() - bounds.width())/6;
        int y = (cp.getHeight() + bounds.height())/5;

        canvas.drawText(s, 0, 72 * scale, paint);
         */
        //canvas.translate(canvas.getWidth(), canvas.getHeight() * 2);
        //canvas.drawText("My Text\n My Text My \n Text My Text", 0, 72 * scale, paint);

        //binding.ifv.setImageBitmap(cp);

        /*
        BitmapDrawable draw = (BitmapDrawable) binding.ifv.getDrawable();
        Bitmap oldb = draw.getBitmap();
        Bitmap newb = drawTextToBitmap(this, oldb, "Hello world Hello world ", binding);
        binding.iv.setImageBitmap(newb);
*/
       // BitmapDrawable drawableBit = binding.ifv
        Bitmap drawMultilineBitmap = drawMultilineTextToBitmap(this, originalBitmap,
                "This is my string message but now it is much much longer! So very long in fact I don't think this will ever fit on anything again."
        );
        binding.iv.setImageBitmap(drawMultilineBitmap);



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

        binding.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yOffset += 10;
                Bitmap drawMultilineBitmap = drawMultilineTextToBitmap(FilterActivity.this, originalBitmap,
                        "This is my string message");
                binding.iv.setImageBitmap(drawMultilineBitmap);
            }
        });
        binding.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yOffset -= 10;
                Bitmap drawMultilineBitmap = drawMultilineTextToBitmap(FilterActivity.this, originalBitmap,
                        "This is my string message");
                binding.iv.setImageBitmap(drawMultilineBitmap);
            }
        });
        binding.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xOffset += 10;
                Bitmap drawMultilineBitmap = drawMultilineTextToBitmap(FilterActivity.this, originalBitmap,
                        "This is my string message");
                binding.ifv.setImageBitmap(drawMultilineBitmap);
            }
        });
        binding.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xOffset -= 10;
                Bitmap drawMultilineBitmap = drawMultilineTextToBitmap(FilterActivity.this, originalBitmap,
                        "This is my string message");
                binding.ifv.setImageBitmap(drawMultilineBitmap);
            }
        });


        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

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
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public Bitmap drawMultilineTextToBitmap(Context gContext,
                                            Bitmap inBitmap,
                                            String gText) {

        // prepare canvas
        Resources resources = gContext.getResources();
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
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (14 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // set text width to canvas width minus 16dp padding
        int textWidth = canvas.getWidth() - (int) (16 * scale);

        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(
                gText, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

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


    public Bitmap drawTextToBitmap(Context mContext, Bitmap resourceId, String mText, ActivityFilterBinding binding) {
        try {
            Log.i(TAG, "drawTextToBitmap: ");
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            Bitmap bitmap = resourceId;
            android.graphics.Bitmap.Config bitmapConfig =   bitmap.getConfig();
            // set default bitmap config if none
            if(bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            // resource bitmaps are imutable,
            // so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);
            // new antialised Paint
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // text color - #3D3D3D
            paint.setColor(Color.rgb(110,110, 110));
            // text size in pixels
            paint.setTextSize((int) (48 * scale));
            // text shadow
            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);

            // draw text to the Canvas center
            Rect bounds = new Rect();

            paint.getTextBounds(mText, 0, mText.length(), bounds);
            Log.i(TAG, "drawTextToBitmap: " + bitmap.getWidth() + " " + bounds.width());
            int x = (bitmap.getWidth() - bounds.width())/2;
            int y = (bitmap.getHeight() + bounds.height())/5;

            //binding.tvFilter.setHeight(bounds.height());
            //binding.tvFilter.setWidth(bitmap.getWidth());

            canvas.drawText(mText, x * scale, y * scale, paint);

            return bitmap;
        } catch (Exception e) {
            Log.e(TAG, "drawTextToBitmap: " + e.getMessage());
            return null;
        }



    }


    // function to draw text wrap
    public Bitmap drawTextWrap(String text,
                             Float textSize,   // in pixels
                             int margin, // in pixels
                             int textColor,
                             int canvasColor
    ){
        Bitmap bitmap = Bitmap.createBitmap(
                1500,
                850,
                Bitmap.Config.ARGB_8888
        );

        // canvas to draw text
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(canvasColor);


        // text paint to draw text on canvas
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);


        // static layout configurations
        int width = canvas.getWidth() - margin * 2;
        Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
        float spacingMultiplier = 1F;
        float spacingAddition = 0F;
        boolean includePadding = false;

        // generate a static layout
        StaticLayout staticLayout = new
            StaticLayout(
                    text,
                    textPaint,
                    width,
                    alignment,
                    spacingMultiplier,
                    spacingAddition,
                    includePadding
            );


        // finally, draw the multiline text on canvas
        canvas.save();
        // add margin on canvas to draw text
        canvas.translate((float) margin, (float) margin);
        staticLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }
}