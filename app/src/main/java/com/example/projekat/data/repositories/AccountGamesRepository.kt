package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.BuildConfig
import com.example.projekat.EsrbRating
import com.example.projekat.Game
import com.example.projekat.GamePost
import com.example.projekat.data.repositories.AccountApiConfig
import com.example.projekat.data.repositories.GamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountGamesRepository {
    var apiKey: String = BuildConfig.Api_key
    var age: Int = 18

    suspend fun setHash(acHash:String):Boolean{
        var current = BuildConfig.Api_key
        apiKey = acHash
        return apiKey != current
    }

    suspend fun getHash():String{
        return apiKey
    }

    suspend fun saveGame(game: Game): Game {
        game.gamePost = GamePost(game.id, game.title)
    return withContext(Dispatchers.IO){
        var response = AccountApiConfig.retrofit.saveGame(game = game)
        val responseBody = response.body()
        return@withContext responseBody!!
        }
    }

    suspend fun getSavedGames():List<Game>{
        return withContext(Dispatchers.IO){
            var response = AccountApiConfig.retrofit.getSavedGames()
            val responseBody = response.body()
            val games = mutableListOf<Game>()
            for(x in responseBody!!){
                x.id = x.idFromAccount!!
                games.add(GamesRepository.getGameById(x).first())
            }
            return@withContext games
        }
    }

    suspend fun removeGame(id:Int):Boolean{
        return withContext(Dispatchers.IO){
            var response = AccountApiConfig.retrofit.removeGame(id = id)
            return@withContext response.body()?.isDeleted.equals("Games deleted")
        }
    }

    suspend fun removeNonSafe():Boolean{
        return withContext(Dispatchers.IO){
            var responseBody = getSavedGames()
            val number = responseBody!!.size
            for(x in responseBody!!){
                if(x.ageRatingObject == null){
                    continue
                }else if(x.ageRatingObject!!.any {it!!.ratingCategory == 2 && EsrbRating.values()[it.ratingValue - 1].numbericValue  >= AccountGamesRepository.age}){
                    AccountApiConfig.retrofit.removeGame(id = x.id!!)
                }else if(x.ageRatingObject!!.any {it!!.ratingCategory == 1 && EsrbRating.values()[it.ratingValue - 1].numbericValue >= AccountGamesRepository.age}){
                    AccountApiConfig.retrofit.removeGame(id = x.id!!)
                }else if(x.ageRatingObject!!.all {it!!.ratingCategory == 2 && it.ratingCategory != 1}){
                    AccountApiConfig.retrofit.removeGame(id = x.id!!)
                }
            }
            return@withContext number > responseBody.size
        }
    }

    suspend fun getGamesContainingString(query:String):List<Game>{
        return withContext(Dispatchers.IO) {
            var response = AccountApiConfig.retrofit.getSavedGames()
            val responseBody = response.body()
            val games = mutableListOf<Game>()
            for (x in responseBody!!) {
                games.add(GamesRepository.getGameById(x).first())
            }
            return@withContext games.filter<Game> { it.title.contains(query) }
        }
    }

    suspend fun setAge(age:Int):Boolean{
        this.age = age
        return age in 3..100
    }
}
