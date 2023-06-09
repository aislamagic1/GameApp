package com.example.projekat

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.R
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import com.bumptech.glide.Glide
import com.example.projekat.GameData.Companion.getAll
import com.example.projekat.data.repositories.GamesRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameDetailsFragment : Fragment() {

    private lateinit var game: Game
    private lateinit var title: TextView
    private lateinit var coverImage: ImageView
    private lateinit var platform: TextView
    private lateinit var releaseDate: TextView
    private lateinit var esrbRating: TextView
    private lateinit var developer: TextView
    private lateinit var publisher: TextView
    private lateinit var genre: TextView
    private lateinit var description: TextView
    private lateinit var addGame: FloatingActionButton
    private lateinit var deletGame: FloatingActionButton

    private lateinit var users: RecyclerView
    private lateinit var gameReviewRatingAdapter: GameReviewRatingAdapter
    private lateinit var userList: List<UserImpression>


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)

        title = view.findViewById(R.id.item_title_textview)
        coverImage = view.findViewById(R.id.cover_imageview)
        platform = view.findViewById(R.id.platform_textview)
        releaseDate = view.findViewById(R.id.release_date_textview)
        esrbRating = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        genre = view.findViewById(R.id.genre_textview)
        description = view.findViewById(R.id.description_textview)
        addGame = view.findViewById(R.id.add_game)
        deletGame = view.findViewById(R.id.delete_game)

        if(arguments?.get("id") != null) {
//            game = GameData.getDetails(arguments?.get("id") as Int)
//            if(game.title != "Test"){
//                userList = game.userImpressions
//                populateDetails()
//            }
//            else{
                getGameDetails(arguments?.get("id") as Int)
//            }
        }else{
            game = getAll().first()
            userList = game.userImpressions
        }

        addGame.setOnClickListener {
            AddGame()
        }

        deletGame.setOnClickListener {
            DeleteGame()
        }

//        val sortedList = userList.sortedBy { it.timestamp }.reversed()
//        users = view.findViewById(R.id.user_list)
//        users.setHasFixedSize(true)
//        users.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        gameReviewRatingAdapter = GameReviewRatingAdapter(listOf())
//        users.adapter = gameReviewRatingAdapter
//        gameReviewRatingAdapter.updateUsers(sortedList)

        return view
    }

    private fun AddGame(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        var result: Game
        scope.launch {
            result = AccountGamesRepository.saveGame(game)
            val toast = Toast.makeText(context, "Game Added", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun DeleteGame(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        var result: Boolean
        scope.launch {
            result = AccountGamesRepository.removeGame(game.id)
            if(result){
                val toast = Toast.makeText(context, "Game Removed", Toast.LENGTH_SHORT)
                toast.show()
            }else{
                val toast = Toast.makeText(context, "Game Not Removed", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }


    private fun populateDetails(){
        title.text = game.title
//        var context: Context = coverImage.getContext()
//      Glide.with(context)
//          .load("http://i.imgur.com/DvpvklR.png")
//          .centerCrop()
//          .placeholder(R.drawable.backdrop)
//          .error(R.drawable.ic_launcher_background)
//          .fallback(R.drawable.backdrop)
//          .into(coverImage)
        Glide.with(coverImage.getContext()).load("http:" + game.coverImage).placeholder(R.drawable.backdrop).dontAnimate().into(coverImage);
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esrbRating.text = game.esrbRating
        description.text = game.developer
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description
    }

    fun getGameDetails(id: Int){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        var result: Game
        scope.launch {
            result = GamesRepository.getGameById(Game(id,"","","",0.0,"","","","","","",listOf<UserImpression>())).first()

            game = result
            //AccountGamesRepository.saveGame(game)
            //AccountGamesRepository.removeGame(game.idOfGame!!)
            populateDetails()
        }
    }


}