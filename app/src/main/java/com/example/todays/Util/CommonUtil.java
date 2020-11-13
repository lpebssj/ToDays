package com.example.todays.Util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.media.ThumbnailUtils;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.todays.Picture;
import com.example.todays.R;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommonUtil {
    @NonNull
    public static NavController getNavController(AppCompatActivity activity) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment2);
        if (!(fragment instanceof NavHostFragment)) {
            throw new IllegalStateException("Activity " + activity
                    + " does not have a NavHostFragment");
        }
        return ((NavHostFragment) fragment).getNavController();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static long calculateDays(String date) {

        DateFormat df = new SimpleDateFormat("yyyy–MM–dd", Locale.CANADA);

        try {
            Date d2 = df.parse(date);
            Date d1 = new Date();
            long diff = d2.getTime() - (d1.getTime() + 1);
            return (diff / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Picture> getDrawable(String imgName, Context context) {
        List<Picture> imgList = new ArrayList<>();
        Resources resources = context.getResources();
        Field[] fields = R.drawable.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {

            fields[i].setAccessible(true);
            String name = fields[i].getName();
            if (name.contains(imgName)) {
                Picture picture = new Picture();
                int resId = resources.getIdentifier(name, "drawable", context.getPackageName());
                Drawable drawable = resources.getDrawable(resId);
                picture.setDrawable(drawable);
                picture.setPictureName(name);
                imgList.add(picture);
            }
        }
        return imgList;
    }


}
