package com.example.projekat.data.repositories

import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import com.example.projekat.Game
import com.example.projekat.GameData
import com.example.projekat.GamePost
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("games")
    suspend fun getGamesByName(
        @Header("Client-Id") clientId: String = "ua7365o8m8ta9hgedtbbl6y5560ie5",
        @Header("Authorization") authToken: String = "Bearer zll9976bpdq70jfroskgpnzcs1zzoh",
        @Query("fields") field: String = "name, genres.name, rating, age_ratings.rating, age_ratings.category, summary, platforms.name, release_dates.human, involved_companies.company.name, platforms.name, release_dates.human, cover.url, id",
        @Query("search") nameOfGame: String
    ): Response<List<Game>>

//    @GET("games")
//    suspend fun getGameDetails(
//        @Header("Client-Id") clientId: String = "ua7365o8m8ta9hgedtbbl6y5560ie5",
//        @Header("Authorization") authToken: String = "Bearer zll9976bpdq70jfroskgpnzcs1zzoh",
//        @Query("fields") field: String = "name, genres.name, rating, age_ratings.rating, age_ratings.category, summary, platforms.name, release_dates.human, involved_companies.company.name, platforms.name, release_dates.human, cover.url, id",
//        @Query("search") nameOfGame2: String,
//        @Query("limit") limit: Int = 1
//    ):Response<List<Game>>

    @GET("games/{id}")
    suspend fun getGameById(
        @Header("Client-Id") clientId: String = "ua7365o8m8ta9hgedtbbl6y5560ie5",
        @Header("Authorization") authToken: String = "Bearer zll9976bpdq70jfroskgpnzcs1zzoh",
        @Path("id") id: Int,
        @Query("fields") field: String = "name, genres.name, rating, age_ratings.rating, age_ratings.category, summary, platforms.name, release_dates.human, involved_companies.company.name, platforms.name, release_dates.human, cover.url, id",
        @Query("limit") limit: Int = 1
    ):Response<List<Game>>

    @POST("account/{aid}/game")
    suspend fun saveGame(
        @Path("aid") api: String = AccountGamesRepository.apiKey,
        @Body game: Game
    ): Response<Game>

    @GET("account/{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") api: String = AccountGamesRepository.apiKey
    ): Response<List<Game>>

    @DELETE("account/{aid}/game/{gid}/")
    suspend fun removeGame(
        @Path("aid") api: String = AccountGamesRepository.apiKey,
        @Path("gid") id: Int
    ): Response<Game>
}
