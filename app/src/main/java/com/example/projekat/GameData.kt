package com.example.projekat

class GameData {
    companion object {
        fun getAll(): List<Game>{
            return listOf(Game(1,"Hollow Knight", "PC/PlayStation/XBox/Nintendo Switch", "24.2.2017",
            90.0, "hollow_knight", "E10+", "Team Cherry", "Team Cherry",
            "Metroidvania", "Forge your own path in Hollow Knight! An epic action adventure through a vast ruined kingdom of insects and heroes. Explore twisting caverns, battle tainted creatures and befriend bizarre bugs, all in a classic, hand-drawn 2D style."
            , listOf(UserRating("mossbag", "1", 5.0), UserReview("IndigoWendigo", "1", "Full of bugs. 10/10"), UserRating("GreySteel", "1", 4.0),
                UserReview("I_Am_Maty", "1", "It is ok no other game made me rage quit in the first 40 minutes." +
                        "But it is a actually good game."), UserReview("Aza", "1", "What can I say that has not already been said about this Overwhelmingly Positive experience. Just get the game already.")), gamePost = GamePost(0, "")
            )
            , Game(1,"Apex Legends", "PC/PlayStation/XBox/Nintendo Switch", "5.11.2020", 88.0, "apex_legends", "T", "Respawn Entertainment",
                "EA Games", "Action", "Apex Legends is the award-winning, free-to-play Hero Shooter from Respawn Entertainment. Master an ever-growing roster of legendary characters with powerful abilities, and experience strategic squad play and innovative gameplay in the next evolution of Hero Shooter and Battle Royale.",
                listOf(UserRating("Hekki", "1", 4.0), UserReview("Fleetfoot Hammerci", "1", "At least you don't have to build an apartment complex to win in Apex."), UserReview("Nebula", "1", "My biggest mistake in life was installing this video game"),
                UserRating("Aldin", "1", 4.5), UserReview("Beetle", "1", "Tap-strafe your way into certain death as 60% of this player base relying on a cheeky little feature called aim assist.")), gamePost = GamePost(0, "")),
                Game(1,"Elden Ring", "PC/PlayStation/XBox", "25.2.2022", 96.0, "elden_ring", "M+17",
                    "FromSoftware Inc.", "FromSoftware Inc.", "Souls-like", "THE NEW FANTASY ACTION RPG. Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between.",
                    listOf(), gamePost =GamePost(0, "")), Game(1,"Dead Cells", "PC/PlayStation/XBox/Nintendo Switch", "6.8.2018", 89.0, "dead_cells",
                    "T", "Motion Twin", "Motion Twin", "Action Roguelike", "Dead Cells is a rogue-lite, metroidvania inspired, action-platformer. You'll explore a sprawling, ever-changing castle... assuming you’re able to fight your way past its keepers in 2D souls-lite combat. No checkpoints. Kill, die, learn, repeat. Regular free content updates!",
                    listOf(), gamePost = GamePost(0, "")), Game(1,"Red Dead Redemption 2", "PC/PlayStation/XBox", "5.12.2019", 97.0, "red_dead_2", "M17+",
                    "Rockstar Games", "Rockstar Games", "Open World", "Winner of over 175 Game of the Year Awards and recipient of over 250 perfect scores, RDR2 is the epic tale of outlaw Arthur Morgan and the infamous Van der Linde gang, on the run across America at the dawn of the modern age. Also includes access to the shared living world of Red Dead Online.",
                    listOf(), gamePost = GamePost(0, "")), Game(1,"Marvel’s Spider-Man Remastered", "PlayStation/Pc", "12.8.2022", 87.0, "spider_man", "T",
                    "Insomniac Games", "PlayStation", "Open World", "In Marvel’s Spider-Man Remastered, the worlds of Peter Parker and Spider-Man collide in an original action-packed story. Play as an experienced Peter Parker, fighting big crime and iconic villains in Marvel’s New York.",
                    listOf(), gamePost = GamePost(0, "")), Game(1,"STAR WARS Jedi: Fallen Order", "PC/PlayStation/XBox", "15.11.2019", 79.0, "star_wars", "T",
                    "Respawn Entertainment", "Electronic Arts", "Action-Adventure", "A galaxy-spanning adventure awaits in Star Wars Jedi: Fallen Order, a 3rd person action-adventure title from Respawn. An abandoned Padawan must complete his training, develop new powerful Force abilities, and master the art of the lightsaber - all while staying one step ahead of the Empire.",
                    listOf(), gamePost = GamePost(0, "")), Game(1,"Portal 2", "PC", "19.4.2011", 95.0, "portal_2", "E+10",
                    "Valve", "Valve", "Puzzle", "The \"Perpetual Testing Initiative\" has been expanded to allow you to design co-op puzzles for you and your friends!",
                    listOf(), gamePost = GamePost(0, "")), Game(1,"Dark Souls III", "PC/PlayStation/XBox", "11.4.2016", 89.0, "dark_souls_3", "M+17","FromSoftware Inc.",
                    "FromSoftware Inc.", "Souls-like", "Dark Souls continues to push the boundaries with the latest, ambitious chapter in the critically-acclaimed and genre-defining series. Prepare yourself and Embrace The Darkness!",
                    listOf(), gamePost = GamePost(0, "")), Game(1,"Forza Horizon 5", "XBox/Pc", "9.11.2021", 92.0, "forza_5", "E", "Playground Games",
                    "XBox Game Studio", "Racing", "Your Ultimate Horizon Adventure awaits! Explore the vibrant open world landscapes of Mexico with limitless, fun driving action in the world’s greatest cars. Conquer the rugged Sierra Nueva in the ultimate Horizon Rally experience. Requires Forza Horizon 5 game, expansion sold separately.",
                    listOf(), gamePost = GamePost(0, "")))
        }
        fun getDetails(title: String): Game{
            val games: ArrayList<Game> = arrayListOf()
            games.addAll(getAll())
            val game = games.find { game -> title == game.title }
            return game?:Game(1,"Test", "Test", "Test", 0.0, "Test", "Test",
                "Test", "Test", "Test", "Test", listOf(), gamePost = GamePost(0, ""))
        }
    }
}