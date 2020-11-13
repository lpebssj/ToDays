package com.example.todays;

import android.widget.Checkable;

public class Theme {
    private String name;

    private byte[] theme;

    private byte[] miniTheme;

    private String accountId;  //to match accounts with events

    private boolean isChecked;

    public Theme() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getMiniTheme() {
        return miniTheme;
    }

    public void setMiniTheme(byte[] miniTheme) {
        this.miniTheme = miniTheme;
    }


    public byte[] getTheme() {
        return theme;
    }

    public void setTheme(byte[] theme) {
        this.theme = theme;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }



    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

}
