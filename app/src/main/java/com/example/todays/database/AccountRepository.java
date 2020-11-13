package com.example.todays.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountRepository {
    private LiveData<List<Account>> allAccountsLive;
    private AccountDao accountDao;

    public AccountRepository(Context context) {
        AccountDatabase accountDatabase = AccountDatabase.getDatabase(context.getApplicationContext());
        accountDao = accountDatabase.getAccountDao();
        allAccountsLive = accountDao.getAllAccountsLive();
    }

    public void insertAccounts(Account... accounts) {
        new AccountRepository.InsertAsyncTask(accountDao).execute(accounts);
    }

    public void updateAccounts(Account... accounts) {
        new AccountRepository.UpdateAsyncTask(accountDao).execute(accounts);
    }

    public void deleteAccounts(Account... accounts) {
        new AccountRepository.DeleteAsyncTask(accountDao).execute(accounts);
    }

    public void deleteAllAccounts(Account... accounts) {
        new AccountRepository.DeleteAllAsyncTask(accountDao).execute();
    }

    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    public LiveData<List<Account>> getAllAccountsLive() {
        return allAccountsLive;
    }

    public LiveData<Account> findAccountsWithPattern(String pattern) {
        return accountDao.findAccountsWithPattern(pattern);
    }

    public LiveData<Account> findAccount(String username, String password) {
        return accountDao.findAccount(username, password);

    }

    public LiveData<Account> findAccountById(String id) {
        return accountDao.findAccountById(id);
    }

    //insert, update, delete or delete all asynchronous accounts.
    static class InsertAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao accountDao;

        InsertAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.insertAccounts(accounts);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao accountDao;

        UpdateAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.updateAccounts(accounts);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao accountDao;

        DeleteAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.deleteAccounts(accounts);
            return null;
        }
    }
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private AccountDao accountDao;

        DeleteAllAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accountDao.deleteAllAccounts();
            return null;
        }
    }
}
