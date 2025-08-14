package com.example.core_data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavoritesDao {
    // Create the (user, place) favorite row. IGNORE avoids crashing on duplicates.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(crossRef: UserFavoriteCrossRef): Long

    // Remove the favorite row
    @Query("DELETE FROM user_favorites WHERE userId = :userId AND placeId = :placeId")
    suspend fun removeFavorite(userId: Int, placeId: String): Int

    // Check if a place is favorited by this user
    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM user_favorites
            WHERE userId = :userId AND placeId = :placeId
        )
    """)
    suspend fun isFavorite(userId: Int, placeId: String): Boolean

    // Get all favorite places for a user (returns real PlaceEntity rows)
    @Query("""
        SELECT p.* FROM places p
        INNER JOIN user_favorites uf ON p.xid = uf.placeId
        WHERE uf.userId = :userId
        ORDER BY p.name
    """)
    suspend fun getFavoritesForUser(userId: Int): List<PlaceEntity>
}