package com.example.projekat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.R

class GameListAdapter(
    private var games: List<Game>,
    private val onItemClicked: (game: Game) -> Unit
): RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.gameTitle.text = games[position].title
        holder.gameRating.text = games[position].rating.toString()
        holder.releaseDate.text = games[position].releaseDate
        holder.gamePlatform.text = games[position].platform
        holder.itemView.setOnClickListener{onItemClicked(games[position])}
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateGames(newGames: List<Game>){
        this.games = newGames
        notifyDataSetChanged()
    }

    fun getGames(): List<Game>{
        return this.games
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val gameTitle: TextView = itemView.findViewById(R.id.item_title_textview)
        val gameRating: TextView = itemView.findViewById(R.id.game_rating_textview)
        val releaseDate: TextView = itemView.findViewById(R.id.release_date)
        val gamePlatform: TextView = itemView.findViewById(R.id.game_platform_textview)
    }
}