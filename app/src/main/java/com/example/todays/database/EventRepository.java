package com.example.todays.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {
    private LiveData<List<Event>> allEventsLive;
    private EventDao eventDao;

    public EventRepository(Context context) {
        EventDatabase eventDatabase = EventDatabase.getDatabase(context.getApplicationContext());
        eventDao = eventDatabase.getEventDao();
        allEventsLive = eventDao.getAllEventsLive();
    }

    public void insertEvents(Event... events) {
        new InsertAsyncTask(eventDao).execute(events);
    }

    public void updateEvents(Event... events) {
        new UpdateAsyncTask(eventDao).execute(events);
    }

    public void deleteEvents(Event... events) {
        new DeleteAsyncTask(eventDao).execute(events);
    }

    public void deleteAllEvents(Event... events) {
        new DeleteAllAsyncTask(eventDao).execute();
    }


    public LiveData<List<Event>> getAllEventsLive() {
        return allEventsLive;
    }

    public LiveData<Event> findEventById(String id){
        return eventDao.findEventById(id);
    }

    public LiveData<List<Event>> findEventsWithPattern(String pattern) {
        return eventDao.findEventsWithPattern("%" + pattern + "%");
    }

    public LiveData<List<Event>> findEventByAccountId(String accountId) {
        return eventDao.findEventByAccountId(accountId);
    }

    public LiveData<Integer> getEventNum(String accountId) {
        return eventDao.getEventNum(accountId);
    }

    static class InsertAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDao eventDao;

        InsertAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.insertEvents(events);
            return null;
        }

    }

    static class UpdateAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDao eventDao;

        UpdateAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.updateEvents(events);
            return null;
        }

    }

    static class DeleteAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDao eventDao;

        DeleteAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.deleteEvents(events);
            return null;
        }

    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private EventDao eventDao;

        DeleteAllAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            eventDao.deleteAllEvents();
            return null;
        }

    }
}
