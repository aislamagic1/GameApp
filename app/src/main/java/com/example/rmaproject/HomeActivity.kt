package com.example.rmaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rmaproject.GameData.Companion.getAll

class HomeActivity : AppCompatActivity() {

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
    }

    private fun showGameDetails(game: Game){
        val intent = Intent(this, GameDetailsActivity::class.java).apply {
            putExtra("game_title_textview", game.title)
        }
        startActivity(intent)
    }
}