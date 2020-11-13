package com.example.todays.ViewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todays.database.Event;
import com.example.todays.database.EventRepository;
import com.example.todays.database.TIH;
import com.example.todays.database.TIHRepository;

import java.util.List;

/*
 * ViewModel for Today in History
 */
public class TIHViewModel extends AndroidViewModel {
    private TIHRepository tihRepository;

    public TIHViewModel(@NonNull Application application) {
        super(application);
        tihRepository = new TIHRepository(application);
    }

    public LiveData<List<TIH>> getAllEventsLive(String year) {
        return tihRepository.getAllTIHsLive(year);
    }

    public void insertTIHs(TIH... tihs) {
        tihRepository.insertTIHs(tihs);
    }

    public void updateTIHs(TIH... tihs) {
        tihRepository.updateTIHs(tihs);
    }

    public void deleteTIHs(TIH... tihs) {
        tihRepository.deleteTIHs(tihs);
    }

    public void deleteAllTIHs() {
        tihRepository.deleteAllTIHs();
    }


}
