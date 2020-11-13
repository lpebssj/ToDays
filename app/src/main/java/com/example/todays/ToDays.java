package com.example.todays;

import android.app.Application;

/*
 * to store accountId, for counting the number of events and number of days the app had been used by each user
 */
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ToDays extends Application {
    private String accountId;

    public MutableLiveData<List<Theme>> picturePool = new MutableLiveData<>();

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
