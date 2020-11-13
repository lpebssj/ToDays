package com.example.todays.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDao {
    @Insert
    void insertAccounts(Account... accounts);

    @Update
    void updateAccounts(Account... accounts);

    @Delete
    void deleteAccounts(Account... accounts);

    @Query("DELETE FROM ACCOUNT")
    void deleteAllAccounts();

    @Query("SELECT * FROM ACCOUNT ORDER BY ID DESC")
        //List<Account> getAllAccounts();
    LiveData<List<Account>> getAllAccountsLive();

    @Query("SELECT * FROM ACCOUNT ORDER BY ID DESC")
    List<Account> getAllAccounts();

    @Query("SELECT * FROM ACCOUNT WHERE USERNAME = :pattern")
    LiveData<Account> findAccountsWithPattern(String pattern);

    @Query("select * from ACCOUNT where USERNAME = :username and PASSWORD = :password")
    LiveData<Account> findAccount(String username, String password);

    @Query("select * from ACCOUNT where ID = :id")
    LiveData<Account> findAccountById(String id);
}
