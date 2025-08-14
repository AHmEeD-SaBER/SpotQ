package com.example.core_data.database


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

@Entity(
    tableName = "user_favorites",
    primaryKeys = ["userId", "placeId"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = PlaceEntity::class,
            parentColumns = ["xid"],
            childColumns = ["placeId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("userId"), Index("placeId")]
)
data class UserFavoriteCrossRef(
    val userId: Int,
    val placeId: String
)

