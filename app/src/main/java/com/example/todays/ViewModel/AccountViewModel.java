package com.example.todays.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todays.database.Account;
import com.example.todays.database.AccountRepository;

import java.util.List;

/*
 * ViewModel for the accounts
 */
public class AccountViewModel extends AndroidViewModel {
    private AccountRepository accountRepository;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.getAllAccounts();
    }

    public LiveData<List<Account>> getAllAccountsLive() {
        return accountRepository.getAllAccountsLive();
    }

    public LiveData<Account> findAccountsWithPattern(String pattern) {
        return accountRepository.findAccountsWithPattern(pattern);
    }

    public LiveData<Account> findAccount(String username, String password) {
        return accountRepository.findAccount(username, password);
    }

    public LiveData<Account> findAccountById(String id) {
        return accountRepository.findAccountById(id);
    }

    public void insertAccounts(Account... accounts) { accountRepository.insertAccounts(accounts); }
    public void updateAccounts(Account... accounts) { accountRepository.updateAccounts(accounts); }
    public void deleteAccounts(Account... accounts) { accountRepository.deleteAccounts(accounts); }
    public void deleteAllAccounts() { accountRepository.deleteAllAccounts(); }
}
