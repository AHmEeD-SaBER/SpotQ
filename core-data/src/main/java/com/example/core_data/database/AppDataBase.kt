package com.example.core_data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        UserEntity::class,
        PlaceEntity::class,
        UserFavoriteCrossRef::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun placesDao(): PlacesDao
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        const val DATABASE_NAME = "spotq_database"
    }
}
