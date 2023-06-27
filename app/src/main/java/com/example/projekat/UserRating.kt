package com.example.projekat

data class UserRating(
    override val userName: String,
    override val timestamp: String,
    val rating: Double
) : UserImpression(userName, timestamp)