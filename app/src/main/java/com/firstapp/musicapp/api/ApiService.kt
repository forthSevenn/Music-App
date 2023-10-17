package com.firstapp.musicapp.api

import com.firstapp.musicapp.model.MusicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    fun getMusic(
        @Header("X-RapidAPI-Key") key: String,
        @Header("X-RapidAPI-Host") host: String,
        @Query("q") query: String,
    ): Call<MusicResponse>

}