package com.example.todays;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;

import com.example.todays.Util.CommonUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Theme> themes = new ArrayList<>();

        List<Picture> pictures = CommonUtil.getDrawable("picture", this);

        ToDays toDays = (ToDays) this.getApplicationContext();

        //read all pictures in another Thread
        new Thread(()->{
            pictures.forEach(picture -> {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable)picture.getDrawable()).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] theme = stream.toByteArray();
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                Bitmap bitmap1 = ThumbnailUtils.extractThumbnail(bitmap, 100, 100);
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
                byte[] miniTheme = stream1.toByteArray();
                try {
                    stream1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Theme theme1 = new Theme();
                theme1.setTheme(theme);
                theme1.setMiniTheme(miniTheme);
                theme1.setName(picture.getPictureName());
                themes.add(theme1);
            });
            toDays.picturePool.postValue(themes);
        }).start();
    }


}