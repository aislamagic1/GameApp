package com.example.rmaproject

class GameData {
    companion object {
        fun getAll(): List<Game>{
            return listOf(Game("Hollow Knight", "PC/PlayStation/XBox/Nintendo Switch", "24.2.2017",
            87.0, "hollow_knight", "E10+", "Team Cherry", "Team Cherry",
            "metroidvania", "Forge your own path in Hollow Knight! An epic action adventure through a vast ruined kingdom of insects and heroes. Explore twisting caverns, battle tainted creatures and befriend bizarre bugs, all in a classic, hand-drawn 2D style."
            , listOf(UserRating("Player", 1, 5.0))))
        }
        fun getDetails(title: String): Game{
            val games: ArrayList<Game> = arrayListOf()
            games.addAll(getAll())
            val game = games.find { game -> title == game.title }
            return game?:Game("Test", "Test", "Test", 0.0, "Test", "Test",
                "Test", "Test", "Test", "Test", listOf())
        }
    }
}