package com.example.core_data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PlacesDao {
    // Cache one place (from the API) or update it if it already exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(place: PlaceEntity)

    // Cache a list (e.g., search results) fast
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(places: List<PlaceEntity>)

    @Query("SELECT * FROM places WHERE xid = :xid LIMIT 1")
    suspend fun getById(xid: String): PlaceEntity?

    // Optional helpers
    @Query("DELETE FROM places")
    suspend fun clearAll()
}