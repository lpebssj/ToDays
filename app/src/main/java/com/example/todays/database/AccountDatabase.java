package com.example.todays.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Account.class},version = 6,exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {
    private static AccountDatabase INSTANCE;

    static synchronized AccountDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AccountDatabase.class, "account_database")
                    .build();
        }
        return INSTANCE;
    }
    public abstract AccountDao getAccountDao();
}
