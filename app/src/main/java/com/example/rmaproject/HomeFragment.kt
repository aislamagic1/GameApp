package com.example.rmaproject

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rmaproject.GameData.Companion.getAll
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var games: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private  var gamesList = getAll()

    private lateinit var bottomNavView: BottomNavigationView
    private var  isClicked: Boolean = false

    private var isLandscape: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_activity, container, false)
        games = view.findViewById(R.id.game_list)
        games.setHasFixedSize(true)
        games.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) isLandscape = true
        gamesAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        games.adapter = gamesAdapter
        gamesAdapter.updateGames(gamesList)

        if(!isLandscape) {
            bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
            bottomNavView.menu.findItem(R.id.homeItem).isEnabled = isClicked
            bottomNavView.menu.findItem(R.id.gameDetailsItem).isEnabled = isClicked
        }

        return view
    }

    private fun showGameDetails(game: Game){
        val bundle = Bundle()
        bundle.putString("game_title_textview", game.title)
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

}
