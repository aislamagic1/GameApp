package com.example.rmaproject

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
        games.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        gamesAdapter = GameListAdapter(listOf())
        games.adapter = gamesAdapter
        gamesAdapter.updateGames(gamesList)
    }
}