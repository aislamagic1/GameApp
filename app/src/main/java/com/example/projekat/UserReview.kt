package com.example.projekat

data class UserReview(
    override val userName: String,
    override val timestamp: String,
    val review: String
    ):UserImpression(userName, timestamp)
