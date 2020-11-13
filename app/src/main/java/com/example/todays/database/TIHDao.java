package com.example.todays.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao   // Database access object
public interface TIHDao {
    @Insert
    void insertTIHs(TIH... TIHs);

    @Update
    void updateTIHs(TIH... TIHs);

    @Delete
    void deleteTIHs(TIH... TIHs);

    @Query("DELETE FROM TIH")
    void deleteAllTIHs();

    @Query("SELECT * FROM TIH WHERE YEAR = :year")
        //List<TIH> getAllTIHs();
    LiveData<List<TIH>> getAllTIHsLive(String year);


}