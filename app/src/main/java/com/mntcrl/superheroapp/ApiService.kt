package com.mntcrl.superheroapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/122110206434146106/search/{name}")
    suspend fun getSuperheros(@Path("name") superheroName : String) : Response<SuperHeroDataResponse>

    @GET("/api/122110206434146106/{id}")
    suspend fun getSuperheroDetail(@Path("id") superheroId : String) : Response<SuperHeroDetailResponse>
}