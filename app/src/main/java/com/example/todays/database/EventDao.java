package com.example.todays.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao   // Database access object
public interface EventDao {
    @Insert
    void insertEvents(Event... events);

    @Update
    void updateEvents(Event... events);

    @Delete
    void deleteEvents(Event... events);

    @Query("DELETE FROM EVENT")
    void deleteAllEvents();

    @Query("SELECT * FROM EVENT ORDER BY ID DESC")
        //List<Event> getAllEvents();
    LiveData<List<Event>> getAllEventsLive();

    @Query("SELECT * FROM EVENT WHERE NAME LIKE :pattern ORDER BY ID DESC")
    LiveData<List<Event>> findEventsWithPattern(String pattern);

    @Query("SELECT * FROM EVENT WHERE ID = :id")
    LiveData<Event> findEventById(String id);

    @Query("SELECT * FROM EVENT WHERE ACCOUNTID = :accountId")
    LiveData<List<Event>> findEventByAccountId(String accountId);

    @Query("SELECT count(*) FROM EVENT WHERE ACCOUNTID = :accountId")
    LiveData<Integer> getEventNum(String accountId);
}