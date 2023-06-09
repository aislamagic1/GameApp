package com.example.projekat.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object IGDBApiConfig {
    val retrofit : Api = Retrofit.Builder().baseUrl("https://api.igdb.com/v4/").addConverterFactory(
        GsonConverterFactory.create()).build().create(Api::class.java)
}