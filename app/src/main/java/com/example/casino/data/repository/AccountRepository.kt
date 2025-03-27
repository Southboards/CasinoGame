package com.example.casino.data.repository

import android.content.Context
import android.util.Log
import com.example.casino.data.entities.Account
import com.example.casino.data.DAO.AccountDAO
import com.example.casino.data.database.CasinoDatabase

class AccountRepository(private val context: Context) {

    private val accountDao: AccountDAO = CasinoDatabase.getDatabase(context).accountDao()

    suspend fun insert(account: Account) {
        Log.d("check", "insert")
        accountDao.insert(account)
    }

    suspend fun update(account: Account) {
        accountDao.update(account)
    }

    suspend fun delete(account: Account) {
        accountDao.delete(account)
    }

    suspend fun getAccountByUsername(username: String): Account? {
        return accountDao.getAccountByUsername(username)
    }

    suspend fun updatePassword(username: String, password: String) {
        accountDao.updatePassword(username, password)
    }

    suspend fun checkLogin(username: String, password: String): Account? {
        return accountDao.checkLogin(username, password)
        Log.d("check", "checklogin")
    }

    suspend fun getMoneyByUsername(username: String): Int? {
        return accountDao.getMoneyByUsername(username)
    }

    suspend fun updateMoney(username: String, money: Int) {
        return accountDao.updateMoney(username, money)
        Log.d("check", "updatemoeny")
    }
}
