package com.example.projekat.data.repositories

import android.annotation.SuppressLint
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import com.example.projekat.EsrbRating
import com.example.projekat.Game
import com.example.projekat.GamePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode


object GamesRepository {

    private lateinit var currentGames: List<Game>

    suspend fun getGamesByName(name: String): List<Game> {
        return withContext(Dispatchers.IO){
            var response = IGDBApiConfig.retrofit.getGamesByName(nameOfGame = name)
            val responseBody = response.body()
            val gamesWithDetails = mutableListOf<Game>()
            for(x in responseBody!!){
                gamesWithDetails.add(getGameById(x).first())
            }
            currentGames = gamesWithDetails
            return@withContext gamesWithDetails
        }
    }

    suspend fun getGamesSafe(name:String):List<Game>{
        return withContext(Dispatchers.IO){
            if(AccountGamesRepository.age == 0) return@withContext emptyList()
            var response = IGDBApiConfig.retrofit.getGamesByName(nameOfGame = name)
            val responseBody = response.body()
            val games = mutableListOf<Game>()

            for(x in responseBody!!){
                if(x.ageRatingObject == null){
                    games.add(x)
                }else if(x.ageRatingObject!!.any {it!!.ratingCategory == 2 && EsrbRating.values()[it.ratingValue - 1].numbericValue  <= AccountGamesRepository.age}){
                    games.add(x)
                }else if(x.ageRatingObject!!.any {it!!.ratingCategory == 1 && EsrbRating.values()[it.ratingValue - 1].numbericValue <= AccountGamesRepository.age}){
                    games.add(x)
                }else if(x.ageRatingObject!!.all {it!!.ratingCategory == 2 && it.ratingCategory != 1}){
                    games.add(x)
                }
            }
            currentGames = games
            return@withContext games
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun getGameById(game: Game): List<Game>{
        return withContext(Dispatchers.IO){
            val response = IGDBApiConfig.retrofit.getGameById(id = game.id)
            val result = response.body()
            val gameById = result?.first()
                if(gameById!!.ageRatingObject?.first() != null)
                    gameById!!.esrbRating = EsrbRating.fromNumber(gameById!!.ageRatingObject?.first()?.ratingValue!!).toString()
                else gameById!!.esrbRating = "Empty"
            gameById!!.genre = gameById!!.genresObject?.first()?.genre.toString()
                if(gameById.developerPublusherObject?.first() != null) {
                    gameById.developer = gameById.developerPublusherObject?.first()?.companyObject!!.name
                    gameById.publisher = gameById.developerPublusherObject?.first()?.companyObject!!.name
                }else{
                    gameById.developer = "Empty"
                    gameById.publisher = "Empty"
                }
                gameById.platform = ""
//                    for (y in result!!.first().platformObject!!) {
            gameById.platform += gameById.platformObject?.first()?.platform + "/"
//                    }
            gameById.platform = gameById.platform.dropLast(1)
            gameById.releaseDate = gameById.releaseDateObject?.first()?.releaseDate
            gameById.userImpressions = emptyList()
            gameById.coverImage = gameById.coverImageObject?.url
            gameById.gamePost = GamePost(gameById.id, gameById.title)
            gameById.rating = BigDecimal(gameById.rating).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            val rez = mutableListOf<Game>()
            rez.add(gameById)
            return@withContext rez
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun getGameByIdWithInt(id: Int): List<Game>{
        return withContext(Dispatchers.IO){
            val response = IGDBApiConfig.retrofit.getGameById(id = id)
            val result = response.body()
            if(result!!.first().ageRatingObject?.first() != null)
                result!!.first().esrbRating = EsrbRating.fromNumber(result!!.first().ageRatingObject?.first()?.ratingValue!!).toString()
            else result!!.first().esrbRating = "Empty"
            result!!.first().genre = result!!.first().genresObject?.first()?.genre.toString()
            if(result!!.first().developerPublusherObject?.first() != null) {
                result!!.first().developer = result!!.first().developerPublusherObject?.first()?.companyObject!!.name
                result!!.first().publisher = result!!.first().developerPublusherObject?.first()?.companyObject!!.name
            }else{
                result!!.first().developer = "Empty"
                result!!.first().publisher = "Empty"
            }
            result!!.first().platform = ""
//                    for (y in result!!.first().platformObject!!) {
            result!!.first().platform += result!!.first().platformObject!!.first().platform + "/"
//                    }
            result!!.first().platform = result!!.first().platform.dropLast(1)
            result!!.first().releaseDate = result!!.first().releaseDateObject?.first()!!.releaseDate
            result!!.first().userImpressions = emptyList()
            result!!.first().coverImage = result!!.first().coverImageObject!!.url
            result!!.first().gamePost = GamePost(result!!.first().id!!, result!!.first().title)
            result!!.first().rating = BigDecimal(result!!.first().rating).setScale(2, RoundingMode.HALF_EVEN).toDouble()
            return@withContext result
        }
    }

    suspend fun sortGames():List<Game>{
        val favoriteGames = AccountGamesRepository.getSavedGames()
        val idOfFavoritesGames: MutableList<Int> = emptyList<Int>().toMutableList()
        for(x in favoriteGames){
            idOfFavoritesGames.add(x.id!!)
        }
        return currentGames.sortedWith(compareByDescending<Game> { it.id in idOfFavoritesGames }.thenBy { it.title })
    }
}