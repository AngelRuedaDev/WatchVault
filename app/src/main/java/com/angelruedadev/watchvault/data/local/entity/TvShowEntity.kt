package com.angelruedadev.watchvault.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows")
data class TvShowEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")override val id: Int,
    @ColumnInfo(name = "title")override val title: String,
    @ColumnInfo(name = "photo_path")override val photoPath: String?,
    @ColumnInfo(name = "is_liked") var isLiked: Boolean = false,
    @ColumnInfo(name = "is_inWatchList") var isInWatchList: Boolean = false,
    @ColumnInfo(name = "user_rating") override var userRating: Int = 0,
): CollectionItemData