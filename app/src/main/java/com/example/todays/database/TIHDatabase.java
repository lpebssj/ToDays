package com.example.todays.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//singleton
@Database(entities = {TIH.class},version = 1,exportSchema = false)
public abstract class TIHDatabase extends RoomDatabase {
    private static TIHDatabase INSTANCE;

    static synchronized TIHDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TIHDatabase.class, "TIH_database")
                    .build();
        }
        return INSTANCE;
    }
    public abstract TIHDao getTIHDao();
}