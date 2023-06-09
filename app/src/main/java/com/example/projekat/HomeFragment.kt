package com.example.projekat

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.R
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import com.example.projekat.GameData.Companion.getAll
import com.example.projekat.data.repositories.GamesRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private  var gamesList = getAll()

    private lateinit var search: EditText
    private lateinit var searchButton: Button
    private lateinit var sortGames: Button
    private lateinit var getSavedGames: Button

    private lateinit var bottomNavView: BottomNavigationView
    private var  isClicked: Boolean = false

    private var isLandscape: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_activity, container, false)
        games = view.findViewById(R.id.game_list)
        games.setHasFixedSize(true)
        games.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        search = view.findViewById(R.id.search_query_edittext)
        sortGames = view.findViewById(R.id.sort_games)
        getSavedGames = view.findViewById(R.id.saved_games)
        arguments?.getString("search")?.let {
            search.setText(it)
        }
        searchButton = view.findViewById(R.id.search_button)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) isLandscape = true
        gamesAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        games.adapter = gamesAdapter
        //gamesAdapter.updateGames(gamesList)

        if(!isLandscape) {
            bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
            bottomNavView.menu.findItem(R.id.homeItem).isEnabled = isClicked
            bottomNavView.menu.findItem(R.id.gameDetailsItem).isEnabled = isClicked
        }
        searchButton.setOnClickListener{
            getGames()
        }
        sortGames.setOnClickListener{
            sortGames()
        }
        getSavedGames.setOnClickListener {
            getsaved()
        }

        return view
    }

    private fun showGameDetails(game: Game){
        val bundle = Bundle()
        bundle.putInt("id", game.id)
        if(!isLandscape) {
            isClicked = true
            bottomNavView.menu.findItem(R.id.homeItem).isEnabled = isClicked
            bottomNavView.menu.findItem(R.id.gameDetailsItem).isEnabled = isClicked
            bottomNavView.selectedItemId = R.id.gameDetailsItem
            val navController = view?.let { Navigation.findNavController(it) }
            navController?.navigate(R.id.gameDetailsItem, bundle)
        }else if(isLandscape){
            val fragment = GameDetailsFragment()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_right, fragment)?.commit()
        }

    }

    fun getsaved(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        var result: List<Game>
        scope.launch {
            result = AccountGamesRepository.getSavedGames()
            if(result.isNotEmpty()){
                onSuccess(result)
            }else{
                onError()
            }
        }
    }
    fun sortGames(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        var result: List<Game>
        scope.launch {
            result = GamesRepository.sortGames()

            if(result.isNotEmpty()){
                onSuccess(result)
            }else{
                onError()
            }
        }

    }

    @SuppressLint("SuspiciousIndentation")
    fun getGames(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        var result: List<Game>
        scope.launch {
            if(AccountGamesRepository.age >= 18)
            result = GamesRepository.getGamesByName(search.text.toString())
            else result = GamesRepository.getGamesSafe(search.text.toString())
            when (result) {
                is List<Game> -> onSuccess(result)
                else -> onError()
            }
        }
    }

    fun onSuccess(games:List<Game>){
        val toast = Toast.makeText(context, "Games found", Toast.LENGTH_SHORT)
        toast.show()
        gamesAdapter.updateGames(games)
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }



}
