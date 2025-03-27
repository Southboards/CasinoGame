package com.example.casino.data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.casino.data.entities.Account

@Dao
interface AccountDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)

    @Query("SELECT * FROM accounts WHERE username = :username LIMIT 1")
    fun getAccountByUsername(username: String): Account?

    @Query("SELECT * FROM accounts WHERE username = :username AND password = :password LIMIT 1")
    fun checkLogin(username: String, password: String): Account?

    @Query("UPDATE accounts SET password = :password WHERE username = :username")
    fun updatePassword(username: String, password: String)

    @Query("SELECT money FROM accounts WHERE username = :username LIMIT 1")
    fun getMoneyByUsername(username: String): Int?

    @Query("UPDATE accounts SET money = :money WHERE username = :username")
    fun updateMoney(username: String, money: Int)
}
