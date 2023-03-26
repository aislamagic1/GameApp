package com.example.rmaproject

data class UserRating(
    override val userName: String,
    override val timestamp: Long,
    val rating: Double
) : UserImpression(userName, timestamp)