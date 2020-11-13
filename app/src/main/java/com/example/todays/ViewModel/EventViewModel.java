package com.example.todays.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todays.database.Event;
import com.example.todays.database.EventRepository;

import java.util.List;

/*
 * ViewModel for the events
 */
public class EventViewModel extends AndroidViewModel {
    private EventRepository eventRepository;
    public EventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = new EventRepository(application);
    }

    public LiveData<List<Event>> getAllEventsLive() {
        return eventRepository.getAllEventsLive();
    }

    public LiveData<List<Event>> findEventsWithPattern(String pattern) {
        return eventRepository.findEventsWithPattern(pattern);
    }

    public LiveData<Event> findEventById(String id){
        return eventRepository.findEventById(id);
    }

    public void insertEvents(Event... events) {
        eventRepository.insertEvents(events);
    }
    public void updateEvents(Event... events) {
        eventRepository.updateEvents(events);
    }
    public void deleteEvents(Event... events) {
        eventRepository.deleteEvents(events);
    }
    public void deleteAllEvents() {
        eventRepository.deleteAllEvents();
    }


    public LiveData<List<Event>> findEventByAccountId(String accountId) {
        return eventRepository.findEventByAccountId(accountId);
    }

    public LiveData<Integer> getEventNum(String accountId) {
        return eventRepository.getEventNum(accountId);
    }
}
