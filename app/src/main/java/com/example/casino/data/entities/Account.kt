package com.example.casino.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val password: String,
    val money: Int
)
