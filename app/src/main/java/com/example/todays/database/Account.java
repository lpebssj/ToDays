package com.example.todays.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Account {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "numEvent")
    private String numEvent;
    @ColumnInfo(name = "numDay")
    private String numDay;
    @ColumnInfo(name = "numShare")
    private String numShare;
    @ColumnInfo(name = "createTime")
    private String createTime;

    public Account(String username, String password, String createTime) {
        this.username = username;
        this.password = password;
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumEvent() {
        return numEvent;
    }

    public void setNumEvent(String numEvent) {
        this.numEvent = numEvent;
    }

    public String getNumDay() {
        return numDay;
    }

    public void setNumDay(String numDay) {
        this.numDay = numDay;
    }

    public String getNumShare() {
        return numShare;
    }

    public void setNumShare(String numShare) {
        this.numShare = numShare;
    }
}

