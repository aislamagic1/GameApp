package com.example.projekat.data.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AccountApiConfig {
    val retrofit : Api = Retrofit.Builder().baseUrl("https://rma23ws.onrender.com/").addConverterFactory(
        GsonConverterFactory.create()).build().create(Api::class.java)
}