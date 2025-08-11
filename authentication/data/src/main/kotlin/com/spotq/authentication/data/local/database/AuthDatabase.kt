package com.spotq.authentication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spotq.authentication.data.local.dao.UserDao
import com.spotq.authentication.data.models.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AuthDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "auth_database"
    }
}
