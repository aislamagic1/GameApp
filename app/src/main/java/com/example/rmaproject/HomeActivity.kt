package com.example.rmaproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rmaproject.GameData.Companion.getAll

class HomeActivity : AppCompatActivity() {

    private lateinit var detailsButton: Button
    private lateinit var logoImage: ImageView
    private lateinit var homeButton: Button

    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private  var gamesList = getAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        games = findViewById(R.id.game_list)
        games.setHasFixedSize(true)
        games.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gamesAdapter = GameListAdapter(arrayListOf()) {game -> showGameDetails(game) }
        games.adapter = gamesAdapter
        gamesAdapter.updateGames(gamesList)

        detailsButton = findViewById(R.id.details_button)
        detailsButton.setOnClickListener(){
            val gameTitle = intent.getStringExtra("gameTitle")
            val findGame = gamesList.find() {game -> gameTitle.equals(game.title)}
            if(findGame != null) showGameDetails(findGame)
            else{
                detailsButton.isEnabled = false
                detailsButton.isClickable = false
            }
        }

        homeButton = findViewById(R.id.home_button)
        homeButton.isEnabled = false
        homeButton.isClickable = false

        logoImage = findViewById(R.id.logo_image)
        val context: Context = logoImage.context
        val id: Int = context.resources.getIdentifier("game_logo", "drawable", context.packageName)
        logoImage.setImageResource(id)
    }

    private fun showGameDetails(game: Game){
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game_title_textview", game.title)
        }
        startActivity(intent)
    }

}