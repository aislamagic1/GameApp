package com.example.rmaproject

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rmaproject.GameData.Companion.getAll
import com.example.rmaproject.GameData.Companion.getDetails
import org.w3c.dom.Text

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

    private lateinit var users: RecyclerView
    private lateinit var gameReviewRatingAdapter: GameReviewRatingAdapter
    private lateinit var userList: List<UserImpression>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        title = findViewById(R.id.game_title_textview)
        coverImage = findViewById(R.id.cover_imageview)
        platform = findViewById(R.id.platform_textview)
        releaseDate = findViewById(R.id.release_date_textview)
        esrbRating = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        publisher = findViewById(R.id.publisher_textview)
        genre = findViewById(R.id.genre_textview)
        description = findViewById(R.id.description_textview)
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
        users.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        gameReviewRatingAdapter = GameReviewRatingAdapter(listOf())
        users.adapter = gameReviewRatingAdapter
        gameReviewRatingAdapter.updateUsers(sortedList)

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
        platform.text = game.platform
        releaseDate.text = game.releaseDate
        esrbRating.text = game.esrbRating
        description.text = game.developer
        developer.text = game.developer
        publisher.text = game.publisher
        genre.text = game.genre
        description.text = game.description
    }

    private fun homeButton(){
        val homeIntent = Intent(this, HomeActivity::class.java)
        try {
            homeIntent.putExtra("gameTitle", game.title)
            startActivity(homeIntent)
        }catch(e: ActivityNotFoundException){
            finish()
        }
    }

}