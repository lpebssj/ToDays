package com.example.todays;

import android.graphics.drawable.Drawable;

/*
 *to match background picture's name with drawable
 */
public class Picture {

    private String pictureName;
    private Drawable drawable;

    public Picture() {

    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

}
