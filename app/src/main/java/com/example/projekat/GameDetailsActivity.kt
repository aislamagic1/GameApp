package com.example.projekat

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.R
import com.example.projekat.GameData.Companion.getDetails
import ba.etf.rma23.projekat.MainActivity

class GameDetailsActivity : AppCompatActivity() {
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
    private lateinit var homeButton : Button
    private lateinit var logoImage : ImageView
    private lateinit var detailsButton: Button

    private lateinit var users: RecyclerView
    private lateinit var gameReviewRatingAdapter: GameReviewRatingAdapter
    private lateinit var userList: List<UserImpression>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        title = findViewById(R.id.item_title_textview)
        coverImage = findViewById(R.id.cover_imageview)
        platform = findViewById(R.id.platform_textview)
        releaseDate = findViewById(R.id.release_date_textview)
        esrbRating = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre = findViewById(R.id.genre_textview)
        description = findViewById(R.id.description_textview)
        logoImage = findViewById(R.id.logo_image)
        val extras =intent.extras
        if(extras != null){
            game = getDetails(extras.getString("game_title_textview", ""))
            userList = game.userImpressions
            populateDetails()
        }else{
            finish()
        }
        val sortedList = userList.sortedBy { it.timestamp }.reversed()
        users = findViewById(R.id.user_list)
        users.setHasFixedSize(true)
        users.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gameReviewRatingAdapter = GameReviewRatingAdapter(listOf())
        users.adapter = gameReviewRatingAdapter
        gameReviewRatingAdapter.updateUsers(sortedList)

        detailsButton = findViewById(R.id.details_button)
        detailsButton.isEnabled = false
        detailsButton.isClickable = false
        detailsButton.alpha = 0.5f
        homeButton = findViewById(R.id.home_button)
        homeButton.setOnClickListener(){
            homeButton()
        }
    }

    private fun populateDetails(){
        title.text = game.title
        val context: Context = coverImage.context
        var id: Int =context.resources.getIdentifier(game.coverImage, "drawable", context.packageName)
        if(id == 0) id = context.resources.getIdentifier("picture1", "drawable", context.packageName)
        coverImage.setImageResource(id)
        val context2: Context = logoImage.context
        id = context2.resources.getIdentifier("game_logo", "drawable", context2.packageName)
        logoImage.setImageResource(id)
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esrbRating.text = game.esrbRating
        description.text = game.developer
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description
    }

    override fun onBackPressed() {
        val homeIntent = Intent(this, MainActivity::class.java)
        try {
            homeIntent.putExtra("gameTitle", game.title)
            startActivity(homeIntent)
        }catch(e: ActivityNotFoundException){
            finish()
        }
    }

    private fun homeButton(){
        val homeIntent = Intent(this, MainActivity::class.java)
        try {
            homeIntent.putExtra("gameTitle", game.title)
            startActivity(homeIntent)
        }catch(e: ActivityNotFoundException){
            finish()
        }
    }

}