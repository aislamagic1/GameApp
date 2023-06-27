package com.example.projekat.data.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GameReview(
    @ColumnInfo(name = "rating") @SerializedName("rating") var rating: Int?,
    @ColumnInfo(name = "review") @SerializedName("review") var review: String?,
    @ColumnInfo(name = "igdb_id") var igdb_id: Int,
    @ColumnInfo(name = "online") var online: Boolean = false,
    @ColumnInfo(name = "student") var student: String?,
    @ColumnInfo(name = "timestamp") @SerializedName("timestamp") var timestamp: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)