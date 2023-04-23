package com.example.rmaproject

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rmaproject.GameData.Companion.getAll
import com.google.android.material.bottomnavigation.BottomNavigationView

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

    private lateinit var users: RecyclerView
    private lateinit var gameReviewRatingAdapter: GameReviewRatingAdapter
    private lateinit var userList: List<UserImpression>



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

        if(arguments?.get("game_title_textview") != null) {
            game = GameData.getDetails(arguments?.get("game_title_textview") as String)
            userList = game.userImpressions
        }else{
            game = getAll().first()
            userList = game.userImpressions
        }
        populateDetails()

        val sortedList = userList.sortedBy { it.timestamp }.reversed()
        users = view.findViewById(R.id.user_list)
        users.setHasFixedSize(true)
        users.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        gameReviewRatingAdapter = GameReviewRatingAdapter(listOf())
        users.adapter = gameReviewRatingAdapter
        gameReviewRatingAdapter.updateUsers(sortedList)

        return view
    }


    private fun populateDetails(){
        title.text = game.title
        val context: Context = coverImage.context
        var id: Int =context.resources.getIdentifier(game.coverImage, "drawable", context.packageName)
        if(id == 0) id = context.resources.getIdentifier("picture1", "drawable", context.packageName)
        coverImage.setImageResource(id)
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esrbRating.text = game.esrbRating
        description.text = game.developer
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description
    }

}