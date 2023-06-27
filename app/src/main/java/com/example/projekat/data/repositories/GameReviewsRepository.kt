package com.example.projekat.data.repositories

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

object GameReviewsRepository {

    suspend fun getOfflineReviews(context: Context):List<GameReview>{
        return withContext(Dispatchers.IO){
            val db = AppDatabase.getInstance(context)
            val games = db.GameReviewDao().getOfflineReviews()
            return@withContext games
        }
    }

    suspend fun sendOfflineReviews(context: Context):Int{
        return withContext(Dispatchers.IO){
            val db = AppDatabase.getInstance(context)
            val gamesReview = db.GameReviewDao().getOfflineReviews()
            var savedGamesResponse = AccountApiConfig.retrofit.getSavedGames()
            val savedGames = savedGamesResponse.body()
            var counter: Int = 0
            for(x in gamesReview){
                if(!savedGames!!.any { it.id == x.igdb_id }){
                    AccountApiConfig.retrofit.saveGame(game = IGDBApiConfig.retrofit.getGameById(id = x.igdb_id).body()!!.first())
                }
                var response =  AccountApiConfig.retrofit.sendReview(id = x.igdb_id, review = x)
                if(response.raw().code() == 200){
                    db.GameReviewDao().markReviewAsOnline(student = x.student!!)
                }
                counter++
            }
            return@withContext counter
        }
    }

    suspend fun sendReview(context: Context, gameReview: GameReview):Boolean{
        return withContext(Dispatchers.IO){
//            var savedGamesResponse = AccountApiConfig.retrofit.getSavedGames()
//            val savedGames = savedGamesResponse.body()
//            if(!savedGames!!.any { it.id == gameReview.igdb_id }){
//                AccountApiConfig.retrofit.saveGame(game = IGDBApiConfig.retrofit.getGameById(id = gameReview.igdb_id).body()!!.first())
//            }
//            var response = AccountApiConfig.retrofit.sendReview(id = gameReview.igdb_id, review = gameReview)
//            if(response.raw().code() == 200){
//                return@withContext true
//            }else{
//                var db = AppDatabase.getInstance(context)
//                db.GameReviewDao().insertReview(gameReview)
//                return@withContext false
//            }
            try {
                var savedGamesResponse = AccountApiConfig.retrofit.getSavedGames()
                val savedGames = savedGamesResponse.body()

                if (!savedGames!!.any { it.id == gameReview.igdb_id }) {
                    AccountApiConfig.retrofit.saveGame(
                        game = IGDBApiConfig.retrofit.getGameById(id = gameReview.igdb_id).body()!!.first()
                    )
                }

                var response = AccountApiConfig.retrofit.sendReview(id = gameReview.igdb_id, review = gameReview)
                if (response.isSuccessful) {
                    return@withContext true
                } else {
                    throw IOException("API call failed with status code: ${response.code()}")
                }
            } catch (e: IOException) {
                var db = AppDatabase.getInstance(context)
                db.GameReviewDao().insertReview(gameReview)
                return@withContext false
            }
        }
    }

    suspend fun getReviewsForGame(igdb_id:Int):List<GameReview>{
        return withContext(Dispatchers.IO){
            var response = AccountApiConfig.retrofit.getReviewsForGame(id = igdb_id)
            val responseBody = response.body()
            return@withContext responseBody!!
        }
    }
}