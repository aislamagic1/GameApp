package com.example.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameReviewDao{
    @Query("SELECT * FROM GameReview WHERE online = false")
    suspend fun getOfflineReviews():List<GameReview>
    @Insert
    suspend fun insertReview(vararg gameReview: GameReview)
    @Query("UPDATE GameReview SET online = 1 WHERE student = :student")
    suspend fun markReviewAsOnline(student: String)
}