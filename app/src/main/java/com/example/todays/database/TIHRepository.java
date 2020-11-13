package com.example.todays.database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TIHRepository {
    private TIHDao tihDao;

    public TIHRepository(Context context) {
        TIHDatabase tihDatabase = TIHDatabase.getDatabase(context.getApplicationContext());
        tihDao = tihDatabase.getTIHDao();
    }

    public void insertTIHs(TIH... events) {
        new InsertAsyncTask(tihDao).execute(events);
    }

    public void updateTIHs(TIH... events) {
        new UpdateAsyncTask(tihDao).execute(events);
    }

    public void deleteTIHs(TIH... events) {
        new DeleteAsyncTask(tihDao).execute(events);
    }

    public void deleteAllTIHs(TIH... events) {
        new DeleteAllAsyncTask(tihDao).execute();
    }


    public LiveData<List<TIH>> getAllTIHsLive(String year) {

        return tihDao.getAllTIHsLive(year);
    }


    static class InsertAsyncTask extends AsyncTask<TIH, Void, Void> {
        private TIHDao tihDao;

        InsertAsyncTask(TIHDao tihDao) {
            this.tihDao = tihDao;
        }

        @Override
        protected Void doInBackground(TIH... tihs) {
            tihDao.insertTIHs(tihs);
            return null;
        }

    }

    static class UpdateAsyncTask extends AsyncTask<TIH, Void, Void> {
        private TIHDao tihDao;

        UpdateAsyncTask(TIHDao tihDao) {
            this.tihDao = tihDao;
        }

        @Override
        protected Void doInBackground(TIH... tihs) {
            tihDao.updateTIHs(tihs);
            return null;
        }

    }

    static class DeleteAsyncTask extends AsyncTask<TIH, Void, Void> {
        private TIHDao tihDao;

        DeleteAsyncTask(TIHDao tihDao) {
            this.tihDao = tihDao;
        }

        @Override
        protected Void doInBackground(TIH... tihs) {
            tihDao.deleteAllTIHs();
            return null;
        }

    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private TIHDao tihDao;

        DeleteAllAsyncTask(TIHDao tihDao) {
            this.tihDao = tihDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tihDao.deleteAllTIHs();
            return null;
        }

    }
}
